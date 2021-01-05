package thealeshka.test.task.botscrew;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import thealeshka.test.task.botscrew.entity.Department;
import thealeshka.test.task.botscrew.entity.Lector;
import thealeshka.test.task.botscrew.repository.DepartmentJPARepository;
import thealeshka.test.task.botscrew.repository.LectorJPARepository;
import thealeshka.test.task.botscrew.service.parcer.Parser;
import thealeshka.test.task.botscrew.utils.Degree;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@SpringBootTest
class BotsCrewApplicationTests {
    @Autowired
    DepartmentJPARepository departmentJPARepository;

    @Autowired
    LectorJPARepository lectorJPARepository;

    @Autowired
    Parser parser;

    Lector lector1;
    Lector lector2;
    Lector lector3;
    Lector lector4;
    Lector lector5;
    Lector lector6;
    Lector lector7;

    Department department1;
    Department department2;
    Department department3;

    @BeforeEach
    void beforeEach() {
        lector1 = buildLector();
        lector2 = buildLector();
        lector3 = buildLector();
        lector4 = buildLector();
        lector5 = buildLector();
        lector6 = buildLector();
        lector7 = buildLector();


        department1 = buildDepartment(lector1, List.of(lector1, lector2, lector3));
        department2 = buildDepartment(lector4, List.of(lector4, lector5, lector6));
        department3 = buildDepartment(lector7, List.of(lector3, lector4, lector7));

        lector1.addDepartment(department1);
        lector2.addDepartment(department1);
        lector5.addDepartment(department2);
        lector6.addDepartment(department2);
        lector7.addDepartment(department3);

        lector3.setDepartments(List.of(department1, department3));
        lector4.setDepartments(List.of(department2, department3));
        lectorJPARepository.save(lector1);
        lectorJPARepository.save(lector2);
        lectorJPARepository.save(lector3);
        lectorJPARepository.save(lector4);
        lectorJPARepository.save(lector5);
        lectorJPARepository.save(lector6);
        lectorJPARepository.save(lector7);
    }

    @AfterEach
    void afterEach() {
        departmentJPARepository.deleteAll();
        lectorJPARepository.deleteAll();
    }


    @Test
    void getDepartmentHead() {
        String expectedResult = "Head of " + department1.getName() + " department is " + department1.getHeadOfDepartment().getName() + " " + department1.getHeadOfDepartment().getSurname();
        String actualResult = parser.readStringAndChooseMethod("Who is head of department " + department1.getName());

        Assertions.assertEquals(expectedResult, actualResult, "verify actual result and expected result");
    }

    @Test
    void getDepartmentStatistic() {
        String expectedResult = "assistants - " + department1.getLectors().stream().filter(n -> n.getDegree().equals(Degree.assistant)).count() + ".\n" +
                "associate professors - " + department1.getLectors().stream().filter(n -> n.getDegree().equals(Degree.associateProfessor)).count() + ".\n" +
                "professors - " + department1.getLectors().stream().filter(n -> n.getDegree().equals(Degree.professor)).count() + ".\n";
        String actualResult = parser.readStringAndChooseMethod("Show " + department1.getName() + " statistics.");

        Assertions.assertEquals(expectedResult, actualResult, "verify actual result and expected result");
    }

    @Test
    void getDepartmentAvgSalary() {
        String expectedResult = "The average salary of " + department1.getName() + " is " + String.format("%.2f", department1.getLectors().stream().mapToDouble(Lector::getSalary).average().orElseThrow());
        String actualResult = parser.readStringAndChooseMethod("Show the average salary for the department " + department1.getName() + ".");

        Assertions.assertEquals(expectedResult, actualResult, "verify actual result and expected result");
    }

    @Test
    void getDepartmentEmployeeCount() {
        String expectedResult = String.valueOf(department1.getLectors().size());
        String actualResult = parser.readStringAndChooseMethod("Show count of employee for " + department1.getName() + ".");

        Assertions.assertEquals(expectedResult, actualResult, "verify actual result and expected result");
    }

    @Test
    void globalSearchPartName() {
        String expectedResult = lector4.getName() + " " + lector4.getSurname();
        String actualResult = parser.readStringAndChooseMethod("Global search by " + lector4.getName().substring(4, 7) + ".");

        Assertions.assertTrue(actualResult.contains(expectedResult));
    }

    @Test
    void globalSearchFullName() {
        String expectedResult = lector4.getName() + " " + lector4.getSurname() + ".";
        String actualResult = parser.readStringAndChooseMethod("Global search by " + lector4.getName() + ".");

        Assertions.assertEquals(expectedResult, actualResult, "verify actual result and expected result");
    }

    private Lector buildLector() {
        return Lector.builder()
                .name(UUID.randomUUID().toString())
                .surname(UUID.randomUUID().toString())
                .degree(Degree.values()[new Random().nextInt(Degree.values().length)])
                .salary(new Random().nextLong())
                .departments(new LinkedHashSet<>())
                .build();
    }

    private Department buildDepartment(Lector head, List<Lector> lectors) {
        return Department.builder()
                .headOfDepartment(head)
                .lectors(new LinkedHashSet<>(lectors))
                .name(UUID.randomUUID().toString())
                .build();
    }

}
