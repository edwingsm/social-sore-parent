package ie.newwhip.social.socre.client.score.core;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SocialScoreUpdate implements Serializable {
    private SearchKey keyName;
    private String keyValue;
    private long updatedScore;
}
