package me.ankanroychowdhury.scm.controllers;

import me.ankanroychowdhury.scm.entities.Task;
import me.ankanroychowdhury.scm.services.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {
    private final TaskService taskService;
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<?> addTask(@RequestBody Task request) {
        try {
            Task task = this.taskService.addTask(request);
            return new ResponseEntity<>(task, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity<?> getAllTasks() {
        try {
            List<Task> tasks = this.taskService.getTasks();
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
