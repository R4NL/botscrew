package thealeshka.test.task.botscrew.utils;


import lombok.Getter;

@Getter
public enum Degree {
    assistant("Assistant"), associateProfessor("Associate professor"), professor("Professor");

    private final String value;

    Degree(String value) {
        this.value = value;
    }
}
