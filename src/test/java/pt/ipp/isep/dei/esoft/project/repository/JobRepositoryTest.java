package pt.ipp.isep.dei.esoft.project.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.Job;
import pt.ipp.isep.dei.esoft.project.repository.JobRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Job repository test.
 */
class JobRepositoryTest {

    private JobRepository jobRepository;
    private Job job;

    /**
     * Sets up.
     *
     * @throws IllegalArgumentException the illegal argument exception
     */
    @BeforeEach
    void setUp() throws IllegalArgumentException {

        jobRepository = new JobRepository();
        List<Job> jobList = jobRepository.getJobs();

        for (Job j : jobList) {
            jobRepository.remove(j);
        }

        job = new Job("Developer", "Short Description");

    }

    /**
     * Add job returns optional with job.
     */
    @Test
    void addJobReturnsOptionalWithJob() {
        Optional<Job> result = jobRepository.add(job);
        assertTrue(result.isPresent());
        assertEquals(job, result.get());
    }

    /**
     * Add job throws error when job exists.
     */
    @Test
    void addJobThrowsErrorWhenJobExists() {

        jobRepository.add(job);
        assertThrows(IllegalArgumentException.class, () -> jobRepository.add(job));

    }

    /**
     * Remove job returns optional with job.
     */
    @Test
    void removeJobReturnsOptionalWithJob() {
        jobRepository.add(job);
        Optional<Job> result = jobRepository.remove(job);
        assertTrue(result.isPresent());
        assertEquals(job, result.get());
    }

    /**
     * Remove job returns empty optional when job does not exist.
     */
    @Test
    void removeJobReturnsEmptyOptionalWhenJobDoesNotExist() {
        assertThrows(IllegalArgumentException.class, () -> jobRepository.remove(job));
    }

    /**
     * Update job updates and returns job.
     */
    @Test
    void updateJobUpdatesAndReturnsJob() {
        jobRepository.add(job);
        Job updatedJob = jobRepository.update(job, "Manager", "Short Description").get();
        assertEquals("Manager", updatedJob.getName());
        assertEquals("Short Description", updatedJob.getShortDescription());
    }

    /**
     * Update job throws exception when job does not exist.
     */
    @Test
    void updateJobThrowsExceptionWhenJobDoesNotExist() {
        assertThrows(IllegalArgumentException.class, () -> jobRepository.update(job, "Manager", "Short Description"));
    }

    /**
     * Gets jobs returns list of jobs.
     */
    @Test
    void getJobsReturnsListOfJobs() {
        jobRepository.add(job);
        assertEquals(1, jobRepository.getJobs().size());
        assertEquals(job, jobRepository.getJobs().get(0));
    }
}