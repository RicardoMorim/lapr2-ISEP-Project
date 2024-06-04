# US021 - Add a new toDoEntry to the To-Do List

## 3. Design - User Story Realization

### 3.1. Rationale

_**Note that SSD - Alternative One is adopted.**_

| Interaction ID | Question: Which class is responsible for...           | Answer                 | Justification (with patterns)                                                                                 |
|:--------------|:------------------------------------------------------|:-----------------------|:--------------------------------------------------------------------------------------------------------------|
| Step 1  		    | 	... interacting with the actor?                      | ToDoListGUI            | Pure Fabrication: there is no reason to assign this responsibility to any existing class in the Domain Model. |
| 			  	        | 	... coordinating the US?                             | GreenSpaceController   | Controller                                                                                                    |
| Step 2  		    | 	... requesting data?                                 | ToDoListGUI            | Pure Fabrication                                                                                              |
| Step 3			     | ... receiving the data?                               | ToDoListGUI            | Pure Fabrication                                                                                              |
| 			  		       | ... get the green space repository?					            | Repositories           | Information Expert, High cohesion, Low coupling                                                               |
| 			  		       | ... get all the green spaces and return them as a list? | GreenSpaceRepository   | Information Expert                                                                                            |
| Step 4  		    | 	...showing the green spaces to select?						           | ToDoListGUI            | Pure Fabrication                                                                                              |
| Step 5  		    | ... handling the user selecting a green space?         | ToDoListGUI            | Pure Fabrication                                                                                              |
| Step 6  		    | 	...show the confirmation info?                       | ToDoListGUI            | Pure Fabrication                                                                                              |
| Step 7  		    | 	... instantiating a new To-Do List Entry?            | ToDoListController     | Creator, High cohesion, Low coupling                                                                          |
| 		            | 	... validating all data (local validation)?          | ToDoList               | Information Expert                                                                                            |
| 			  		       | 	... validating all data (global validation)?         | ToDoListRepository     | Information Expert                                                                                     |
| 			  		       | 	... saving the To-Do List Entry?                     | ToDoListRepository     | Information Expert                                                                                       |
|               | ... having all the repositories?                      | Repositories           |  Information Expert, High cohesion, Low coupling                                                                                                        |
| Step 8  		    | 	... informing operation success?                     | ToDoListGUI            | IE: is responsible for user interactions.                                                                     |

### Systematization ##

According to the taken rationale, the conceptual classes promoted to software classes are:

* Green Space Manager
* Green Space
* ToDoList

Other software classes (i.e. Pure Fabrication) identified:

* ToDoListGUI
* GreenSpaceController
* GreenSpaceRepository
* ToDoListController
* ToDoListRepository
* Repositories


## 3.2. Sequence Diagram (SD)

_**Note that SSD - Alternative One is adopted.**_

### Full Diagram

This diagram shows the full sequence of interactions between the classes involved in the realization of this user story.

![Sequence Diagram - Full](svg/us021-sequence-diagram-full.svg)

## 3.3. Class Diagram (CD)

![Class Diagram](svg/us021-class-diagram.svg)