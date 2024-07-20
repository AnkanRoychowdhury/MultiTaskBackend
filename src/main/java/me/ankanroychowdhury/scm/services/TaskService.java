package me.ankanroychowdhury.scm.services;

import me.ankanroychowdhury.scm.entities.Task;

import java.util.List;

public interface TaskService {
    public Task addTask(Task task) throws Exception;
    public List<Task> getTasks() throws Exception;
}
