# Mon mini Twitter en RESTlet

L'objectif du TP est d'écrire une application réalisant un twitter minimal.
Cette application est basée sur le framework [RESTlet](http://restlet.com/) pour
construire une API REST. L'implémentation donnée est incomplète et c'est à vous
de la compléter.

## Récupération des fichiers du TP

Copiez ```tpsweb-tp-restlet-twitter.zip``` le et décompressez le sur votre espace personnel.

Ouvrez Eclipse et importez le projet en tant que projet Maven.

## Lancement du serveur

Au choix :

* Depuis Eclipse, clic droit sur le projet, puis ```exécuter en tant qu'application java```.
Le main se situe dans ```org.inria.restlet.mta.main.Main```

* Depuis la ligne de commande :

```
mvn package
java -jar target/uber-mta-0.0.1.jar
```

Le serveur se lance et écoute sur le port 5000 de votre machine localhost.

> Connectez votre navigateur web à l'adresse : ```http://127.0.0.1:5000```,
un client web minimal vous est présenté. Certaines fonctionnalités restent à implémenter.

## Tester

Lancer le script ```create_users.sh```.

> Corriger l'implémentation

# Coder les nouvelles fonctionnalités

On désire que le serveur puisse suivre cette spécification :


|URI                     | command   | description                     |
|------------------------|-----------|---------------------------------|
|/users                  | GET       | return the list of users        |
|/users                  | POST      | add a user                      |
|/users/{userId}         | GET       | retrieve a user (and its tweets)|
|/users/{userId}         | DELETE    | delete the user                 |
|/users/{userId}/tweets  | POST      | add a tweet to this user        |
|/users/{userId}/tweets  | GET       | return the tweets of this user |



> Ajoutez une classe ```Tweet``` et ajouter à la classe ```User``` la possibilité de conserver une liste de ```Tweet``` en mémoire

> Ajoutez les fonctions responsables de traiter les requêtes arrivant sur l'URI : ```/users/{userId}/tweets```

> Codez les fonctionnalités manquantes (Si le temps le permet vous pouvez coder les parties html clientes manquantes.)
