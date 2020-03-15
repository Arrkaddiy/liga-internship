package ru.liga.songtask.domain;

public enum MethodName {
    ANALYZE("analyze"),
    CHANGE("change");

    private String methodName;

    MethodName(String methodName) {
        this.methodName = methodName;
    }

    public static boolean findMethod(String methodName) {
        boolean searchResult = false;
        for (MethodName value : MethodName.values())
            if (value.methodName.toUpperCase().equals(methodName.toUpperCase())) {
                searchResult = true;
                break;
            }
        return searchResult;
    }
}
