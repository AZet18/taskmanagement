package pl.taskmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.taskmanagement.entity.Tag;

public interface TagRepository extends JpaRepository <Tag, Long> {


}
