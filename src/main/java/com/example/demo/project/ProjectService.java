package com.example.demo.project;

import com.example.demo.project.exception.BadRequestException;
import com.example.demo.project.exception.ProjectNotFoundException;
import com.example.demo.student.Student;
import com.example.demo.student.StudentRepository;
import com.example.demo.student.exception.StudentNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final StudentRepository studentRepository;

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }

    public void addProject(Project project) {
        Boolean projectExists = projectRepository.selectExistsProject(project.getTitle());
        if(projectExists) {
            throw new BadRequestException("Project " + project.getTitle() + " already taken");
        }
        projectRepository.save(project);
    }

    public void deleteProject(Long project) {
        if(!projectRepository.existsById(project)) {
            throw new ProjectNotFoundException(
                    "Project with id " + project + " does not exists");
        }
        projectRepository.deleteById(project);
    }

    public void addStudentToProject(Long projectId, Long studentId) {
        Optional<Project> projectResponse = projectRepository.findById(projectId);
        if(!projectResponse.isPresent()) {
            throw new ProjectNotFoundException(
                    "Project with id " + projectId + " does not exists");
        }

        if(Optional.ofNullable(projectResponse.get().getStudent()).isPresent()) {
            throw new BadRequestException("Project " + projectId + " already assigned to other student");
        }

        Optional<Student> studentResponse = studentRepository.findById(studentId);
        if(!studentResponse.isPresent()) {
            throw new StudentNotFoundException(
                    "Student wit hid " + studentId + " does not exists");
        }

        Project project = projectResponse.get();
        Student student = studentResponse.get();

        project.setStudent(student);
        projectRepository.save(project);
    }
}

