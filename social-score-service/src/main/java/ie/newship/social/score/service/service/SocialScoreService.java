package ie.newship.social.score.service.service;

import ie.newship.social.score.service.exception.SocialScoreException;
import ie.newship.social.score.core.SearchKey;
import ie.newship.social.score.core.SocialScore;
import ie.newship.social.score.core.SocialScoreReport;

import java.util.List;


public interface SocialScoreService {
    SocialScore save(SocialScore socialScoreRequest) throws SocialScoreException;

    void updateSocialScore(SearchKey key , String value, long score) throws SocialScoreException;

    void deleteSocialScore(String key, SearchKey keyType) throws SocialScoreException;

    List<SocialScore> searchSocialScores(SearchKey key,String value);

    List<SocialScoreReport> generateReport();

}
