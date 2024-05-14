package pt.ipp.isep.dei.esoft.project.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.Job;
import pt.ipp.isep.dei.esoft.project.repository.JobRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class JobRepositoryTest {

    private JobRepository jobRepository;
    private Job job;

    @BeforeEach
    void setUp() throws IllegalArgumentException {

        jobRepository = new JobRepository();
        List<Job> jobList = jobRepository.getJobs();

        for (Job j : jobList) {
            jobRepository.remove(j);
        }

        job = new Job("Developer", "Short Description");

    }

    @Test
    void addJobReturnsOptionalWithJob() {
        Optional<Job> result = jobRepository.add(job);
        assertTrue(result.isPresent());
        assertEquals(job, result.get());
    }

    @Test
    void addJobThrowsErrorWhenJobExists() {

        jobRepository.add(job);
        assertThrows(IllegalArgumentException.class, () -> jobRepository.add(job));

    }

    @Test
    void removeJobReturnsOptionalWithJob() {
        jobRepository.add(job);
        Optional<Job> result = jobRepository.remove(job);
        assertTrue(result.isPresent());
        assertEquals(job, result.get());
    }

    @Test
    void removeJobReturnsEmptyOptionalWhenJobDoesNotExist() {
        assertThrows(IllegalArgumentException.class, () -> jobRepository.remove(job));
    }

    @Test
    void updateJobUpdatesAndReturnsJob() {
        jobRepository.add(job);
        Job updatedJob = jobRepository.update(job, "Manager", "Short Description").get();
        assertEquals("Manager", updatedJob.getName());
        assertEquals("Short Description", updatedJob.getShortDescription());
    }

    @Test
    void updateJobThrowsExceptionWhenJobDoesNotExist() {
        assertThrows(IllegalArgumentException.class, () -> jobRepository.update(job, "Manager", "Short Description"));
    }

    @Test
    void getJobsReturnsListOfJobs() {
        jobRepository.add(job);
        assertEquals(1, jobRepository.getJobs().size());
        assertEquals(job, jobRepository.getJobs().get(0));
    }
}