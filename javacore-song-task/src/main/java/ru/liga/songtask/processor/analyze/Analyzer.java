package ru.liga.songtask.processor.analyze;

import com.leff.midi.MidiFile;
import com.leff.midi.MidiTrack;
import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.ProgramChange;
import com.leff.midi.event.meta.Tempo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.songtask.domain.Note;
import ru.liga.songtask.domain.VoiceValue;
import ru.liga.songtask.processor.domain.ExecuteProcess;
import ru.liga.songtask.util.SongUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Analyzer implements ExecuteProcess {

    private static final Logger log = LoggerFactory.getLogger(Analyzer.class);

    @Override
    public void execute(MidiFile midiFile) {
        analyzeMidiFile(midiFile);
    }

    private void analyzeMidiFile(MidiFile midiFile) {
        log.debug("Analyze midi file");
        Tempo tempo = SongUtils.getTempoFromMidiFile(midiFile);
        if (tempo != null) {
            getMidiTrackByProgramChange(midiFile).forEach(
                    midiTrack -> AnalyzerStatistics.getAllStatistics(
                            getVoiceValue(midiTrack), getNoteByTrack(midiTrack), tempo.getBpm(), midiFile.getResolution()));
        }
    }

    private List<Note> getNoteByTrack(MidiTrack midiTrack) {
        log.debug("Get notes by track");
        List<Note> notes = SongUtils.eventsToNotes(midiTrack.getEvents());
        log.debug("Notes has been found - '{}'", notes.size());
        return notes;
    }

    private List<MidiTrack> getMidiTrackByProgramChange(MidiFile midiFile) {
        log.debug("Get tracks by midi file");
        List<MidiTrack> midiTrackList = new ArrayList<>();
        for (MidiTrack midiTrack : midiFile.getTracks()) {
            if ((midiTrack.getEvents().stream()
                    .anyMatch(value -> (value instanceof ProgramChange)
                            && (VoiceValue.findVoiceByProgramNumber(((ProgramChange) value).getProgramNumber()))))) {
                midiTrackList.add(midiTrack);
            }
        }

        log.debug("Tracks has been found - '{}'", midiTrackList.size());
        return midiTrackList;
    }

    private VoiceValue getVoiceValue(MidiTrack midiTrack) {
        log.debug("Get voice value");
        Optional<MidiEvent> programChange = midiTrack.getEvents().stream()
                .filter(midiEvent -> midiEvent instanceof ProgramChange)
                .findFirst();

        return VoiceValue.getVoiceByProgramNumber(programChange.map(
                midiEvent -> ((ProgramChange) midiEvent).getProgramNumber()).orElse(0));

    }

}
