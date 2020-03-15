package ru.liga.songtask.processor.change;

import com.leff.midi.MidiFile;
import com.leff.midi.MidiTrack;
import com.leff.midi.event.NoteOff;
import com.leff.midi.event.NoteOn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoteChanger {

    private static final Logger log = LoggerFactory.getLogger(NoteChanger.class);

    public void changeNotesByMidiFile(Integer transParam, MidiFile midiFile) {
        log.debug("Change notes midi file by - '{}'", transParam);
        midiFile.getTracks().forEach(midiTrack -> changeNotesByTrack(transParam, midiTrack));
    }

    public void changeNotesByTrack(Integer transParam, MidiTrack midiTrack) {
        log.debug("Change notes track by - '{}'", transParam);

        midiTrack.getEvents().stream()
                .filter(midiEvent -> midiEvent instanceof NoteOn)
                .forEach(midiEvent -> {
                    NoteOn noteOn = (NoteOn) midiEvent;
                    noteOn.setNoteValue(noteOn.getNoteValue() + transParam);
                });

        midiTrack.getEvents().stream()
                .filter(midiEvent -> midiEvent instanceof NoteOff)
                .forEach(midiEvent -> {
                    NoteOff noteOff = (NoteOff) midiEvent;
                    noteOff.setNoteValue(noteOff.getNoteValue() + transParam);
                });
    }
}
