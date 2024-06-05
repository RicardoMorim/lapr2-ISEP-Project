package pt.ipp.isep.dei.esoft.project.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Repositories test.
 */
class RepositoriesTest {

    /**
     * Test get instance.
     */
    @Test
    void testGetInstance() {
        Repositories instance = Repositories.getInstance();
        assertNotNull(instance);
    }

    /**
     * Test get organization repository.
     */
    @Test
    void testGetOrganizationRepository() {
        Repositories instance = Repositories.getInstance();
        assertNotNull(instance.getOrganizationRepository());
    }

    /**
     * Test get task category repository.
     */
    @Test
    void testGetTaskCategoryRepository() {
        Repositories instance = Repositories.getInstance();
        assertNotNull(instance.getTaskCategoryRepository());
    }

    private Repositories repositories;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        repositories = Repositories.getInstance();
    }

    /**
     * Gets agenda repository should return non null agenda.
     */
    @Test
    void getAgendaRepositoryShouldReturnNonNullAgenda() {
        assertNotNull(repositories.getAgendaRepository());
    }

    /**
     * Gets agenda repository should return same instance.
     */
    @Test
    void getAgendaRepositoryShouldReturnSameInstance() {
        assertSame(repositories.getAgendaRepository(), repositories.getAgendaRepository());
    }

}