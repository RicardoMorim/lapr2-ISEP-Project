# US002 - Register a Job

## 4. Tests 

Gets and sets
    
    @Test
    void getNameReturnsCorrectName() {
    Job job = new Job("Developer", "Writes code");
    assertEquals("Developer", job.getName());
    }

    @Test
    void getShortDescriptionReturnsCorrectShortDescription() {
        Job job = new Job("Developer", "Writes code");
        assertEquals("Writes code", job.getShortDescription());
    }

    @Test@Test
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

Equals method in Java is used to compare two objects for equality. It checks if the two objects have the same values for their properties.

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

## 5. Construction (Implementation)

### Class Job 

```java
     public Job(String name, String shortDescription) {
    this.name = name;
    this.shortDescription = shortDescription;
}
```

### Class JobRepository

```java
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
```

```java
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
    
```



## 6. Integration and Demo 

* The user is able to register a job.
* The user is able to remove a job.
* For demo purposes some jobs are bootstrapped while system starts.


## 7. Observations

n/a