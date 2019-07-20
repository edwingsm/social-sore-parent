package ie.newswhip.social.score.service.mongodb;

import ie.newswhip.social.score.service.mongodb.dbo.SocialScoreDBO;
import ie.newswhip.social.score.core.model.SocialScoreReport;

import java.util.List;

public interface SocialScoreReportRepository {

    List<SocialScoreReport> aggregateSocialScoreByDomainName();

    SocialScoreDBO updateSocialScore(String searchKey, String searchKeyValue, long score);
}
