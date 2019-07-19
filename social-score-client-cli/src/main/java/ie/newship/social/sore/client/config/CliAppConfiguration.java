package ie.newship.social.sore.client.config;

import ie.newship.social.sore.client.cli.console.ConsoleReader;
import ie.newship.social.sore.client.cli.console.ScannerConsoleReader;
import ie.newship.social.sore.client.cli.listener.DefaultSocialScoreCliExecutorListener;
import ie.newship.social.sore.client.cli.listener.SocialScoreCliExecutorListener;
import ie.newship.social.sore.client.exception.SocialScoreApiErrorHandler;
import java.time.Duration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CliAppConfiguration {

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder) {
    return builder
        .setConnectTimeout(Duration.ofMillis(3000))
        .setReadTimeout(Duration.ofMillis(3000))
        .errorHandler(new SocialScoreApiErrorHandler())
        .build();
  }

  @Bean
  public ConsoleReader scanner() {
    return new ScannerConsoleReader();
  }

  @Bean
  public SocialScoreCliExecutorListener executorListener(){
    return new DefaultSocialScoreCliExecutorListener();
  }

}
