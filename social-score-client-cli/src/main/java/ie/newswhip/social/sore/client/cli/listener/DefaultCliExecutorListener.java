package ie.newswhip.social.sore.client.cli.listener;

import ie.newswhip.social.sore.client.model.Command;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultCliExecutorListener implements CliExecutorListener {

    @Override
    public void onSuccess(Command input, String output) {
        log.info("Execution for command {} finished successfully with message {}", input, output);
    }

    @Override
    public void onFailure(Command input, Exception e) {
        log.warn("On Failure form command {} - {}", input, e.getMessage());
    }
}
