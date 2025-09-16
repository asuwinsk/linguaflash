# Liguaflash

**Liguaflash** application was designed to help with learning languages. Key features of the application include:

- Creating flashcards with auto translation
- Joining flashcards in custom decks
- Creating user's own decks
- Learning mode with rotatable flashcards
- Measuring usage statistics for decks

This Spring Boot application provides two types of endpoints – **REST API** returning JSON responses and 
**Web View** endpoints rendering HTML via Thymeleaf templates (for browser-based UI).

## Installation
**Liguaflash** application is written in Spring Boot. In Docker, only the MySQL database is started, 
the application itself runs locally. 

To run:
1. Clone the repository  
    `git clone https://github.com/asuwinsk/linguaflash/.git`<br>
    `cd Liguaflash`

2. Start MySQL DB in Docker<br>
    `docker-compose up -d`

DB connection details:<br>
host: localhost<br>
port: 3306<br>
user: root<br>
password: root<br>
database: mydb<br>

2.	Run the Application<br>
    Run locally with:  `./mvnw spring-boot:run` or use IDE.

Application will be available at:
http://localhost:8080

## Technologies
- Java 17
- Spring Boot
- Spring Data JPA (Hibernate)
- MySQL DB
- Jackson
- Docker
- Thymeleaf
- Maven

## User flow
1. List of available flashcards<br>
   ![Flashcards list](images/readme/1.png)<br>
   This page enables to perform CRUD operations for flashcards.

2. Adding flashcards with auto translation<br>
    ![Add flashcard](images/readme/2.png)<br>
    When the Front field is filled, the Back field populates automatically with the translation.

3.	List of decks<br>
    ![Decks list](images/readme/3.png)<br>
    Switching to a view deck endpoint get an access to perform CRUD operations for decks. From here 
    user has access to learning mode for a selected deck.

4.	View deck<br>
    ![View deck](images/readme/4.png)<br>
    This page shows the deck details. 

5.	View flashcards in specific deck<br>
    ![View flashcards in deck](images/readme/5.png)<br>
    This page enables to add or delate flashcards for that deck. 

6.	Adding flashcards to deck<br>
    ![Add flashcards to deck](images/readme/6.png)<br>
    Filter by source/target languages and multi-select flashcards from a range.

7.	Learning mode<br>
    ![Learning mode](images/readme/7.png)<br>
    By clicking ‘Let’s Learn!’ in View flashcards, user can test knowledge by rotating flashcards. Firstly, 
    showing Front and then Back with example sentence and level. User can navigate with Next/Previous, 
    draw random flashcards, or reset to start over.