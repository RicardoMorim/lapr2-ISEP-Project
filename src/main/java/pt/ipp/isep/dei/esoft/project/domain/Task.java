package pt.ipp.isep.dei.esoft.project.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * The Task class represents a task that an Employee can have.
 * Each task has a reference, description, informal description, technical description, duration, cost, task category, and an employee.
 */
public class Task implements Serializable {
    private final String reference;
    private String description;
    private String informalDescription;
    private String technicalDescription;
    private int duration;
    private double cost;

    private TaskCategory taskCategory;

    private Employee employee;

    /**
     * Instantiates a new Task.
     *
     * @param reference            the reference of the task
     * @param description          the description of the task
     * @param informalDescription  the informal description of the task
     * @param technicalDescription the technical description of the task
     * @param duration             the duration of the task
     * @param cost                 the cost of the task
     * @param taskCategory         the task category of the task
     * @param employee             the employee of the task
     */
    public Task(String reference, String description, String informalDescription, String technicalDescription,
                int duration, double cost, TaskCategory taskCategory, Employee employee) {

        validateReference(reference);
        this.reference = reference;
        this.description = description;
        this.informalDescription = informalDescription;
        this.technicalDescription = technicalDescription;
        this.duration = duration;
        this.cost = cost;
        this.taskCategory = taskCategory;
        this.employee = employee;
    }

    private void validateReference(String reference) {
        //TODO: missing from the diagrams
        if (reference == null || reference.isEmpty()) {
            throw new IllegalArgumentException("Reference cannot be null or empty.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Task)) {
            return false;
        }
        Task task = (Task) o;
        return reference.equals(task.reference) && employee.equals(task.employee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reference, employee);
    }


    /**
     * Clone method.
     *
     * @return A clone of the current instance.
     */
    public Task clone() {
        return new Task(this.reference, this.description, this.informalDescription, this.technicalDescription,
                this.duration, this.cost, this.taskCategory, this.employee);
    }
}