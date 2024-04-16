# US010 - Register the equipment used 


## 1. Requirements Engineering

### 1.1. User Story Description

As a GSM, I want to know which piece(s) of equipment is/are used in each day so I can understand the users preferences.

### 1.2. Customer Specifications and Clarifications 

**From the specifications document:**

>  The park is characterized by having the following equipment: walking paths, children's playground, picnic area, and exercise machines (gymnastics equipment).

>	

**From the client clarifications:**

> **Question:** In the US10 the client says they have an electronic device that allows the users to insert the equipment they used. How is that csv file sent to our product? Is it trough an API or is the GSM responsible for uploading the document before generating the list of equipment used in that day?
>
> **Answer:** 

> **Question:** Hello client, in US010 is mentioned that we have equipments, are the equipments mentioned all of them? If not which are missing? It is also mentioned that "EquipmentUsed.csv" file records 1000 users, what happens if there is a 1001 user? Should it replace any one of there that was already recorded?
>
> **Answer:** 

> **Question:** On the "EquipmentUsed.csv", are there only recorded the choices of 1000 users? Could the GSM request a different number, for example, the choices of 800 users? Is it a random selection, or is it the last responses?
> 
> **Answer:**  

> **Question:** 
>
> **Answer:** 

> **Question:** 
>
> **Answer:** 

> **Question:** 
>
> **Answer:** 

> **Question:** 
>
> **Answer:** 


### 1.3. Acceptance Criteria

* **AC1:** All required fields must be filled in.
* **AC2:** The task reference must have at least 5 alphanumeric characters.
* **AC3:** When creating a task with an existing reference, the system must reject such operation and the user must be able to modify the typed reference.

### 1.4. Found out Dependencies

* 

### 1.5 Input and Output Data

**Input Data:**

* Typed data:
    * a reference
    * a designation 
    * an informal description
    * a technical description
    * an estimated duration
    * an estimated cost
	
* Selected data:
    * a task category 

**Output Data:**

* List of existing task categories
* (In)Success of the operation

### 1.6. System Sequence Diagram (SSD)

#### Alternative One

![System Sequence Diagram - Alternative One](svg/us010-system-sequence-diagram.svg)

