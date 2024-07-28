package me.ankanroychowdhury.scm.controllers;

import jakarta.servlet.http.HttpServletRequest;
import me.ankanroychowdhury.scm.adapters.TaskDtoToTaskAdapter;
import me.ankanroychowdhury.scm.dtos.TaskAssignRequestDTO;
import me.ankanroychowdhury.scm.dtos.TaskRequestDTO;
import me.ankanroychowdhury.scm.dtos.TaskResponseDTO;
import me.ankanroychowdhury.scm.entities.Task;
import me.ankanroychowdhury.scm.entities.User;
import me.ankanroychowdhury.scm.services.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {
    private final TaskService taskService;
    private final TaskDtoToTaskAdapter taskDtoToTaskAdapter;
    public TaskController(TaskService taskService, TaskDtoToTaskAdapter taskDtoToTaskAdapter) {
        this.taskService = taskService;
        this.taskDtoToTaskAdapter = taskDtoToTaskAdapter;
    }

    @PostMapping
    public ResponseEntity<?> addTask(@RequestBody TaskRequestDTO request) {
        try {
            Task task = this.taskService.addTask(this.taskDtoToTaskAdapter.convertRequestDTO(request));
            TaskResponseDTO response = TaskResponseDTO.builder()
                                       .title(task.getTitle())
                                       .description(task.getDescription())
                                       .startDate(task.getStartDate())
                                       .endDate(task.getEndDate())
                                       .dueDate(task.getDueDate())
                                       .build();
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity<?> getAllTasks() {
        try {
            User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Map<String, List<Task>> tasks = this.taskService.getTasks(principal);
            Map<String, List<TaskResponseDTO>> response = new HashMap<>();
            tasks.entrySet().iterator().forEachRemaining(entry -> {
                try {
                    response.put(entry.getKey(), this.taskDtoToTaskAdapter.from(entry.getValue()));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/assign/{taskId}")
    public ResponseEntity<?> assignTask(@RequestBody TaskAssignRequestDTO taskAssignRequestDTO, @PathVariable Long taskId) {
        try {
            List<String> emailsToAssign = taskAssignRequestDTO.getEmailsToAssign();
            Boolean response = this.taskService.assignTask(emailsToAssign, taskId);
            return new ResponseEntity<>("Task assigned: "+response, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
