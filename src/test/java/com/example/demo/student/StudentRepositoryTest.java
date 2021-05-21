package com.example.demo.student;

import com.example.demo.project.Project;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @AfterEach
    void tearDown() {
        studentRepository.deleteAll();
    }

    @Test
    void itShouldCheckWhenStudentEmailExists() {
        //TODO fix test
        String email = "mail@test.com";
        Student student = new Student();
        student.setEmail(EMAIL);
        studentRepository.save(new Student());
        assertThat(studentRepository.selectExistsEmail(email)).isFalse();
    }

    @Test
    void itShouldCheckWhenStudentEmailDoesNotExists() {
        //TODO fix test
        String email = "mail@test.com";
        Student student = new Student();
        student.setEmail(EMAIL);
        assertThat(studentRepository.selectExistsEmail(email)).isFalse();
        verify(studentRepository).selectExistsEmail(email);
    }

}