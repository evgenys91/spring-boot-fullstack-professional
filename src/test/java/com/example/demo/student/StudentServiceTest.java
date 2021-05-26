package com.example.demo.student;

import com.example.demo.student.Student;
import com.example.demo.student.exception.BadRequestException;
import com.example.demo.student.Student;
import com.example.demo.student.exception.StudentNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository repository;
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentService = new StudentService(repository);
    }

    @Test
    void canGetAllStudents() {
        //TODO fix test
        List<Student> students = new ArrayList<>();
        students.add(new Student());
        when(repository.findAll()).thenReturn(students);
        assertEquals(students, studentService.getAllStudents());
    }

    @Test
    public void testAddStudent() {
        //TODO fix test
        Student student = new Student();
        String email = "mail@test.com";
        student.setEmail(email);
        when(repository.selectExistsEmail(email)).thenReturn(Boolean.FALSE);
        studentService.addStudent(student);
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(repository, times(1)).save(studentArgumentCaptor.capture());
        Student capturedStudent = studentArgumentCaptor.getValue();
        assertThat(capturedStudent).isEqualTo(student);
    }

    @Test
    void willThrowWhenTitleIsTaken() {
        //TODO fix test
        Student student = new Student();
        String email = "mail@test.com";
        student.setEmail(email);
        when(repository.selectExistsEmail(anyString())).thenReturn(Boolean.TRUE);
        assertThatThrownBy(() -> studentService.addStudent(student)).isInstanceOf(BadRequestException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void willThrowWhenDeleteStudentNotFound() {
        //TODO fix test
        long id = 10;
        given(repository.existsById(id)).willReturn(false);
        assertThatThrownBy(() -> studentService.deleteStudent(id)).isInstanceOf(StudentNotFoundException.class);
        verify(repository, never()).deleteById(any());
    }

    @Test
    void canDeleteStudent() {
        //TODO fix test
        long id = 10;
        given(repository.existsById(id)).willReturn(true);
        studentService.deleteStudent(id);
        verify(repository, times(1)).deleteById(id);
    }


}