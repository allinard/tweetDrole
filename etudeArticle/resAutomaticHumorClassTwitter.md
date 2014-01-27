# R�sum� de l'article : Automatic Humor Classifcation on Twitter
---------

### En quelques mots : 
L'auteur se propose de tester la classifaction de tweet humouristique selon le type de l'humour. 
Il veut par le biais d'algorithme identifier automatiquement le type et le sujet du tweet humoristique voir pour apr�s regrouper avec des informations sur l�auteur du tweet : le genre, le lieu � 


## Introduction
Des exemples sont fournis sur comment identifier des tweets humouristiques :

* Un jeu de mot qui paraphrase une phrase connu, le hasktag peut �galement aider
* Reconnaissance d'entit�s nomm�es (besoin d'un lexique NE) dans le contexte d'un sujet particulier (besoin d'un lexique), par exemple : le poke sur facebook d�finit comme une action gay. L'auteur mentionne une n�cessit� de tag sur le discours.
* Une anecdote de vie, avec le pr�sent progressif du verbe ( ou non ), utilisation d'un lexique qui contient des termes anecdotiques
* La reconnaissance de mot vulgaire (besoin d'un lexique)
* Dans le cas d'une insulte, n�c�ssit� de connaitre la personne cit�, de plus l'analyse du verbe et le COI qui l'accompagne
* La phonologie peut �tre une source d'humour (mais necessite de la connaissance) 

## Le travail r�alis�
La principale tache est de classifier les tweets selon les diff�rents type d'humour ainsi que leur sous-cat�gories : 

* anecdotes ex: les VDM (vie de merde 
* insultes
	* homophobe
	* x�nophobe ex blague belge
	* misogyne ex http://www.slate.fr/france/54807/salviac-rtl-trierweiler 
* ironie
* autod�rision 
* blagues
* citations ex: phrase humouristique tir� d'une �mission #tpmp 
* phrases vulgaires et grasses
* jeux de mots
	* homophone 
	* contrep�trie
* les autres ...


## Les donn�es
Twitter en quelque mots c'est plus de 200 millions messages par jour, une limite de 140 caract�res par message, un utilisateur peut s'abonner pour suivre d'autres comptes, les messages peuvent contenir des url des hashtag et des r�f�rences � d'autres utilisateurs.
(L'API tweeter permet de les r�cup�rer) 

## Evaluation 
Ils ont utilis� le site http://www.funny-tweets.com pour collecter un ensemble de tweet "drole" ce qui leur a �vit� de faire le tri entre les tweets dr�les et ce qui ne le sont pas.
La cat�gorisation est tr�s dificile et subjectif donc un message peut appartenir � plusieurs sujets et types d'humours. Pour avoir les meilleurs r�sultats, il faut le faire � la main ce qui peut �tre fait en utilisant une m�canique Turc (???). 

## Classification de l'humour
Ils utilisent un algorithme semi supervis� qui prend en entr�e des tweets annot�s pour produire des ensembles avec des caract�ristiques propres au classifieur.
L�examen peut se faire � plusieurs niveaux que voici :
*	Syntaxique : 
	*	Transitivit� du verbe (regarder COD, COI)
	*	Recherche d amibiguit�
*	Patrons prenant en compte les mots les plus fr�quents
*	Caract�ristiques lexicales : 
	*	quel lexique de mot est repr�sent� (ex : lexique gay)
	*	existence d�une entit� nomm� (ex : Facebook, Starbucks) 
	*	ambiguit� du lexique 
*	Caract�ristiques morphologiques :
	*	le temps du verbe
	*	mot qui n'existe pas
*	Phonologie : mot provenant d'une liste d'homophone
*	Style : smiley, ponctuation ( grand nombre de !!!!!), hashtag




