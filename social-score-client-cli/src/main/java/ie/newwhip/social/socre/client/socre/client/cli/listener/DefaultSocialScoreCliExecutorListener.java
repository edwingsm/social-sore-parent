package ie.newwhip.social.socre.client.socre.client.cli.listener;

import ie.newwhip.social.socre.client.socre.client.model.Command;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultSocialScoreCliExecutorListener  implements SocialScoreCliExecutorListener {

  @Override
  public void onSuccess(Command input, String output) {
    log.info("On Success");
  }

  @Override
  public void onFailure(Command input, String output) {
    log.warn("On Failure {} - {}",input,output);
  }
}
