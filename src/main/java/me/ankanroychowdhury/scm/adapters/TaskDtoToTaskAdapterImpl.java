package me.ankanroychowdhury.scm.adapters;

import me.ankanroychowdhury.scm.dtos.TaskRequestDTO;
import me.ankanroychowdhury.scm.dtos.TaskResponseDTO;
import me.ankanroychowdhury.scm.entities.Task;
import me.ankanroychowdhury.scm.entities.TaskStatus;
import me.ankanroychowdhury.scm.entities.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TaskDtoToTaskAdapterImpl implements TaskDtoToTaskAdapter {


    @Override
    public Task convertRequestDTO(TaskRequestDTO taskRequestDTO) throws Exception{
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            return Task.builder()
                    .title(taskRequestDTO.getTitle())
                    .description(taskRequestDTO.getDescription())
                    .taskStatus(TaskStatus.INPROGRESS)
                    .startDate(taskRequestDTO.getStartDate())
                    .dueDate(taskRequestDTO.getDueDate())
                    .endDate(taskRequestDTO.getEndDate())
                    .assignor(user)
                    .build();
        }catch(Exception e){
            throw new Exception(e);
        }

    }

    @Override
    public List<TaskResponseDTO> from(List<Task> tasks) throws Exception {
        try {
            List<TaskResponseDTO> taskResponseDTOS = new ArrayList<>();
            for (Task task : tasks) {
                taskResponseDTOS.add(fromTask(task));
            }
            return taskResponseDTOS;
        }catch(Exception e){
            throw new Exception(e);
        }
    }

    private static TaskResponseDTO fromTask(Task task) throws Exception {
        return TaskResponseDTO.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .startDate(task.getStartDate())
                .dueDate(task.getDueDate())
                .endDate(task.getEndDate())
                .build();
    }
}
