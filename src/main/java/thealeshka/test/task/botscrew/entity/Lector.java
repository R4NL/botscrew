package thealeshka.test.task.botscrew.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import thealeshka.test.task.botscrew.utils.Degree;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

import static java.util.Objects.nonNull;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_lector")
@Builder
public class Lector {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(unique = true)
    private Long id;

    private String name;

    private String surname;

    private Long salary;

    @ManyToMany(fetch = EAGER, cascade = ALL, mappedBy = "lectors", targetEntity = Department.class)
    private Collection<Department> departments;

    private Degree degree;

    public void addDepartment(Department department) {
        if (nonNull(department)) {
            department.getLectors().add(this);
            this.getDepartments().add(department);
        } else throw new IllegalArgumentException("get null");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lector)) return false;

        Lector lector = (Lector) o;

        if (!Objects.equals(id, lector.id)) return false;
        if (!Objects.equals(name, lector.name)) return false;
        if (!Objects.equals(surname, lector.surname)) return false;
        if (!Objects.equals(salary, lector.salary)) return false;
        if (!Objects.equals(departments, lector.departments)) return false;
        return degree == lector.degree;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (salary != null ? salary.hashCode() : 0);
        result = 31 * result + (degree != null ? degree.hashCode() : 0);
        return result;
    }
}
