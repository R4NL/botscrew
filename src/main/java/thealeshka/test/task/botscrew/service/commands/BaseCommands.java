package thealeshka.test.task.botscrew.service.commands;

public interface BaseCommands {
    String getDepartmentHead(String departmentName);

    String getDepartmentStatistic(String departmentName);

    String getDepartmentAvgSalary(String departmentName);

    String getDepartmentEmployeeCount(String departmentName);

    String globalSearch(String template);
}
