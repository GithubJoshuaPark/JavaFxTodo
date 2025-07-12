# Todo Desktop application using JavaFX, maven which support JavaFX 21 and later.

1. Project Structure
   - src/main/java: Contains the Java source files.
   - src/main/resources: Contains the FXML files and other resources.
   - src/test/java: Contains the test files.
   - pom.xml: The Maven project file. (https://maven.apache.org/pom.html)
   - README.md: This file.

   ```aiignore
     JavaFxEx01/
     ├── src/
     │   └── main/
     │       ├── java/
     │       │   └── com/
     │       │       └── example/
     │       │           └── javafxex01/
     │       │               ├── Main.java
     │       │               ├── TodoApp.java
     │       │               ├── controller/
     │       │               ├── model/
     │       │               └── view/
     │       └── resources/
     │           └── fxml/
   ```
   
### Project Explanation

This project is a JavaFX-based desktop application 
for managing a simple to-do list. 
It uses Maven for dependency management and supports JavaFX 21 and later. 
The application allows users to add, edit, delete, 
and mark tasks as completed. Key features include:

1. Model:
   - TodoItem: Represents individual to-do items with properties like title,
      description, and completed.
   
2. Main Application:
   - Main: Entry point that launches the JavaFX application.
   - TodoApp: Contains the main UI and application logic, including:
     - A TableView for displaying tasks.
     - Columns for task details (title, description, completed).
     - Buttons for adding, editing, and deleting tasks.
     - Observable properties for real-time updates.
     
3. UI Features: 
   - Editable table rows.
   - Checkboxes for marking tasks as completed.
   - Double-click functionality for editing tasks.
   - Styled buttons and layout for a user-friendly interface.
<hr></hr>

--- 
## Requirements
- **JDK**: 21 or later
- **JavaFX**: 21 or later
- **Maven**: For dependency management

## How to Run
1. Clone the repository:
   ```bash
   git clone https://github.com/GithubJoshuaPark/JavaFxEx01.git
   cd JavaFxEx01
   ```
