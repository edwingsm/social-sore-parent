package ie.newswhip.social.sore.client.cli;

import ie.newswhip.social.score.core.api.SocialScoreApi;
import ie.newswhip.social.score.core.api.SocialScoreSearchApi;
import ie.newswhip.social.score.core.model.SearchKey;
import ie.newswhip.social.score.core.model.SocialScore;
import ie.newswhip.social.score.core.model.SocialScoreReport;
import ie.newswhip.social.score.core.model.SocialScoreUpdate;
import ie.newswhip.social.sore.client.cli.listener.CliExecutorListener;
import ie.newswhip.social.sore.client.model.Command;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.function.Consumer;

@Service
@Slf4j
public class CliExecutorImpl implements CliExecutor, SocialScoreApi<String>, SocialScoreSearchApi<List> {

    @Value("${api.url}")
    private String baseUrl;

    private Consumer<String> style = (String s) -> System.out.println(s);

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CliExecutorListener executorListener;

    @Override
    public void executeCommand(Command command) {
        try {
            switch (command.getAction()) {
                case ADD:
                    final SocialScore request = new SocialScore();
                    request.setScore(command.getScore());
                    request.setUrl(command.getValue());
                    String saveResult = save(request);
                    executorListener.onSuccess(command, saveResult);
                    break;
                case REMOVE:
                    final String removeResult = delete(SearchKey.URL, command.getValue());
                    executorListener.onSuccess(command, removeResult);
                    break;
                case EXPORT:
                    List<SocialScoreReport> reports = report();
                    System.out.println("domain;count;score");
                    reports.stream()
                            .map(r -> {
                                StringBuffer buffer = new StringBuffer();
                                buffer.append(r.getDomain());
                                buffer.append(";");
                                buffer.append(r.getUrlCount());
                                buffer.append(";");
                                buffer.append(r.getScore());
                                return buffer.toString();
                            }).forEach(style);
                    executorListener.onSuccess(command, "Report Exported Successfully");
                    break;
                case EXIT:
                    executorListener.onSuccess(command, "Existing Application");
                    System.exit(0);
                    break;
                default:
                    log.info("Action currently not supported");
                    break;
            }
        } catch (Exception e) {
            executorListener.onFailure(command, e);
        }
    }

    @Override
    @SneakyThrows(Exception.class)
    public String save(SocialScore socialScore) {
        final ResponseEntity<SocialScore> responseEntity =
                restTemplate.postForEntity("http://localhost:8080/save", socialScore, SocialScore.class);
        final SocialScore saved = responseEntity.getBody();
        log.debug("Saved Successfully {}", saved);
        return saved.toString();
    }

    @Override
    public List search(SearchKey key, String value) {
        //TODO
        return null;
    }

    @Override
    public String update(SocialScoreUpdate socialScoreUpdate) {
        //TODO
        return null;
    }

    @Override
    @SneakyThrows(Exception.class)
    public String delete(SearchKey key, String value) {
        restTemplate.delete("http://localhost:8080/delete?key=url&value=" + value);
        log.debug("Removed Successfully {}", value);
        return String.format("Url is removed %s", value);
    }

    @Override
    @SneakyThrows(Exception.class)
    public List report() {
        final ResponseEntity<List<SocialScoreReport>> response =
                restTemplate.exchange(
                        "http://localhost:8080/report",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<SocialScoreReport>>() {
                        });
        final List<SocialScoreReport> socialScoreReports = response.getBody();
        return socialScoreReports;
    }
}
