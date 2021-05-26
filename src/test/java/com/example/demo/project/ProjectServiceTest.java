package com.example.demo.project;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.demo.project.exception.BadRequestException;
import com.example.demo.student.Student;
import com.example.demo.student.StudentRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


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
        List<Project> projects = new ArrayList<>();
        projects.add(new Project());
        projects.add(new Project());
        projects.add(new Project());
        when(projectRepository.findAll()).thenReturn(projects);
        assertEquals(projects, projectService.getAllProjects());
    }

    @Test
    public void testAddProject() {
        Project project = new Project();
        when(projectRepository.save(any())).thenReturn(project);
        projectService.addProject(project);
        verify(projectRepository, times(1)).save(project);
    }

    @Test
    void willThrowWhenTitleIsTaken() {
        String title = "title";
        Project project = new Project();
        project.setTitle(title);
        when(projectRepository.selectExistsProject(title)).thenReturn(Boolean.TRUE);
        assertThatThrownBy(() -> projectService.addProject(project)).isInstanceOf(BadRequestException.class);
        verify(projectRepository, never()).save(project);
    }

    @Test
    void canAddStudentToProject() {
        Long projectId = 1L;
        Long studentId = 2L;
        Project project = new Project();
        Student student = new Student();
        student.setId(studentId);
        when(projectRepository.findById(eq(projectId))).thenReturn(Optional.of(project));
        when(studentRepository.findById(eq(studentId))).thenReturn(Optional.of(student));
        projectService.addStudentToProject(projectId, studentId);
        verify(projectRepository, times(1)).save(project);
        assertThat(project.getStudent()).isEqualTo(student);
    }


}
