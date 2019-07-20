package ie.newswhip.social.socre.core.cli.listener;

import ie.newswhip.social.sore.client.cli.listener.CliExecutorListener;
import ie.newswhip.social.sore.client.cli.listener.DefaultCliExecutorListener;
import ie.newswhip.social.sore.client.model.Command;
import org.assertj.core.api.Assertions;
import org.junit.Test;


public class CliExecutorListenerTest {

    @Test
    public void testOnSuccess() {
        CliExecutorListener cliExecutorListener = new DefaultCliExecutorListener();
        Assertions.assertThatCode(
                () -> cliExecutorListener.onSuccess(new Command(), "some-message"))
                .doesNotThrowAnyException();
    }

    @Test
    public void testOnFailure() {
        CliExecutorListener cliExecutorListener = new DefaultCliExecutorListener();
        Assertions.assertThatCode(
                () -> cliExecutorListener.onFailure(new Command(), new RuntimeException("Exception message")))
                .doesNotThrowAnyException();
    }
}
