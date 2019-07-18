package ie.newwhip.social.socre.client.socre.client.utill;

import ie.newwhip.social.socre.client.socre.client.model.ClientAction;
import ie.newwhip.social.socre.client.socre.client.model.Command;
import java.net.URL;
import lombok.Builder;
import lombok.SneakyThrows;
import org.springframework.util.StringUtils;

public class Utility {

  @Builder(builderMethodName = "buildCommand")
  @SneakyThrows
  public static Command buildCommandFromInput(String input) {
    if(StringUtils.isEmpty(input.trim()))
      throw new RuntimeException("Command Execution Failed due to Invalid Command ");

    final String[] temp = input.replaceAll("\\s{2,}", " ").trim().split(" ");
    final int paramCount = temp.length;
    if  (paramCount ==2 || paramCount>3) {
      throw new RuntimeException("Command Execution Failed due to Invalid Command ");
    }
    final Command command = new Command();
    command.setAction(ClientAction.valueOf(temp[0].toUpperCase()));
    if (paramCount == 3) {
      command.setScore(Long.parseLong(temp[2]));
      command.setUrl(new URL(temp[1]));
    }
    return command;
  }

}
