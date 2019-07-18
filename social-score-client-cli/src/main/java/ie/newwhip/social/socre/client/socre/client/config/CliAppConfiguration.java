package ie.newwhip.social.socre.client.socre.client.config;

import ie.newwhip.social.socre.client.socre.client.cli.console.ConsoleReader;
import ie.newwhip.social.socre.client.socre.client.cli.console.ScannerConsoleReader;
import ie.newwhip.social.socre.client.socre.client.cli.listener.DefaultSocialScoreCliExecutorListener;
import ie.newwhip.social.socre.client.socre.client.cli.listener.SocialScoreCliExecutorListener;
import ie.newwhip.social.socre.client.socre.client.exception.SocialScoreApiErrorHandler;
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
