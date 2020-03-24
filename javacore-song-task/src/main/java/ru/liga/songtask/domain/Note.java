package ru.liga.songtask.domain;

/**
 * Created by bshestakov on 13.07.2017.
 * <p>
 * Нота с длительностью
 */
public class Note {
    private final NoteSign note;
    private final Long durationTicks;
    private final Long startTick;

    public Note(NoteSign note, Long startTick, Long durationTicks) {
        this.note = note;
        this.startTick = startTick;
        this.durationTicks = durationTicks;
    }

    public NoteSign sign() {
        return note;
    }

    public Long durationTicks() {
        return durationTicks;
    }

    public Long startTick() {
        return startTick;
    }

    public Long endTickInclusive() {
        return startTick + durationTicks;
    }

    public Note compareToLowest(Note note) {
        return this.sign().getMidi() < note.sign().getMidi() ? this : note;
    }

    public Note compareToHighest(Note note) {
        return this.sign().getMidi() > note.sign().getMidi() ? this : note;
    }

    @Override
    public String toString() {
        return "{" +
                note.fullName() +
                ", S|" + startTick +
                ", D|" + durationTicks +
                '}';
    }
}
