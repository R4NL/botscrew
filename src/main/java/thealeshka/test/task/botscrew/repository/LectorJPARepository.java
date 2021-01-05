package thealeshka.test.task.botscrew.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import thealeshka.test.task.botscrew.entity.Lector;

public interface LectorJPARepository extends JpaRepository<Lector, Long> {
}
