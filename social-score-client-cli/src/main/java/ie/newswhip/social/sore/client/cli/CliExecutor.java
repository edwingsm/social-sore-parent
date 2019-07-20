package ie.newswhip.social.sore.client.cli;

import ie.newswhip.social.sore.client.model.Command;

public interface CliExecutor<C extends Command>  {

    void executeCommand(C command) ;

}
