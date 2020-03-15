package ru.liga.songtask.util;

import com.leff.midi.MidiFile;
import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.NoteOff;
import com.leff.midi.event.NoteOn;
import com.leff.midi.event.meta.Tempo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.songtask.domain.Note;
import ru.liga.songtask.domain.NoteSign;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class SongUtils {

    private static final Logger log = LoggerFactory.getLogger(SongUtils.class);

    /**
     * Перевод тиков в миллисекунды
     *
     * @param bpm          - количество ударов в минуту (темп)
     * @param resolution   - midiFile.getResolution()
     * @param amountOfTick - то что переводим в миллисекунды
     * @return int
     */
    public static int tickToMs(float bpm, int resolution, long amountOfTick) {
        log.debug("Ticks to milliseconds conversion");
        return (int) (((60 * 1000) / (bpm * resolution)) * amountOfTick);
    }

    /**
     * Получение ивента класса Tempo из midi файла
     *
     * @param midiFile - Midi файл
     * @return Ивента класса Tempo
     */
    public static Tempo getTempoFromMidiFile(MidiFile midiFile) {
        log.debug("Get Tempo from midi file");
        Optional<MidiEvent> tempoEvent = midiFile.getTracks().get(0).getEvents().stream()
                .filter(midiEvent -> midiEvent instanceof Tempo)
                .findFirst();

        return (Tempo) tempoEvent.orElseThrow(NullPointerException::new);
    }

    /**
     * Этот метод, чтобы вы не афигели переводить эвенты в ноты
     *
     * @param events эвенты одного трека
     * @return список нот
     */
    public static List<Note> eventsToNotes(TreeSet<MidiEvent> events) {
        log.debug("Translation of events into notes");
        List<Note> vbNotes = new ArrayList<>();

        Queue<NoteOn> noteOnQueue = new LinkedBlockingQueue<>();
        for (MidiEvent event : events) {
            if (event instanceof NoteOn || event instanceof NoteOff) {
                if (isEndMarkerNote(event)) {
                    NoteSign noteSign = NoteSign.fromMidiNumber(extractNoteValue(event));
                    if (noteSign != NoteSign.NULL_VALUE) {
                        NoteOn noteOn = noteOnQueue.poll();
                        if (noteOn != null) {
                            long start = noteOn.getTick();
                            long end = event.getTick();
                            vbNotes.add(
                                    new Note(noteSign, start, end - start));
                        }
                    }
                } else {
                    noteOnQueue.offer((NoteOn) event);
                }
            }
        }
        return vbNotes;
    }

    private static Integer extractNoteValue(MidiEvent event) {
        if (event instanceof NoteOff) {
            return ((NoteOff) event).getNoteValue();
        } else if (event instanceof NoteOn) {
            return ((NoteOn) event).getNoteValue();
        } else {
            return null;
        }
    }

    private static boolean isEndMarkerNote(MidiEvent event) {
        if (event instanceof NoteOff) {
            return true;
        } else if (event instanceof NoteOn) {
            return ((NoteOn) event).getVelocity() == 0;
        } else {
            return false;
        }

    }

}
