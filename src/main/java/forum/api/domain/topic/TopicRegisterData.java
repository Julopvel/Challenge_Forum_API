package forum.api.domain.topic;

import jakarta.validation.constraints.NotBlank;

public record TopicRegisterData(
        @NotBlank
        String title,
        @NotBlank
        String message,
        @NotBlank
        String author,
        @NotBlank
        String course
) {
}
