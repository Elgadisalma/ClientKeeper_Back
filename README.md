# ClientKeeper - Backend

ClientKeeper est un backend Spring Boot conçu pour gérer les opérations bancaires, sécuriser les données sensibles, et intégrer des fonctionnalités d’intelligence artificielle pour analyser le risque de churn et générer des offres personnalisées.

---

## **Table des matières**
1. [Prérequis](#prérequis)
2. [Installation](#installation)
3. [Fonctionnalités](#fonctionnalités)

---

## **Prérequis**
Avant de démarrer, assurez-vous d’avoir installé les éléments suivants :

- [Java 17+](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Maven 3.8+](https://maven.apache.org/)
- [PostgreSQL 12+](https://www.postgresql.org/download/)
- [Python 3.8+](nécessaire pour l'IA)
- [Docker](https://www.docker.com/) (optionnel pour la base de données)

---

## **Installation**

1. **Cloner le dépôt**
   ```bash
   git clone https://github.com/votre-repo/clientkeeper-backend.git
   cd clientkeeper-backend
   ```

2. **Configurer la base de données**
   - Créez une base de données PostgreSQL nommée `clientkeeper`.
   - Configurez les informations de connexion dans le fichier `src/main/resources/application.properties` :
     ```properties
     spring.datasource.url=jdbc:postgresql://localhost:5432/clientkeeper
     spring.datasource.username=USERNAME
     spring.datasource.password=PASSWORD
     ```

3. **Installer les dépendances et compiler le projet**
   ```bash
   mvn clean install
   ```

4. **Démarrer l’application**
   ```bash
   mvn spring-boot:run
   ```

L’application sera disponible à l’adresse : [http://localhost:8080](http://localhost:8080).

5. **Exécuter l’intelligence artificielle**
   Une partie de l’application repose sur un moteur d’IA externe écrit en Python. Voici les étapes pour le configurer :
   
   - Accédez au répertoire AI (à cloner séparément si nécessaire) :
     ```bash
     git clone https://github.com/votre-repo/clientkeeper-ai.git
     cd clientkeeper-ai
     ```

   - Créez un environnement virtuel et installez les dépendances :
     ```bash
     python -m venv env
     source env/bin/activate   # Utilisez env\Scripts\activate sous Windows
     pip install -r requirements.txt
     ```

   - Entraînez le modèle :
     ```bash
     python train_model.py
     ```

   - Lancez l’API Flask :
     ```bash
     python app.py
     ```

   - L’API sera disponible à : [http://localhost:5000](http://localhost:5000).

---

## **Fonctionnalités**

### **1. Gestion des clients**
- Ajouter, mettre à jour, supprimer et consulter les informations des clients.
- Gestion des statuts client (actif/inactif).

### **2. Gestion des transactions**
- Effectuer des dépôts et retraits.
- Consulter le solde et l’historique des transactions des clients.

### **3. Prédiction du churn**
- Analyse des données client pour identifier ceux à risque.
- Appel au moteur d’IA TensorFlow via une API Flask pour prédire le churn.

### **4. Sécurité**
- Authentification basée sur JWT.
- Chiffrement des données sensibles dans la base.
- Utilisation du protocole HTTPS pour sécuriser les communications.

---

## **Tests**

1. **Exécuter les tests unitaires**
   ```bash
   mvn test
   ```

---
**© 2025 ClientKeeper**

