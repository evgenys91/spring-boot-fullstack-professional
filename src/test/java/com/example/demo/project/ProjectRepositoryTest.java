package com.example.demo.project;

import com.example.demo.student.Gender;
import com.example.demo.student.Student;
import com.example.demo.student.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldCheckWhenProjectTitleExists() {
        // given
        String title = "Diploma title";
        Project project = new Project(title);
        underTest.save(project);

        // when
        boolean expected = underTest.selectExistsProject(title);

        // then
        assertThat(expected).isTrue();
    }

    @Test
    void itShouldCheckWhenProjectTitleDoesNotExists() {
        // given
        String title = "Diploma title";
        // when
        boolean expected = underTest.selectExistsProject(title);
        // then
        assertThat(expected).isFalse();
    }

}
