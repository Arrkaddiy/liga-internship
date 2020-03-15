package ru.liga.songtask;

import com.leff.midi.MidiFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.songtask.input.ExecuteProcessFactory;
import ru.liga.songtask.input.InputParametersParser;
import ru.liga.songtask.loader.MidiLoader;
import ru.liga.songtask.processor.domain.ExecuteProcess;

import java.util.Optional;

public class Manager {

    private static final Logger log = LoggerFactory.getLogger(Manager.class);

    public static void run(String[] args) {
        log.info("Run a program with input parameters");
        InputParametersParser parametersParser = new InputParametersParser(args);
        MidiFile midiFile = MidiLoader.loadFile(parametersParser.getFilePath());

        ExecuteProcessFactory processFactory = new ExecuteProcessFactory();
        Optional<ExecuteProcess> process = processFactory.createExecuteProcess(parametersParser);

        process.ifPresent(executeProcess -> executeProcess.execute(midiFile));
        log.info("End program");
    }
}
