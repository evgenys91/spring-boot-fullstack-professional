package com.example.demo.project;

import com.example.demo.project.exception.BadRequestException;
import com.example.demo.student.Gender;
import com.example.demo.student.Student;
import com.example.demo.student.StudentRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock private ProjectRepository projectRepository;
    @Mock private StudentRepository studentRepository;
    private ProjectService underTest;

    @BeforeEach
    void setUp() {
        underTest = new ProjectService(projectRepository, studentRepository);
    }

    @Test
    void canGetAllProjects() {
        // when
        underTest.getAllProjects();
        // then
        verify(projectRepository).findAll();
    }

    @Test
    void canAddProject() {
        // given

        Project project = new Project(
                "Diploma"
        );

        // when
        underTest.addProject(project);

        // then
        ArgumentCaptor<Project> projectArgumentCaptor =
                ArgumentCaptor.forClass(Project.class);

        verify(projectRepository)
                .save(projectArgumentCaptor.capture());

        Project capturedProject = projectArgumentCaptor.getValue();

        assertThat(capturedProject).isEqualTo(project);
    }

    @Test
    void willThrowWhenTitleIsTaken() {
        // given
        Project project = new Project("Diploma");

        given(projectRepository.selectExistsProject(anyString()))
                .willReturn(true);

        // when
        // then
        assertThatThrownBy(() -> underTest.addProject(project))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Project " + project.getTitle() + " already taken");

        verify(studentRepository, never()).save(any());

    }

    @Test
    void canAddStudentToProject() {

        Project project = new Project("Diploma");

        given(projectRepository.findById(any()))
                .willReturn(Optional.of(project));

        Student student = new Student(
                "Jack",
                "jack@email.com",
                Gender.MALE
        );

        given(studentRepository.findById(any()))
                .willReturn(Optional.of(student));

        underTest.addStudentToProject(new Long(1), new Long(1));


        ArgumentCaptor<Project> projectArgumentCaptor =
                ArgumentCaptor.forClass(Project.class);

        verify(projectRepository)
                .save(projectArgumentCaptor.capture());

        Project capturedProject = projectArgumentCaptor.getValue();

        assertThat(capturedProject.getStudent()).isEqualTo(student);
    }



}
