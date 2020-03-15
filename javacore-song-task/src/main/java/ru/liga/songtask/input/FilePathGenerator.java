package ru.liga.songtask.input;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.songtask.domain.CommandName;

import java.util.Map;

public class FilePathGenerator {

    private static final Logger log = LoggerFactory.getLogger(FilePathGenerator.class);

    public static String generateNewFilePath(String oldFilePath, Map<CommandName, Integer> commands) {
        log.debug("Generate new file path");
        StringBuilder result = new StringBuilder(getFilePath(oldFilePath)).append(getFileName(oldFilePath));
        if (commands.containsKey(CommandName.TRANS)) {
            result.append(CommandName.TRANS.getCommand())
                    .append(commands.get(CommandName.TRANS));
        }
        if (commands.containsKey(CommandName.TEMPO)) {
            result.append(CommandName.TEMPO.getCommand())
                    .append(commands.get(CommandName.TEMPO));
        }
        result.append(".mid");
        log.debug("New file path - '{}'", result.toString());
        return result.toString();
    }

    private static String getFilePath(String oldFilePath) {
        log.debug("Check file path from path - '{}'", oldFilePath);

        String repositoryPath = null;
        int lastIndex = oldFilePath.lastIndexOf("\\");

        if (lastIndex > 0) {
            repositoryPath = oldFilePath.substring(0, ++lastIndex);
        }

        log.debug("The file path has been defined as - '{}'", repositoryPath);
        return repositoryPath;
    }

    private static String getFileName(String oldFilePath) {
        log.debug("Check file name from path - '{}'", oldFilePath);

        String fileName = null;
        int firstIndex = oldFilePath.lastIndexOf("\\");
        int lastIndex = oldFilePath.lastIndexOf(".");

        if (lastIndex > 0) {
            fileName = oldFilePath.substring(++firstIndex, lastIndex);
        }

        log.debug("The file name has been defined as - '{}'", fileName);
        return fileName;
    }
}
