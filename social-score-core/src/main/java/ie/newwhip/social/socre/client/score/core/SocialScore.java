package ie.newwhip.social.socre.client.score.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocialScore {
    public String id;
    public String url;
    public long score;
}
