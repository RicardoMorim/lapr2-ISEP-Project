package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.application.session.ApplicationSession;
import pt.ipp.isep.dei.esoft.project.domain.Collaborator;

public class Repositories {

    private static Repositories instance;
    private final OrganizationRepository organizationRepository;
    private final TaskCategoryRepository taskCategoryRepository;
    private final AuthenticationRepository authenticationRepository;
    private final JobRepository jobRepository;
    private final CollaboratorRepository collaboratorRepository;
    private final VehicleRepository vehicleRepository;
    private final SkillRepository skillRepository;

    private Repositories() {
        organizationRepository = new OrganizationRepository();
        taskCategoryRepository = new TaskCategoryRepository();
        authenticationRepository = new AuthenticationRepository();
        jobRepository = new JobRepository();
        collaboratorRepository = new CollaboratorRepository();
        vehicleRepository = new VehicleRepository();
        skillRepository = new SkillRepository();
    }

    public static Repositories getInstance() {
        if (instance == null) {
            synchronized (Repositories.class) {
                instance = new Repositories();
            }
        }
        return instance;
    }

    public JobRepository getJobRepository() {
        return jobRepository;
    }

    public SkillRepository getSkillRepository() {
        return skillRepository;
    }

    public VehicleRepository getVehicleRepository() {
        return vehicleRepository;
    }

    public CollaboratorRepository getCollaboratorRepository() {
        return collaboratorRepository;
    }

    public OrganizationRepository getOrganizationRepository() {
        return organizationRepository;
    }

    public TaskCategoryRepository getTaskCategoryRepository() {
        return taskCategoryRepository;
    }

    public AuthenticationRepository getAuthenticationRepository() {
        return authenticationRepository;
    }
}