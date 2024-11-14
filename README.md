# Agence de Voyage - Backend

Ce projet est une application backend pour la gestion d'une agence de voyage, développée avec Spring Boot et sécurisée avec Spring Security. L'application permet la gestion des clients, des réservations, des destinations, des moyens de transport, et bien plus encore. Elle est conçue pour s'intégrer avec une base de données MySQL et offre une API REST sécurisée pour faciliter l'interaction avec un frontend.

## Fonctionnalités

- **Gestion des utilisateurs** : Création, mise à jour et suppression des clients et administrateurs de l'agence.
- **Réservations** : Enregistrement, consultation et gestion des réservations pour les voyages.
- **Destinations et moyens de transport** : Gestion des destinations disponibles et des différents moyens de transport (aérien, terrestre, maritime).
- **Sécurité** : Authentification et autorisation des utilisateurs via Spring Security, avec gestion des rôles et des droits d'accès.
- **API REST** : Fournit des endpoints RESTful pour l'intégration avec un frontend ou des applications mobiles.

## Technologies utilisées

- **Java** : Langage de programmation principal
- **Spring Boot** : Framework pour le développement backend rapide et efficace
- **Spring Security** : Module pour la gestion de la sécurité de l'application
- **MySQL** : Base de données relationnelle pour stocker les informations des clients, des réservations, des destinations, etc.
- **JPA/Hibernate** : Pour la gestion de la persistance des données

## Configuration de la base de données

L'application utilise une base de données MySQL. Assurez-vous que MySQL est installé et configuré. Les paramètres de connexion à la base de données se trouvent dans le fichier `application.yml`.

- **URL** : `jdbc:mysql://localhost:3306/agenceVoyage`
- **Utilisateur** : `root`
- **Mot de passe** : *votre mot de passe MySQL*

## Prérequis

- **Java** 17 ou version supérieure
- **Maven** pour la gestion des dépendances
- **MySQL** pour la base de données

## Lancer l'application

1. Clonez le projet :
   ```bash
   git clone https://github.com/votreutilisateur/agence-voyage-backend.git
