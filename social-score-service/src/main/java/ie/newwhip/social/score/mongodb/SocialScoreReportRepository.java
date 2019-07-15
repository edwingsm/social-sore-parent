package ie.newwhip.social.score.mongodb;

import ie.newwhip.social.score.model.SocialScoreReport;

import ie.newwhip.social.score.mongodb.dbo.SocialScoreDBO;
import java.util.List;

public interface SocialScoreReportRepository {

    List<SocialScoreReport> aggregateSocialScoreByDomainName();

    SocialScoreDBO updateSocialScore(String searchKey, String searchKeyValue, long score);
}
