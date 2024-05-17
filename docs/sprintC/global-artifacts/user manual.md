# User Manual


### [Glossary](01.requirements-engineering/glossary.md)



## Introduction
### Purpose and Scope
The user manual contains information on how to use and operate the application developed. It has a glossary that explains some of the more complex terms as well as some system features explained to contribute to the better understanding of the application. This also specifies the features of the application and has a list of possible situations that might help the user identify and solve various issues that may occur while using the application.

To support the activity of MusgoSublime, our application pretends to help in the planning, construction and maintenance of green spaces for collective use.


## System Overview

### Objectives

MusgoSublime is an organization dedicated to the planning, construction and maintenance of green spaces for collective use in their multiple dimensions.

EcoSphere is a robust solution crafted to streamline and optimize the oversight of natural environments and help MusgoSublime.

Its main goals are centered on fostering efficient organization, upkeep, and improvement of green areas, with a focus on ensuring their well-being and long-term viability. For that it creates a team with various collaborators with different skills and jobs, assigning tasks and improving regular check-ups.


### Structure

The application is structured into distinct modules, each tailored to fulfill specific functions while remaining interconnected to deliver a seamless user experience.
* Dashboard: The central hub where users can access key metrics, notifications, and quick links to various functions.
* Inventory Management: Allows users to catalog and track resources such as plants, tools, equipment, and materials essential for green space maintenance.
* Task Management: Enables users to create, assign, and track tasks related to maintenance activities, ensuring efficient allocation of resources and timely completion of assignments.
* Reporting and Analytics: Provides comprehensive reporting functionalities, including data analysis tools to monitor the health and performance of green spaces over time.
* Communication: Facilitates communication and collaboration among team members.
* Documentation: Stores essential documents, manuals, guidelines, and references relevant to green space management for easy access and reference.

### Main Features

* Inventory tracking and management
* Task assignment and tracking
* Vehicle registration
* Reporting and analytics
* Communication tools
* Document storage and access
* Team Proposal Generation
* Water Consumption Cost Analysis
* Optimal Route Determination
* Equipment Usage Analysis
* User Portal Data Collection

### Diagram

![Diagram](01.requirements-engineering/svg/use-case-diagram.svg)


## Features/Functions

###  1. Register a skill | Human Resources Manager

This feature allows the Human Resources Manager (HRM) to register a skill to the system.

#### Step by step

1. Open the HRM system.
2. Select the option to register a skill.
3. Input the skill name and description.
4. Confirm the data.

### 2. Register a job | Human Resources Manager

This feature allows the Human Resources Manager (HRM) to add a job to the system

### Step by step
1. Open the HRM system.
2. Select the option to register a job.
3. Input the job name and description.
4. Confirm the data.

### 3. Register a collaborator and characteristics | Human Resources Manager

This features provides the HRM the ability to register a collaborator with job-related information and characteristics.

#### Step by step

1. Log in to the HRM system.
2. Access the section for creating a new collaborator.
3. Choose to create a new collaborator.
4. Enter the requested data for the new collaborator with personal information and job details.
5. Upon inputting the data, the system will present all entered information for a review. Review the entered information.
6. Confirm the provided data by selecting the appropriate option on the collaborator.
7. Upon confirmation, the system will display a success message confirming the creation of new collaborator.


### 4. Assign one or more skills | Human Resources Manager

This feature allows the Human Resources Manager (HRM) to add one or more skills to a collaborator.

#### Step by step
1. Open the HRM system.
2. Select the option to add one or more skills to a collaborator.
3. Select the skills
4. Select the collaborator
5. Confirm the data

#### Step by step

### 5. Generate a team proposal | Human Resources Manager

This feature allows the Human Resources Manager (HRM) to generate a team proposal automatically for a task.

### Step by step
1. Open the HRM system.
2. Select the option to generate a team proposal
3. Select the required skills
4. Select the required jobs
5. Select the minimum and maximum amount of collaborators
6. Confirm the data
7. Accept or refuse the team proposal


#### Step by step

### 6. Register a vehicle | Human Resources Manager

This feature allows the FM (Fleet Manager) to register a new vehicle and its characteristics in the system.

#### Step by step
1. Log into the system as an FM.
2. Navigate to the vehicle registration section.
3. Choose to register a new vehicle.
4. Fill in the form with the necessary vehicle information.
5. Confirm the entered data.


### 7. Register a Vehicle's Check-up | Fleet Manager

This feature allows the Fleet Manager (FM) to register a vehicle's check-up, capturing details such as the vehicle's ID, date of the check-up, and current kilometers.

#### Step by step

1. Open the FM system.
2. Access the vehicle check-up registration section.
3. Choose the vehicle for check-up registration.
4. Input the date of the check-up and the current kilometers.
5. Review the entered information.
6. Confirm the check-up registration.

## 8. List the vehicles needing the check-up | Fleet Manager

This feature allows the Fleet Manager (FM) to list vehicles requiring a check-up, presenting relevant information about each vehicle, such as its model, brand, type, tare weight, gross weight, current kilometers, registration date, acquisition date, maintenance frequency, and vehicle plate.

#### Step by step

1. Open the FM system.
2. Navigate to the section for listing vehicles needing a check-up.
3. Select the option to list vehicles.
4. Review the displayed list of vehicles requiring a check-up, along with their associated details.
5. Receive information about the vehicles and their check-up status.

## 9. Water cunsumption cost

This functionality empowers the Green Space Manager (GSM) to request a statistical breakdown of water consumption costs.

### Step by step 

1. Access the GMS system.
2. Navigate to the section dedicated to requesting statistical analysis of water consumption costs.
3. Input the necessary data concerning the time frame and park identification.
4. Submit the data.
5. The system will validate the input and present the analysis results, including a bar chart, average expenses, and statistical metrics.
6. Visualize equipment usage patterns through a pie chart for each day.

## 10. Pie chart of the equipment used in each day

This feature enables the Green Space Manager (GSM) to analyze equipment usage.

### Step by step

1. Log in to the GMS system.
2. Proceed to the equipment usage analysis section.
3. Upload the "EquipmentUsed.csv" file as prompted.
4. Provide the file path and upload.
5. The system will process the file and showcase the analysis outcome in the form of a pie chart.
6. Retrieve data from the user portal.

## 11. Collect data from the user portal

This functionality empowers the Green Space Manager (GSM) to gather information from the user portal pertaining to park usage.

### Step by step

1. Access the GMS system.
2. Navigate to the data collection section from the user portal.
3. Upload the "Inquiry.csv" file.
4. Provide the file path and upload.
5. The system will process the file and present analysis findings and conclusions regarding park usage.
6. Import a .csv file containing pipe installation routes.

## 12. Import a .csv file containing routes for pipe installation

This capability allows the Green Space Manager (GSM) to import a .csv file containing information on potential routes for laying pipes between water points, along with associated installation costs.

### Step by step

1. Access the GMS system.
2. Navigate to the section for managing pipe installation routes.
3. Upload the .csv file containing information on water points and distances between them.
4. Provide the file path and upload.
5. The system will process the file and visualize the imported route data.
6. Determine optimal routes for pipe installation with minimal cost.

## 13. Know the routes to be opened with minimum cost

This functionality empowers the Green Space Manager (GSM) to utilize an algorithm that identifies the most efficient routes to be established and determines the pipes required to be laid with the least overall cost.

### Step by step

1. Access the GMS system.
2. Navigate to the pipe installation routes section.
3. Upload the .csv file with water point and distance data.
4. Provide the file path and upload.
5. The system will process the file and display a graph depicting the imported route data. 
6. Utilizing previously collected route data, the system will generate another graph showcasing routes connecting all water points with minimal cost.

