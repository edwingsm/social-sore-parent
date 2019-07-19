package ie.newship.social.score.service.util;

import ie.newship.social.score.core.SocialScore;
import ie.newship.social.score.service.mongodb.dbo.SocialScoreDBO;
import lombok.Builder;

public class ServiceUtil {

  @Builder(builderMethodName = "builder")
  public static SocialScore buildSocialScore(SocialScoreDBO socialScoreDBO) {
    return new SocialScore(socialScoreDBO.getId(),socialScoreDBO.getUrl(),socialScoreDBO.getScore() );
  }

}
