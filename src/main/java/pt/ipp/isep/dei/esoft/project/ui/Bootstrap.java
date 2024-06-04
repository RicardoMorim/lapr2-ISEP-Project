package pt.ipp.isep.dei.esoft.project.ui;

import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.domain.*;
import pt.ipp.isep.dei.esoft.project.repository.*;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.io.*;
import java.util.*;

public class Bootstrap implements Runnable {

    private static final String DATA_FILE = "appData.ser";

    public void run() {
        loadData();
    }

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

    public void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            addOrganization();
            addUsers();
            Map<String, Object> data = (Map<String, Object>) ois.readObject();
            Repositories.getInstance().getTaskCategoryRepository().setTaskCategories((List<TaskCategory>) data.get("TaskCategoryRepository"));
            Repositories.getInstance().getJobRepository().setJobs((List<Job>) data.get("JobRepository"));
            Repositories.getInstance().getSkillRepository().setSkills((List<Skill>) data.get("SkillRepository"));
            Repositories.getInstance().getCollaboratorRepository().setCollaborators((List<Collaborator>) data.get("CollaboratorRepository"));
            Repositories.getInstance().getVehicleRepository().setVehicleList((List<Vehicle>) data.get("VehicleRepository"));
            Repositories.getInstance().getAgenda().setEntries((List<AgendaEntry>) data.get("Agenda"));
            Repositories.getInstance().getGreenSpaceRepository().setGreenSpaces((List<GreenSpace>) data.get("GreenSpaceRepository"));
            Repositories.getInstance().getToDoList().setEntries((List<Entry>) data.get("ToDoList"));
            Repositories.getInstance().getVehicleRepository().setBrandList((List<String>) data.get("Brands"));
            Repositories.getInstance().getVehicleRepository().setTypeList((List<String>) data.get("Types"));
            Repositories.getInstance().getVehicleRepository().setBrandToModelsMap((Map<String, List<String>>) data.get("Models"));
            Repositories.getInstance().getTeamRepository().setTeams((List<Team>) data.get("TeamRepository"));

        } catch (IOException | ClassNotFoundException e) {
            addTaskCategories();
            addJobs();
            addOrganization();
            addSkills();
            addUsers();
            addBrands();
            addTypes();
            addModels();
            addVehicles();
            addCollaborators();
            addVehicleMaintenances();
            addEntries();
            addAgendaEntries();
            addTeam();
        }
    }

    public void addTeam() {
        CollaboratorRepository collaboratorRepository = Repositories.getInstance().getCollaboratorRepository();
        TeamRepository teamRepository = Repositories.getInstance().getTeamRepository();

        List<Collaborator> collaborators = collaboratorRepository.getCollaborators();
        List<Collaborator> teamCollaborators = new ArrayList<>();
        teamCollaborators.add(collaborators.get(0));
        teamCollaborators.add(collaborators.get(1));
        Team team = new Team(teamCollaborators);
    }

    public void addEntries() {
        // Get the necessary repositories
        EntryRepository entryRepository = Repositories.getInstance().getEntryRepository();
        GreenSpaceRepository greenSpaceRepository = Repositories.getInstance().getGreenSpaceRepository();

        // Create some addresses
        Address address1 = new Address("Rua do Ricardo", "Porto", "123-456");
        Address address2 = new Address("Rua do Gonçalo", "Porto", "123-456");

        // Create some GreenSpaces
        GreenSpace greenSpace1 = new GreenSpace("Park1", address1, 1000, Type.GARDEN, new Email("admin1@this.app"));
        GreenSpace greenSpace2 = new GreenSpace("Park2", address2, 2000, Type.LARGE_SIZED_PARK, new Email("admin2@this.app"));
        greenSpaceRepository.addGreenSpace(greenSpace1);
        greenSpaceRepository.addGreenSpace(greenSpace2);

        // Create some Entries
        Entry entry1 = new Entry("State1", greenSpace1, "Title1", "Description1", Urgency.HIGH, 2.0f);
        Entry entry2 = new Entry("State2", greenSpace2, "Title2", "Description2", Urgency.MEDIUM, 3.0f);

        // Add the Entries to the repository
        entryRepository.addEntry(entry1);
        entryRepository.addEntry(entry2);
    }

    public void addAgendaEntries() {
        Agenda agenda = Repositories.getInstance().getAgenda();

        // Create some addresses
        Address address1 = new Address("Rua do Ricardo", "Porto", "123-456");
        Address address2 = new Address("Rua do Gonçalo", "Porto", "123-456");


        GreenSpace greenSpace1 = new GreenSpace("Park1", address1 , 1000, Type.GARDEN, new Email("admin1@this.app"));
        GreenSpace greenSpace2 = new GreenSpace("Park2", address2, 2000, Type.LARGE_SIZED_PARK, new Email("admin2@this.app"));


        Entry entry1 = new Entry("State1", greenSpace1, "Title1", "Description1", Urgency.HIGH, 2.0f);
        Entry entry2 = new Entry("State2", greenSpace2, "Title2", "Description2", Urgency.MEDIUM, 3.0f);

        VehicleRepository vehicleRepository = Repositories.getInstance().getVehicleRepository();
        Vehicle vehicle1 = vehicleRepository.getVehicleList().get(0);
        Vehicle vehicle2 = vehicleRepository.getVehicleList().get(1);
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);

        AgendaEntry agendaEntry1 = new AgendaEntry(entry1, "1 hour", Status.PLANNED);
        AgendaEntry agendaEntry2 = new AgendaEntry(entry2, vehicles, "2 hours", Status.PLANNED);
        agenda.addEntry(agendaEntry1);
        agenda.addEntry(agendaEntry2);
    }

    public void addVehicleMaintenances() {
        VehicleRepository vehicleRepository = Repositories.getInstance().getVehicleRepository();
        for (Vehicle vehicle : vehicleRepository.getVehicleList()) {
            vehicle.registerMaintenance(new Date(), vehicle.getCurrentKM() - 10000);
        }
    }

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
        collaboratorRepository.add(new Collaborator("ricardo@gmail.com", "ricardo", new Address("Rua do Ricardo", "Porto", "123-456"), "913456789", jobs.get(0), new Date(), new Date(), "CC", 13456789, 12345678, new ArrayList<>(list3)));
        collaboratorRepository.add(new Collaborator("goncalo@gmail.com", "goncalo", new Address("Rua do Gonçalo", "Porto", "123-456"), "961456789", jobs.get(1), new Date(), new Date(), "CC", 12456789, 12345679, new ArrayList<>(list2)));
        collaboratorRepository.add(new Collaborator("marisa@gmail.com", "marisa", new Address("Rua da Marisa", "Porto", "123-456"), "931345689", jobs.get(2), new Date(), new Date(), "CC", 12346789, 12345689, new ArrayList<>(list1)));
        collaboratorRepository.add(new Collaborator("filipa@gmail.com", "filipa", new Address("Rua da Filipa", "Porto", "123-456"), "962345679", jobs.get(3), new Date(), new Date(), "CC", 12356789, 12345789, new ArrayList<>(list1)));
        collaboratorRepository.add(new Collaborator("coll@gmail.com", "collaborator", new Address("123 Street", "Porto", "123-456"), "123456789", jobs.get(4), new Date(), new Date(), "CC", 12345678, 13456789, new ArrayList<>(list3)));

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
        vehicleRepository.addVehicle("AA-00-00", "BMW", "X1", "SUV", 2000, 2500, 1000, new Date(), new Date(), 10000, 0);
        vehicleRepository.addVehicle("AA-00-01", "Mercedes", "A", "Sedan", 1500, 2000, 60000, new Date(), new Date(), 10000, 50000);
        vehicleRepository.addVehicle("AA-00-02", "Audi", "A3", "Sedan", 1500, 2000, 12003210, new Date(), new Date(), 10000, 12003000);
        vehicleRepository.addVehicle("AA-00-03", "Toyota", "Corolla", "Sedan", 1500, 2000, 12310, new Date(), new Date(), 10000, 12300);
        vehicleRepository.addVehicle("AA-00-04", "Honda", "Civic", "Sedan", 1500, 2000, 6540, new Date(), new Date(), 10000, 6000);
        vehicleRepository.addVehicle("AA-00-05", "Ford", "Fiesta", "Sedan", 1500, 2000, 21340, new Date(), new Date(), 10000, 21300);
        vehicleRepository.addVehicle("AA-00-06", "Chevrolet", "Spark", "Sedan", 1500, 2000, 1235430, new Date(), new Date(), 10000, 1235400);
        vehicleRepository.addVehicle("AA-00-07", "Hyundai", "Accent", "Sedan", 1500, 2000, 12340, new Date(), new Date(), 10000, 12300);

    }

    private void addOrganization() {
        //TODO: add organizations bootstrap here
        //get organization repository
        OrganizationRepository organizationRepository = Repositories.getInstance().getOrganizationRepository();
        Organization organization = new Organization("This Company");
        organization.addEmployee(new Employee("admin@this.app"));
        organization.addEmployee(new Employee("employee@this.app"));
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
        authenticationRepository.addUserRole(AuthenticationController.ROLE_EMPLOYEE,
                AuthenticationController.ROLE_EMPLOYEE);

        authenticationRepository.addUserWithRole("Main Administrator", "admin@this.app", "admin",
                AuthenticationController.ROLE_ADMIN);

        authenticationRepository.addUserWithRole("Employee", "employee@this.app", "pwd",
                AuthenticationController.ROLE_EMPLOYEE);
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