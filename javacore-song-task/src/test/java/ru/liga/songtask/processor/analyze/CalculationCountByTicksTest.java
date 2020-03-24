package ru.liga.songtask.processor.analyze;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import ru.liga.songtask.domain.Note;
import ru.liga.songtask.domain.NoteSign;
import ru.liga.songtask.util.SongUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CalculationCountByTicksTest {

    private CalculationCountByTicks calculationCountByTicks = new CalculationCountByTicks();

    @Test
    public void positive() {
        Note note1 = new Note(NoteSign.A_4, 1000L, 150L);
        Note note2 = new Note(NoteSign.A_0, 1600L, 200L);
        Note note3 = new Note(NoteSign.C_2, 1400L, 300L);
        Note note4 = new Note(NoteSign.A_SHARP_7, 1600L, 200L);
        Note note5 = new Note(NoteSign.A_4, 1800L, 200L);
        List<Note> notes = Arrays.asList(note1, note2, note3, note4, note5);

        Map<Integer, Integer> count = calculationCountByTicks.calculation(notes, 2, 5);

        Assertions.assertThat(count.size())
                .as("Ноты подсчитаны некорректно!")
                .isEqualTo(3);

        Assertions.assertThat(count.get(SongUtils.tickToMs(2, 5, 150L)))
                .as("Количество одинаковых нот подсчитанно некорректно!")
                .isEqualTo(1);
        Assertions.assertThat(count.get(SongUtils.tickToMs(2, 5, 200L)))
                .as("Количество одинаковых нот подсчитанно некорректно!")
                .isEqualTo(3);
        Assertions.assertThat(count.get(SongUtils.tickToMs(2, 5, 300L)))
                .as("Количество одинаковых нот подсчитанно некорректно!")
                .isEqualTo(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeNullArgs() {
        calculationCountByTicks.calculation(null, 0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeEmptyArgs() {
        calculationCountByTicks.calculation(new ArrayList<>(), 0, 0);
    }
}
