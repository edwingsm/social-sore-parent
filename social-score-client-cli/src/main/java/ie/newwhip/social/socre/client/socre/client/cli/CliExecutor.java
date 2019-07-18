package ie.newwhip.social.socre.client.socre.client.cli;

import ie.newwhip.social.socre.client.socre.client.model.Command;

public interface CliExecutor<C extends Command> {

  void executeCommand(C c) throws Exception;

}
