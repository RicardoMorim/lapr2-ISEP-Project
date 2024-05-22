package pt.ipp.isep.dei.esoft.project.repository;

public class Repositories {

    private static Repositories instance;

    private final ToDoList toDoList;
    private final OrganizationRepository organizationRepository;
    private final TaskCategoryRepository taskCategoryRepository;
    private final AuthenticationRepository authenticationRepository;
    private final JobRepository jobRepository;
    private final CollaboratorRepository collaboratorRepository;
    private final VehicleRepository vehicleRepository;
    private final SkillRepository skillRepository;
    private final GreenSpaceRepository greenSpaceRepository;
    private final EntryRepository entryRepository;
    private final Agenda agenda;

    private Repositories() {
        organizationRepository = new OrganizationRepository();
        taskCategoryRepository = new TaskCategoryRepository();
        authenticationRepository = new AuthenticationRepository();
        jobRepository = new JobRepository();
        collaboratorRepository = new CollaboratorRepository();
        vehicleRepository = new VehicleRepository();
        skillRepository = new SkillRepository();
        greenSpaceRepository = new GreenSpaceRepository();
        toDoList = new ToDoList();
        entryRepository = new EntryRepository();
        agenda = new Agenda();
    }

    public static Repositories getInstance() {
        if (instance == null) {
            synchronized (Repositories.class) {
                instance = new Repositories();
            }
        }
        return instance;
    }

    public Agenda getAgenda() {
        return agenda;
    }

    public EntryRepository getEntryRepository() {
        return entryRepository;
    }

    public ToDoList getToDoList() {
        return toDoList;
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

    public GreenSpaceRepository getGreenSpaceRepository() {
        return greenSpaceRepository;
    }
}