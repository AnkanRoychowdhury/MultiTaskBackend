package me.ankanroychowdhury.scm.adapters;

import me.ankanroychowdhury.scm.dtos.TaskRequestDTO;
import me.ankanroychowdhury.scm.dtos.TaskResponseDTO;
import me.ankanroychowdhury.scm.entities.Task;

import java.util.List;

public interface TaskDtoToTaskAdapter {
    Task convertRequestDTO(TaskRequestDTO taskRequestDTO) throws Exception;
    List<TaskResponseDTO> from(List<Task> task) throws Exception;
}
