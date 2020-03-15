package ru.liga.songtask.processor.change;

import com.leff.midi.event.meta.Tempo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TempoChanger {

    private static final Logger log = LoggerFactory.getLogger(TempoChanger.class);

    public void changeBpmByParam(Integer tempoParam, Tempo tempo) {
        log.debug("Change tempo Bpm by - '{}'", tempoParam);
        tempo.setBpm(tempo.getBpm() + ((tempo.getBpm()) / 100) * tempoParam);
        log.debug("Tempo Bpm was changed to - '{}'", tempo.getBpm());
    }
}
