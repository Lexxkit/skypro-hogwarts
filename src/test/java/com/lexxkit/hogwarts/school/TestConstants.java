package com.lexxkit.hogwarts.school;

import com.lexxkit.hogwarts.school.model.Faculty;
import com.lexxkit.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class TestConstants {
    public static final Faculty GRIFFINDOR = new Faculty(1L, "Griffindor", "red", null);
    public static final Faculty UPD_GRIFFINDOR = new Faculty(1L, "Griffindor", "gold", null);
    public static final Faculty SLYTHERIN = new Faculty(2L, "Slytherin", "green", null);
    public static final Student POTTER = new Student(1L, "Harry Potter", 12, GRIFFINDOR);
    public static final Student UPD_POTTER = new Student(1L, "Harry Potter", 13, null);
    public static final Student MALFOY = new Student(2L, "Drako Malfoy", 14, SLYTHERIN);
    public static final Student GRANGER = new Student(3L, "Hermiona Granger", 12, GRIFFINDOR);
    public static final Student TEST_STUDENT = new Student(1L, "Test student", 42, null);
    public static final Student UPDATE_TEST_STUDENT = new Student(1L, "Test student", 20, null);
    public static final int AGE = 12;
    public static final int MIN_AGE = AGE;
    public static final int MAX_AGE = 13;
    public static final String COLOR = SLYTHERIN.getColor();
    public static final String NAME = GRIFFINDOR.getName().toLowerCase();

    public static final String BASE_URL = "http://localhost:";

    public static final Integer NUMBER_OF_STUDENTS = 2;
    public static final double AVERAGE_AGE = AGE;

    public static final List<Student> STUDENTS_WITH_A = List.of(
            new Student(1L, "Alex", 20, null),
            new Student(1L, "Anna", 22, null)
    );

    public static final Collection<String> NAMES_A_CAPITALIZE = List.of("ALEX", "ANNA");

    public static final String THE_LONGEST_NAME = Stream.of(GRIFFINDOR, SLYTHERIN).map(Faculty::getName)
                                                    .max(Comparator.comparing(String::length)).get();
}
