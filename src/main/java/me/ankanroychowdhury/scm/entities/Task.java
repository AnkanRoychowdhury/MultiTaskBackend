package me.ankanroychowdhury.scm.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Task extends AuditBaseModel {

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @ColumnDefault("INPROGRESS")
    private TaskStatus taskStatus;

    private Date startDate;

    private Date endDate;

    private Date dueDate;

    @ManyToMany(mappedBy = "assignedTasks", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<User> assignees;

    @ManyToOne
    private User assignor;
}
