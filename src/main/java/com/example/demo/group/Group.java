package com.example.demo.group;

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
@Table(name = "students_group")
public class Group {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name="student_id")
    private Student student;

    public Group(String title) {
        this.title = title;
    }
}
