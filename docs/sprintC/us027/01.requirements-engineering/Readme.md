# US027 - List all green spaces managed by a GSM.

## 1. Requirements Engineering

### 1.1. User Story Description

As a GSM, I need to list all green spaces managed by me.

### 1.2. Customer Specifications and Clarifications

**From the specifications document:**

> The list must be organized by size in descending order. There should be at least two algorithms for organizing the list.

**From the client clarifications:**

> **Question:** Dear client, which info about Green Spaces do you want the GSM see when listing? only the name ?
>
> **Answer:** Each de team can decide about the aspects related to UX/UI.

> **Question:** Dear client, in this user story you only want to list the Green Spaces manage by the GSM. Due to this, the GSM should be register in the app previusly, isn't it? Which atributes should it have? A  GSM is a collaborator?
>
> **Answer:** yes; the GSM (you can have many) should be registered in the app.
> GSM is a role that can be played a registered user with the appropriate priviliges;

> **Question:** What's in the configuration file mentioned in AC1?
>
> **Answer:** That file will indicate the possible sorting methods that the application works with. Through a method given in ESOFT (protected variations), an interface with several methods will be imported.



### 1.3. Acceptance Criteria

* **AC1:** The list of green spaces must be sorted by size in descending order. The sorting algorithm to be used by the application must be defined through a configuration file. At least two sorting algorithms should be available.
* **AC2:** If there are no parks registered to form the list, the program should warn the user.

### 1.4. Found out Dependencies

* There is a dependency on "US020 - Register a Green Space and its area" - there must also be a manager (US2) registered for that particular location (US3).

### 1.5 Input and Output Data

**Input Data:**

* Typed data:
  * None

* Selected data:
  * Sorting algorithm (descending by size (default))

**Output Data:**

* The generated list ((In)Success of the operation)

### 1.6. System Sequence Diagram (SSD)

**_Other alternatives might exist._**

#### Alternative One

![System Sequence Diagram - Alternative One](svg/us027-system-sequence-diagram-alternative-one.svg)


### 1.7 Other Relevant Remarks

* none