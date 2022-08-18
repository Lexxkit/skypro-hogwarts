package com.lexxkit.hogwarts.school.service;

import com.lexxkit.hogwarts.school.model.Faculty;
import com.lexxkit.hogwarts.school.model.Student;

public class TestConstants {
    public static final Faculty GRIFFINDOR = new Faculty(0L, "Griffindor", "red");
    public static final Faculty UPD_GRIFFINDOR = new Faculty(1L, "Griffindor", "gold");
    public static final Faculty SLYTHERIN = new Faculty(2L, "Slytherin", "green");
    public static final Student POTTER = new Student(0L, "Harry Potter", 12);
    public static final Student UPD_POTTER = new Student(1L, "Harry Potter", 13);
    public static final Student MALFOY = new Student(0L, "Drako Malfoy", 12);
}
