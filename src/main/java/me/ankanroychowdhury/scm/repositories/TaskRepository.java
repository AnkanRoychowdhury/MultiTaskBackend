package me.ankanroychowdhury.scm.repositories;

import me.ankanroychowdhury.scm.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

}
