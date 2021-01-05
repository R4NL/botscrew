package thealeshka.test.task.botscrew.service.command.line;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Scanner;

@Component
@Slf4j
public class CommandLineReaderImpl implements CommandLineReader {
    private Scanner scanner;

    @PostConstruct
    public void init() {
        scanner = new Scanner(System.in);
    }

    @Override
    public String readLine() {
        try {
            String line = replaceDoubleSpace(scanner.nextLine());
            log.info("read line {}", line);
            return line;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public String replaceDoubleSpace(String line) {
        while (line.contains("  ")) {
            line = line.replaceAll(" {2}", " ");
        }
        return line;
    }
}
