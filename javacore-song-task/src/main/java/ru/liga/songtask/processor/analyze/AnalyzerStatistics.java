package ru.liga.songtask.processor.analyze;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.songtask.domain.Note;
import ru.liga.songtask.domain.NoteSign;
import ru.liga.songtask.domain.VoiceValue;
import ru.liga.songtask.util.SongUtils;

import java.util.HashMap;
import java.util.List;

class AnalyzerStatistics {

    private static final Logger log = LoggerFactory.getLogger(AnalyzerStatistics.class);

    static void getAllStatistics(List<Note> notes, float bpm, int resolution) {
        log.info("Get all statistics for Track");
        getRange(notes);
        getCountByTicks(notes, bpm, resolution);
        getCountByFullName(notes);
    }

    private static void getRange(List<Note> notes) {
        log.info("Get Range:");

        Note lowestNote = notes.get(0);
        Note highestNote = notes.get(0);

        for (Note note : notes) {
            if (lowestNote.sign().getMidi() > note.sign().getMidi()) lowestNote = note;
            if (highestNote.sign().getMidi() < note.sign().getMidi()) highestNote = note;
        }

        log.info("Highest: '{}'", highestNote.sign().getNoteName());
        log.info("Lowest: '{}'", lowestNote.sign().getNoteName());
        log.info("Range: '{}'", highestNote.sign().getMidi() - lowestNote.sign().getMidi());
    }

    private static void getCountByTicks(List<Note> notes, float bpm, int resolution) {
        log.info("Get count notes by ticks:");
        HashMap<Long, Integer> count = new HashMap<>();

        for (Note note : notes)
            if (count.containsKey(note.durationTicks()))
                count.put(note.durationTicks(), count.remove(note.durationTicks()) + 1);
            else count.put(note.durationTicks(), 1);

        for (Long key : count.keySet())
            log.info("'{}'ms: '{}'", SongUtils.tickToMs(bpm, resolution, key), count.get(key));
    }

    private static void getCountByFullName(List<Note> notes) {
        log.info("Get count notes by full name:");
        HashMap<NoteSign, Integer> count = new HashMap<>();

        for (Note note : notes)
            if (count.containsKey(note.sign())) count.put(note.sign(), count.remove(note.sign()) + 1);
            else count.put(note.sign(), 1);

        for (NoteSign key : count.keySet())
            log.info("'{}': '{}'", key.getNoteName(), count.get(key));
    }
}
