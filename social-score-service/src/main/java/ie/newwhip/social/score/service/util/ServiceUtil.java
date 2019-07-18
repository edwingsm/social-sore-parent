package ie.newwhip.social.score.service.util;

import ie.newwhip.social.socre.client.score.core.SocialScore;
import ie.newwhip.social.score.service.mongodb.dbo.SocialScoreDBO;
import lombok.Builder;

public class ServiceUtil {

  @Builder(builderMethodName = "builder")
  public static SocialScore buildSocialScore(SocialScoreDBO socialScoreDBO) {
    return new SocialScore(socialScoreDBO.getId(),socialScoreDBO.getUrl(),socialScoreDBO.getScore() );
  }

}
