package ru.liga.songtask.processor.analyze;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.songtask.domain.Note;
import ru.liga.songtask.processor.analyze.entity.Range;

import java.util.List;

public class CalculationRange {

    private static final Logger log = LoggerFactory.getLogger(CalculationRange.class);

    public Range calculation(List<Note> notes) {
        log.info("Calculation Range");
        if (notes == null || notes.isEmpty()) {
            throw new IllegalArgumentException("Передано недопустимое колличество нот!");
        }

        Range range = new Range(notes.get(0), notes.get(0));

        notes.forEach(note -> {
            range.setLowest(note.compareToLowest(range.getLowest()));
            range.setHighest(note.compareToHighest(range.getHighest()));
        });

        log.info("{}", range);
        return range;
    }
}
