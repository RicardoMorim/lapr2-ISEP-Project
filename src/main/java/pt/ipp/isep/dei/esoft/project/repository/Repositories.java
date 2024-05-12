package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.application.session.ApplicationSession;

public class Repositories {

    private static Repositories instance;
    private final OrganizationRepository organizationRepository;
    private final TaskCategoryRepository taskCategoryRepository;
    private final AuthenticationRepository authenticationRepository;
    private final VehicleRepository vehicleRepository;
    private final SkillRepository skillRepository;
    private final JobRepository jobRepository;
    private final CollaboratorRepository collaboratorRepository;


    private Repositories() {
        organizationRepository = new OrganizationRepository();
        taskCategoryRepository = new TaskCategoryRepository();
        authenticationRepository = new AuthenticationRepository();
        vehicleRepository = new VehicleRepository();
        skillRepository = new SkillRepository();
        jobRepository = new JobRepository();
        collaboratorRepository = new CollaboratorRepository();
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

    public static Repositories getInstance() {
        if (instance == null) {
            synchronized (Repositories.class) {
                instance = new Repositories();
            }
        }
        return instance;
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