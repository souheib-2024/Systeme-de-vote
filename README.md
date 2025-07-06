🗳️ Système de Vote via les Produits de Meta

Plateforme sécurisée de gestion de vote en temps réel, intégrée aux canaux de communication de Meta — **WhatsApp**, **Messenger** et **Instagram** *(Phase 1 centrée sur WhatsApp)*.

📌 Description

Ce système permet à des administrateurs de configurer des sessions de vote et d’en suivre les résultats en direct, tandis que les participants votent via WhatsApp. Le système garantit une expérience utilisateur fluide et une sécurité avancée des données.

🎯 Objectif

Optimiser la gestion des votes lors d'événements grâce à un système **automatisé, sécurisé et multicanal.


🔍 Phase 1 – Focus sur WhatsApp

🎛️ Fonctionnalités principales

🔐 Interface Administrateur
- Connexion sécurisée via **Spring Security**
- Gestion des événements, questions, et options de vote
- Administration des utilisateurs et rôles
- Visualisation en temps réel des résultats

💬 Vote via WhatsApp
- Envoi automatique des choix via l’API WhatsApp Cloud
- Traitement des réponses des utilisateurs
- Gestion des erreurs et confirmations automatiques
- Vote limité à une fois par utilisateur

🧠 Traitement des données
- Stockage des données avec **Spring Data JPA**, **Hibernate**
- Cryptage et sécurisation des données utilisateurs
- Base de données : **MariaDB** (prod), **H2** (test/dev)
- Statistiques en temps réel


🧱 Stack Technique

| Catégorie         | Technologies utilisées                                       |
|-------------------|--------------------------------------------------------------|
|     Langage       | Java                                                         |
|     Back-end      | Spring Boot, Spring MVC, Spring Security, RESTful API        |
|     Front-end     | Thymeleaf                                                    |
|     ORM / DB      | Spring Data JPA, Hibernate, MariaDB, H2                      |
|     Sécurité      | Spring Security, Authentification par rôle (ADMIN/USER)      |
|     Tests         | JUnit 5, Mockito                                             |
| Conteneurisation  | Docker, Docker Compose, DockerHub                            |
|     CI/CD         | GitHub Actions                                               |


