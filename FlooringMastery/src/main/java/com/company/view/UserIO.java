package com.company.view;

// Wiley's UserIO - Taken from ClassRoster Exercise
public interface UserIO {
    void print(String msg);
    double readDouble(String prompt);
    double readDouble(String prompt, double min, double max);
    float readFloat(String prompt);
    float readFloat(String prompt, float min, float max);
    int readInt(String prompt);
    int readInt(String prompt, int min, int max);
    long readLong(String prompt);
    long readLong(String prompt, long min, long max);
    String readString(String prompt);

    // Own Methods
    String readDay(String prompt, int min, int max);
    String readMonth(String prompt, int min, int max);
    String readYear(String prompt);
    boolean readChoice(String prompt);
    String readName (String prompt);
    double readDouble(String prompt, double min);

}
