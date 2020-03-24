package ru.liga.songtask.processor.change;

import com.leff.midi.event.meta.Tempo;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class TempoChangerTest {

    private TempoChanger tempoChanger = new TempoChanger();

    @Test
    public void positive() {
        Tempo tempo = new Tempo();
        tempo.setBpm(20);
        tempoChanger.changeBpmByParam(20, tempo);

        Assertions.assertThat(tempo.getBpm())
                .as("Изменение Bpm в процентном соотношении некорректно!")
                .isEqualTo(24);
    }

    @Test
    public void positiveByZero() {
        Tempo tempo = new Tempo();
        tempo.setBpm(20);
        tempoChanger.changeBpmByParam(0, tempo);

        Assertions.assertThat(tempo.getBpm())
                .as("Изменение Bpm в процентном соотношении некорректно!")
                .isEqualTo(20);
    }
}
