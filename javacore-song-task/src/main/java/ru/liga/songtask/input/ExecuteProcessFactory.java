package ru.liga.songtask.input;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.songtask.domain.MethodName;
import ru.liga.songtask.processor.analyze.Analyzer;
import ru.liga.songtask.processor.change.Changer;
import ru.liga.songtask.processor.domain.ExecuteProcess;

import java.util.Optional;

public class ExecuteProcessFactory {

    private static final Logger log = LoggerFactory.getLogger(ExecuteProcessFactory.class);

    public Optional<ExecuteProcess> createExecuteProcess(InputParametersParser inputParameters) {
        log.info("Create Execute Process");
        Optional<ExecuteProcess> executeProcess;
        MethodName methodName = inputParameters.getMethodName();

        switch (methodName) {
            case ANALYZE: {
                log.debug("Create Execute Process Class - Analyzer");
                Analyzer analyzer = new Analyzer();
                executeProcess = Optional.of(analyzer);
                break;
            }
            case CHANGE: {
                log.debug("Create Execute Process Class - Changer");
                Changer changer = new Changer();
                changer.setCommands(inputParameters.getCommands());
                changer.setFilePath(FilePathGenerator.generateNewFilePath(inputParameters.getFilePath(), inputParameters.getCommands()));
                executeProcess = Optional.of(changer);
                break;
            }
            default: {
                log.debug("Execute Process Class by method - '{}' not found!", methodName);
                executeProcess = Optional.empty();
            }
        }

        return executeProcess;
    }
}
