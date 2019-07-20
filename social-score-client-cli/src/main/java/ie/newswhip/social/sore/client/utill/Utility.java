package ie.newswhip.social.sore.client.utill;

import ie.newswhip.social.sore.client.model.ClientAction;
import ie.newswhip.social.sore.client.model.Command;
import java.net.URL;
import lombok.Builder;
import lombok.SneakyThrows;
import org.springframework.util.StringUtils;

public class Utility {

  @Builder(builderMethodName = "buildCommand")
  @SneakyThrows
  public static Command buildCommandFromInput(String input) {
    if(StringUtils.isEmpty(input.trim()))
      throw new IllegalArgumentException("Invalid Command :Empty command");

    final String[] temp = input.replaceAll("\\s{2,}", " ").trim().split(" ");
    final int paramCount = temp.length;
    if  (paramCount ==2 || paramCount>3) {
      throw new IllegalArgumentException("Invalid Command");
    }
    final Command command = new Command();
    command.setAction(ClientAction.valueOf(temp[0].toUpperCase()));
    if (paramCount == 3) {
      command.setScore(Long.parseLong(temp[2]));
      command.setValue(new URL(temp[1]).toString());
    }
    return command;
  }

}
