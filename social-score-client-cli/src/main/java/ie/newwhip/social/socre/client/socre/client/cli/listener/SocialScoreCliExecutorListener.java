package ie.newwhip.social.socre.client.socre.client.cli.listener;

import ie.newwhip.social.socre.client.socre.client.model.Command;

public interface SocialScoreCliExecutorListener {

   void onSuccess(Command input , String output );

  void onFailure(Command input , String output );
}
