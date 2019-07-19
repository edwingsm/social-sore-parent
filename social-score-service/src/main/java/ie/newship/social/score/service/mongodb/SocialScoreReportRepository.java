package ie.newship.social.score.service.mongodb;

import ie.newship.social.score.service.mongodb.dbo.SocialScoreDBO;
import ie.newship.social.score.core.SocialScoreReport;

import java.util.List;

public interface SocialScoreReportRepository {

    List<SocialScoreReport> aggregateSocialScoreByDomainName();

    SocialScoreDBO updateSocialScore(String searchKey, String searchKeyValue, long score);
}
