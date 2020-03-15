package ru.liga.domain.input;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import ru.liga.songtask.domain.CommandName;
import ru.liga.songtask.input.FilePathGenerator;

import java.util.HashMap;
import java.util.Map;

public class FilePathGeneratorTest {

    private final String oldFilePath = "Repo\\file\\MidiFile.mid";

    @Test
    public void positiveAllCommands() {
        Map<CommandName, Integer> commands = new HashMap<>();
        commands.put(CommandName.TRANS, 20);
        commands.put(CommandName.TEMPO, 10);

        Assertions.assertThat(FilePathGenerator.generateNewFilePath(oldFilePath, commands))
                .as("Новый путь сгенерирован неверно!")
                .isEqualTo("Repo\\file\\MidiFile-trans20-tempo10.mid");
    }

    @Test
    public void positiveTramsCommand() {
        Map<CommandName, Integer> commands = new HashMap<>();
        commands.put(CommandName.TRANS, -2);

        Assertions.assertThat(FilePathGenerator.generateNewFilePath(oldFilePath, commands))
                .as("Новый путь сгенерирован неверно!")
                .isEqualTo("Repo\\file\\MidiFile-trans-2.mid");
    }

    @Test
    public void positiveTempoCommand() {
        Map<CommandName, Integer> commands = new HashMap<>();
        commands.put(CommandName.TEMPO, 30);

        Assertions.assertThat(FilePathGenerator.generateNewFilePath(oldFilePath, commands))
                .as("Новый путь сгенерирован неверно!")
                .isEqualTo("Repo\\file\\MidiFile-tempo30.mid");
    }

    @Test
    public void positiveWithoutCommand(){
        Map<CommandName, Integer> commands = new HashMap<>();
        Assertions.assertThat(FilePathGenerator.generateNewFilePath(oldFilePath, commands))
                .as("Новый путь сгенерирован неверно!")
                .isEqualTo(oldFilePath);
    }
}
