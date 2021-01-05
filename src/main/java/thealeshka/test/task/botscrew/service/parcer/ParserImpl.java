package thealeshka.test.task.botscrew.service.parcer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import thealeshka.test.task.botscrew.service.commands.BaseCommands;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

import static thealeshka.test.task.botscrew.Constants.*;

@Component
@Slf4j
public class ParserImpl implements Parser {
    private final BaseCommands baseCommands;

    public ParserImpl(BaseCommands baseCommands) {
        this.baseCommands = baseCommands;
    }

    @Override
    public String readStringAndChooseMethod(String line) {
        try {
            if (line.contains(DEPARTMENT_HEAD))
                return baseCommands.getDepartmentHead(deleteSpaceAndDot(deleteFromLine(line, DEPARTMENT_HEAD)));
            if (line.matches(DEPARTMENT_STATISTIC_REG_EX)) {
                return baseCommands.getDepartmentStatistic(deleteSpaceAndDot(deleteFromLine(line, DEPARTMENT_STATISTIC)));
            }
            if (line.contains(DEPARTMENT_AVG_SALARY))
                return baseCommands.getDepartmentAvgSalary(deleteSpaceAndDot(deleteFromLine(line, DEPARTMENT_AVG_SALARY)));
            if (line.contains(DEPARTMENT_EMPLOYEE_COUNT))
                return baseCommands.getDepartmentEmployeeCount(deleteSpaceAndDot(deleteFromLine(line, DEPARTMENT_EMPLOYEE_COUNT)));
            if (line.contains(GLOBAL_SEARCH))
                return baseCommands.globalSearch(deleteSpaceAndDot(deleteFromLine(line, GLOBAL_SEARCH)));
            throw new NoSuchMethodException(line);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private String deleteSpaceAndDot(String line) {
        while (line.contains(" ")) {
            line = line.replace(" ", "");
        }
        return deleteDot(line);
    }

    private String deleteDot(String line) {
        String result = line;
        if (line.charAt(line.length() - 1) == '.')
            result = line.substring(0, line.lastIndexOf("."));
        return result;
    }

    private String deleteFromLine(String line, String remove) {
        AtomicReference<String> result = new AtomicReference<>(line);
        Arrays.stream(remove.split(" ")).forEach(n -> result.set(result.get().replaceAll(n, "")));
        return result.get();
    }
}
