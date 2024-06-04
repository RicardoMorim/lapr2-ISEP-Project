# US025 - Cancel an entry in the Agenda

## 3. Design - User Story Realization

### 3.1. Rationale

| Interaction ID                                                      | Question: Which class is responsible for...                  | Answer                 | Justification (with patterns)                                                                                 |
|:--------------------------------------------------------------------|:-------------------------------------------------------------|:-----------------------|:--------------------------------------------------------------------------------------------------------------|
| Step 1: asks to assign one or more skills to a collaborator  		     | 	... interacting with the actor?                             | AddSkillUI             | Pure Fabrication: there is no reason to assign this responsibility to any existing class in the Domain Model. |
| 			  		                                                             | 	... coordinating the US?                                    | AddSkillUI             | Pure Fabrication: there is no reason to assign this responsibility to any existing class in the Domain Model. |
| Step 2: Shows the list of collaborators  		                         | 		... interacting with the actor?					                       | AddSkillUI             | Pure Fabrication: there is no reason to assign this responsibility to any existing class in the Domain Model. |
| Step 3: Choosing the collaborator  		                               | 	... interacting with the actor?                             | AddSkillUI             | Pure Fabrication: there is no reason to assign this responsibility to any existing class in the Domain Model. |
|                                                                     | ... accessing the repository to get the collaborator list    | CollaboratorController | IE: its the controller with access to the collaborator repository                                             |
|                                                                     | ... getting the collaborator list                            | CollaboratorRepository | IE: its the repository that stores the collaborator's information's                                           |
| Step 4: shows the list of skills to assign to that collaborator  		 | 	...interacting with the actor?                              | AddSkillUI             | Pure Fabrication: there is no reason to assign this responsibility to any existing class in the Domain Model. |
|                                                                     | ... accessing the skill repository to get the list of skills | SkillController        | IE: its the controller that has access to the skill repository                                                |
| 			  		                                                             | 	... knowing the list of skills                              | SkillRepository        | IE: The repository keeps all the skills                                                                       |
| Step 5: Chooses one or more skills to add                           | 	... interacting with the actor?                             | AddSkillUI             | Pure Fabrication: there is no reason to assign this responsibility to any existing class in the Domain Model. |
|                                                                     | ... validate skill                                           | Skill                  | IE: Validates the skill before validating it                                                                  |
|                                                                     | ... saving the collaborator's skills                         | Collaborator           | IE: Manages collaborator's information                                                                        |
| Step 6: Displays operation success 		                               | ... interacting with the actor?							                       | AddSkillUI             | Pure Fabrication: there is no reason to assign this responsibility to any existing class in the Domain Model  |              

### Systematization ##

According to the taken rationale, the conceptual classes promoted to software classes are:

* CancelEntryUI
* CancelEntryController
* AgendaEntry
* Repositories
* AgendaRepository

Other software classes (i.e. Pure Fabrication) identified:

* 

## 3.2. Sequence Diagram (SD)

### Full Diagram

This diagram shows the full sequence of interactions between the classes involved in the realization of this user story.

![Sequence Diagram - Full](svg/us004-sequence-diagram-full.svg)

### Split Diagrams

N/A

## 3.3. Class Diagram (CD)

![Class Diagram](svg/us004-class-diagram.svg)