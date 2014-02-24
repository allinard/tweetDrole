#!/bin/bash
# script bash pour lancer du weka au LINA
#java -Xmx1000M -jar weka.jar weka.classifiers.bayes.BayesNet -t iris.arff
java -Xmx1000M -jar weka.jar weka.classifiers.trees.J48 -t corpusTweet.arff -i -d outputModelCorpusTweetj48.model -T corpusTweetTest.arff -x 10 > outputCorpusTweetj48.txt
echo "FINIIII! Merci de remettre les résultats a F.Boudin, A.Granet et A.Linard ;)"
exit 0

