package ru.liga.songtask.processor.change;

import com.leff.midi.MidiFile;
import com.leff.midi.MidiTrack;
import com.leff.midi.event.NoteOff;
import com.leff.midi.event.NoteOn;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class NoteChangerTest {

    private NoteChanger noteChanger = new NoteChanger();

    @Test
    public void positiveNoteOn() {
        MidiTrack midiTrack = new MidiTrack();
        NoteOn noteOn = new NoteOn(100, 4, 3, 2);
        midiTrack.insertEvent(noteOn);

        noteChanger.changeNotesByTrack(10, midiTrack);

        Assertions.assertThat(noteOn.getNoteValue())
                .as("Нота изменена некорректно")
                .isEqualTo(13);
    }

    @Test
    public void positiveNoteOff() {
        MidiTrack midiTrack = new MidiTrack();
        NoteOff noteOff = new NoteOff(300, 1, 2, 6);
        midiTrack.insertEvent(noteOff);

        noteChanger.changeNotesByTrack(10, midiTrack);

        Assertions.assertThat(noteOff.getNoteValue())
                .as("Нота изменена некорректно")
                .isEqualTo(12);
    }

    @Test
    public void positiveMidiFile() {
        MidiFile midiFile = new MidiFile();
        MidiTrack midiTrack = new MidiTrack();

        NoteOn noteOn = new NoteOn(100, 4, 3, 2);
        NoteOff noteOff = new NoteOff(300, 1, 2, 6);

        midiTrack.insertEvent(noteOn);
        midiTrack.insertEvent(noteOff);
        midiFile.addTrack(midiTrack);

        noteChanger.changeNotesByMidiFile(10, midiFile);

        Assertions.assertThat(noteOn.getNoteValue())
                .as("Нота изменена некорректно")
                .isEqualTo(13);

        Assertions.assertThat(noteOff.getNoteValue())
                .as("Нота изменена некорректно")
                .isEqualTo(12);
    }


}
