package ie.newswhip.social.sore.client;

import ie.newswhip.social.sore.client.cli.CliExecutorImpl;
import ie.newswhip.social.sore.client.cli.console.CommandReader;
import ie.newswhip.social.sore.client.utill.Utility;
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

  @Value("${app.single.exec:true}")
  private boolean keepAppRunning ;

  @Autowired private CommandReader scanner;

  @Autowired private CliExecutorImpl cliExecutor;


  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(SocialScoreCliApplication.class);
    app.setWebApplicationType(WebApplicationType.NONE);
    app.run(args);
  }

  public void run(String... args) {
    do {
      try {
        System.out.print("Enter Command: ");
        String input = scanner.readCommand();
        cliExecutor.executeCommand(Utility.buildCommand().input(input).build());
      } catch (Exception e) {
        log.error("Command execution failed");
      }
    } while (keepAppRunning);
  }
}
