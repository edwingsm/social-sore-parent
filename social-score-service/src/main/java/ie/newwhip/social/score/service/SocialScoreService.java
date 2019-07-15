package ie.newwhip.social.score.service;

import ie.newwhip.social.score.exception.SocialScoreException;
import ie.newwhip.social.score.model.KeyName;
import ie.newwhip.social.score.model.SocialScore;
import ie.newwhip.social.score.model.SocialScoreReport;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface SocialScoreService {
    SocialScore save(SocialScore socialScoreRequest) throws SocialScoreException;

    void updateSocialScore(KeyName key , String value, long score) throws SocialScoreException;

    void deleteSocialScore(String key, KeyName keyType) throws SocialScoreException;

    List<SocialScore> searchSocialScores(KeyName key,String value);

    List<SocialScoreReport> generateReport();

}
