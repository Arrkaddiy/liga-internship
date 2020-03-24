package ru.liga.songtask.processor.analyze;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.songtask.domain.Note;
import ru.liga.songtask.util.SongUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculationCountByTicks {

    private static final Logger log = LoggerFactory.getLogger(CalculationCountByTicks.class);

    public Map<Integer, Integer> calculation(List<Note> notes, float bpm, int resolution) {
        log.info("Calculation count notes by ticks:");
        if (notes == null || notes.isEmpty()) {
            throw new IllegalArgumentException("Передано недопустимое колличество нот!");
        }

        Map<Integer, Integer> count = new HashMap<>();

        notes.forEach(note -> {
            int ticks = SongUtils.tickToMs(bpm, resolution, note.durationTicks());

            if (count.containsKey(ticks)) {
                count.put(ticks, count.remove(ticks) + 1);
            } else {
                count.put(ticks, 1);
            }
        });

        for (Integer key : count.keySet())
            log.info("'{}'ms: '{}'", key, count.get(key));

        return count;
    }
}
