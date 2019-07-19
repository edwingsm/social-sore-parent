package ie.newship.social.sore.client.cli;

import ie.newship.social.score.core.SocialScore;
import ie.newship.social.score.core.SocialScoreReport;
import ie.newship.social.sore.client.cli.listener.SocialScoreCliExecutorListener;
import ie.newship.social.sore.client.model.Command;

import java.util.List;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class SocialScoreCliExecutorImpl implements SocialScoreCliExecutor {

  @Autowired private RestTemplate restTemplate;

  @Autowired private SocialScoreCliExecutorListener executorListener;

  @Override
  @SneakyThrows(Exception.class)
  public String saveSocialScore(Command input) {
    SocialScore request = new SocialScore();
    request.setScore(input.getScore());
    request.setUrl(input.getUrl().toString());
    ResponseEntity<SocialScore> responseEntity =
        restTemplate.postForEntity("http://localhost:8080/save", input, SocialScore.class);
    //    if (HttpStatus.OK.equals(responseEntity.getStatusCode())) {
    //      log.info("Creation success");
    //    }
    return null;
  }

  @Override
  @SneakyThrows(Exception.class)
  public boolean removeSocialScore(Command input) {
    restTemplate.delete("http://localhost:8080/delete?key=url&value=" + input.getUrl());
    log.info("Deletion Was success");
    return true;
  }

  @Override
  @SneakyThrows(Exception.class)
  public List<SocialScoreReport> generateReport(Command command) {
    final ResponseEntity<List<SocialScoreReport>> response =
        restTemplate.exchange(
            "http://localhost:8080/report",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<SocialScoreReport>>() {});
    // final List<SocialScoreReport> socialScoreReports = response.getBody();
    //  response.getStatusCode();
    return null;
  }

  @Override
  public void executeCommand(Command command) throws Exception {
    try {
      switch (command.getAction()) {
        case ADD:
          saveSocialScore(command);
          executorListener.onSuccess(command, "SUCCESSFULLY ADDED URL");
          System.out.println("Add Something");
          break;
        case REMOVE:
          removeSocialScore(command);
          executorListener.onSuccess(command, "SUCCESSFULLY REMOVED URL");
          System.out.println("Remove Something");
          break;
        case EXPORT:
          generateReport(command);
          executorListener.onSuccess(command, "SUCCESSFULLY  EXPORT SOCIAL SCORE");
          System.out.println("ExportExecution Something");
          break;
        case EXIT:
          executorListener.onSuccess(command, "");
          System.exit(0);
          break;
      }
    } catch (Exception e) {
      executorListener.onFailure(command, "");
    }
  }
}
