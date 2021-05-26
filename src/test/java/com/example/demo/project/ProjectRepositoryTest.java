package com.example.demo.project;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @AfterEach
    void tearDown() {
        projectRepository.deleteAll();
    }

    @Test
    void itShouldCheckWhenProjectTitleExists() {
        Project project = new Project();
        String title = "title";
        project.setTitle(title);
        assertThat(projectRepository.selectExistsProject(title)).isFalse();
        projectRepository.save(project);
        assertThat(projectRepository.selectExistsProject(title)).isTrue();
    }

    @Test
    void itShouldCheckWhenProjectTitleDoesNotExists() {
        String title = "title";
        assertThat(projectRepository.selectExistsProject(title)).isFalse();
    }

}
