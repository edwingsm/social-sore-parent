package ie.newwhip.social.socre.client.socre.client.exception;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

@Slf4j
public class SocialScoreApiErrorHandler  extends DefaultResponseErrorHandler {
  @Override
  public void handleError(ClientHttpResponse response) throws IOException {

  }
}
