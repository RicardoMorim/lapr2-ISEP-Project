# US023 - Assign A Team To An Agenda NEtry

## 4. Tests

**Test 1** Ensure that a team is added to an entry

    @Test
    void testAssignTeamToEntry() {
        // Setup
        Team team = new Team("Team A");
        AgendaEntry entry = new AgendaEntry("Entry 1");

        // Execute
        entry.assignTeam(team);

        // Assert
        assertEquals(team, entry.getAssignedTeam());
    }

**Test 2** Ensure that a team cannot be assigned to two entries at the same time 

    @Test
    void testTeamCannotBeAssignedToTwoEntriesAtSameTime() {
        // Setup
        Team team = new Team("Team A");
        AgendaEntry entry1 = new AgendaEntry("Entry 1");
        AgendaEntry entry2 = new AgendaEntry("Entry 2");

        // Execute
        entry1.assignTeam(team);
        assertThrows(IllegalArgumentException.class, () -> entry2.assignTeam(team));
    }


## 5. Implementation
### Team Controller class
    
    ```java
        public class TeamController {
            private final TeamRepository teamRepository;
            public List<Team> getTeams() {
                return teamRepository.getTeams();
            }
        }
    ```

### Team Repository class

    ```java
        public class TeamRepository {
            private final List<Team> teams = new ArrayList<>();
            public List<Team> getTeams() {
                return teams;
            }
        }
    ```

### AgendaController class

    ```java
        public class AgendaController {
            private final AgendaRepository agendaRepository;
            public List<AgendaEntry> getEntries() {
                return agendaRepository.getEntries();
            }
        }
    ```

### Agenda class

    ```java
        public class AgendaRepository {
            private final List<AgendaEntry> entries = new ArrayList<>();
            public List<AgendaEntry> getEntries() {
                return entries;
            }
        }
    ```

## 6. Integration and Demo

* A new option on the admin and GRM menu options was added.

* For demo purposes a team is bootstrapped while system starts.

## 7. Observations

n/a