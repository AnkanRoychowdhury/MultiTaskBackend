package me.ankanroychowdhury.scm.dtos;

import jakarta.persistence.*;
import lombok.*;
import me.ankanroychowdhury.scm.entities.TaskStatus;
import me.ankanroychowdhury.scm.entities.User;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequestDTO {
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private Date dueDate;
}
