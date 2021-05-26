package com.example.demo.student;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
        String email = "mail@test.com";
        Student student = new Student();
        student.setEmail(email);
        student.setName("TEST");
        student.setGender(Gender.OTHER);
        assertThat(studentRepository.selectExistsEmail(email)).isFalse();
        studentRepository.save(student);
        assertThat(studentRepository.selectExistsEmail(email)).isTrue();
    }

    @Test
    void itShouldCheckWhenStudentEmailDoesNotExists() {
        String email = "mail@test.com";
        assertThat(studentRepository.selectExistsEmail(email)).isFalse();
    }

}