package ie.newship.social.score.core;

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
