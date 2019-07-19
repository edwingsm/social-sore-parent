package ie.newship.social.score.service.controller;

import ie.newship.social.score.core.SearchKey;
import java.beans.PropertyEditorSupport;

public class SearchKeyEnumEditor extends PropertyEditorSupport {

  public void setAsText(String text) throws IllegalArgumentException {
    try {
      setValue(SearchKey.valueOf(text.toUpperCase()));
    } catch (Exception ex) {
      throw new IllegalArgumentException(ex.getMessage(), ex.getCause());
    }
  }
}
