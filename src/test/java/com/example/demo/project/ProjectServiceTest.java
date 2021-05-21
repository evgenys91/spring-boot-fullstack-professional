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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private StudentRepository studentRepository;
    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        projectService = new ProjectService(projectRepository, studentRepository);
    }

    @Test
    public void canGetAllProjects() {
        //TODO fix test
        List<Project> projects = new ArrayList<>();
        projects.add(new Project());
        when(projectRepository.findAll()).thenReturn(projects);
        assertEquals(projects, service.getAllProject());
    }

    @Test
    public void testAddProject() {
        //TODO fix test
        Project project = new Project();
        project.setTitle(TITLE);
        when(repository.selectExistsProject(TITLE)).thenReturn(Boolean.TRUE);
        projectService.addProject(project);
        ArgumentCaptor<Project> projectArgumentCaptor = ArgumentCaptor.forClass(Project.class);
        verify(repository).save(projectArgumentCaptor.capture());
        Project capturedProject = projectArgumentCaptor.getValue();
        assertThat(capturedProject).isEqualTo(project);
    }

    @Test
    void willThrowWhenTitleIsTaken() {
        //TODO fix test
        Project project = new Project();
        project.setTitle(TITLE);
        given(repository.selectExistsProject(anyString())).willReturn(Boolean.TRUE);
        assertThatThrownBy(() -> projectService.addProject(new Project())).isInstanceOf(BadRequestException.class);
        verify(studentRepository, never()).save(any());
    }

    @Test
    void canAddStudentToProject() {
        //TODO fix test
        Project project = new Project();
        project.setTitle(TITLE);
        Student student = new Student();
        given(projectRepository.findById(any())).willReturn(Optional.of(project));
        given(studentRepository.findById(any())).willReturn(Optional.of(student));
        projectService.addStudentToProject(1L, 1L);
        ArgumentCaptor<Project> projectArgumentCaptor = ArgumentCaptor.forClass(Project.class);
        verify(projectRepository).save(projectArgumentCaptor.capture());
        Project capturedProject = projectArgumentCaptor.getValue();
        assertThat(capturedProject.getStudent()).isEqualTo(student);
    }


}
