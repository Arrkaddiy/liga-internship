package ru.liga.songtask.processor.analyze.entity;

import ru.liga.songtask.domain.Note;

public class Range {

    private Note highest;
    private Note lowest;

    public Range(Note highest, Note lowest) {
        this.highest = highest;
        this.lowest = lowest;
    }

    public Note getHighest() {
        return highest;
    }

    public void setHighest(Note highest) {
        this.highest = highest;
    }

    public Note getLowest() {
        return lowest;
    }

    public void setLowest(Note lowest) {
        this.lowest = lowest;
    }

    public Integer getRange() {
        return highest.sign().getMidi() - lowest.sign().getMidi();
    }

    @Override
    public String toString() {
        return "Range : \n" +
                "Highest = '" + highest.sign().fullName() + "'\n" +
                "Lowest = '" + lowest.sign().fullName() + "'\n" +
                "Range = '" + getRange() + '\'';
    }
}
