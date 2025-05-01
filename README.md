# Application Vélib' - JavaFX

Ce projet est une application JavaFX permettant d'afficher en temps réel les données du service Vélib' Métropole à Paris.

## Fonctionnalités

- Affichage des stations disponibles (nom, numéro, statut, etc.)
- Détails sur la station sélectionnée (capacité, vélos disponibles, bornes)
- Filtres disponibles :
  - Par département (75, 92, 93, 94)
  - Par arrondissement (1 à 20 pour Paris)
  - Par type de station (fixe ou mobile, simulé)

## Technologies utilisées

- Java 17+
- JavaFX
- FXML pour l’interface
- API OpenData Paris
- org.json pour parser le JSON

## Installation

1. Cloner ce dépôt
2. Ouvrir le projet avec Eclipse ou un IDE Java compatible
3. Vérifier que JavaFX et la librairie `org.json` sont bien ajoutées au classpath/modulepath
4. Lancer `Main.java`

## Données

Les données sont récupérées via l’API publique :
https://opendata.paris.fr/explore/dataset/velib-disponibilite-en-temps-reel/information/

