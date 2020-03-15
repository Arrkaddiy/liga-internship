package ru.liga.songtask.processor.change;

import com.leff.midi.MidiFile;
import com.leff.midi.event.meta.Tempo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.songtask.domain.CommandName;
import ru.liga.songtask.processor.domain.ExecuteProcess;
import ru.liga.songtask.util.SongUtils;
import ru.liga.songtask.writer.MidiWriter;

import java.util.Map;

public class Changer implements ExecuteProcess {

    private static final Logger log = LoggerFactory.getLogger(Changer.class);

    private Map<CommandName, Integer> commands;
    private String filePath;

    @Override
    public void execute(MidiFile midiFile) {
        log.info("Execute Changer");
        for (CommandName command : commands.keySet()) {
            if (command.equals(CommandName.TRANS)) changeMidiFile(commands.get(CommandName.TRANS), midiFile);
            if (command.equals(CommandName.TEMPO)) changeTempo(commands.get(CommandName.TEMPO), midiFile);
        }
        writeFile(filePath, midiFile);
    }

    public void setCommands(Map<CommandName, Integer> commands) {
        this.commands = commands;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    private void changeMidiFile(Integer transParam, MidiFile midiFile) {
        log.info("Trans midi file");
        NoteChanger noteChanger = new NoteChanger();
        noteChanger.changeNotesByMidiFile(transParam, midiFile);
    }

    private void changeTempo(Integer tempoParam, MidiFile midiFile) {
        log.info("Change tempo by param");
        Tempo tempo = SongUtils.getTempoFromMidiFile(midiFile);
        TempoChanger tempoChanger = new TempoChanger();
        tempoChanger.changeBpmByParam(tempoParam, tempo);
    }

    private void writeFile(String filePath, MidiFile midiFile) {
        log.info("Write file");
        MidiWriter.writeFile(filePath, midiFile);
    }
}