# US011 - Collect data from the user portal


## 1. Requirements Engineering

### 1.1. User Story Description

As a GSM, I want to be able to collect data from the user portal about the use of the park, so that I may understand the use of the park by different age groups.

### 1.2. Customer Specifications and Clarifications 

**From the specifications document:**

>	Each collaborator is characterized by a name, a birthdate, admission date, an address, contact (email or phone number), an identification document and its identification number.


**From the client clarifications:**

> **Question:** When creating a collaborator with an existing name, what does the system do?
>
> **Answer:** It's not common and most improbable to have different individual with same name in the same context, however it’s ID documentation number should be unique for sure.

> **Question:** Which information is mandatory to insert a collaborator in the program (fundamental characteristics)?
>
> **Answer:** Name, birthdate, admission date, address, contact info (mobile and email), taxpayer number, ID doc type and respective number.

> **Question:** Does the HRM select the job from a list that we display?
>
> **Answer:** Displaying or not, It's a matter of UX, the dev team should decide about it, but the valid jobs are the ones created within the US02.

> **Question:**  The term "fundamental characteristics" is mentioned in US003. What, precisely, are those characteristics?
>
> **Answer:** The essential data are name, birthdate, admission date, address, contact (email or phone number), an identification document and its identification number.



### 1.3. Acceptance Criteria

* **AC1:** All required fields must be filled in.
* **AC2:** Every collaborator must have an associated job.
* **AC3:** Name, birthdate, admission date, address, contact info (mobile and e-mail), taxpayer number, ID doc type, ID number.


### 1.4. Found out Dependencies

* There is a dependency on "US010 - Register the equipment used- " as there must be a job to associate to every collaborator.


### 1.5 Input and Output Data

**Input Data:**

* Typed data:
    * name
    * birthdate 
    * admission date
    * address
    * contact info (mobile and e-mail)
    * identification document 
    * identification document number

	
* Selected data:
    * a job

**Output Data:**

* List of existing task categories
* (In)Success of the operation

### 1.6. System Sequence Diagram (SSD)

**_Other alternatives might exist._**

#### Alternative One

![System Sequence Diagram - Alternative One](svg/us003-system-sequence-diagram-alternative-one.svg)

#### Alternative Two

![System Sequence Diagram - Alternative Two](svg/us003-system-sequence-diagram-alternative-two.svg)

### 1.7 Other Relevant Remarks

* None