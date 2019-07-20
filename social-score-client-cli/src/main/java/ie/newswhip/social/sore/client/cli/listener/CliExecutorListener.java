package ie.newswhip.social.sore.client.cli.listener;

import ie.newswhip.social.sore.client.model.Command;

public interface CliExecutorListener {

   void onSuccess(Command input , String output );

  void onFailure(Command input , Exception e );
}
