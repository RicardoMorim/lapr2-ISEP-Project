# US026 - Assign a vehicle to an entry in the agenda 

## 1. Requirements Engineering

### 1.1. User Story Description

As a GSM, I want to assign one or more vehicles to an entry in
the Agenda.

### 1.2. Customer Specifications and Clarifications

**From the specifications document:**

> None.

**From the client clarifications:**

> **Question:** What is the criteria to accept an assign of a Vehicle to an Entry?
Only Vehicle with no Entry's can be assigned ?
Only Vehicles with no Entry on the day selected ?
It is possible to add any kind of vehicles?
>
> **Answer:** The vehicle needs to be available in the period.
Yes, any can of vehicles can be assigned.

> **Question:** How will the Green Spaces Manager choose the vehicle to assign? By its plate?
>
> **Answer:** Assuming you mean assingning to a Task/Agenda Entry, the data related with vehicle should be provided in order to ease the selection.

> **Question:** Is the number of vehicles to be assigned provided by the Green Spaces Manager?
>
> **Answer:** There is no specification concerning the number of vehicles, is upt to GSM decide what vehicles the task needs.

> **Question:** Todos os veículos da empresa devem estar disponíveis para os atribuir a uma entrada da agenda, ou só os veículos com a manutenção em dia?
>
> **Answer:** Todos os veiculos que não estejam assignados a uma tarefa no mesmo periodo. 
Num contexto real precisariamos de gerir também (in)disponibilidade dos veiculos por revisões ou avarias mas não é necessário nesta prova-de-conceito.


### 1.3. Acceptance Criteria

* **AC1:** The vehicle(s) must exist in the system.
* **AC2:** The vehicle(s) must not be assigned to another task in the same period.
* **AC3** The entry in the Agenda must exist and be in a state that allows assignment of vehicles.


### 1.4. Found out Dependencies

* There is a dependency on "US006 - Since the vehicle must exist in the system to be assigned to an entry in the agenda"
* There is a dependency on "US022 - Since we need to have an entry beforehand, so we can later assign a vehicle to it"
* There is a dependency on "US021 - Since US022 depends on US021"

### 1.5 Input and Output Data

**Input Data:**

* Selected data:
  * The vehicle(s)
  * The agenda entry

**Output Data:**

* (In)Success of the operation
* The entry and the vehicle(s) assigned to the entry

### 1.6. System Sequence Diagram (SSD)

![System Sequence Diagram - Alternative One](svg/us026-alternative-one.svg)

### 1.7 Other Relevant Remarks

* The user can also remove a vehicle from an entry in the agenda.