package thealeshka.test.task.botscrew.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Table(name = "t_department")
@Builder
public class Department {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(unique = true)
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToOne(targetEntity = Lector.class, cascade = ALL, fetch = EAGER)
    private Lector headOfDepartment;

    @ManyToMany(cascade = ALL, targetEntity = Lector.class, fetch = EAGER)
    private Collection<Lector> lectors;

    public void addLector(Lector lector) {
        if (nonNull(lector)) {
            lector.getDepartments().add(this);
            this.getLectors().add(lector);
        } else throw new IllegalArgumentException("get null");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department)) return false;

        Department that = (Department) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(headOfDepartment, that.headOfDepartment))
            return false;
        return Objects.equals(lectors, that.lectors);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (lectors != null ? lectors.hashCode() : 0);
        return result;
    }
}
