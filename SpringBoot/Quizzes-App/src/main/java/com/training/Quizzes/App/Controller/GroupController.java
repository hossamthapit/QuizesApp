package com.training.Quizzes.App.Controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.training.Quizzes.App.entity.Group;
import com.training.Quizzes.App.repository.ExamRepository;
import com.training.Quizzes.App.repository.GroupRepository;


@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api")
public class GroupController {

  @Autowired
  GroupRepository groupRepository;
  @Autowired
  ExamRepository examRepository;

  @GetMapping("/groups")
	public ResponseEntity<Page<Group>> getAllGroups(@RequestParam(required = false) String title,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {

		Page<Group> groupPage;

		if (title == null || title.isEmpty()) {
			groupPage = groupRepository.findAll(PageRequest.of(page, size));
		} else {
			groupPage = groupRepository.findByTitleContaining(title, PageRequest.of(page, size));
		}

		if (groupPage.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(groupPage, HttpStatus.OK);
	}

  @GetMapping("/groups/{id}")
  public ResponseEntity<Group> getGroupById(@PathVariable("id") int id) {
    Group group = groupRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Group with id = " + id));

    return new ResponseEntity<>(group, HttpStatus.OK);
  }

  @PostMapping("/groups")
  public ResponseEntity<Group> createTutorial(@RequestBody Group group) {
    Group tempGroup = groupRepository.save(new Group(group.getTitle(), group.getDescription()));
    return new ResponseEntity<>(tempGroup, HttpStatus.CREATED);
  }

  @PutMapping("/groups/{id}")
  public ResponseEntity<Group> updateGroup(@PathVariable("id") int id, @RequestBody Group group) {
    Group tempGroup = groupRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Group with id = " + id));

    tempGroup.setTitle(group.getTitle());
    tempGroup.setDescription(group.getDescription());
    
    return new ResponseEntity<>(groupRepository.save(tempGroup), HttpStatus.OK);
  }

  @DeleteMapping("/groups/{id}")
  public ResponseEntity<HttpStatus> deleteGroup(@PathVariable("id") int id) {
	  groupRepository.deleteById(id);
    
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping("/groups")
  public ResponseEntity<HttpStatus> deleteAllGroups() {
	  groupRepository.deleteAll();
    
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
  


}

