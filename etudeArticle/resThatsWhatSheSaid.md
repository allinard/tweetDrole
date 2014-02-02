# Résumé de l'article : That’s What She Said: Double Entendre Identification
---------

### En quelques mots : 
Cet article traite de l'identification des phrases à double sens (That's What She Said), dont l'un des sens est connoté sexuellement.


## Introduction
La tâche est relativement complexe en raison de l'analyse sémantique, et de la compréhension culturelle qu'elle nécessite. Il y a en effet un lexique important de phrases à double sens.

Une idée de reconnaissance des blagues "TWSS" est de prendre cela comme une métaphore.

Une blague That's What She Said constitue aussi un double mapping entre deux domaines : le domaine/contexte initial, et le contexte sexuel cible.
* Comparaison des préférences adjectivales de noms sexuellement explicites avec celles d'autres noms pour déterminer quels noms sont des euphémismes pour des noms sexuellement explicites.
* Examen des relations entre structures dans les domaines érotique et non érotique.

Utilisation d'une approche DEviaNT (Double Entendre via Noun Transfer) 


## Problème
Les blagues a double sens (TWSS) ont plusieurs caractéristiques :
* Les phrases avec des noms euphémismes pour des noms sexuellement explicites ont des chances d'être des TWSS
	* par ex : "banane", "saucisse", "boule" ...
* Les phrases initiales peuvent avoir la même structure qu'une phrase du domaine gras
	* par ex : [sujet] pourrait manger [objet] toute la journée (avec objet = saucisse par ex.)

Une reconnaissance de ces tournures peut être d'identifier si le contexte source dans lequel se situe la potentielle blague à double sens est aussi dans le contexte sexuel

Deux choses qui font que c'est difficile :
* Dans la plupart des domaines, un classifieur de blagues TWSS rapporte plus de précision que de rappel
	* le coup de dire "TWSS" après une phrase à double sens dans un contexte non approprié est important (car on peut passer pour un blaireau si on sort ça dans un diner huppé), alors que le cout de ne pas le dire dans le cas contraire est négligeable (on ne passe pas pour qqn de pas drôle si on ne dit pas "TWSS" à chaque phrase potentiellement sale).
	* pour le traitement de données FB ou Twitter, les faux positifs sont considérés comme spam ou censurés, alors que les faux négatifs ne sont pas notifiés.
* L'écrasante majorité des phrases de tous les jours ne sont pas des TWSSs, et faire atteindre une grande précision est encore plus difficile.


## Approche DEviaNT (Double Entendre via Noun Transfer)
Le corpus est composé de :
* un set de 76 noms avec forte prédominance dans des contextes sexuels, clusterisés dans 9 catégories (basés sur objets sexuels, parties du corps, etc...)
* un set composé du dernier, constituté de noms sexuellement explicites candidats à l'euphémisme
* un set de 98 noms représentant des parties du corps
* deux corpus utilisés par DEviaNT :
	* corpus érotique avec 1.5M phrases (EN - textfiles.com/sex/EROTICA) taggé avec Stanford Parser
	* Brown Corpus avec 57K phrases non érotiques issues de la littérature déjà taggé

L'analyse au niveau mot et phrase, 3 fonctions pour mesurer à quel point une phrase est érotique :
* Noun Sexiness : 
	* ex : en anglais, les mots avec le NS le plus élevé sont "barre" et "viande"
* Adjective Sexiness :
	* mesure sur comment un adjectif peut modifier un nom avec forte prédominance dans des contextes sexuels
	* ex : en anglais, les mots avec le AS le plus élevé sont "chaud" et "mouillé"
* Verb Sexiness

Caractéristiques DEviaNT :
* Euphémismes de noms
* Eléments structurants 
* Structure basique / Syntaxe
	* ponctuation
	* répétitions

Apprentissage :
* Utilisation de WEKA (classifieur SVM)
* DEviaNT fixe le cout des faux positifs a 100 fois celui des faux négatifs


## Evaluation
DEviaNT’s precision > 71.4% (pour rappel de < 20%)



