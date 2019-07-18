package ie.newwhip.social.score.service.config;

import ie.newwhip.social.socre.client.score.core.SearchKey;
import org.springframework.core.convert.converter.Converter;

//@Component
public class SearchKeyEnumConverter implements Converter<String, SearchKey> {
    @Override
    public SearchKey convert(String value) {
        return SearchKey.valueOf(value);
    }
}
