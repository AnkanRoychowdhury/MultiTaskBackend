package me.ankanroychowdhury.scm.repositories;

import me.ankanroychowdhury.scm.entities.Task;
import me.ankanroychowdhury.scm.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{
    List<Task> findTasksByAssignees(List<User> assignees);
    List<Task> findTasksByAssignor(User assignor);
}
