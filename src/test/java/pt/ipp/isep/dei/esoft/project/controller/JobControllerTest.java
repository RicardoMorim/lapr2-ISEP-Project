package pt.ipp.isep.dei.esoft.project.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.application.controller.JobController;
import pt.ipp.isep.dei.esoft.project.domain.Job;
import pt.ipp.isep.dei.esoft.project.repository.JobRepository;

import java.util.ArrayList;
import java.util.Collections;

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