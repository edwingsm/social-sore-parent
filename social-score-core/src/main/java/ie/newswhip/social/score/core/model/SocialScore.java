package ie.newswhip.social.score.core.model;

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
