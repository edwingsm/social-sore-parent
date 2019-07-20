package ie.newswhip.social.score.service.controller;

import ie.newswhip.social.score.core.model.SearchKey;
import java.beans.PropertyEditorSupport;

/**
 * Mapper for mapping string to enum in Controller
 */
public class SearchKeyEnumEditor extends PropertyEditorSupport {

  public void setAsText(String text) throws IllegalArgumentException {
    try {
      setValue(SearchKey.valueOf(text.toUpperCase()));
    } catch (Exception ex) {
      throw new IllegalArgumentException(ex.getMessage(), ex.getCause());
    }
  }
}
