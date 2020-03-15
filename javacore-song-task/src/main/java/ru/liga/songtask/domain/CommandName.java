package ru.liga.songtask.domain;

public enum CommandName {

    TRANS("-trans"),
    TEMPO("-tempo");

    private String command;

    CommandName(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

}
