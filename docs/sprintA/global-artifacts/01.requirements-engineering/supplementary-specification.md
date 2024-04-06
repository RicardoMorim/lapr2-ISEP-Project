# Supplementary Specification (FURPS+)

## Functionality

_Specifies functionalities that:_

_are common across several US/UC;_
_are not related to US/UC, namely: Audit, Reporting and Security._

> - The application should be capable of recording certain information.
> - The application should provide funcionalities that allow organization and strutucture of the information presented.
> - The application should provide services that gives management and security for a better use.
> - The application must support the English language.
> - All images should be in SVG format.

## Usability

_Evaluates the client interface. It has several subcategories,
among them: error prevention; interface aesthetics and design; help and
documentation; consistency and standards. Practically speaking, usability
is about the ability of the expected client(s) to operate our product under
the expected operating conditions._

> - The application should have a client-friendly environment.
> - The application should be easy to use.
> - The application should be intuitive.

## Reliability

_Refers to the integrity, compliance and interoperability of the software. The requirements to be considered are: frequency and severity of failure, possibility of recovery, possibility of prediction, accuracy, average time between failures._

> - The application needs to be secure and protected against attacks.
> - The application should not crash.
> - The application must support the English language.

## Performance

_Evaluates the performance requirements of the software, namely: response time, start-up time, recovery time, memory consumption, CPU usage, load capacity and application availability._

> - The application shouldn't have any delays when operating.

## Supportability

_The supportability requirements gathers several characteristics, such as:
testability, adaptability, maintainability, compatibility,
configurability, installability, scalability and more._

(fill in here )

## +

### Design Constraints

_Specifies or constraints the system design process. Examples may include: programming languages, software process, mandatory standards/patterns, use of development tools, class library, etc._

> - The application must be developed in Java.
> - The application must be developed using the IntelliJ IDE or NetBeans.

### Implementation Constraints

_Specifies or constraints the code or construction of a system such
such as: mandatory standards/patterns, implementation languages,
database integrity, resource limits, operating system._

> - The graphical user interface application must be developed in JavaFX 11.
> - CamelCase standard should be used.
> - Javadoc should be used to generate useful documentation.
> - There should be unit tests for all methods.
> - The unit tests should be implemented using JUnit 5 framework.
> - The JaCoCo plugin should be used to generate the coverage report.
> - All the images/figures produced during the software development process should be recorded in
    SVG format.

### Interface Constraints

_Specifies or constraints the features inherent to the interaction of the
system being developed with other external systems._

> - All application users need to go through the authentication process.
> - The application must have a simple interface and easy navigation to simplify usage for all users.
> - The application must be easily accessible both via computer and mobile devices.
> - The application must provide feedback to users when they perform any operation.

### Physical Constraints

_Specifies a limitation or physical requirement regarding the hardware used to house the system, as for example: material, shape, size or weight._

> - Regular backups should be performed to uphold data integrity and avert system malfunctions.