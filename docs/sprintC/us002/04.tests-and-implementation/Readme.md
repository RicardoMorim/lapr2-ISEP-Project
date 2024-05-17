# US006 - Create a Task 

## 4. Tests 

    @Test
    void getShortDescriptionReturnsCorrectShortDescription() {
        Job job = new Job("Developer", "Writes code");
        assertEquals("Writes code", job.getShortDescription());
    }

    @Test
    void setNameUpdatesName() {
        Job job = new Job("Developer", "Writes code");
        job.setName("Designer");
        assertEquals("Designer", job.getName());
    }

    @Test
    void setShortDescriptionUpdatesShortDescription() {
        Job job = new Job("Developer", "Writes code");
        job.setShortDescription("Designs interfaces");
        assertEquals("Designs interfaces", job.getShortDescription());
    }


    @Test
    void equalsReturnsTrueForSameObject() {
        Job job = new Job("Developer", "Writes code");
        assertTrue(job.equals(job));
    }

    @Test
    void equalsReturnsFalseForNull() {
        Job job = new Job("Developer", "Writes code");
        assertFalse(job.equals(null));
    }

    @Test
    void equalsReturnsFalseForDifferentClass() {
        Job job = new Job("Developer", "Writes code");
        assertFalse(job.equals("Developer"));
    }

    @Test
    void equalsReturnsTrueForSameValues() {
        Job job1 = new Job("Developer", "Writes code");
        Job job2 = new Job("Developer", "Writes code");
        assertTrue(job1.equals(job2));
    }

    @Test
    void hashCodeReturnsSameHashCodeForSameValues() {
        Job job1 = new Job("Developer", "Writes code");
        Job job2 = new Job("Developer", "Writes code");
        assertEquals(job1.hashCode(), job2.hashCode());
    }

## 5. Construction (Implementation)

### Class CreateTaskController 

```java
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
```



## 6. Integration and Demo 

* A new option on the Employee menu options was added.

* For demo purposes some tasks are bootstrapped while system starts.


## 7. Observations

n/a