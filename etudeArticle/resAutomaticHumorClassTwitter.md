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
La principale tache est de classifier les tweets selon les différents type d'humour ainsi que leur sous-catégories : 

* anecdotes ex: les VDM (vie de merde 
* insultes
** homophobe
** xénophobe ex blague belge
** misogyne ex http://www.slate.fr/france/54807/salviac-rtl-trierweiler 
* ironie
* autodérision 
* blagues
* citations ex: phrase humouristique tiré d'une émission #tpmp 
* phrases vulgaires et grasses
* jeux de mots
** homophone 
** contrepétrie
* les autres ...


## Les données
Twitter en quelque mots c'est plus de 200 millions messages par jour, une limite de 140 caractères par message, un utilisateur peut s'abonner pour suivre d'autres comptes, les messages peuvent contenir des url des hashtag et des références à d'autres utilisateurs.
(L'API tweeter permet de les récupérer) 

## Evaluation 
Ils ont utilisé le site http://www.funny-tweets.com pour collecter un ensemble de tweet "drole" ce qui leur a évité de faire le tri entre les tweets drôles et ce qui ne le sont pas.
La catégorisation est très dificile et subjectif donc un message peut appartenir à plusieurs sujets et types d'humours. Pour avoir les meilleurs résultats, il faut le faire à la main ce qui peut être fait en utilisant une mécanique Turc (???). 

## Classification de l'humour
Ils utilisent un algorithme semi supervisé qui prend en entrée des tweets annotés pour produire des ensembles avec des caractéristiques propres au classifieur.
L’examen peut se faire à plusieurs niveaux que voici :
*	Syntaxique : 
**	Transitivité du verbe (regarder COD, COI)
**	Recherche d amibiguité
*	Patrons prenant en compte les mots les plus fréquents
*	Caractéristiques lexicales : 
**	quel lexique de mot est représenté (ex : lexique gay)
**	existence d’une entité nommé (ex : Facebook, Starbucks) 
**	ambiguité du lexique 
*	Caractéristiques morphologiques :
**	le temps du verbe
**	mot qui n'existe pas
*	Phonologie : mot provenant d'une liste d'homophone
*	Style : smiley, ponctuation ( grand nombre de !!!!!), hashtag




