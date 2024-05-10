# US005 - Generate a team proposal 

## 4. Tests 

**Test 1:** ensure that the system returns all valid teams, so that the user can choose which one he wants
   
    @Test
        void generateTeamProposalsReturnsAllValidTeams() {
        Collaborator collaborator1 = new Collaborator("john.doe@example.com", "John Doe", "123 Street", "1234567890", new Job("Developer"), new Date(), new Date(), "ID", 123456, 123456, Arrays.asList(new Skill("Java")));
        Collaborator collaborator2 = new Collaborator("jane.doe@example.com", "Jane Doe", "456 Street", "0987654321", new Job("Designer"), new Date(), new Date(), "Passport", 654321, 654321, Arrays.asList(new Skill("Photoshop")));
        Collaborator collaborator3 = new Collaborator("jao.doe@example.com", "Jao", "123 Street", "1234567890", new Job("Developer"), new Date(), new Date(), "ID", 123456, 123456, Arrays.asList(new Skill("Java")));
        Collaborator collaborator4 = new Collaborator("jaoA.doe@example.com", "Jao", "123 Street", "1234567890", new Job("Developer"), new Date(), new Date(), "ID", 123456, 123456, Arrays.asList(new Skill("Java")));
        repository.add(collaborator1);
        repository.add(collaborator2);
        repository.add(collaborator3);
        repository.add(collaborator4);
        List<List<Collaborator>> teamProposals = controller.GenerateTeamProposals(1, 2, Arrays.asList(new Skill("Java"), new Skill("Photoshop")));
        assertEquals(3, teamProposals.size());
        assertTrue(teamProposals.stream().anyMatch(team -> team.contains(collaborator1) && team.contains(collaborator2)));
        assertFalse(teamProposals.stream().anyMatch(team -> team.contains(collaborator1) && !team.contains(collaborator2)));
        assertFalse(teamProposals.stream().anyMatch(team -> team.contains(collaborator1) && team.contains(collaborator3)));
        assertFalse(teamProposals.stream().anyMatch(team -> team.contains(collaborator1) && team.contains(collaborator4)));
    }

**Test 2:** Ensure that an IllegalArgumentException gets thrown when no teams can be generate

    @Test
    void generateTeamProposalsThrowsExceptionWhenNoValidTeamsCanBeGenerated() {
        Collaborator collaborator = new Collaborator("John Doe", "john.doe@example.com", "123 Street", "1234567890", new Job("Developer"), new Date(), new Date(), "ID", 123456, 123456, Arrays.asList(new Skill("Java")));
        repository.add(collaborator);
        assertThrows(IllegalArgumentException.class, () -> controller.GenerateTeamProposals(2, 2, Arrays.asList(new Skill("Java"), new Skill("Photoshop"))));
    }


**Test 3:** Ensure that the required skills for the team are properly checked when they are met
    @Test
    void hasRequiredSkillsReturnsTrueWhenTeamHasRequiredSkills() {
        Skill java = new Skill("Java");
        Skill photoshop = new Skill("Photoshop");

        Collaborator collaborator1 = new Collaborator("john.doe@example.com", "John Doe", "123 Street", "1234567890", new Job("Developer"), new Date(), new Date(), "ID", 123456, 123456, Arrays.asList(java, photoshop));
        Collaborator collaborator2 = new Collaborator("jane.doe@example.com", "Jane Doe", "456 Street", "0987654321", new Job("Designer"), new Date(), new Date(), "Passport", 654321, 654321, Arrays.asList(java, photoshop));

        List<Collaborator> team = Arrays.asList(collaborator1, collaborator2);
        List<Skill> requiredSkills = Arrays.asList(java, photoshop);

        assertTrue(controller.hasRequiredSkills(team, requiredSkills));
    }


**Test 4:** Ensure that the required skills for the team are properly checked when they are not met

    @Test
    void hasRequiredSkillsReturnsFalseWhenTeamDoesNotHaveRequiredSkills() {
        Skill java = new Skill("Java");
        Skill photoshop = new Skill("Photoshop");

        Collaborator collaborator1 = new Collaborator("john.doe@example.com", "John Doe", "123 Street", "1234567890", new Job("Developer"), new Date(), new Date(), "ID", 123456, 123456, Arrays.asList(java));
        Collaborator collaborator2 = new Collaborator("jane.doe@example.com", "Jane Doe", "456 Street", "0987654321", new Job("Designer"), new Date(), new Date(), "Passport", 654321, 654321, Arrays.asList(java));

        List<Collaborator> team = Arrays.asList(collaborator1, collaborator2);
        List<Skill> requiredSkills = Arrays.asList(java, photoshop);

        assertFalse(controller.hasRequiredSkills(team, requiredSkills));
    }


**Test 5:** Ensure that each collaborator can only contribute once for each skill
    @Test
    void hasRequiredSkillsReturnsFalseWhenTeamDoesNotHaveEnoughOfRequiredSkill() {
        Skill java = new Skill("Java");

        Collaborator collaborator1 = new Collaborator("john.doe@example.com", "John Doe", "123 Street", "1234567890", new Job("Developer"), new Date(), new Date(), "ID", 123456, 123456, Arrays.asList(java));
        Collaborator collaborator2 = new Collaborator("jane.doe@example.com", "Jane Doe", "456 Street", "0987654321", new Job("Designer"), new Date(), new Date(), "Passport", 654321, 654321, Arrays.asList(java));

        List<Collaborator> team = Arrays.asList(collaborator1, collaborator2);
        List<Skill> requiredSkills = Arrays.asList(java, java, java);

        assertFalse(controller.hasRequiredSkills(team, requiredSkills));
    }
 


## 5. Construction (Implementation)

### Class CollaboratorController

```java
    public List<List<Collaborator>> GenerateTeamProposals(int minTeamSize, int maxTeamSize, List<Skill> requiredSkillList) {
        return collaboratorRepository.GenerateTeamProposals(minTeamSize, maxTeamSize, requiredSkillList);
    }
    
    public void startTask(List<Collaborator> collaboratorList) {
        for (Collaborator collaborator : collaboratorList) {
            collaborator.setFree(false);
        }
    }
```

### Class CollaboratorRepository

```java
    public List<List<Collaborator>> GenerateTeamProposals(int minTeamSize, int maxTeamSize, List<Skill> requiredSkillList) {
    List<List<Collaborator>> allTeams = new ArrayList<>();
    List<Collaborator> collaboratorList = new ArrayList<>(collaboratorRepository.getCollaborators()); // Create a new mutable list
    collaboratorList.removeIf(collaborator -> !collaborator.isFree());

    generateTeams(new ArrayList<>(), collaboratorList, requiredSkillList, minTeamSize, maxTeamSize, allTeams);
    if (allTeams.isEmpty()) {
        throw new IllegalArgumentException("No team proposal could be generated with the given parameters.");
    }
    return allTeams;
}

private void generateTeams(List<Collaborator> currentTeam, List<Collaborator> remainingCollaborators, List<Skill> requiredSkills, int minTeamSize, int maxTeamSize, List<List<Collaborator>> allTeams) {
    if (currentTeam.size() > maxTeamSize) {
        return;
    }
    
    if (currentTeam.size() >= minTeamSize && currentTeam.size() <= maxTeamSize && hasRequiredSkills(currentTeam, requiredSkills)) {
        allTeams.add(new ArrayList<>(currentTeam));
    }

    List<Collaborator> remainingCollaboratorsCopy = new ArrayList<>(remainingCollaborators);
    for (Collaborator collaborator : remainingCollaborators) {
        currentTeam.add(collaborator);
        remainingCollaboratorsCopy.remove(collaborator);
        generateTeams(currentTeam, remainingCollaboratorsCopy, requiredSkills, minTeamSize, maxTeamSize, allTeams);
        currentTeam.remove(collaborator);
    }
}

public boolean hasRequiredSkills(List<Collaborator> team, List<Skill> requiredSkills) {
    List<Skill> teamSkills = new ArrayList<>();
    for (Collaborator collaborator : team) {
        teamSkills.addAll(collaborator.getSkills());
    }

    for (Skill requiredSkill : requiredSkills) {
        if (Collections.frequency(teamSkills, requiredSkill) < Collections.frequency(requiredSkills, requiredSkill)) {
            return false;
        }
    }

    return true;
}
```


## 6. Integration and Demo 

* A new option on the admin menu options was added.


## 7. Observations

n/a