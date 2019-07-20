package ie.newswhip.social.socre.core.cli.console;

import ie.newswhip.social.sore.client.cli.console.CommandReader;
import ie.newswhip.social.sore.client.cli.console.ScannerCommandReader;
import org.junit.Test;

public class CommandReaderTest {

    @Test
    public void testConsoleResder(){
        CommandReader commandReader = new ScannerCommandReader();
       // Assertions.assertThatCode(()->commandReader.readCommand()).doesNotThrowAnyException();
    }
}
