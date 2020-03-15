package ru.liga.songtask.input;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.songtask.domain.CommandName;
import ru.liga.songtask.domain.MethodName;

import java.util.HashMap;
import java.util.Map;

public class InputParametersParser {

    private static final Logger log = LoggerFactory.getLogger(InputParametersParser.class);

    private final String[] inputArgs;

    public InputParametersParser(String[] inputArgs) {
        this.inputArgs = inputArgs;
    }

    public String getFilePath() {
        log.info("Get path to file from input parameters");
        String filePath;

        if (inputArgs[0] != null && !inputArgs[0].isEmpty()) {
            filePath = inputArgs[0];
            log.info("File path has been defined as - '{}'", filePath);
        } else {
            filePath = null;
            log.error("The file path has not been defined!");
        }

        return filePath;
    }

    public MethodName getMethodName() {
        log.info("Get processor name from input parameters");
        MethodName methodName;

        try {
            if (inputArgs[1] != null && !inputArgs[1].isEmpty()) {
                methodName = MethodName.valueOf(inputArgs[1].toUpperCase());
                log.info("Method name has been defined as - '{}'", methodName);
            } else {
                methodName = null;
                log.error("Method name has not been defined!");
            }

        } catch (IllegalArgumentException iae) {
            methodName = null;
            log.error("Method name has not been defined!");
        }

        return methodName;
    }

    public Map<CommandName, Integer> getCommands() {
        log.debug("Get commands from input parameters");
        Map<CommandName, Integer> commands = new HashMap<>();

        if (inputArgs.length > 2 && inputArgs.length % 2 == 0) {

            for (int i = 2; i < inputArgs.length; i++) {

                switch (CommandName.valueOf(inputArgs[i].substring(1).toUpperCase())) {
                    case TRANS:
                        if (!commands.containsKey(CommandName.TRANS)) {
                            commands.put(CommandName.TRANS, getParamsValue(inputArgs[++i]));
                        } else {
                            log.warn("Input command value is already defined");
                        }
                        break;

                    case TEMPO:
                        if (!commands.containsKey(CommandName.TEMPO)) {
                            commands.put(CommandName.TEMPO, getParamsValue(inputArgs[++i]));
                        } else {
                            log.warn("Input command value is already defined");
                        }
                        break;

                    default:
                        log.error("Input command not find");
                }
            }

        } else {
            log.warn("Input parameters is empty");
        }

        log.info("Method name has been defined as: {}", commands);
        return commands;
    }

    private Integer getParamsValue(String value) {
        log.debug("Get value from string - '{}'", value);
        Integer result;

        try {
            result = Integer.parseInt(value);
            log.debug("Value has been defined as - '{}'", result);
        } catch (NumberFormatException nfe) {
            result = null;
            log.error(nfe.getMessage());
        }

        return result;
    }
}
