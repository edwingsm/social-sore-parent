package ie.newwhip.social.score.config;

import ie.newwhip.social.score.model.KeyName;
import org.springframework.core.convert.converter.Converter;

//@Component
public class SearchKeyEnumConverter implements Converter<String, KeyName> {
    @Override
    public KeyName convert(String value) {
        return KeyName.valueOf(value);
    }
}
