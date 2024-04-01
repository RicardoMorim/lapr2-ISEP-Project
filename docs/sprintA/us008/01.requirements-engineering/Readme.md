# US008 - List of the vehicles needing check-up


## 1. Requirements Engineering

### 1.1. User Story Description

As an FM, I want to list the vehicles needing the check-up.

### 1.2. Customer Specifications and Clarifications 

**From the specifications document:**

>	The system should provide a list of vehicles that need maintenance or check-ip based on their maintenance/check-up frequency and current kilometers.

**From the client clarifications:**

> **Question:** Can the vehicles get placed automatically on a list or the one listing has to be the FM?
>
> **Answer:** The list of vehicles is automatically created but the creation is triggered by the FM.

> **Question:** What information will appear on the final list regarding the vehicle,besides the needing for check-up?
>
> **Answer:** Data that allow to identify the vehicle like Plate, brand and model, as well as, the data that allowed to select/insert te vehicle in the list, number of kms, frequency of checkup and the last checkup.

> **Question** Are there acceptance criteria when asking for the list?
> 
> **Answer** The list must clearly identify the vehicles through: plate number, brand, model and the that justified the checkup need.

### 1.3. Acceptance Criteria

* **AC1:** The system is required to produce a roster of vehicles schedules for check-ups, considering their check-up intervals and current kilometers.
* **AC2:** The list must include pertinent information like ID/VIN, brand, model and current kilometers.
* **AC3:** Accessibility to the list should be seamless for the Facilities Manager.

### 1.4. Found out Dependencies

* There is a dependency on "US006 - Register a vehicle" as there is a need to have vehicles registered in the system with information, such as current kilometers and checkup frequency, in order to list vehicles needing check-up.
* There is a dependency on "US007 - Register a vehicle's check-up" - The system's capacity to identify vehicles requiring inspection depends on their registered check-up status.

### 1.5 Input and Output Data

**Input Data:**

* None

**Output Data:**

* List of vehicles needing check-up
* Vehicle information 
* (In)Success of the operation

### 1.6. System Sequence Diagram (SSD)

**_Other alternatives might exist._**

#### Alternative One

![System Sequence Diagram - Alternative One](svg/us008-system-sequence-diagram-alternative-one.svg)

#### Alternative Two

![System Sequence Diagram - Alternative Two](svg/us008-system-sequence-diagram-alternative-two.svg)

### 1.7 Other Relevant Remarks

* 