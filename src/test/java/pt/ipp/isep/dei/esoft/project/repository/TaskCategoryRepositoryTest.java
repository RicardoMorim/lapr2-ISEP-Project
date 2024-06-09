package pt.ipp.isep.dei.esoft.project.repository;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.TaskCategory;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Task category repository test.
 */
class TaskCategoryRepositoryTest {

    /**
     * Gets task category by description empty list.
     */
    @Test
    void getTaskCategoryByDescriptionEmptyList() {
        TaskCategoryRepository taskCategoryRepository = new TaskCategoryRepository();
        String taskCategoryDescription = "Task Category Description";
        assertThrows(IllegalArgumentException.class,
                () -> taskCategoryRepository.getTaskCategoryByDescription(taskCategoryDescription));
    }

    /**
     * Gets task category by description null list.
     */
    @Test
    void getTaskCategoryByDescriptionNullList() {
        TaskCategoryRepository taskCategoryRepository = new TaskCategoryRepository();
        String taskCategoryDescription = "Task Category Description";
        assertThrows(IllegalArgumentException.class,
                () -> taskCategoryRepository.getTaskCategoryByDescription(taskCategoryDescription));
    }

    /**
     * Ensure new task category successfully added.
     */
    @Test
    void ensureNewTaskCategorySuccessfullyAdded() {
        TaskCategoryRepository taskCategoryRepository = new TaskCategoryRepository();
        String taskCategoryDescription = "Task Category Description";
        TaskCategory taskCategory = new TaskCategory(taskCategoryDescription);
        taskCategoryRepository.add(taskCategory);
    }

    /**
     * Ensure get task category for existing task category.
     */
    @Test
    void ensureGetTaskCategoryForExistingTaskCategory() {
        TaskCategoryRepository taskCategoryRepository = new TaskCategoryRepository();
        String taskCategoryDescription = "Task Category Description";
        TaskCategory taskCategory = new TaskCategory(taskCategoryDescription);
        taskCategoryRepository.add(taskCategory);
        TaskCategory taskCategory1 = taskCategoryRepository.getTaskCategoryByDescription(taskCategoryDescription);
        assertEquals(taskCategory, taskCategory1);
    }

    /**
     * Ensure get task category fails for non existing task category.
     */
    @Test
    void ensureGetTaskCategoryFailsForNonExistingTaskCategory() {
        TaskCategoryRepository taskCategoryRepository = new TaskCategoryRepository();
        String taskCategoryDescription = "Task Category Description";
        TaskCategory taskCategory = new TaskCategory(taskCategoryDescription);
        taskCategoryRepository.add(taskCategory);
        String taskCategoryDescription1 = "Task Category Description 1";
        assertThrows(IllegalArgumentException.class,
                () -> taskCategoryRepository.getTaskCategoryByDescription(taskCategoryDescription1));

    }

    /**
     * Ensure get task categories returns an immutable list.
     */
    @Test
    void ensureGetTaskCategoriesReturnsAnImmutableList() {
        TaskCategoryRepository taskCategoryRepository = new TaskCategoryRepository();
        String taskCategoryDescription = "Task Category Description";
        TaskCategory taskCategory = new TaskCategory(taskCategoryDescription);
        taskCategoryRepository.add(taskCategory);

        assertThrows(UnsupportedOperationException.class,
                () -> taskCategoryRepository.getTaskCategories().add(new TaskCategory("Task Category Description 1")));

    }

    /**
     * Ensure get task categories returns the correct list.
     */
    @Test
    void ensureGetTaskCategoriesReturnsTheCorrectList() {
        //Arrange
        TaskCategoryRepository taskCategoryRepository = new TaskCategoryRepository();
        String taskCategoryDescription = "Task Category Description";
        TaskCategory taskCategory = new TaskCategory(taskCategoryDescription);
        taskCategoryRepository.add(taskCategory);
        int expectedSize = 1;

        //Act
        int size = taskCategoryRepository.getTaskCategories().size();

        //Assert
        assertEquals(expectedSize, size);
        assertEquals(taskCategory, taskCategoryRepository.getTaskCategories().get(size - 1));
    }

    /**
     * Ensure adding duplicate task category fails.
     */
    @Test
    void ensureAddingDuplicateTaskCategoryFails() {
        //Arrange
        TaskCategoryRepository taskCategoryRepository = new TaskCategoryRepository();
        TaskCategory taskCategory = new TaskCategory("Task Category Description");
        //Add the first task
        taskCategoryRepository.add(taskCategory);

        //Act
        Optional<TaskCategory> duplicateTaskCategory = taskCategoryRepository.add(taskCategory);

        //Assert
        assertTrue(duplicateTaskCategory.isEmpty());
    }

    /**
     * Ensure adding different task categories works.
     */
    @Test
    void ensureAddingDifferentTaskCategoriesWorks() {
        //Arrange
        TaskCategoryRepository taskCategoryRepository = new TaskCategoryRepository();
        TaskCategory taskCategoryOne = new TaskCategory("Task Category Description One");
        TaskCategory taskCategoryTwo = new TaskCategory("Task Category Description Two");
        //Add the first task
        taskCategoryRepository.add(taskCategoryOne);

        //Act
        Optional<TaskCategory> result = taskCategoryRepository.add(taskCategoryTwo);

        //Assert
        assertEquals(taskCategoryTwo, result.get());
    }
}