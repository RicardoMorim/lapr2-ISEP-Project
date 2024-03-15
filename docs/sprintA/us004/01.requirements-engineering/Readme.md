# US006 - Create a Task 


## 1. Requirements Engineering

### 1.1. User Story Description

As an HRM, I want to assign one or more skills to a collaborator.

### 1.2. Customer Specifications and Clarifications 

**From the specifications document:**

>	Each collaborator must have a unique id

>	Using the id, the user must choose the collaborator and add a skill

**From the client clarifications:**

> **Question:** Which skills can be added?
>
> **Answer:** Any skills the HRM wants. He must just add all the skills he needs to the database, to use them later.


### 1.3. Acceptance Criteria

* **AC1:** All required fields must be filled in.
* **AC2:** If the collaborator doesn't exist the system must let the user re-choose the collaborator
* **AC3** If the collaborator already has the skill chosen, the user must be alerted, and the operation aborted.


### 1.4. Found out Dependencies

* There is a dependency on "US003 -  As an HRM, I want to register a collaborator with a job and fundamental
  characteristics." - there has to be at least one collaborator to be assigned a skill

### 1.5 Input and Output Data

**Input Data:**

* Typed data:
    * The collaborator
    * The skill

**Output Data:**

* All the skills of the collaborator
* (In)Success of the operation

### 1.6. System Sequence Diagram (SSD)

**_Other alternatives might exist._**

#### Alternative One

![System Sequence Diagram - Alternative One](svg/us004-alternative-one.svg)



### 1.7 Other Relevant Remarks

* The user can always choose a different person to add the skill in case he makes a mistake