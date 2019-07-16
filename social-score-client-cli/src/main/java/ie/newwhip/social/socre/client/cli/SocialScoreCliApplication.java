package ie.newwhip.social.socre.client.cli;

import java.net.URL;
import java.time.Duration;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@Slf4j
public class SocialScoreCliApplication implements CommandLineRunner {

  public static boolean keepAppRunning = true;

  @Autowired private CliService cliClientService;

  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(SocialScoreCliApplication.class);
    app.setBannerMode(Banner.Mode.OFF);
    app.setWebApplicationType(WebApplicationType.NONE);
    app.run(args);
  }

  public void run(String... args) throws Exception {

//    while (keepAppRunning) {
//      try {
//        Scanner scanner = new Scanner(System.in);
//        //  prompt for the user's name
//        System.out.print("Enter your name: ");
//        // get their input as a String
//        String username = scanner.nextLine();
//
//        if (!ModelBuilders.isInValidInput(username)) {
//          Command command = ModelBuilders.buildCommand().input(username).build();
//          cliClientService.executeAction(command);
//        } else {
//          handleInvalidInput();
//        }
//      } catch (Exception e) {
//        log.error("Command execution failed");
//      }
//    }
    Command command = new Command();
    command.setAction(ClientAction.EXPORT);
    cliClientService.executeAction(command);
    //----
//    command.setAction(ClientAction.REMOVE);
//    command.setUrl(new URL("http://www.google.com/search?query=somethingElse"));
//    cliClientService.executeAction(command);
//    command.setAction(ClientAction.EXPORT);
//    cliClientService.executeAction(command);
    //----
    command.setAction(ClientAction.ADD);
    command.setUrl(new URL("http://www.bing.com/search?query=somethingElse"));
    cliClientService.executeAction(command);
    command.setAction(ClientAction.EXPORT);
    cliClientService.executeAction(command);
  }

  public void handleInvalidInput() {
    log.error("handleInvalidInput");
  }

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder) {
    return builder
        .setConnectTimeout(Duration.ofMillis(3000))
        .setReadTimeout(Duration.ofMillis(3000))
        .build();
  }
}
