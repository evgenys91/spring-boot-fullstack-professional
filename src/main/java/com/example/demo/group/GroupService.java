package com.example.demo.group;

import com.example.demo.group.exception.GroupNotFoundException;
import com.example.demo.student.Student;
import com.example.demo.student.StudentRepository;
import com.example.demo.student.exception.StudentNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    public Optional<Group> getGroupById(Long id) {
        return groupRepository.findById(id);
    }

    public void addGroup(Group group) {
        groupRepository.save(group);
    }

    public void deleteGroup(Long groupId) {
        if(!groupRepository.existsById(groupId)) {
            throw new GroupNotFoundException(
                    "Group with id " + groupId + " does not exists");
        }
        groupRepository.deleteById(groupId);
    }

    public void addStudentToGroup(Long groupId, Long studentId) {
        Optional<Group> groupResponse = groupRepository.findById(groupId);
        if(!groupResponse.isPresent()) {
            throw new GroupNotFoundException(
                    "Group with id " + groupId + " does not exists");
        }

        Optional<Student> studentResponse = studentRepository.findById(studentId);
        if(!studentResponse.isPresent()) {
            throw new StudentNotFoundException(
                    "Student wit hid " + studentId + " does not exists");
        }

        Group group = groupResponse.get();
        Student student = studentResponse.get();

        group.setStudent(student);
        groupRepository.save(group);
    }
}

