package dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Getter
@Setter
@NoArgsConstructor
@Component
public class PostDto {
    private Long id;
    @NotBlank(message = "Post title is required")
    @Size(min = 1, message = "Post title must be at least 1 characters")
    @Size(max = 25, message = "Post title must be at most 25 characters")
    private String title;
    @NotBlank(message = "Post content is required")
    @Size(min = 1, message = "Post content must be at least 1 characters")
    @Size(max = 250, message = "Post content must be at most 250 characters")
    private String content;
    private String author;
}