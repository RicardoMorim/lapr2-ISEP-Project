# US027 - List all green spaces managed by a GSM.

## 4. Tests

**Test 1:** Ensure that the green spaces can be fetched by user email

    @Test
    void getGreenSpacesByUser() {
        GreenSpaceRepository repo = new GreenSpaceRepository();
        GreenSpace greenSpace1 = new GreenSpace("Park1", new Address("Street1", "City1", "1234-567"), 100.0, new Type("Type1"), new Email("user1@gmail.com"));
        GreenSpace greenSpace2 = new GreenSpace("Park2", new Address("Street2", "City2", "1234-567"), 200.0, new Type("Type2"), new Email("user1@gmail.com"));
        repo.addGreenSpace(greenSpace1);
        repo.addGreenSpace(greenSpace2);
        List<GreenSpace> expected = List.of(greenSpace1, greenSpace2);
        List<GreenSpace> result = repo.getGreenSpacesManagedByUser(new EmailWrapper(new Email("user1@gmail.com")));
        assertEquals(expected, result);
    }


**Test 2:** Ensure that the green spaces are sorted by size in descending order


    @Test
    void getGreenSpacesByUserSortedBySize() {
        GreenSpaceRepository repo = new GreenSpaceRepository();
        GreenSpace greenSpace1 = new GreenSpace("Park1", new Address("Street1", "City1", "1234-567"), 100.0, new Type("Type1"), new Email("user1@gmail.com"));
        GreenSpace greenSpace2 = new GreenSpace("Park2", new Address("Street2", "City2", "1234-567"), 200.0, new Type("Type2"), new Email("user1@gmail.com"));
        repo.addGreenSpace(greenSpace1);
        repo.addGreenSpace(greenSpace2);
        List<GreenSpace> expected = List.of(greenSpace2, greenSpace1); // greenSpace2 has larger size
        List<GreenSpace> result = repo.getGreenSpacesManagedByUser(new EmailWrapper(new Email("user1@gmail.com")));
        assertEquals(expected, result);
    }


## 5. Construction (Implementation)

### Class GreenSpaceController

    public List<GreenSpace> getGreenSpacesManagedByUser(EmailWrapper user) {
        return greenSpaceRepository.getGreenSpacesManagedByUser(user);
    }

### Class GreenSpaceRepository


    public List<GreenSpace> getGreenSpacesManagedByUser(EmailWrapper user) {
        List<GreenSpace> greenSpacesManaged = new ArrayList<>();
        for (GreenSpace greenSpace : greenSpaces) {
            if (greenSpace.getUser().equals(user)) {
                greenSpacesManaged.add(greenSpace);
        }
    }

        // Load the properties file
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("src/main/resources/config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Get the sorting algorithm from the properties file
        String sortingAlgorithm = prop.getProperty("SortingAlgorithm");

        // Sort the list based on the specified algorithm
        if ("BubbleSort".equals(sortingAlgorithm)) {
            bubbleSort(greenSpacesManaged);
        } else if ("QuickSort".equals(sortingAlgorithm)) {
            quickSort(greenSpacesManaged, 0, greenSpacesManaged.size() - 1);
        }

        return greenSpacesManaged;      
    }


### Class GreenSpaceManagerGUI


    fetchButton.setOnAction(e -> {
        String email = emailField.getText();
        if (email.isEmpty()) {
            showAlert("Email cannot be empty.");
            return;
        }
        try{
            new Email(email);
        } catch (IllegalArgumentException ex) {
            showAlert("Invalid email.");
            return;
        }
        EmailWrapper user = new EmailWrapper(new Email(email));
        List<GreenSpace> managedGreenSpaces = controller.getGreenSpacesManagedByUser(user);

        greenSpaceListView.getItems().clear();
        for (GreenSpace gs : managedGreenSpaces) {
            greenSpaceListView.getItems().add(gs.toString());
        }
    });


## 6. Integration and Demo

* A new option on the GSM menu options was added.

* For demo purposes some green spaces are bootstrapped while system starts.

## 7. Observations

n/a