package ie.newswhip.social.socre.core.utility;

import ie.newswhip.social.sore.client.model.ClientAction;
import ie.newswhip.social.sore.client.model.Command;
import ie.newswhip.social.sore.client.utill.Utility;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.net.MalformedURLException;

public class UtilityTest {

    @Test
    @SneakyThrows
    public void test(){
        Command command = Utility.buildCommand().input("ADD http://localhost:8080/save 10").build();
        Assertions.assertThat(command.getAction()).isEqualTo(ClientAction.ADD);
        Assertions.assertThat(command.getValue()).isEqualTo("http://localhost:8080/save");
        Assertions.assertThat(command.getScore()).isEqualTo(10);
    }

    @Test
    @SneakyThrows
    public void testWithTrailingWhiteSpace(){
        Command command = Utility.buildCommand().input("ADD http://localhost:8080/save 10  ").build();
        Assertions.assertThat(command.getAction()).isEqualTo(ClientAction.ADD);
        Assertions.assertThat(command.getValue()).isEqualTo("http://localhost:8080/save");
        Assertions.assertThat(command.getScore()).isEqualTo(10);
    }

    @Test
    @SneakyThrows
    public void testWithLeadingWhiteSpace(){
        Command command = Utility.buildCommand().input("    ADD http://localhost:8080/save 10").build();
        Assertions.assertThat(command.getAction()).isEqualTo(ClientAction.ADD);
        Assertions.assertThat(command.getValue()).isEqualTo("http://localhost:8080/save");
        Assertions.assertThat(command.getScore()).isEqualTo(10);
    }

    @Test
    @SneakyThrows
    public void testWithWhiteSpace(){
        Command command = Utility.buildCommand().input("  ADD http://localhost:8080/save 10  ").build();
        Assertions.assertThat(command.getAction()).isEqualTo(ClientAction.ADD);
        Assertions.assertThat(command.getValue()).isEqualTo("http://localhost:8080/save");
        Assertions.assertThat(command.getScore()).isEqualTo(10);
    }

    @Test
    @SneakyThrows
    public void testWithMoreWhiteSpace(){
        Command command = Utility.buildCommand().input("ADD   http://localhost:8080/save 10").build();
        Assertions.assertThat(command.getAction()).isEqualTo(ClientAction.ADD);
        Assertions.assertThat(command.getValue()).isEqualTo("http://localhost:8080/save");
        Assertions.assertThat(command.getScore()).isEqualTo(10);
    }

    @Test
    @SneakyThrows
    public void testExport(){
        Command command = Utility.buildCommand().input("EXPORT").build();
        Assertions.assertThat(command.getAction()).isEqualTo(ClientAction.EXPORT);
        Assertions.assertThat(command.getValue()).isEqualTo(null);
        Assertions.assertThat(command.getScore()).isEqualTo(0);
    }

    @Test
    @SneakyThrows
    public void testFail(){
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> Utility.buildCommand().input("ADD http://localhost:8080/save").build())
                .withMessage("Invalid Command")
                .withNoCause();
    }



    @Test
    @SneakyThrows
    public void testFail2(){
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> Utility.buildCommand().input("ADD http://localhost:8080/save 10 12").build())
                .withMessage("Invalid Command")
                .withNoCause();
    }

    @Test
    @SneakyThrows
    public void testFail3(){

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> Utility.buildCommand().input("    ").build())
                .withMessage("Invalid Command :Empty command")
                .withNoCause();
    }

    @Test
    @SneakyThrows
    public void testFail4(){
        Assertions.assertThatExceptionOfType(MalformedURLException.class)
                .isThrownBy(() -> Utility.buildCommand().input("ADD Some-text 10").build());
    }

}
