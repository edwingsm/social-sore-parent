package ie.newwhip.social.socre.client.cli;

import java.net.URL;
import lombok.Builder;
import lombok.SneakyThrows;
import org.springframework.util.StringUtils;

public class ModelBuilders {

  @Builder(builderMethodName = "buildCommand")
  @SneakyThrows
  public static Command buildCommandFromValidInput(String input) {
    final String[] temp = input.replaceAll("\\s{2,}", " ").trim().split(" ");
    if (temp.length > 3) {
      throw new RuntimeException("");
    }
    final Command command = new Command();
    command.setAction(ClientAction.valueOf(temp[0].toUpperCase()));
    if (temp.length == 3) {
      command.setScore(Long.parseLong(temp[2]));
      command.setUrl(new URL(temp[1]));
    }
    return command;
  }

  public static boolean isInValidInput(String input) {
    return StringUtils.isEmpty(input);
  }
}
