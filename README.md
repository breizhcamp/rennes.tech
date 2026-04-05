# rennes.tech

## What ?

Les communautés techniques sont diverses et vivent leur vie chacune à leur rythme, mais ça ne veut pas dire qu'elles n'ont pas 
des choses à partager. Entre autre, de la visibilité aussi bien pour le public que pour les structures avec qui elles peuvent
être amenées à discuter pour obtenir des salles ou des financements.

Bref, ça ne mange pas de pain de proposer une page qui résume "_ce qui se passe dans le coin_" et d'apprendre à se connaître par la même occasion.

Donc voilà => https://rennes.tech

## Proposer un nouveau meetup

Si vous souhaitez proposer un nouveau meetup, c'est très simple, il suffit de faire une PR avec les éléments suivants :
 - Le logo du meetup, au format 100x100 pixels, dans le répertoire [src/main/resources/static/meetups](https://github.com/breizhcamp/rennes.tech/tree/main/src/main/resources/static/meetups)
 - L'ajout d'une entrée dans le fichier de configuration yaml [src/main/resources/groups.yaml](https://github.com/breizhcamp/rennes.tech/tree/main/src/main/resources/groups.yaml)

## Comment ça marche

rennes.tech est une application Kotlin/Spring Boot avec un frontend généré côté serveur par Thymeleaf.
Le backend va piocher dans les API meetup.com pour récupérer les événements à venir et les afficher.

Les données sont stockées dans une base de données PostgreSQL.

## 🚀 Quick Start

Prérequis : Java 21, Maven (wrapper provided), Docker (for Postgres).

Démarrer Postgres:
```bash
docker-compose up -d
```

Lancer l'app (dev profile):
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

L'application est accessible à l'adresse http://localhost:4010/

Pour activer la synchronisation des événements, il faut définir la configuration suivante :
```yaml
rennes.tech.sync.enabled: true
```

## 🧱 Architecture & Design

L'application est organisée en suivant l'architecture hexagonale :
- application: point d'entrée de l'application, contient les contrôleurs (Web, REST) et le cron pour la synchronisation
- config: configuration Spring
- domain: logique métier (use cases), entités, ports
- infrastructure: implémentation des ports (accès à la base de données, clients externes)