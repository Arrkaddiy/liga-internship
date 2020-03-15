package ru.liga.songtask.loader;

import com.leff.midi.MidiFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

public class MidiLoader {

    private static final Logger log = LoggerFactory.getLogger(MidiLoader.class);

    public static MidiFile loadFile(String filePath) {
        log.info("Download mid-File from path - '{}'", filePath);
        MidiFile midiFile = null;

        try {
            midiFile = new MidiFile(new FileInputStream(filePath));

        } catch (IOException ioe) {
            log.error("File was not uploaded");
            log.error(ioe.getMessage());
        }

        return midiFile;
    }

}
