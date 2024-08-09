package forum.api.domain.topic;

import java.time.LocalDateTime;

public record TopicListData(Long id,
                            String title,
                            String message,
                            LocalDateTime creationDate,
                            TopicStatus topicStatus,
                            String author,
                            String course) {

    public TopicListData(Topic topic){
        this(topic.getId(), topic.getTitle(), topic.getMessage(), topic.getCreationDate(),
                topic.getTopicStatus(), topic.getAuthor(), topic.getCourse());
    }
}
