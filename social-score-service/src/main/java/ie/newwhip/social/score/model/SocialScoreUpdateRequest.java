package ie.newwhip.social.score.model;

import lombok.Data;

import java.io.Serializable;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SocialScoreUpdateRequest implements Serializable {
    private KeyName keyName;
    private String keyValue;
    private long updatedScore;
}
