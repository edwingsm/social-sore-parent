package ie.newwhip.social.socre.client;

// import ie.newwhip.social.socre.client.socre.client.SocialScoreCliApplication;
// import ie.newwhip.social.socre.client.socre.client.cli.console.ConsoleReader;

import ie.newwhip.social.socre.client.socre.client.SocialScoreCliApplication;
import ie.newwhip.social.socre.client.socre.client.cli.console.ConsoleReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@TestPropertySource(properties = {"app.keep.Running=false"})
@SpringBootTest(classes = SocialScoreCliApplication.class)
public class SocialScoreCliApplicationTest {

  @MockBean private ConsoleReader scanner;

  @MockBean private RestTemplate restTemplate;



  @Test
  public void add() {
    Mockito.when(scanner.readLine())
        .thenReturn("REMOVE http://www.google.com/search?query=Something");
    Mockito.doNothing().when(restTemplate).delete(ArgumentMatchers.anyString());


  }
}
