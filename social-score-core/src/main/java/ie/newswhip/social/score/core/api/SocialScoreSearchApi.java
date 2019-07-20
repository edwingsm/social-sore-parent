package ie.newswhip.social.score.core.api;

import ie.newswhip.social.score.core.model.SearchKey;

public interface SocialScoreSearchApi<R> {
    R search(SearchKey key, String value);
    R report();
}
