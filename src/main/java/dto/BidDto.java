package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BidDto {
    private Long id;
    private Long postId;
    private Long bidder;
    private boolean isAccepted;
}
