package me.ankanroychowdhury.scm.services;

import me.ankanroychowdhury.scm.entities.Task;
import me.ankanroychowdhury.scm.entities.User;

import java.util.List;
import java.util.Map;

public interface TaskService {
    Task addTask(Task task) throws Exception;
    Map<String, List<Task>> getTasks(User user) throws Exception;

    Boolean assignTask(List<String> emailsToAssign, Long taskId) throws Exception;
}
