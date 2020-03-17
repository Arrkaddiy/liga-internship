package ru.liga.songtask.processor.analyze;

import com.leff.midi.MidiFile;
import com.leff.midi.MidiTrack;
import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.meta.Tempo;
import com.leff.midi.event.meta.Text;
import com.leff.midi.event.meta.TrackName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.songtask.domain.Note;
import ru.liga.songtask.processor.domain.ExecuteProcess;
import ru.liga.songtask.util.SongUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Analyzer implements ExecuteProcess {

    private static final Logger log = LoggerFactory.getLogger(Analyzer.class);

    @Override
    public void execute(MidiFile midiFile) {
        analyzeMidiFile(midiFile);
    }

    private void analyzeMidiFile(MidiFile midiFile) {
        log.debug("Analyze midi file");
        Tempo tempo = SongUtils.getTempoFromMidiFile(midiFile);
        List<MidiTrack> voiceMidiTrack = getVoiceMidiTrack(midiFile);
        if (tempo != null) {
            voiceMidiTrack.forEach(
                    midiTrack -> AnalyzerStatistics.getAllStatistics(getNoteByMidiTrack(midiTrack), tempo.getBpm(), midiFile.getResolution()));
        }
    }

    private List<Note> getNoteByMidiTrack(MidiTrack midiTrack) {
        log.debug("Get notes by track");
        List<Note> notes = SongUtils.eventsToNotes(midiTrack.getEvents());
        log.debug("Notes has been found - '{}'", notes.size());
        return notes;
    }

    private List<MidiTrack> getVoiceMidiTrack(MidiFile midiFile) {
        List<MidiTrack> result = new ArrayList<>();
        List<MidiTrack> midiTextTrackList = getTextMidiTrack(midiFile);

        for (MidiTrack midiTextTrack : midiTextTrackList) {

            for (MidiTrack midiTrack : midiFile.getTracks()) {
                if (midiTextTrack.getEvents().stream()
                        .map(MidiEvent::getTick)
                        .collect(Collectors.toList())
                        .equals(getNoteByMidiTrack(midiTrack).stream()
                                .map(Note::startTick)
                                .collect(Collectors.toList()))) {
                    result.add(midiTrack);
                }
            }
        }

        return result;

    }

    private List<MidiTrack> getTextMidiTrack(MidiFile midiFile) {
        log.debug("Get midi Tracks with Text");
        List<MidiTrack> midiTrackList = midiFile.getTracks().stream().filter(midiTrack ->
                midiTrack.getEvents().stream().allMatch(midiEvent ->
                        midiEvent instanceof Text || midiEvent instanceof TrackName))
                .collect(Collectors.toList());
        deleteTrackName(midiTrackList);

        return midiTrackList;
    }

    private void deleteTrackName(List<MidiTrack> midiTrackList) {
        log.debug("Delete TrackName from MidiTrack");
        midiTrackList.forEach(midiTrack -> midiTrack.getEvents().removeIf(midiEvent -> midiEvent instanceof TrackName));
    }
}
