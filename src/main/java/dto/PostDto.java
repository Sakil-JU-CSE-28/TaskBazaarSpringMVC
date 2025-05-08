package dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Data
@Getter
@Setter
@NoArgsConstructor
@Component
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private String author;
}