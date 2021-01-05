package thealeshka.test.task.botscrew.service.commands;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import thealeshka.test.task.botscrew.entity.Department;
import thealeshka.test.task.botscrew.entity.Lector;
import thealeshka.test.task.botscrew.repository.DepartmentJPARepository;
import thealeshka.test.task.botscrew.repository.LectorJPARepository;
import thealeshka.test.task.botscrew.utils.Degree;

import java.util.Collection;
import java.util.NoSuchElementException;

import static java.util.Objects.isNull;

@Component
public class BaseCommandsImpl implements BaseCommands {
    final DepartmentJPARepository departmentJPARepository;
    final LectorJPARepository lectorJPARepository;

    public BaseCommandsImpl(DepartmentJPARepository departmentJPARepository, LectorJPARepository lectorJPARepository) {
        this.departmentJPARepository = departmentJPARepository;
        this.lectorJPARepository = lectorJPARepository;
    }


    @Override
    public String getDepartmentHead(String departmentName) {
        Lector lector = findDepartmentByName(departmentName).getHeadOfDepartment();
        return "Head of " + departmentName + " department is " + lector.getName() + " " + lector.getSurname();
    }

    @Override
    public String getDepartmentStatistic(String departmentName) {
        Collection<Lector> lectors = findDepartmentByName(departmentName).getLectors();
        long assistantCount = lectors.stream().filter(n -> n.getDegree().equals(Degree.assistant)).count();
        long associateProfessorCount = lectors.stream().filter(n -> n.getDegree().equals(Degree.associateProfessor)).count();
        long professorCount = lectors.stream().filter(n -> n.getDegree().equals(Degree.professor)).count();
        return "assistants - " + assistantCount + "." + System.lineSeparator() +
                "associate professors - " + associateProfessorCount + "." + System.lineSeparator() +
                "professors - " + professorCount + "." + System.lineSeparator();
    }

    @Override
    public String getDepartmentAvgSalary(String departmentName) {
        Collection<Lector> lectors = findDepartmentByName(departmentName).getLectors();
        double averageSalary = lectors.stream().mapToDouble(Lector::getSalary).average().orElseThrow();
        return "The average salary of " + departmentName + " is " + String.format("%.2f", averageSalary);
    }

    @Override
    public String getDepartmentEmployeeCount(String departmentName) {
        Collection<Lector> lectors = findDepartmentByName(departmentName).getLectors();
        return String.valueOf(lectors.size());
    }

    @Override
    public String globalSearch(String template) {
        Collection<Lector> lectors = lectorJPARepository.findAll();
        StringBuilder result = new StringBuilder();
        lectors.stream()
                .map(n -> n.getName() + " " + n.getSurname())
                .filter(n -> StringUtils.containsIgnoreCase(n, template))
                .map(n -> n + ",")
                .forEach(result::append);
        return result.replace(result.lastIndexOf(","), result.lastIndexOf(",") + 1, ".").toString();
    }

    private Department findDepartmentByName(String departmentName) {
        Department department = departmentJPARepository.findDepartmentByName(departmentName);
        if (isNull(department))
            throw new NoSuchElementException(departmentName);
        return department;
    }

}
