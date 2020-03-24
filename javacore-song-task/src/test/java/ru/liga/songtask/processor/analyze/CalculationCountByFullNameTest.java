package ru.liga.songtask.processor.analyze;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import ru.liga.songtask.domain.Note;
import ru.liga.songtask.domain.NoteSign;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CalculationCountByFullNameTest {

    private CalculationCountByFullName calculationCountByFullName = new CalculationCountByFullName();

    @Test
    public void positive() {
        Note note1 = new Note(NoteSign.A_4, 1000L, 200L);
        Note note2 = new Note(NoteSign.A_0, 1200L, 200L);
        Note note3 = new Note(NoteSign.C_2, 1400L, 200L);
        Note note4 = new Note(NoteSign.A_SHARP_7, 1600L, 200L);
        Note note5 = new Note(NoteSign.A_4, 1800L, 200L);
        List<Note> notes = Arrays.asList(note1, note2, note3, note4, note5);

        Map<NoteSign, Integer> count = calculationCountByFullName.calculation(notes);

        Assertions.assertThat(count.size())
                .as("Ноты подсчитаны некорректно!")
                .isEqualTo(4);

        Assertions.assertThat(count.get(NoteSign.A_4))
                .as("Количество одинаковых нот подсчитанно некорректно!")
                .isEqualTo(2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeNullArgs() {
        calculationCountByFullName.calculation(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeEmptyArgs() {
        calculationCountByFullName.calculation(new ArrayList<>());
    }
}
