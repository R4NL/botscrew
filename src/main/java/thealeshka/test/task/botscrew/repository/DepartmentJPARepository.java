package thealeshka.test.task.botscrew.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import thealeshka.test.task.botscrew.entity.Department;

public interface DepartmentJPARepository extends JpaRepository<Department, Long> {
    Department findDepartmentByName(String s);
}
