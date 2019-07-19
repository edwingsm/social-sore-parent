package ie.newship.social.sore.client.cli;

import ie.newship.social.sore.client.model.Command;

public interface CliExecutor<C extends Command> {

  void executeCommand(C c) throws Exception;

}
