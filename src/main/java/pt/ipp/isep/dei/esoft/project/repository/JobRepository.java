package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.Job;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The type Job repository.
 */
public class JobRepository implements Serializable {

    private List<Job> jobs;


    /**
     * Instantiates a new Job repository.
     */
    public JobRepository() {
        this.jobs = new ArrayList<>();
    }

    /**
     * Update optional.
     *
     * @param job              the job
     * @param name             the name
     * @param shortDescription the short description
     * @return the optional
     */
    public Optional<Job> update(Job job, String name, String shortDescription) {
        for (Job j : this.jobs) {
            if (j.equals(job)) {
                j.setName(name);
                j.setShortDescription(shortDescription);
                return Optional.of(job);
            }
        }

        throw new IllegalArgumentException("Job not found.");
    }

    /**
     * Sets jobs.
     *
     * @param jobs the jobs
     */
    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }


    /**
     * Update optional.
     *
     * @param oldJob the old job
     * @param newJob the new job
     * @return the optional
     */
    public Optional<Job> update(Job oldJob, Job newJob) {
        if (this.jobs.contains(oldJob)) {
            this.jobs.remove(oldJob);
            this.jobs.add(newJob);
            return Optional.of(newJob);
        }

        throw new IllegalArgumentException("Job not found.");
    }


    /**
     * Add optional.
     *
     * @param job the job
     * @return the optional
     */
    public Optional<Job> add(Job job) {
        Optional<Job> newJob = Optional.empty();
        boolean operationSuccess = false;

        if (validateJob(job)) {
            newJob = Optional.of(job);
            operationSuccess = jobs.add(newJob.get());
        }

        if (!operationSuccess) {
            throw new IllegalArgumentException("There was an error registering the job.");
        }

        return newJob;
    }

    private boolean validateJob(Job job) {

        if (job == null) {
            throw new IllegalArgumentException("Job is null.");
        }
        if (job.getName() == null || job.getShortDescription() == null) {
            throw new IllegalArgumentException("Job name or shortDescription parameters are null.");
        }
        if (job.getName().isEmpty() || job.getShortDescription().isEmpty()) {
            throw new IllegalArgumentException("Job name or short description parameters are empty.");
        }

        boolean isValid = !jobs.contains(job) && job.getName().matches("^[a-zA-Z\\s]*$");


        return isValid;
    }

    /**
     * Remove optional.
     *
     * @param job the job
     * @return the optional
     */
    public Optional<Job> remove(Job job) {
        Optional<Job> newJob = Optional.empty();
        boolean operationSuccess = false;

        if (jobs.contains(job)) {
            newJob = Optional.of(job);
            operationSuccess = jobs.remove(newJob.get());
        }

        if (!operationSuccess) {
            throw new IllegalArgumentException("Job not found.");
        }

        return newJob;
    }

    /**
     * Gets jobs.
     *
     * @return the jobs
     */
    public List<Job> getJobs() {
        return jobs;
    }
}