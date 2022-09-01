package com.lexxkit.hogwarts.school;

import com.lexxkit.hogwarts.school.controller.AvatarController;
import com.lexxkit.hogwarts.school.controller.FacultyController;
import com.lexxkit.hogwarts.school.controller.StudentController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SkyproHogwartsApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;
    @Autowired
    private FacultyController facultyController;
    @Autowired
    private AvatarController avatarController;
    @Test
    void contextLoads() {
        assertThat(studentController).isNotNull();
        assertThat(facultyController).isNotNull();
        assertThat(avatarController).isNotNull();
    }

}
