package com.example.demo.project;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class ProjectRepositoryTest {

    @Mock
    private ProjectRepository projectRepository;

    @AfterEach
    void tearDown() {
        projectRepository.deleteAll();
    }

    @Test
    void itShouldCheckWhenProjectTitleExists() {
        //TODO implement test
    }

    @Test
    void itShouldCheckWhenProjectTitleDoesNotExists() {
        //TODO implement test
    }

}
