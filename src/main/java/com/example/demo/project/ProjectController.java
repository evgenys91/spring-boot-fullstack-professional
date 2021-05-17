package com.example.demo.project;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping(path = "projects")
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping(path = "projects/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable("projectId") Long projectId) {
        Optional<Project> project = projectService.getProjectById(projectId);
        return project.map(response -> ResponseEntity.ok().body(response)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(path = "projects")
    public void addProject(@Valid @RequestBody Project project) {
        projectService.addProject(project);
    }

    @PostMapping(path = "projects/{projectId}/{studentId}")
    public void assignProjectToStudent(@PathVariable("projectId") Long projectId, @PathVariable("studentId") Long studentId) {
        projectService.addStudentToProject(projectId, studentId);
    }

    @DeleteMapping(path = "projects/{projectId}")
    public void deleteProject(
            @PathVariable("projectId") Long projectId) {
        projectService.deleteProject(projectId);
    }
}
