package forum.api.domain.topic;

import jakarta.validation.constraints.NotBlank;

public record TopicUpdateData(
        Long id,
        @NotBlank
        String title,
        @NotBlank
        String message,
        @NotBlank
        String author,
        @NotBlank
        String course) {
}
