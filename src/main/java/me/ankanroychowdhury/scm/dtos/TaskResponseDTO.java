package me.ankanroychowdhury.scm.dtos;

import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponseDTO {
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private Date dueDate;
}
