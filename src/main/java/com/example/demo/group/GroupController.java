package com.example.demo.group;

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
public class GroupController {

    private final GroupService groupService;

    @GetMapping(path = "groups")
    public List<Group> getAllGroups() {
        return groupService.getAllGroups();
    }

    @GetMapping(path = "groups/{groupId}")
    public ResponseEntity<?> getGroupById(@PathVariable("groupId") Long groupId) {
        Optional<Group> group = groupService.getGroupById(groupId);
        return group.map(response -> ResponseEntity.ok().body(response)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(path = "groups")
    public void addGroup(@Valid @RequestBody Group group) {
        groupService.addGroup(group);
    }

    @PostMapping(path = "groups/{groupId}/{studentId}")
    public void addStudentToGroup(@PathVariable("groupId") Long groupId, @PathVariable("studentId") Long studentId) {
        groupService.addStudentToGroup(groupId, studentId);
    }

    @DeleteMapping(path = "groups/{groupId}")
    public void deleteGroup(
            @PathVariable("groupId") Long groupId) {
        groupService.deleteGroup(groupId);
    }
}
