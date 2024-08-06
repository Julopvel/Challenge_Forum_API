package forum.api.domain.topic;

import java.time.LocalDateTime;

public record TopicData(Long id,
                        String title,
                        String message,
                        LocalDateTime creationDate,
                        TopicStatus topicStatus,
                        String author,
                        String course) {
}
