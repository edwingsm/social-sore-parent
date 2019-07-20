package ie.newswhip.social.score.service.service;

import ie.newswhip.social.score.service.exception.SocialScoreException;
import ie.newswhip.social.score.core.model.SearchKey;
import ie.newswhip.social.score.core.model.SocialScore;
import ie.newswhip.social.score.core.model.SocialScoreReport;

import java.util.List;


public interface SocialScoreService {
    SocialScore save(SocialScore socialScoreRequest) throws SocialScoreException;

    void updateSocialScore(SearchKey key , String value, long score) throws SocialScoreException;

    void deleteSocialScore(String key, SearchKey keyType) throws SocialScoreException;

    List<SocialScore> searchSocialScores(SearchKey key,String value);

    List<SocialScoreReport> generateReport();

}
