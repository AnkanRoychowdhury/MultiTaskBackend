package me.ankanroychowdhury.scm.services;

import me.ankanroychowdhury.scm.entities.Task;
import me.ankanroychowdhury.scm.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task addTask(Task task) throws Exception {
        try {
           return taskRepository.save(task);
        }catch(Exception e) {
            e.printStackTrace();
            if(e.getCause() instanceof SQLIntegrityConstraintViolationException) {
                throw new SQLIntegrityConstraintViolationException(e.getMessage());
            }
            throw new RuntimeException(e);
        }
    }

    public List<Task> getTasks(){
        try {
            return this.taskRepository.findAll();
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
