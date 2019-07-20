package ie.newswhip.social.score.core.api;

import ie.newswhip.social.score.core.model.SearchKey;
import ie.newswhip.social.score.core.model.SocialScore;
import ie.newswhip.social.score.core.model.SocialScoreUpdate;

public interface SocialScoreApi<S> {
    S save(SocialScore socialScore);
    S update(SocialScoreUpdate socialScoreUpdate);
    S delete(SearchKey key, String value);
}
