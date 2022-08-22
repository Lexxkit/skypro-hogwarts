package com.lexxkit.hogwarts.school.service;

import com.lexxkit.hogwarts.school.model.Faculty;
import com.lexxkit.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public class TestConstants {
    public static final Faculty GRIFFINDOR = new Faculty(1L, "Griffindor", "red");
    public static final Faculty UPD_GRIFFINDOR = new Faculty(1L, "Griffindor", "gold");
    public static final Faculty SLYTHERIN = new Faculty(2L, "Slytherin", "green");
    public static final Student POTTER = new Student(1L, "Harry Potter", 12);
    public static final Student UPD_POTTER = new Student(1L, "Harry Potter", 13);
    public static final Student MALFOY = new Student(2L, "Drako Malfoy", 13);
    public static final Student GRANGER = new Student(3L, "Hermiona Granger", 12);
    public static final int AGE = 12;
    public static final String COLOR = SLYTHERIN.getColor();
}
