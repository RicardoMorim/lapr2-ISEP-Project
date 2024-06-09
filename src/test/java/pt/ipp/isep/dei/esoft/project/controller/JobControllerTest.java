package pt.ipp.isep.dei.esoft.project.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.application.controller.CollaboratorController;
import pt.ipp.isep.dei.esoft.project.application.controller.JobController;
import pt.ipp.isep.dei.esoft.project.domain.Address;
import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.Job;
import pt.ipp.isep.dei.esoft.project.domain.Skill;
import pt.ipp.isep.dei.esoft.project.repository.CollaboratorRepository;
import pt.ipp.isep.dei.esoft.project.repository.JobRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JobControllerTest {

    private JobRepository jobRepository;
    private JobController jobController;

    @BeforeEach
    void setUp() {
        jobRepository = new JobRepository();
        jobController = new JobController(jobRepository);
    }

    @Test
    void registerJobSuccessfully() {
        Job job = new Job("Developer", "Short Description");

        assertDoesNotThrow(() -> jobController.registerJob(job));
        assertTrue(jobRepository.getJobs().contains(job));
    }

    @Test
    void registerJobWithExistingJobThrowsException() {
        Job job = new Job("Developer", "Short Description");
        jobController.registerJob(job);

        assertThrows(IllegalArgumentException.class, () -> jobController.registerJob(job));
    }

    @Test
    void registerJobWithNullNameThrowsException() {
        Job job = new Job(null, "Short Description");

        assertThrows(IllegalArgumentException.class, () -> jobController.registerJob(job));
    }

    @Test
    void registerJobWithEmptyNameThrowsException() {
        Job job = new Job("", "Short Description");

        assertThrows(IllegalArgumentException.class, () -> jobController.registerJob(job));
    }

    @Test
    void updateJobSuccessfully() {
        Job oldJob = new Job("Developer", "Short Description");
        Job newJob = new Job("Tester", "Short Description");

        // First, register the old job
        jobController.registerJob(oldJob);

        // Then, update the job
        assertDoesNotThrow(() -> jobController.updateJob(oldJob, newJob));

        // Check that the job list now contains the new job
        assertTrue(jobRepository.getJobs().contains(newJob));

        // And that it no longer contains the old job
        assertFalse(jobRepository.getJobs().contains(oldJob));
    }

    @Test
    void removeJobSuccessfully() {
        Job job = new Job("Developer", "Short Description");

        // First, register the job
        jobController.registerJob(job);

        // Then, remove the job
        assertDoesNotThrow(() -> jobController.removeJob(job));

        // Check that the job list no longer contains the job
        assertFalse(jobRepository.getJobs().contains(job));
    }



    @Test
    void testGetCollaboratorRepository() {
        CollaboratorRepository expected = new CollaboratorRepository();
        CollaboratorController controller = new CollaboratorController(expected);
        assertEquals(expected, controller.getCollaboratorRepository());
    }

    @Test
    void testGetCollaboratorList() {
        CollaboratorRepository repository = new CollaboratorRepository();
        CollaboratorController controller = new CollaboratorController(repository);
        Collaborator collaborator = new Collaborator("john.doe@example.com", "John Doe", new Address("123 Street", "Porto", "123-456"), "1234567890", new Job("Developer", "java developer"), new Date(), new Date(), "ID", 123456, 123456, List.of(new Skill("Java", "Code")));
        repository.add(collaborator);
        List<Collaborator> expected = List.of(collaborator);
        assertEquals(expected, controller.getCollaboratorList());
    }

    @Test
    void testRegisterCollaboratorWithSkills() {
        CollaboratorRepository repository = new CollaboratorRepository();
        CollaboratorController controller = new CollaboratorController(repository);
        List<Skill> skills = List.of(new Skill("Java", "Code"));
        Collaborator collaborator = controller.registerCollaborator("John Doe", "john.doe@example.com", new Address("123 Street", "Porto", "123-456"), "1234567890", new Job("Developer", "java developer"), skills, new Date(), new Date(), "ID", 123456, 123456);
        assertEquals(skills, collaborator.getSkills());
    }

    @Test
    void registerJobShouldAddJobToRepository() {
        Job job = new Job("Developer", "Short Description");
        jobController.registerJob(job);
        assertTrue(jobRepository.getJobs().contains(job));
    }

    @Test
    void registerJobShouldThrowExceptionWhenJobAlreadyExists() {
        Job job = new Job("Developer", "Short Description");
        jobController.registerJob(job);
        assertThrows(IllegalArgumentException.class, () -> jobController.registerJob(job));
    }

    @Test
    void registerJobShouldThrowExceptionWhenNameIsNull() {
        Job job = new Job(null, "Short Description");
        assertThrows(IllegalArgumentException.class, () -> jobController.registerJob(job));
    }

    @Test
    void registerJobShouldThrowExceptionWhenNameIsEmpty() {
        Job job = new Job("", "Short Description");
        assertThrows(IllegalArgumentException.class, () -> jobController.registerJob(job));
    }

    @Test
    void updateJobShouldUpdateJobInRepository() {
        Job oldJob = new Job("Developer", "Short Description");
        Job newJob = new Job("Tester", "Short Description");
        jobController.registerJob(oldJob);
        jobController.updateJob(oldJob, newJob);
        assertTrue(jobRepository.getJobs().contains(newJob));
        assertFalse(jobRepository.getJobs().contains(oldJob));
    }

    @Test
    void removeJobShouldRemoveJobFromRepository() {
        Job job = new Job("Developer", "Short Description");
        jobController.registerJob(job);
        jobController.removeJob(job);
        assertFalse(jobRepository.getJobs().contains(job));
    }
}