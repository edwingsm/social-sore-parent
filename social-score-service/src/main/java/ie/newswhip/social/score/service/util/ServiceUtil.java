package ie.newswhip.social.score.service.util;

import ie.newswhip.social.score.core.model.SocialScore;
import ie.newswhip.social.score.service.mongodb.dbo.SocialScoreDBO;
import lombok.Builder;

public class ServiceUtil {

  /**
   * A simple builder method
   * @param socialScoreDBO
   * @return
   */
  @Builder(builderMethodName = "builder")
  public static SocialScore buildSocialScore(SocialScoreDBO socialScoreDBO) {
    return new SocialScore(socialScoreDBO.getId(),socialScoreDBO.getUrl(),socialScoreDBO.getScore() );
  }

}
