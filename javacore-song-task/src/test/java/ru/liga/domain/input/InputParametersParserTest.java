package ru.liga.domain.input;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import ru.liga.songtask.domain.CommandName;
import ru.liga.songtask.domain.MethodName;
import ru.liga.songtask.input.InputParametersParser;

import java.util.HashMap;
import java.util.Map;

public class InputParametersParserTest {

    private final String[] argsChange = {"Path", "change", "-tempo", "10", "-trans", "5"};
    private final String[] argsAnalyze = {"Path", "analyze"};

    @Test
    public void positiveGetPath() {
        InputParametersParser inputParametersParser = new InputParametersParser(argsAnalyze);
        Assertions.assertThat(inputParametersParser.getFilePath())
                .as("Путь до файла определен некорректно!")
                .isEqualTo("Path");
    }

    @Test
    public void positiveGetMethodName() {
        InputParametersParser inputParametersParser = new InputParametersParser(argsAnalyze);
        Assertions.assertThat(inputParametersParser.getMethodName())
                .as("Метод определен некорректно!")
                .isEqualTo(MethodName.ANALYZE);

        inputParametersParser = new InputParametersParser(argsChange);
        Assertions.assertThat(inputParametersParser.getMethodName())
                .as("Метод определен некорректно!")
                .isEqualTo(MethodName.CHANGE);
    }

    @Test
    public void positiveGetCommand() {
        InputParametersParser inputParametersParser = new InputParametersParser(argsChange);
        Map<CommandName, Integer> commands = new HashMap<>();
        commands.put(CommandName.TRANS, 5);
        commands.put(CommandName.TEMPO, 10);

        Map<CommandName, Integer> resultCommands = inputParametersParser.getCommands();

        Assertions.assertThat(commands.size())
                .as("Обработаны не все команды!")
                .isEqualTo(resultCommands.size());

        for (CommandName commandName : resultCommands.keySet()) {
            Assertions.assertThat(resultCommands.get(commandName))
                    .as("Значение команды определено не корректно!")
                    .isEqualTo(commands.get(commandName));
        }
    }
}
