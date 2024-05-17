# US001 - Register a Skill

## 3. Design - User Story Realization

### 3.1. Rationale

_**Note that SSD - Alternative One is adopted.**_

| Interaction ID | Question: Which class is responsible for...           | Answer                   | Justification (with patterns)                                                                                 |
|:---------------|:------------------------------------------------------|:-------------------------|:--------------------------------------------------------------------------------------------------------------|
| Step 1         | 	... interacting with the actor?                      | RegisterSkillsUI         | Pure Fabrication: there is no reason to assign this responsibility to any existing class in the Domain Model. |
| 	              | 	... coordinating the US?                             | RegisterSkillsController | Controller                                                                                                    |
| Step 2  	      | 	...knowing the collaborators to show?                | TaskCategoryRepository   | IE: Tasks are kept in TasksRepository. Information.                                                           |
| Step 3         |                                                       |                          |                                                                                                               |
| Step 4         | ... requesting skill(s) to be registered?             | RegisterSkillsUI         | IE: is responsible for user interactions.                                                                     |
| Step 5         |                                                       |                          |                                                                                                               |
| Step 6         | ... asking if there are more skills to be registered? | RegisterSkillsUI         | IE: is responsible for user interactions.                                                                     |              
| Step 7         |                                                       |                          |                                                                                                               | 
| Step 8         | ... showing success message?                          | RegisterSkillsUI         | IE: is responsible for user interactions.                                                                     | 

### Systematization ##

According to the taken rationale, the conceptual classes promoted to software classes are:

* CollaboratorRepository

Other software classes (i.e. Pure Fabrication) identified:

* RegisterSkillsUI
* RegisterSkillsController

## 3.2. Sequence Diagram (SD)

_**Note that SSD - Alternative Two is adopted.**_

### Full Diagram

This diagram shows the full sequence of interactions between the classes involved in the realization of this user story.

![Sequence Diagram - Full](svg/us001-sequence-diagram-full.svg)

### Split Diagrams

The following diagram shows the same sequence of interactions between the classes involved in the realization of this user story, but it is split in partial diagrams to better illustrate the interactions between the classes.

## 3.3. Class Diagram (CD)

![Class Diagram](svg/us001-class-diagram.svg)