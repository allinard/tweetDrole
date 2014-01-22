# Résumé de l'article : Automatic Humor Classifcation on Twitter
---------

### En quelques mots : 
L'auteur se propose de tester la classifaction de tweet humouristique selon le type de l'humour. 
Il veut par le biais d'algorithme identifier automatiquement le type et le sujet du tweet humoristique voir pour après regrouper avec des informations sur l’auteur du tweet : le genre, le lieu … 


## Introduction
Des exemples sont fournis sur comment identifier des tweets humouristiques :

* Un jeu de mot qui paraphrase une phrase connu, le hasktag peut également aider
* Reconnaissance d'entités nommées (besoin d'un lexique NE) dans le contexte d'un sujet particulier (besoin d'un lexique), par exemple : le poke sur facebook définit comme une action gay. L'auteur mentionne une nécessité de tag sur le discours.
* Une anecdote de vie, avec le présent progressif du verbe ( ou non ), utilisation d'un lexique qui contient des termes anecdotiques
* La reconnaissance de mot vulgaire (besoin d'un lexique)
* Dans le cas d'une insulte, nécéssité de connaitre la personne cité, de plus l'analyse du verbe et le COI qui l'accompagne
* La phonologie peut être une source d'humour (mais necessite de la connaissance) 

## Le travail réalisé
On peut prendre en compte les différences d'humour suivantes : anecdotes, insultes, ironie, autodérision, blagues, citations, phrases vulgaires et grasses, jeux de mots, etc.


## Les données


## Classification de l'humour
L’examen peut se faire à plusieurs niveaux que voici :
-        Syntaxe : observation du verbe, recherche d amibiguité
-        Patron
-        Lexique : quel lexique de mot est représenté, existence d’une entité nommé, ambiguité du lexique
-        Morphologie : verbe
-        Phonologie : homophone
-        Pragmatique
-        Style : smiley, ponctuation, hashtag

## A faire 


