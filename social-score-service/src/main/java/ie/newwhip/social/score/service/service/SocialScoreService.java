package ie.newwhip.social.score.service.service;

import ie.newwhip.social.score.service.exception.SocialScoreException;
import ie.newwhip.social.socre.client.score.core.SearchKey;
import ie.newwhip.social.socre.client.score.core.SocialScore;
import ie.newwhip.social.socre.client.score.core.SocialScoreReport;

import java.util.List;


public interface SocialScoreService {
    SocialScore save(SocialScore socialScoreRequest) throws SocialScoreException;

    void updateSocialScore(SearchKey key , String value, long score) throws SocialScoreException;

    void deleteSocialScore(String key, SearchKey keyType) throws SocialScoreException;

    List<SocialScore> searchSocialScores(SearchKey key,String value);

    List<SocialScoreReport> generateReport();

}
