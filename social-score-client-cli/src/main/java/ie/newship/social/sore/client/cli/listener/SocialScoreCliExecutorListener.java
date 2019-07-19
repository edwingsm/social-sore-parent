package ie.newship.social.sore.client.cli.listener;

import ie.newship.social.sore.client.model.Command;

public interface SocialScoreCliExecutorListener {

   void onSuccess(Command input , String output );

  void onFailure(Command input , String output );
}
