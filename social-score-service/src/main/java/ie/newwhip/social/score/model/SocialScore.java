package ie.newwhip.social.score.model;

import ie.newwhip.social.score.mongodb.dbo.SocialScoreDBO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocialScore {
    public String id;
    public String url;
    public long score;


    @Builder(builderMethodName = "builder")
    public static SocialScore buildSocialScore(SocialScoreDBO socialScoreDBO) {
        return new SocialScore(socialScoreDBO.getId(),socialScoreDBO.getUrl(),socialScoreDBO.getScore() );
    }
}
