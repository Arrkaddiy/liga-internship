package ru.liga.songtask.processor.analyze;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import ru.liga.songtask.domain.Note;
import ru.liga.songtask.domain.NoteSign;
import ru.liga.songtask.processor.analyze.entity.Range;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class CalculationRangeTest {

    private CalculationRange calculationRange = new CalculationRange();

    @Test
    public void positiveOneNote() {
        Note note = new Note(NoteSign.A_4, 1000L, 200L);

        Range range = calculationRange.calculation(Collections.singletonList(note));
        Assertions.assertThat(range.getHighest().sign())
                .as("Высшая нота определенна не корреткно!")
                .isEqualTo(NoteSign.A_4);

        Assertions.assertThat(range.getLowest().sign())
                .as("Низжая нота определенна не корреткно!")
                .isEqualTo(NoteSign.A_4);

        Assertions.assertThat(range.getRange())
                .as("Расстояние между высшей нотой и низжей определенно не корректно!")
                .isEqualTo(0);
    }

    @Test
    public void positive() {
        Note note1 = new Note(NoteSign.A_4, 1000L, 200L);
        Note note2 = new Note(NoteSign.A_0, 1200L, 200L);
        Note note3 = new Note(NoteSign.C_2, 1400L, 200L);
        Note note4 = new Note(NoteSign.A_SHARP_7, 1600L, 200L);
        Note note5 = new Note(NoteSign.A_4, 1800L, 200L);

        Range range = calculationRange.calculation(Arrays.asList(note1, note2, note3, note4, note5));
        Assertions.assertThat(range.getHighest().sign())
                .as("Высшая нота определенна не корреткно!")
                .isEqualTo(NoteSign.A_SHARP_7);

        Assertions.assertThat(range.getLowest().sign())
                .as("Низжая нота определенна не корреткно!")
                .isEqualTo(NoteSign.A_0);

        Assertions.assertThat(range.getRange())
                .as("Расстояние между высшей нотой и низжей определенно не корректно!")
                .isEqualTo(85);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeNullArgs() {
        calculationRange.calculation(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeEmptyArgs() {
        calculationRange.calculation(new ArrayList<>());
    }

}
