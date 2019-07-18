package ie.newwhip.social.socre.client.socre.client;

import ie.newwhip.social.socre.client.socre.client.cli.SocialScoreCliExecutor;
import ie.newwhip.social.socre.client.socre.client.cli.console.ConsoleReader;
import ie.newwhip.social.socre.client.socre.client.utill.Utility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class SocialScoreCliApplication implements CommandLineRunner {

  @Value("${app.keep.Running}")
  private boolean keepAppRunning = true;

  @Autowired private ConsoleReader scanner;

  @Autowired private SocialScoreCliExecutor socialScoreCliExecutor;


  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(SocialScoreCliApplication.class);
    app.setWebApplicationType(WebApplicationType.NONE);
    app.run(args);
  }

  public void run(String... args) {
    do {
      try {

        System.out.print("Enter Command: ");
        String input = scanner.readLine();
        socialScoreCliExecutor.executeCommand(Utility.buildCommand().input(input).build());
      } catch (Exception e) {
        log.error("Command execution failed");
      }
    } while (keepAppRunning);
  }
}
