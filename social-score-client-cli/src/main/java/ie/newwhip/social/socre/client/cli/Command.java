package ie.newwhip.social.socre.client.cli;

import java.net.URL;
import lombok.Data;

@Data
public class Command {
  private ClientAction action;
  private URL url;
  private long score;
}
