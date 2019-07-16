package ie.newwhip.social.socre.client.cli;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SocialScoreReport implements Serializable {

    private String domain;
    private int urlCount;
    private long score;

}
