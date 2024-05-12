package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.Job;

import pt.ipp.isep.dei.esoft.project.repository.JobRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.util.List;

public class JobController {
    private JobRepository jobRepository;

    public JobController() {
        if (jobRepository == null) {
            Repositories repositories = Repositories.getInstance();

            //Get the TaskCategoryRepository
            jobRepository = repositories.getJobRepository();
        }
    }

    public JobController(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }


    public JobRepository getJobRepository() {
        return jobRepository;
    }

    public void setJobRepository(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    private boolean validateJob(Job job) {
        List<Job> jobs = jobRepository.getJobs();
        return !jobs.contains(job) && job.getName().matches("^[a-zA-Z\\s]*$");
    }

    public void registerJob(Job job) throws IllegalArgumentException {
        if (jobRepository.getJobs().contains(job))
            throw new IllegalArgumentException("Job already exists.");

        if (job.getName() == null || job.getShortDescription() == null)
            throw new IllegalArgumentException("Job name or shortDescription parameters are null.");

        if (job.getName().isEmpty() || job.getShortDescription().isEmpty()){
            System.out.println(job);
            throw new IllegalArgumentException("Job name or short description parameters are empty.");
        }

        if (!validateJob(job))
            throw new IllegalArgumentException("Job already exists. Or it has an invalid name.");

        jobRepository.add(job);
    }

    public Job registerJob(String name, String shortDescription, String description) throws IllegalArgumentException {
        Job job = new Job(name, shortDescription, description);
        registerJob(job);
        return job;
    }

    public void updateJob( Job job, String name, String shortDescription, String description) {
        try {
            job.setName(name);
            job.setShortDescription(shortDescription);
            job.setDescription(description);
            this.jobRepository.update(job, name, shortDescription, description);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateJob(Job oldJob, Job newJob) {
        try {
            this.jobRepository.update(oldJob, newJob);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeJob(Job job) {
        try {
            jobRepository.remove(job);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Job> getJobList() {
        return this.jobRepository.getJobs();
    }
}