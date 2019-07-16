package ie.newwhip.social.socre.client.cli;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocialScore {
    private String id;
    private String url;
    private long score;

}
