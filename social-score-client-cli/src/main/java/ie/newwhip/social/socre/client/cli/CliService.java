package ie.newwhip.social.socre.client.cli;

import java.util.List;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class CliService {

  @Autowired
  private RestTemplate restTemplate;

  public void executeAction(Command input) {

    try {
      switch (input.getAction()) {
        case ADD:
          addSocialServiceScore(input);
          System.out.println("Add Something");
          break;
        case REMOVE:
          removeSocialServiceScore(input);
          System.out.println("Remove Something");
          break;
        case EXPORT:
          exportSocialServiceScore(input);
          System.out.println("ExportExecution Something");
          break;
        case EXIT:
          System.exit(0);
          break;
      }
    } catch (Exception e) {

    }
  }

  @SneakyThrows(RestClientException.class)
  private void addSocialServiceScore(Command input) {

    SocialScore request = new SocialScore();
    request.setScore(input.getScore());
    request.setUrl(input.getUrl().toString());
    ResponseEntity<SocialScore>  responseEntity = restTemplate.postForEntity("http://localhost:8080/save",input,SocialScore.class);

    if(HttpStatus.OK.equals(responseEntity.getStatusCode()){
      log.info("Creation success");
    }
  }

  @SneakyThrows(RestClientException.class)
  private void removeSocialServiceScore(Command input) {
    restTemplate.delete("http://localhost:8080/delete?key=url&value="+input.getUrl());
    log.info("Deletion Was success");
  }

  @SneakyThrows(RestClientException.class)
  private void exportSocialServiceScore(Command input) {
    final ResponseEntity<List<SocialScoreReport>> response = restTemplate.exchange(
        "http://localhost:8080/report",
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<SocialScoreReport>>(){});
      final List<SocialScoreReport> socialScoreReports = response.getBody();
     socialScoreReports.forEach(s ->System.out.println(s.toString()));

  }
}
