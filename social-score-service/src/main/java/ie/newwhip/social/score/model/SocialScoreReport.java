package ie.newwhip.social.score.model;

import lombok.Data;

import java.io.Serializable;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SocialScoreReport implements Serializable {

    private String domain;
    private int urlCount;
    private long score;

}
