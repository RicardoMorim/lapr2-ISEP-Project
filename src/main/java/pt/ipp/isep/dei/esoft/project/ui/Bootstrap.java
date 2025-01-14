package pt.ipp.isep.dei.esoft.project.ui;

import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.domain.*;
import pt.ipp.isep.dei.esoft.project.repository.*;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.io.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * The type Bootstrap.
 */
public class Bootstrap implements Runnable {

    private static final String DATA_FILE = "appData.ser";

    public void run() {
        loadData();
    }

    /**
     * Save data.
     */
    public void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            Map<String, Object> data = new HashMap<>();
            data.put("TaskCategoryRepository", Repositories.getInstance().getTaskCategoryRepository().getTaskCategories());
            data.put("JobRepository", Repositories.getInstance().getJobRepository().getJobs());
            data.put("SkillRepository", Repositories.getInstance().getSkillRepository().getSkills());
            data.put("CollaboratorRepository", Repositories.getInstance().getCollaboratorRepository().getCollaborators());
            data.put("VehicleRepository", Repositories.getInstance().getVehicleRepository().getVehicleList());
            data.put("Agenda", Repositories.getInstance().getAgenda().getEntries());
            data.put("GreenSpaceRepository", Repositories.getInstance().getGreenSpaceRepository().getGreenSpaces());
            data.put("ToDoList", Repositories.getInstance().getToDoList().getEntries());
            data.put("Brands", Repositories.getInstance().getVehicleRepository().getBrandList());
            data.put("Types", Repositories.getInstance().getVehicleRepository().getTypeList());
            data.put("Models", Repositories.getInstance().getVehicleRepository().getBrandToModelsMap());
            data.put("TeamRepository", Repositories.getInstance().getTeamRepository().getTeams());
            oos.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load data.
     */
    public void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            Map<String, Object> data = (Map<String, Object>) ois.readObject();
            Repositories.getInstance().getJobRepository().setJobs((List<Job>) data.get("JobRepository"));
            Repositories.getInstance().getSkillRepository().setSkills((List<Skill>) data.get("SkillRepository"));
            Repositories.getInstance().getCollaboratorRepository().setCollaborators((List<Collaborator>) data.get("CollaboratorRepository"));
            addOrganization();
            addUsers();
            Repositories.getInstance().getTeamRepository().setTeams((List<Team>) data.get("TeamRepository"));
            Repositories.getInstance().getTaskCategoryRepository().setTaskCategories((List<TaskCategory>) data.get("TaskCategoryRepository"));
            Repositories.getInstance().getVehicleRepository().setBrandList((List<String>) data.get("Brands"));
            Repositories.getInstance().getVehicleRepository().setTypeList((List<String>) data.get("Types"));
            Repositories.getInstance().getVehicleRepository().setBrandToModelsMap((Map<String, List<String>>) data.get("Models"));
            Repositories.getInstance().getVehicleRepository().setVehicleList((List<Vehicle>) data.get("VehicleRepository"));
            Repositories.getInstance().getGreenSpaceRepository().setGreenSpaces((List<GreenSpace>) data.get("GreenSpaceRepository"));
            Repositories.getInstance().getToDoList().setEntries((List<Entry>) data.get("ToDoList"));
            Repositories.getInstance().getAgenda().setEntries((List<AgendaEntry>) data.get("Agenda"));
            updateEntryStatus();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No data file found, bootstrapping data");
            System.out.println(e.getMessage());
            addSkills();
            addJobs();
            addCollaborators();
            addOrganization();
            addUsers();
            addTaskCategories();
            addBrands();
            addTypes();
            addModels();
            addVehicles();
            addTeam();
            addGreenSpaces();
            addEntries();
            addAgendaEntries();
            updateEntryStatus();
        }
    }

    /**
     * Update entry status.
     */
    public void updateEntryStatus() {
        for (AgendaEntry entry : Repositories.getInstance().getAgenda().getEntries()) {
            entry.setStatus(entry.getStatusBasedOnDates());
            entry.getEntry().setState(entry.getStatus());
        }
    }

    /**
     * Add green spaces.
     */
    public void addGreenSpaces() {
        GreenSpaceRepository greenSpaceRepository = Repositories.getInstance().getGreenSpaceRepository();

        greenSpaceRepository.addGreenSpace(new GreenSpace("Cidade", new Address("Rua da Cidade", "Porto", "1234-456"), 10000, Type.LARGE_SIZED_PARK, new Email("grm@this.app")));
        greenSpaceRepository.addGreenSpace(new GreenSpace("Parque", new Address("Rua do Parque", "Porto", "1234-456"), 500, Type.GARDEN, new Email("admin@this.app")));
        greenSpaceRepository.addGreenSpace(new GreenSpace("Covelo", new Address("Rua do Covelo", "Porto", "1211-443"), 5000, Type.MEDIUM_SIZED_PARK, new Email("grm@this.app")));
        greenSpaceRepository.addGreenSpace(new GreenSpace("Asprela", new Address("Rua da Asprela", "Porto", "1214-642"), 500, Type.GARDEN, new Email("admin@this.app")));


    }

    /**
     * Add team.
     */
    public void addTeam() {
        CollaboratorRepository collaboratorRepository = Repositories.getInstance().getCollaboratorRepository();
        TeamRepository teamRepository = Repositories.getInstance().getTeamRepository();

        List<Collaborator> collaborators = collaboratorRepository.getCollaborators();
        List<Collaborator> teamCollaborators = new ArrayList<>();
        teamCollaborators.add(collaborators.get(0));
        teamCollaborators.add(collaborators.get(1));
        Team team = new Team(teamCollaborators);
        for (Collaborator col : new Collaborator[]{collaborators.get(0), collaborators.get(1)})
            col.setFree(false);
        teamRepository.add(team);
        teamRepository.notifyNewTeam(team);
    }

    /**
     * Add entries.
     */
    public void addEntries() {
        // Get the necessary repositories
        ToDoList entryRepository = Repositories.getInstance().getToDoList();

        // Create some GreenSpaces
        GreenSpace greenSpace1 = Repositories.getInstance().getGreenSpaceRepository().getGreenSpaces().get(0);
        GreenSpace greenSpace2 = Repositories.getInstance().getGreenSpaceRepository().getGreenSpaces().get(1);


        // Create some Entries
        Entry entry1 = new Entry(greenSpace1, "Title1", "Description1", Urgency.HIGH, 2.0f);
        Entry entry2 = new Entry(greenSpace2, "Title2", "Description2", Urgency.MEDIUM, 3.0f);
        Entry entry3 = new Entry(greenSpace1, "Title3", "Description3", Urgency.LOW, 4.0f);
        Entry entry4 = new Entry(greenSpace2, "Title4", "Description4", Urgency.HIGH, 5.0f);
        Entry entry5 = new Entry(greenSpace1, "Title5", "Description5", Urgency.MEDIUM, 6.0f);
        Entry entry6 = new Entry(greenSpace2, "Title6", "Description6", Urgency.LOW, 7.0f);
        Entry entry7 = new Entry(greenSpace1, "Title7", "Description7", Urgency.HIGH, 8.0f);
        Entry entry8 = new Entry(greenSpace2, "Title8", "Description8", Urgency.MEDIUM, 9.0f);

        // Add the Entries to the repository
        entryRepository.addEntry(entry1);
        entryRepository.addEntry(entry2);
        entryRepository.addEntry(entry3);
        entryRepository.addEntry(entry4);
        entryRepository.addEntry(entry5);
        entryRepository.addEntry(entry6);
        entryRepository.addEntry(entry7);
        entryRepository.addEntry(entry8);
    }

    /**
     * Add agenda entries.
     */
    public void addAgendaEntries() {
        Agenda agenda = Repositories.getInstance().getAgenda();

        Entry entry1 = Repositories.getInstance().getToDoList().getEntries().get(0);
        Entry entry2 = Repositories.getInstance().getToDoList().getEntries().get(1);

        VehicleRepository vehicleRepository = Repositories.getInstance().getVehicleRepository();
        Vehicle vehicle1 = vehicleRepository.getVehicleList().get(0);
        Vehicle vehicle2 = vehicleRepository.getVehicleList().get(1);
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);

        AgendaEntry agendaEntry1 = new AgendaEntry(entry1, "10", new Date());
        AgendaEntry agendaEntry2 = new AgendaEntry(entry2, vehicles, "30", new Date());
        agenda.addEntry(agendaEntry1);
        agenda.addEntry(agendaEntry2);
    }


    /**
     * Add jobs.
     */
    public void addJobs() {
        JobRepository jobRepository = Repositories.getInstance().getJobRepository();

        jobRepository.add(new Job("Gardener", "The collaborator prunes the grass every week"));
        jobRepository.add(new Job("Painter", "The collaborator paints the walls every week"));
        jobRepository.add(new Job("Electrician", "The collaborator electrifies the house every week"));
        jobRepository.add(new Job("Plumber", "The collaborator plumbs the house every week"));
        jobRepository.add(new Job("Carpenter", "The collaborator carpets the house every week"));
        jobRepository.add(new Job("Driver", "The collaborator drives the car every week"));
        jobRepository.add(new Job("Cleaner", "The collaborator cleans the house every week"));
        jobRepository.add(new Job("Cook", "The collaborator cooks the food every week"));
        jobRepository.add(new Job("Waiter", "The collaborator serves the food every week"));
        jobRepository.add(new Job("Receptionist", "The collaborator receives the clients every week"));
        jobRepository.add(new Job("Manager", "The collaborator manages the company every week"));
        jobRepository.add(new Job("CEO", "The collaborator is the CEO of the company every week"));
        jobRepository.add(new Job("Secretary", "The collaborator is the secretary of the company every week"));
        jobRepository.add(new Job("Accountant", "The collaborator is the accountant of the company every week"));
        jobRepository.add(new Job("HR", "The collaborator is the HR of the company every week"));
        jobRepository.add(new Job("IT", "The collaborator is the IT of the company every week"));
        jobRepository.add(new Job("Designer", "The collaborator is the designer of the company every week"));
        jobRepository.add(new Job("Developer", "The collaborator is the developer of the company every week"));
        jobRepository.add(new Job("Tester", "The collaborator is the tester of the company every week"));
        jobRepository.add(new Job("Analyst", "The collaborator is the analyst of the company every week"));
        jobRepository.add(new Job("Consultant", "The collaborator is the consultant of the company every week"));
        jobRepository.add(new Job("Lawyer", "The collaborator is the lawyer of the company every week"));
        jobRepository.add(new Job("Doctor", "The collaborator is the doctor of the company every week"));
    }


    /**
     * Add collaborators.
     */
    public void addCollaborators() {
        CollaboratorRepository collaboratorRepository = Repositories.getInstance().getCollaboratorRepository();
        List<Job> jobs = Repositories.getInstance().getJobRepository().getJobs();
        Skill skill1 = Repositories.getInstance().getSkillRepository().getSkillByName("light vehicle licence");
        Skill skill2 = Repositories.getInstance().getSkillRepository().getSkillByName("tree pruner");
        List<Skill> list1 = new ArrayList<>();
        list1.add(skill1);
        List<Skill> list2 = new ArrayList<>();
        list2.add(skill2);
        List<Skill> list3 = new ArrayList<>();
        list3.add(skill1);
        list3.add(skill2);
        Collaborator col1 = new Collaborator("ricardo@gmail.com", "ricardo", new Address("Rua do Ricardo", "Porto", "123-456"), "913456789", jobs.get(0), new Date(), new Date(), "CC", 13456789, 12345678, new ArrayList<>(list3));
        collaboratorRepository.notifyNewCollaborator(col1);
        collaboratorRepository.add(col1);

        Collaborator col2 = new Collaborator("goncalo@gmail.com", "goncalo", new Address("Rua do Gonçalo", "Porto", "123-456"), "961456789", jobs.get(1), new Date(), new Date(), "CC", 12456789, 12345679, new ArrayList<>(list2));
        collaboratorRepository.notifyNewCollaborator(col2);
        collaboratorRepository.add(col2);

        Collaborator col3 = new Collaborator("marisa@gmail.com", "marisa", new Address("Rua da Marisa", "Porto", "123-456"), "931345689", jobs.get(2), new Date(), new Date(), "CC", 12346789, 12345689, new ArrayList<>(list1));
        collaboratorRepository.notifyNewCollaborator(col3);
        collaboratorRepository.add(col3);

        Collaborator col4 = new Collaborator("filipa@gmail.com", "filipa", new Address("Rua da Filipa", "Porto", "123-456"), "962345679", jobs.get(3), new Date(), new Date(), "CC", 12356789, 12345789, new ArrayList<>(list1));
        collaboratorRepository.notifyNewCollaborator(col4);
        collaboratorRepository.add(col4);

        Collaborator col5 = new Collaborator("coll@gmail.com", "collaborator", new Address("123 Street", "Porto", "123-456"), "123456789", jobs.get(4), new Date(), new Date(), "CC", 12345678, 13456789, new ArrayList<>(list3));
        collaboratorRepository.notifyNewCollaborator(col5);
        collaboratorRepository.add(col5);

    }

    private void addSkills() {
        SkillRepository skillRepository = Repositories.getInstance().getSkillRepository();
        skillRepository.add(new Skill("tree pruner", "The collaborator prunes the trees every week"));
        skillRepository.add(new Skill("painter", "The collaborator is able to skillfully paint"));
        skillRepository.add(new Skill("gardener", "The collaborator is able to skillfully garden"));
        skillRepository.add(new Skill("plumber", "The collaborator is able to skillfully plumb"));
        skillRepository.add(new Skill("electrician", "The collaborator is able to skillfully electrify"));
        skillRepository.add(new Skill("carpenter", "The collaborator is able to skillfully carpent"));
        skillRepository.add(new Skill("light vehicle licence", "The collaborator has a light vehicle license"));
    }

    private void addVehicles() {
        VehicleRepository vehicleRepository = Repositories.getInstance().getVehicleRepository();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        vehicleRepository.addVehicle("AA-00-00", "BMW", "X1", "SUV", 2000, 2500, 1000, Date.from(LocalDate.parse("01-12-2023", formatter).atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(LocalDate.parse("01-02-2024", formatter).atStartOfDay(ZoneId.systemDefault()).toInstant()), 10000, 0);
        vehicleRepository.addVehicle("AA-00-01", "Mercedes", "A", "Sedan", 1500, 2000, 60000, Date.from(LocalDate.parse("02-01-2024", formatter).atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(LocalDate.parse("02-02-2024", formatter).atStartOfDay(ZoneId.systemDefault()).toInstant()), 10000, 50000);
        vehicleRepository.addVehicle("AA-00-02", "Audi", "A3", "Sedan", 1500, 2000, 12003210, Date.from(LocalDate.parse("06-12-2023", formatter).atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(LocalDate.parse("02-02-2024", formatter).atStartOfDay(ZoneId.systemDefault()).toInstant()), 10000, 12003000);
        vehicleRepository.addVehicle("AA-00-03", "Toyota", "Corolla", "Sedan", 1500, 2000, 12310, Date.from(LocalDate.parse("15-12-2023", formatter).atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(LocalDate.parse("02-02-2024", formatter).atStartOfDay(ZoneId.systemDefault()).toInstant()), 10000, 12300);
        vehicleRepository.addVehicle("AA-00-04", "Honda", "Civic", "Sedan", 1500, 2000, 6540, Date.from(LocalDate.parse("01-01-2024", formatter).atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(LocalDate.parse("02-02-2024", formatter).atStartOfDay(ZoneId.systemDefault()).toInstant()), 10000, 6000);
        vehicleRepository.addVehicle("AA-00-05", "Ford", "Fiesta", "Sedan", 1500, 2000, 21340, Date.from(LocalDate.parse("10-01-2024", formatter).atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(LocalDate.parse("02-02-2024", formatter).atStartOfDay(ZoneId.systemDefault()).toInstant()), 10000, 21300);
        vehicleRepository.addVehicle("AA-00-06", "Chevrolet", "Spark", "Sedan", 1500, 2000, 1235430, Date.from(LocalDate.parse("05-01-2024", formatter).atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(LocalDate.parse("02-02-2024", formatter).atStartOfDay(ZoneId.systemDefault()).toInstant()), 10000, 1235400);
        vehicleRepository.addVehicle("AA-00-07", "Hyundai", "Accent", "Sedan", 1500, 2000, 12340, Date.from(LocalDate.parse("03-01-2024", formatter).atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(LocalDate.parse("02-02-2024", formatter).atStartOfDay(ZoneId.systemDefault()).toInstant()), 10000, 12300);
    }

    private void addOrganization() {
        //TODO: add organizations bootstrap here
        //get organization repository
        OrganizationRepository organizationRepository = Repositories.getInstance().getOrganizationRepository();
        Organization organization = new Organization("This Company");
        organization.addEmployee(new Employee("admin@this.app"));
        organization.addEmployee(new Employee("employee@this.app"));
        organization.addEmployee(new Employee("hrm@this.app"));
        organization.addEmployee(new Employee("vfm@this.app"));
        organization.addEmployee(new Employee("grm@this.app"));
        for (Collaborator col : Repositories.getInstance().getCollaboratorRepository().getCollaborators())
            organization.addEmployee(new Employee(col.getEmail()));
        organizationRepository.add(organization);
    }

    private void addTaskCategories() {
        //TODO: add bootstrap Task Categories here

        //get task category repository
        TaskCategoryRepository taskCategoryRepository = Repositories.getInstance().getTaskCategoryRepository();
        taskCategoryRepository.add(new TaskCategory("Analysis"));
        taskCategoryRepository.add(new TaskCategory("Design"));
        taskCategoryRepository.add(new TaskCategory("Implementation"));
        taskCategoryRepository.add(new TaskCategory("Development"));
        taskCategoryRepository.add(new TaskCategory("Testing"));
        taskCategoryRepository.add(new TaskCategory("Deployment"));
        taskCategoryRepository.add(new TaskCategory("Maintenance"));
    }

    private void addUsers() {
        //TODO: add Authentication users here: should be created for each user in the organization
        AuthenticationRepository authenticationRepository = Repositories.getInstance().getAuthenticationRepository();
        authenticationRepository.addUserRole(AuthenticationController.ROLE_ADMIN, AuthenticationController.ROLE_ADMIN);
        authenticationRepository.addUserRole(AuthenticationController.ROLE_EMPLOYEE, AuthenticationController.ROLE_EMPLOYEE);
        authenticationRepository.addUserRole(AuthenticationController.ROLE_VFM, AuthenticationController.ROLE_VFM);
        authenticationRepository.addUserRole(AuthenticationController.ROLE_HRM, AuthenticationController.ROLE_HRM);
        authenticationRepository.addUserRole(AuthenticationController.ROLE_GRM, AuthenticationController.ROLE_GRM);
        authenticationRepository.addUserRole(AuthenticationController.ROLE_EMPLOYEE, AuthenticationController.ROLE_EMPLOYEE);

        authenticationRepository.addUserWithRole("Main Administrator", "admin@this.app", "admin", AuthenticationController.ROLE_ADMIN);

        authenticationRepository.addUserWithRole("Employee", "employee@this.app", "pwd", AuthenticationController.ROLE_EMPLOYEE);
        authenticationRepository.addUserWithRole("HRM", "hrm@this.app", "hrm", AuthenticationController.ROLE_HRM);
        authenticationRepository.addUserWithRole("GRM", "grm@this.app", "grm", AuthenticationController.ROLE_GRM);
        authenticationRepository.addUserWithRole("vfm", "vfm@this.app", "vfm", AuthenticationController.ROLE_VFM);

        for (Collaborator col : Repositories.getInstance().getCollaboratorRepository().getCollaborators()) {
            authenticationRepository.addUserWithRole(col.getName(), col.getEmail(), "pwd", AuthenticationController.ROLE_EMPLOYEE);
        }
    }

    private void addBrands() {

        VehicleRepository vehicleRepository = Repositories.getInstance().getVehicleRepository();
        vehicleRepository.addBrand("BMW");
        vehicleRepository.addBrand("Mercedes");
        vehicleRepository.addBrand("Audi");
        vehicleRepository.addBrand("Toyota");
        vehicleRepository.addBrand("Honda");
        vehicleRepository.addBrand("Ford");
        vehicleRepository.addBrand("Chevrolet");
        vehicleRepository.addBrand("Hyundai");
        vehicleRepository.addBrand("Nissan");
        vehicleRepository.addBrand("Volkswagen");

    }

    private void addTypes() {
        VehicleRepository vehicleRepository = Repositories.getInstance().getVehicleRepository();
        vehicleRepository.addType("Sedan");
        vehicleRepository.addType("SUV");
        vehicleRepository.addType("Truck");
    }

    private void addModels() {
        VehicleRepository vehicleRepository = Repositories.getInstance().getVehicleRepository();
        Map<String, List<String>> brandsAndModels = new HashMap<>();
        brandsAndModels.put("BMW", Arrays.asList("X1", "X3", "X5", "X7"));
        brandsAndModels.put("Mercedes", Arrays.asList("Class A", "Class B", "Class C", "Class E", "Class S"));
        brandsAndModels.put("Audi", Arrays.asList("A3", "A4", "A5", "A6", "A7"));
        brandsAndModels.put("Toyota", Arrays.asList("Corolla", "Camry", "RAV4", "Highlander", "4Runner"));
        brandsAndModels.put("Honda", Arrays.asList("Civic", "Accord", "CR-V", "Pilot", "Odyssey"));
        brandsAndModels.put("Ford", Arrays.asList("Fiesta", "Focus", "Fusion", "Mustang", "Escape"));
        brandsAndModels.put("Chevrolet", Arrays.asList("Spark", "Sonic", "Cruze", "Malibu", "Impala"));
        brandsAndModels.put("Hyundai", Arrays.asList("Accent", "Elantra", "Sonata", "Azera", "Genesis"));
        brandsAndModels.put("Nissan", Arrays.asList("Versa", "Sentra", "Altima", "Maxima", "370Z"));
        brandsAndModels.put("Volkswagen", Arrays.asList("Jetta", "Passat", "Arteon", "Golf", "Tiguan"));

        for (Map.Entry<String, List<String>> entry : brandsAndModels.entrySet()) {
            String brand = entry.getKey();
            List<String> models = entry.getValue();
            for (String model : models) {
                vehicleRepository.addModel(model, brand);
            }
        }


    }
}