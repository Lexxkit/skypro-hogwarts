package com.lexxkit.hogwarts.school.service;

import com.lexxkit.hogwarts.school.model.Faculty;
import com.lexxkit.hogwarts.school.model.Student;

public class TestConstants {
    public static final Faculty GRIFFINDOR = new Faculty(1L, "Griffindor", "red", null);
    public static final Faculty UPD_GRIFFINDOR = new Faculty(1L, "Griffindor", "gold", null);
    public static final Faculty SLYTHERIN = new Faculty(2L, "Slytherin", "green", null);
    public static final Student POTTER = new Student(1L, "Harry Potter", 12, null);
    public static final Student UPD_POTTER = new Student(1L, "Harry Potter", 13, null);
    public static final Student MALFOY = new Student(2L, "Drako Malfoy", 14, null);
    public static final Student GRANGER = new Student(3L, "Hermiona Granger", 12, null);
    public static final int AGE = 12;
    public static final int MIN_AGE = AGE;
    public static final int MAX_AGE = 13;
    public static final String COLOR = SLYTHERIN.getColor();
    public static final String NAME = GRIFFINDOR.getName().toLowerCase();
}
