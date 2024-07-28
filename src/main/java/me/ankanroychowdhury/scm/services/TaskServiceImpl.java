package me.ankanroychowdhury.scm.services;

import me.ankanroychowdhury.scm.entities.Task;
import me.ankanroychowdhury.scm.entities.User;
import me.ankanroychowdhury.scm.repositories.TaskRepository;
import me.ankanroychowdhury.scm.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public Task addTask(Task task) throws Exception {
        try {
           return this.taskRepository.save(task);
        }catch(Exception e) {
            e.printStackTrace();
            if(e.getCause() instanceof SQLIntegrityConstraintViolationException) {
                throw new SQLIntegrityConstraintViolationException(e.getMessage());
            }
            throw new RuntimeException(e);
        }
    }

    public Map<String, List<Task>> getTasks(User user) throws Exception {
        try {
            List<Task> maintainingTasks = this.taskRepository.findTasksByAssignor(user);
            List<Task> assignedTasks = this.taskRepository.findTasksByAssignees(List.of(user));
            Map<String, List<Task>> allTasks = new HashMap<>();
            allTasks.put("assignedTasks", assignedTasks);
            allTasks.put("maintainingTasks", maintainingTasks);
            return allTasks;
        }catch(Exception e){
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    @Override
    public Boolean assignTask(List<String> emailsToAssign, Long taskId) throws Exception{
        try {
            List<User> assignees = this.userRepository.findAllByEmailIn(emailsToAssign);
            Task taskToAssign = this.taskRepository.findById(taskId).get();
            for(User assignee : assignees) {
                assignee.getAssignedTasks().add(taskToAssign);
            }
            this.userRepository.saveAll(assignees);
            return true;
        }catch(Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }

    }
}
