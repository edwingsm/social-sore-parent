package ie.newswhip.social.sore.client.model;

import ie.newswhip.social.score.core.model.SearchKey;
import lombok.Data;

@Data
public class Command {
  private ClientAction action;
  private String value;
  private SearchKey key;
  private long score;
}
