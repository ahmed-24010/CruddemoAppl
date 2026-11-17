# TP 03 — Réponses aux questions de compréhension

## 1. Différence entre JPA, Hibernate et Spring Data JPA

- **JPA (Jakarta Persistence API)** est une **spécification** qui définit les interfaces et les annotations pour gérer la persistance des données en Java, comme `@Entity`, `@Id` et `EntityManager`.
- **Hibernate** est une **implémentation** de JPA. Il assure la communication avec la base de données et génère les requêtes SQL.
- **Spring Data JPA / Spring Boot** assure l’**orchestration** : il facilite l’intégration de JPA et Hibernate dans Spring et automatise leur configuration.

## 2. Pourquoi `@Transactional` sur les écritures ?

Les méthodes `save`, `update`, `delete` et `deleteAll` modifient les données de la base de données. Elles nécessitent une transaction pour garantir la cohérence des opérations et permettre un rollback en cas d’erreur.

Les méthodes `findById` et `findAll` effectuent uniquement des lectures. Dans le cadre de ce TP, elles ne nécessitent donc pas explicitement `@Transactional`.

## 3. Différence entre `persist(entity)` et `merge(entity)`

- **`persist(entity)`** sert à enregistrer une nouvelle entité. L’objet devient géré par le contexte de persistance et son identifiant peut être généré.
- **`merge(entity)`** sert à réintégrer les données d’une entité détachée dans le contexte de persistance.

La méthode `merge()` renvoie une **instance gérée** contenant les données fusionnées. L’objet passé en paramètre reste détaché.

## 4. Différence entre JPQL et SQL

SQL travaille directement avec les tables et les colonnes de la base de données, tandis que JPQL travaille avec les entités Java et leurs attributs.

Ainsi, `FROM Student WHERE lastName = :theData` désigne la **classe `Student` et le champ `lastName`**, et non directement la table `student` et la colonne `last_name`. Hibernate traduit ensuite la requête JPQL en SQL.

## 5. Pourquoi utiliser un paramètre nommé ?

L’utilisation de `:theData` avec `setParameter()` permet de transmettre la valeur séparément de la requête. Cela évite que la saisie de l’utilisateur soit interprétée comme une partie du code SQL et protège contre les injections SQL.

À l’inverse, la concaténation de chaînes peut permettre à une entrée malveillante de modifier la requête.

## 6. Rôle de `@Repository`

`@Repository` déclare une classe comme composant Spring spécialisé dans l’accès aux données. En plus de permettre la détection du bean, cette annotation participe à la **traduction des exceptions** de persistance en exceptions Spring de type `DataAccessException`.

Cela facilite la gestion des erreurs et réduit la dépendance du code aux exceptions spécifiques de la technologie utilisée.

## 7. Pourquoi ne pas utiliser `remove(new Student(5))` ?

La méthode `remove()` exige une entité gérée par le contexte de persistance. Or, `new Student(5)` crée un nouvel objet qui n’est pas géré par `EntityManager`.

Il faut d’abord récupérer l’étudiant avec `find()`, puis le supprimer :

```java
Student student = entityManager.find(Student.class, 5);

if (student != null) {
    entityManager.remove(student);
}
```

Ces opérations doivent être effectuées dans la même transaction.

## 8. Comportement de `spring.jpa.hibernate.ddl-auto`

| Valeur | Comportement |
|---|---|
| `none` | Ne modifie pas le schéma de la base de données. |
| `validate` | Vérifie la correspondance entre les entités et le schéma existant, sans le modifier. |
| `update` | Met à jour le schéma selon les entités sans recréer systématiquement les tables. |
| `create` | Crée le schéma au démarrage et peut supprimer les tables existantes ainsi que leurs données. |
| `create-drop` | Crée le schéma au démarrage et le supprime à l’arrêt de l’application. |

**Choix pour la production :**

En production, je choisirais généralement `none` ou `validate`, avec un outil de migration comme Flyway ou Liquibase pour contrôler les modifications du schéma.

Il ne faut pas utiliser `create` en production, car le redémarrage de l’application peut entraîner la suppression des données existantes.

**Configuration utilisée dans ce TP :**

```properties
spring.jpa.hibernate.ddl-auto=none
```

Cette configuration permet de conserver le schéma et les données existantes de la base `student_tracker`.
