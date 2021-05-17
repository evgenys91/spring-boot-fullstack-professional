package com.example.demo.project;

import com.example.demo.student.Student;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@ToString
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "project")
public class Project {

    @Id
    @SequenceGenerator(
            name = "project_sequence",
            sequenceName = "project_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "project_sequence",
            strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name="student_id")
    private Student student;

    public Project(String title) {
        this.title = title;
    }
}
