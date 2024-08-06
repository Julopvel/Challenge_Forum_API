package forum.api.domain.topic;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "topics")
@Entity(name = "Topic")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String message;
    private LocalDateTime creationDate;
    @Enumerated(EnumType.STRING)
    private TopicStatus topicStatus;
    private String author;
    private String course;
    private boolean active;


    public Topic(TopicRegisterData topicRegisterData) {
        this.title = topicRegisterData.title();
        this.message = topicRegisterData.message();
        this.creationDate = LocalDateTime.now();
        this.topicStatus = TopicStatus.UNANSWERED;
        this.author = topicRegisterData.author();
        this.course = topicRegisterData.course();
        this.active = true;
    }

    public void updateData(TopicUpdateData topicUpdateData) {
        if (this.title != null){
            this.title = topicUpdateData.title();
        }
        if (this.message != null){
            this.message = topicUpdateData.message();
        }
        if (this.author != null){
            this.author = topicUpdateData.author();
        }
        if (this.course != null){
            this.course = topicUpdateData.course();
        }
    }

    public void disableTopic() {
        this.active = false;
    }
}

