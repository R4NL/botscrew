package thealeshka.test.task.botscrew.service.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import thealeshka.test.task.botscrew.service.command.line.CommandLineReader;
import thealeshka.test.task.botscrew.service.parcer.Parser;

@Component
@Slf4j
public class Scheduler {
    private final CommandLineReader reader;
    private final Parser parser;

    public Scheduler(CommandLineReader reader, Parser parser) {
        this.reader = reader;
        this.parser = parser;
    }


    @Scheduled(fixedDelay = 100)
    private void readCommandAndExecute() {
        try {
            String command = reader.readLine();
            log.info("read command {}", command);
            String result = parser.readStringAndChooseMethod(command);
            log.info("result of command {} is: {}", command, result.replaceAll("\n", " "));
            System.out.println(result);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
