package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.Job;
import pt.ipp.isep.dei.esoft.project.repository.JobRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.util.List;

/**
 * The type Job controller.
 */
public class JobController {
    private JobRepository jobRepository;

    /**
     * Instantiates a new Job controller.
     */
    public JobController() {
        if (jobRepository == null) {
            Repositories repositories = Repositories.getInstance();

            //Get the TaskCategoryRepository
            jobRepository = repositories.getJobRepository();
        }
    }

    /**
     * Instantiates a new Job controller.
     *
     * @param jobRepository the job repository
     */
    public JobController(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    /**
     * Gets job repository.
     *
     * @return the job repository
     */
    public JobRepository getJobRepository() {
        return jobRepository;
    }

    /**
     * Sets job repository.
     *
     * @param jobRepository the job repository
     */
    public void setJobRepository(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    private boolean validateJob(Job job) {
        List<Job> jobs = jobRepository.getJobs();
        return !jobs.contains(job) && job.getName().matches("^[a-zA-Z\\s]*$");
    }

    /**
     * Register job.
     *
     * @param job the job
     * @throws IllegalArgumentException the illegal argument exception
     */
    public void registerJob(Job job) throws IllegalArgumentException {
        if (jobRepository.getJobs().contains(job))
            throw new IllegalArgumentException("Job already exists.");

        if (job.getName() == null || job.getShortDescription() == null)
            throw new IllegalArgumentException("Job name or shortDescription parameters are null.");

        if (job.getName().isEmpty() || job.getShortDescription().isEmpty()) {
            System.out.println(job);
            throw new IllegalArgumentException("Job name or short description parameters are empty.");
        }

        if (!validateJob(job))
            throw new IllegalArgumentException("Job already exists. Or it has an invalid name.");

        jobRepository.add(job);
    }

    /**
     * Register job job.
     *
     * @param name             the name
     * @param shortDescription the short description
     * @return the job
     * @throws IllegalArgumentException the illegal argument exception
     */
    public Job registerJob(String name, String shortDescription) throws IllegalArgumentException {
        Job job = new Job(name, shortDescription);
        registerJob(job);
        return job;
    }


    /**
     * Update job.
     *
     * @param oldJob the old job
     * @param newJob the new job
     */
    public void updateJob(Job oldJob, Job newJob) {
        this.jobRepository.update(oldJob, newJob);
    }

    /**
     * Remove job.
     *
     * @param job the job
     */
    public void removeJob(Job job) {
        jobRepository.remove(job);
    }

    /**
     * Gets job list.
     *
     * @return the job list
     */
    public List<Job> getJobList() {
        return this.jobRepository.getJobs();
    }
}