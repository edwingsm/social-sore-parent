package ie.newwhip.social.score.service.mongodb;

import ie.newwhip.social.score.service.mongodb.dbo.SocialScoreDBO;
import ie.newwhip.social.socre.client.score.core.SocialScoreReport;

import java.util.List;

public interface SocialScoreReportRepository {

    List<SocialScoreReport> aggregateSocialScoreByDomainName();

    SocialScoreDBO updateSocialScore(String searchKey, String searchKeyValue, long score);
}
