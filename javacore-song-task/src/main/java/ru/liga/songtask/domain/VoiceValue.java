package ru.liga.songtask.domain;

public enum VoiceValue {

    MEANDER(81),
    PILOT_WAVE(82),
    CALLIOPE(83),
    CHIFF(84),
    CHARANG(85),
    VOICE(86),
    QUINT(87),
    BAS_AND_LEAD_VOICE(88),
    NEW_AGE(89),
    WARM_SOUND(90),
    POLYSYNTHESIZER(91),
    CHOIR(92),
    TWISTED_SOUND(93),
    METAL_SOUND(94),
    HALO(95),
    SWEEP(96);

    private Integer programNumber;

    VoiceValue(Integer programNumber) {
        this.programNumber = programNumber;
    }

    public static boolean findVoiceByProgramNumber(Integer programNumber) {
        boolean searchResult = false;

        for (VoiceValue value : VoiceValue.values())
            if (value.programNumber.equals(programNumber)) {
                searchResult = true;
                break;
            }

        return searchResult;
    }

    public static VoiceValue getVoiceByProgramNumber(Integer programNumber) {
        VoiceValue searchResult = null;

        for (VoiceValue value : VoiceValue.values())
            if (value.programNumber.equals(programNumber)) {
                searchResult = value;
                break;
            }

        return searchResult;
    }
}
