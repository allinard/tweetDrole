# Résumé de l'article : Robust Sentiment Detection on Twitter from Biased and Noisy Data
---------

### En quelques mots : 
Cet article traite de la façon de détecter automatique les "sentiments" émis dans un tweet.


## Etat de l'art
Du boulot a déjà été abattu pour détécter des sentiments en utilisant des modèles n-grammes (cf blogs + Pang et al.,2002 ; Pang et Lee,2004 ; Wiebe et Riloff,2005 ; Glance et al.,2005 ; Wilson et al.,2005).

DataSources : tweets de Twendz, Twitter Sentiment, TweetFeel, qui sont des sites proposant de la détéction de sentiments en temps réels de tweets.



## Classification
L'idée est de procéder de la façon suivante

                texte
                  |
                 /  \
                /    \
               /      \
      subjectif        objectif
          |
        /  \
       /    \
       +    -
       
       
Deux features possibles :
* Utilisation du POS tagging (messages d'opinion contiennent plus d'adjectifs et d'interjections par exemple)
* Polarité (opinion négative/positive)
* Syntaxe spécifique du tweet (links, ponctuation, emoticons, case...)
     
     
Classification du niveau de subjectivité :
* En général plus un utilisateur émet, plus ses tweets sont objectifs
* Certains mots véhiculent fortement l'opinion (ex: craint, cool, génial, naze...)
       