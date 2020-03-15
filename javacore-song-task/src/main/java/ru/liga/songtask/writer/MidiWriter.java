package ru.liga.songtask.writer;

import com.leff.midi.MidiFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class MidiWriter {

    private static final Logger log = LoggerFactory.getLogger(MidiWriter.class);

    public static void writeFile(String filePath, MidiFile midiFile) {
        log.info("Write mid-File to path - '{}'", filePath);

        try {
            midiFile.writeToFile(new File(filePath));
        } catch (IOException ioe) {
            log.error(ioe.getMessage());
        }
    }
}
