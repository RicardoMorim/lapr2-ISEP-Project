# US004 - Add a skill to a collaborator

## 1. Requirements Engineering

### 1.1. User Story Description

As a HRM, I want to add a skill to a collaborator.

### 1.2. Customer Specifications and Clarifications

**From the specifications document:**

> Skills are what enables a collaborator to do a certain task

**From the client clarifications:**

> **Question:** Can any skill be registered to any collaborator/job? Or should they be associated in categories in
> association with a specific job?
>
> **Answer:** There is no association, it totally depends on the CV of the collaborator.

> **Question:** Should it be possible to add the same skill to a collaborator multiple times?
>
> **Answer:** That does not make sense.

> **Question:** Is there any certification/proof needed to register a skill to a collaborator?
>
> **Answer:** no.

> **Question:** Is there any limit of skills or a minimum number of skills?
>
> **Answer:** No.

> **Question:** Is there any special characteristic to be able to add a skill to a collaborator?
>
> **Answer:** No.

> **Question:** Can a collaborator have no skills assigned?
>
> **Answer:** Yes.



### 1.3. Acceptance Criteria

* **AC1:** All required fields must be filled in.
* **AC2:** The collaborator must exist in the system.
* **AC3** The collaborator must not have the skill already.
* **AC4:** The skill must exist in the system.


### 1.4. Found out Dependencies

* There is a dependency on "US003 - As an HRM, I want to register a collaborator with a job and fundamental
  characteristics." - there has to be at least one collaborator to be assigned a skill
* There is a dependency on "US001 - As an HRM, I want to register skills that a collaborator may have" - there has to be at least one skill to be assigned to a collaborator

### 1.5 Input and Output Data

**Input Data:**

* Typed data:
    * The collaborator
    * The skill

**Output Data:**

* (In)Success of the operation
* The updated skills of the collaborator

### 1.6. System Sequence Diagram (SSD)

![System Sequence Diagram - Alternative One](svg/us004-alternative-one.svg)

### 1.7 Other Relevant Remarks

* The user can always choose a different person to add the skill in case he makes a mistake