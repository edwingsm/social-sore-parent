package ie.newswhip.social.score.core.model;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SocialScoreUpdate implements Serializable {
    private SearchKey key;
    private String value;
    private long updatedScore;
}
