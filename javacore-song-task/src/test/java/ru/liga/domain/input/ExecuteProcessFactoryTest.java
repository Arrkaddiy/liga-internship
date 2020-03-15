package ru.liga.domain.input;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import ru.liga.songtask.input.ExecuteProcessFactory;
import ru.liga.songtask.input.InputParametersParser;
import ru.liga.songtask.processor.analyze.Analyzer;
import ru.liga.songtask.processor.change.Changer;

public class ExecuteProcessFactoryTest {

    @Test
    public void positiveAnalyze() {
        String[] argsAnalyze = {"Path\\midi.mid", "analyze"};
        ExecuteProcessFactory factory = new ExecuteProcessFactory();

        Assertions.assertThat(factory.createExecuteProcess(new InputParametersParser(argsAnalyze)).get())
                .as("Выполняемый процесс определен некорректно!")
                .isInstanceOf(Analyzer.class);
    }

    @Test
    public void positiveChange() {
        String[] argsChange = {"Path\\midi.mid", "change", "-tempo", "10", "-trans", "5"};
        ExecuteProcessFactory factory = new ExecuteProcessFactory();

        Assertions.assertThat(factory.createExecuteProcess(new InputParametersParser(argsChange)).get())
                .as("Выполняемый процесс определен некорректно!")
                .isInstanceOf(Changer.class);
    }
}
