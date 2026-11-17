# TP 03 — CRUD Demo (Spring Boot + JPA + MySQL)

Application Spring Boot en ligne de commande illustrant les opérations CRUD sur une entité `Student` avec JPA/Hibernate et MySQL.

## Prérequis

- Java 25
- MySQL (port 3306)
- Maven (le wrapper `mvnw` est fourni)

## Structure

```text
sql/                         scripts d'initialisation MySQL
src/main/java/com/tp3/cruddemo/
  CruddemoApplication.java   point d'entrée
  dao/                       StudentDAO + implémentation (EntityManager)
  entity/Student.java        entité JPA
src/main/resources/application.properties
REPONSES.md                  réponses aux questions de compréhension
```

## Installation de la base de données

Exécuter les scripts dans l'ordre :

```bash
mysql -u root -p < sql/02-student-tracker.sql   # crée la base student_tracker et la table student
mysql -u root -p < sql/01-create-user.sql       # crée l'utilisateur springstudent
```

La connexion est configurée dans `src/main/resources/application.properties`
(`springstudent` / `springstudent` sur `jdbc:mysql://localhost:3306/student_tracker`).
`ddl-auto=none` : le schéma est géré uniquement par les scripts SQL.

## Lancer l'application

```bash
./mvnw spring-boot:run      # Linux / macOS / Git Bash
mvnw.cmd spring-boot:run    # Windows (cmd / PowerShell)
```

## Tests

```bash
./mvnw test
```

## Réponses aux questions

Voir [REPONSES.md](REPONSES.md).
