package ru.liga.songtask.processor.analyze;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.songtask.domain.Note;
import ru.liga.songtask.domain.NoteSign;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculationCountByFullName {

    private static final Logger log = LoggerFactory.getLogger(CalculationCountByFullName.class);

    public Map<NoteSign, Integer> calculation(List<Note> notes) {
        log.info("Calculation count notes by full name:");
        if (notes == null || notes.isEmpty()) {
            throw new IllegalArgumentException("Передано недопустимое колличество нот!");
        }

        Map<NoteSign, Integer> count = new HashMap<>();

        notes.forEach(note -> {
            if (count.containsKey(note.sign())) {
                count.put(note.sign(), count.remove(note.sign()) + 1);
            } else {
                count.put(note.sign(), 1);
            }
        });

        for (NoteSign key : count.keySet()) {
            log.info("'{}': '{}'", key.fullName(), count.get(key));
        }

        return count;
    }
}
