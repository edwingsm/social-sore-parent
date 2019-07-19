package ie.newship.social.socre.core.cli;


import ie.newship.social.score.core.SocialScore;
import ie.newship.social.score.core.SocialScoreReport;
import ie.newship.social.sore.client.cli.SocialScoreCliExecutor;
import ie.newship.social.sore.client.cli.SocialScoreCliExecutorImpl;
import ie.newship.social.sore.client.cli.listener.SocialScoreCliExecutorListener;
import ie.newship.social.sore.client.model.ClientAction;
import ie.newship.social.sore.client.model.Command;
import java.net.URL;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
public class SocialScoreCliExecutorTest {

  @MockBean private RestTemplate restTemplate;

  @MockBean private SocialScoreCliExecutorListener executorListener;

  @Autowired private SocialScoreCliExecutor socialScoreCliExecutor;

  @SneakyThrows
  @Test
  public void testDelete() {
    Mockito.doNothing().when(restTemplate).delete(ArgumentMatchers.anyString());

    Command command = new Command();
    command.setAction(ClientAction.REMOVE);
    command.setUrl(new URL("http://www.google.com/search?query=Something"));
    socialScoreCliExecutor.executeCommand(command);
    Mockito.verify(executorListener, Mockito.times(1))
        .onSuccess(command, "SUCCESSFULLY REMOVED URL");
  }

  @SneakyThrows
  @Test
  public void testSave() {

    Command command = new Command();
    command.setAction(ClientAction.ADD);
    command.setUrl(new URL("http://www.google.com/search?query=Something"));
    command.setScore(10l);

    Mockito.when(restTemplate.postForEntity("http://localhost:8080/save",command,
        SocialScore.class)).thenReturn(null);


    socialScoreCliExecutor.executeCommand(command);
    Mockito.verify(executorListener, Mockito.times(1))
        .onSuccess(command, "SUCCESSFULLY ADDED URL");
  }

  @SneakyThrows
  @Test
  public void testGenerateReport() {

    Command command = new Command();
    command.setAction(ClientAction.EXPORT);

    Mockito.when(restTemplate.exchange("http://localhost:8080/report", HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<SocialScoreReport>>() {})).thenReturn(null);

    socialScoreCliExecutor.executeCommand(command);
    Mockito.verify(executorListener, Mockito.times(1))
        .onSuccess(command, "SUCCESSFULLY  EXPORT SOCIAL SCORE");
  }

  @TestConfiguration
  static class ConfigurationForThisTest {
    @Bean
    public SocialScoreCliExecutor socialScoreCliExecutor() {
      return new SocialScoreCliExecutorImpl();
    }
  }
}
