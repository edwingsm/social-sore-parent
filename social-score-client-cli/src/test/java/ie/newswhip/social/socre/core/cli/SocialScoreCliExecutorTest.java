package ie.newswhip.social.socre.core.cli;


import ie.newswhip.social.score.core.model.SocialScore;
import ie.newswhip.social.score.core.model.SocialScoreReport;
import ie.newswhip.social.sore.client.cli.CliExecutor;
import ie.newswhip.social.sore.client.cli.CliExecutorImpl;
import ie.newswhip.social.sore.client.cli.listener.CliExecutorListener;
import ie.newswhip.social.sore.client.model.ClientAction;
import ie.newswhip.social.sore.client.model.Command;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RunWith(SpringRunner.class)
public class SocialScoreCliExecutorTest {

    @Mock
    private ResponseEntity mockResponseEntity;
    @MockBean
    private RestTemplate restTemplate;
    @MockBean
    private CliExecutorListener executorListener;
    @Autowired
    private CliExecutor cliExecutor;

    private SocialScoreReport facebook;
    private SocialScoreReport google;
    private SocialScore socialScore;

    @Before
    public void setUp() {

        google = new SocialScoreReport();
        google.setDomain("www.google.com");
        google.setScore(10l);
        google.setUrlCount(2);

        facebook = new SocialScoreReport();
        facebook.setDomain("www.fb.com");
        facebook.setScore(10l);
        facebook.setUrlCount(2);

        socialScore = new SocialScore();
        socialScore.setId("some-id");
        socialScore.setUrl("http://www.google.com/search?query=Something");
        socialScore.setScore(10l);
    }


    @SneakyThrows
    @Test
    public void testDelete() {

        Command command = new Command();
        command.setAction(ClientAction.REMOVE);
        command.setValue("http://www.google.com/search?query=Something");
        Mockito.doNothing().when(restTemplate).delete(ArgumentMatchers.anyString());
        cliExecutor.executeCommand(command);
        Mockito.verify(executorListener, Mockito.times(1))
                .onSuccess(command, "Url is removed http://www.google.com/search?query=Something");
    }

    @SneakyThrows
    @Test
    public void testDeleteFailure() {

        Command command = new Command();
        command.setAction(ClientAction.REMOVE);
        command.setValue("http://www.google.com/search?query=Something");
        Exception exception = new RuntimeException("Exception from service");
        Mockito.doThrow(exception)
                .when(restTemplate).delete(ArgumentMatchers.anyString());
        cliExecutor.executeCommand(command);
        Mockito.verify(executorListener, Mockito.times(1))
                .onFailure(command, exception);
    }

    @SneakyThrows
    @Test
    public void testSave() {

        Command command = new Command();
        command.setAction(ClientAction.ADD);
        command.setValue("http://www.google.com/search?query=Something");
        command.setScore(10l);
        Mockito.when(mockResponseEntity.getStatusCode()).thenReturn(HttpStatus.OK);
        Mockito.when(mockResponseEntity.getBody()).thenReturn(socialScore);
        Mockito.when(restTemplate.postForEntity(ArgumentMatchers.anyString(), ArgumentMatchers.any(),
                ArgumentMatchers.any())).thenReturn(mockResponseEntity);
        cliExecutor.executeCommand(command);
        Mockito.verify(executorListener, Mockito.times(1))
                .onSuccess(command, socialScore.toString());
    }

    @SneakyThrows
    @Test
    public void testSaveFailure() {

        Command command = new Command();
        command.setAction(ClientAction.ADD);
        command.setValue("http://www.google.com/search?query=Something");
        command.setScore(10l);

        Exception exception = new RuntimeException("Exception from service");

        Mockito.when(restTemplate.postForEntity(ArgumentMatchers.anyString(), ArgumentMatchers.any(),
                ArgumentMatchers.any())).thenThrow(exception);
        cliExecutor.executeCommand(command);
        Mockito.verify(executorListener, Mockito.times(1))
                .onFailure(command, exception);
    }

    @SneakyThrows
    @Test
    public void testReport() {

        Command command = new Command();
        command.setAction(ClientAction.EXPORT);
        Mockito.when(mockResponseEntity.getStatusCode()).thenReturn(HttpStatus.OK);
        Mockito.when(mockResponseEntity.getBody()).thenReturn(Arrays.asList(google, facebook));
        Mockito.when(restTemplate.exchange(ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.nullable(HttpEntity.class),
                ArgumentMatchers.any(ParameterizedTypeReference.class))).thenReturn(mockResponseEntity);
        cliExecutor.executeCommand(command);
        Mockito.verify(executorListener, Mockito.times(1))
                .onSuccess(command, "Report Exported Successfully");
    }

    @SneakyThrows
    @Test
    public void testReportFailure() {

        Command command = new Command();
        command.setAction(ClientAction.EXPORT);

        Exception exception = new RuntimeException("Exception from service");

        Mockito.when(restTemplate.exchange(ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.nullable(HttpEntity.class),
                ArgumentMatchers.any(ParameterizedTypeReference.class))).thenThrow(exception);
        cliExecutor.executeCommand(command);
        Mockito.verify(executorListener, Mockito.times(1))
                .onFailure(command, exception);
    }

    @SneakyThrows
    @Test
    public void testNotSupportedAction() {
        Command command = new Command();
        command.setAction(ClientAction.SEARCH);
        Assertions.assertThatCode(() -> cliExecutor.executeCommand(command))
                .doesNotThrowAnyException();
    }


    @TestConfiguration
    static class ConfigurationForThisTest {
        @Bean
        public CliExecutor cliExecutor() {
            return new CliExecutorImpl();
        }
    }
}
