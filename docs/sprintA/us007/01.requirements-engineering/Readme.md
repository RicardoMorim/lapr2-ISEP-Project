# US007 - Register a vehicle's check-up

## 1. Requirements Engineering

### 1.1. User Story Description

As a fleet manager (FM), I wish to register a vehicle's checkup.
 
### 1.2. Customer Specifications and Clarifications

**From the specifications document:**

>	Each vehicle should have is first checkup date and frequency associated in Km.

**From the client clarifications:**

> **Question:** Which attributes will you need for the vehicle's check-up?
>
> **Answer:** Plate number, date, kms at checkup.
>
> **Question:** What are the validation requirements for the vehicle ID?
>
> **Answer:** After 2020: AA-00-AA Between 2005-2020 00-AA-00
Between 1992-2005 00-00-XX
>
>**Question** Can a vehicle have more than one check-up?
>
> **Answer** Yes.
>
> **Question** What is the unit of measurement used to estimate the check-up frequency (Kms, months, etc.)?
>
> **Answer** In real context all could be considered, in the scope of this project just Kms will be considered.

### 1.3. Acceptance Criteria

* **AC1:** All required fields must be filled in.
* **AC2:** Every vehicle should have a check-up frequency associated.
* **AC3:** When creating a check-up, the vehicle should not have a check-up scheduled within the estimated frequency.

### 1.4. Found out Dependencies

* There is a dependency on "US008 - List the vehicles needing the check-up" as there must be a vehicle existent to associate a checkup.

### 1.5 Input and Output Data

**Input Data:**

* Typed data:
  * Check-up date

* Selected data:
  * Vehicle

**Output Data:**

* Vehicle and the date of the check-up
* (In)Success of the operation

### 1.6. System Sequence Diagram (SSD)

**_Other alternatives might exist._**

#### Alternative One

![System Sequence Diagram - Alternative One](svg/us007-system-sequence-diagram-alternative-one.svg)

#### Alternative Two

![System Sequence Diagram - Alternative Two](svg/us007-system-sequence-diagram-alternative-two.svg)

### 1.7 Other Relevant Remarks

* None