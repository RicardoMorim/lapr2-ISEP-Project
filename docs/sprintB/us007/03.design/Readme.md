# US007 - Register a vehicle's check-up

## 3. Design - User Story Realization 

### 3.1. Rationale

_**Note that SSD - Alternative One is adopted.**_

| Interaction ID                                           | Question: Which class is responsible for... | Answer                        | Justification (with patterns)                                                                                |
|:---------------------------------------------------------|:--------------------- |:------------------------------|:-------------------------------------------------------------------------------------------------------------|
| Step 1: asks to register a vehicle's check-up 	  		      |	... interacting with the actor? | RegisterMaintenanceUI         | Pure Fabrication|
| 			  		                                                  |	... coordinating the US? | RegisterMaintenanceController | Controller                                                                                                   |
| Step 2: shows all registered vehicles needing check-up		 |							 |                               |                                                                                                              |
| Step 3: selects a vehicle 		                             |	...saving the inputted data? | Task                          | IE: object created in step 1 has its own data.                                                               |
| Step 4:  		                                              |	...knowing the task categories to show? | System                        | IE: Task Categories are defined by the Administrators.                                                       |
| Step 5  		                                               |	... saving the selected category? | Task                          | IE: object created in step 1 is classified in one Category.                                                  |
| Step 6  		                                               |							 |                               |                                                                                                              |              
| Step 7  		                                               |	... validating all data (local validation)? | Task                          | IE: owns its data.                                                                                           | 
| 			  		                                                  |	... validating all data (global validation)? | Organization                  | IE: knows all its tasks.                                                                                     | 
| 			  		                                                  |	... saving the created task? | Organization                  | IE: owns all its tasks.                                                                                      | 
| Step 8  		                                               |	... informing operation success?| CreateTaskUI                  | IE: is responsible for user interactions.                                                                    | 

### Systematization ##

According to the taken rationale, the conceptual classes promoted to software classes are: 

* Maintenance

Other software classes (i.e. Pure Fabrication) identified: 

* RegisterMaintenanceUI
* RegisterMaintenanceController
* MaintenanceRepository
* VehicleRepository

## 3.2. Sequence Diagram (SD)

_**Note that SSD - Alternative Two is adopted.**_

### Full Diagram

This diagram shows the full sequence of interactions between the classes involved in the realization of this user story.

![Sequence Diagram - Full](svg/us007-sequence-diagram-full.svg)

### Split Diagrams

The following diagram shows the same sequence of interactions between the classes involved in the realization of this user story, but it is split in partial diagrams to better illustrate the interactions between the classes.

It uses Interaction Occurrence (a.k.a. Interaction Use).

![Sequence Diagram - split](svg/us007-sequence-diagram-split.svg)

**Get Vehicles List**

![Sequence Diagram - Partial - Get Task Category Object](svg/us007-sequence-diagram-partial-get-list.svg)

**Create Maintenance**

![Sequence Diagram - Partial - Create Maintenance](svg/us007-sequence-diagram-partial-create-maintenance.svg)

## 3.3. Class Diagram (CD)

![Class Diagram](svg/us006-class-diagram.svg)