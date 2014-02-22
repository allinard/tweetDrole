#!/usr/bin/env python
# -*- coding: utf-8 -*-
#Libraries
from __future__ import print_function
from nltk.stem import *
from nltk.stem.snowball import SnowballStemmer
import sys, codecs, re, os
"""
Parser pour fichiers Arff. regroupant les mots par stemm
Input : fichier .arff avec 1 attribut/mot
Output : fichier .arff avec 1 attribut/stemm
Author: Alexis LINARD
		M1ATAL
Date: 12.17.13
"""





stemmerObject = SnowballStemmer("french", ignore_stopwords=True)




#the output cluster string
outputArff = ''


#get the file's content
fh = codecs.open('corpusTweet.arff', "r", "utf-8")
content = fh.readlines()
fh.close()

listeMots = []
listeStemms = []

dicoMotsIndice = {}
dicoStemmsIndice = {}
dicoMotsStemms = {}

i=0
for lineArff in content:
	if(lineArff.startswith("@ATTRIBUTE") and not "TweetDrole" in lineArff):
		i+=1
		listeMots.append(lineArff.split()[1])
		listeStemms.append(stemmerObject.stem(lineArff.split()[1]))


listeStemms = list(set(listeStemms))
listeStemms.sort()

listeStemms.append("TweetDroleHashtag")
listeStemms.append("TweetDroleSmileyPasContent")
listeStemms.append("TweetDroleSmileyContent")
listeStemms.append("TweetDroleExclamation")
listeStemms.append("TweetDroleRetweet")
listeStemms.append("TweetDroleNbRetweet")
listeStemms.append("TweetDroleLongeur")
listeStemms.append("TweetDroleNbMots")
listeStemms.append("TweetDroleDrole")

listeMots.append("TweetDroleHashtag")
listeMots.append("TweetDroleSmileyPasContent")
listeMots.append("TweetDroleSmileyContent")
listeMots.append("TweetDroleExclamation")
listeMots.append("TweetDroleRetweet")
listeMots.append("TweetDroleNbRetweet")
listeMots.append("TweetDroleLongeur")
listeMots.append("TweetDroleNbMots")
listeMots.append("TweetDroleDrole")

i=0
for mot in listeMots:
	dicoMotsIndice[i] = mot
	i+=1


i=0
for stemm in listeStemms:
	dicoStemmsIndice[stemm] = i
	i=i+1


for mot in listeMots:
	dicoMotsStemms[mot] = stemmerObject.stem(mot)

dicoMotsStemms["TweetDroleHashtag"] = "TweetDroleHashtag"
dicoMotsStemms["TweetDroleSmileyPasContent"] = "TweetDroleSmileyPasContent"
dicoMotsStemms["TweetDroleSmileyContent"] = "TweetDroleSmileyContent"
dicoMotsStemms["TweetDroleExclamation"] = "TweetDroleExclamation"
dicoMotsStemms["TweetDroleRetweet"] = "TweetDroleRetweet"
dicoMotsStemms["TweetDroleNbRetweet"] = "TweetDroleNbRetweet"
dicoMotsStemms["TweetDroleLongeur"] = "TweetDroleLongeur"
dicoMotsStemms["TweetDroleNbMots"] = "TweetDroleNbMots"
dicoMotsStemms["TweetDroleDrole"] = "TweetDroleDrole"




outputArff = "@RELATION tweet\n\n"
for stemm in listeStemms:
	if(stemm=="TweetDroleHashtag"):
		outputArff += '@ATTRIBUTE TweetDroleHashtag {"true","false"}\n'
	elif(stemm=="TweetDroleSmileyPasContent"):
		outputArff += '@ATTRIBUTE TweetDroleSmileyPasContent {"true","false"}\n'
	elif(stemm=="TweetDroleSmileyContent"):
		outputArff += '@ATTRIBUTE TweetDroleSmileyContent {"true","false"}\n'
	elif(stemm=="TweetDroleExclamation"):
		outputArff += '@ATTRIBUTE TweetDroleExclamation {"absent", "regulier", "surnombre"}\n'
	elif(stemm=="TweetDroleRetweet"):
		outputArff += '@ATTRIBUTE TweetDroleRetweet {"true","false"}\n'
	elif(stemm=="TweetDroleNbRetweet"):
		outputArff += '@ATTRIBUTE TweetDroleNbRetweet NUMERIC\n'
	elif(stemm=="TweetDroleLongeur"):
		outputArff += '@ATTRIBUTE TweetDroleLongeur NUMERIC\n'
	elif(stemm=="TweetDroleNbMots"):
		outputArff += '@ATTRIBUTE TweetDroleNbMots NUMERIC\n'
	elif(stemm=="TweetDroleDrole"):
		outputArff += '@ATTRIBUTE TweetDroleDrole {"true","false"}\n'
	else:
		outputArff += "@ATTRIBUTE "+stemm+" {0,1}\n"

outputArff += "\n\n@DATA\n"


for lineArff in content:
	if(lineArff.startswith("{")):
		attrsResu="{"
		temporSet = []
		temporDico = {}
		lineArff = lineArff.replace("{", "");
		lineArff = lineArff.replace("}", "");
		lineArff = lineArff.replace("\n", "");
		listAttrs4Tweet = lineArff.split(',')
		for attrs in listAttrs4Tweet:
			tabAttr = attrs.split()
			temporSet.append(dicoStemmsIndice[dicoMotsStemms[dicoMotsIndice[int(tabAttr[0])]]])
			temporDico[dicoStemmsIndice[dicoMotsStemms[dicoMotsIndice[int(tabAttr[0])]]]] = tabAttr[1]
			
			#attrsResu += str(dicoStemmsIndice[dicoMotsStemms[dicoMotsIndice[int(tabAttr[0])]]]) + " " + tabAttr[1] +","

		temporSet = list(set(temporSet))
		temporSet.sort()

		for attribut in temporSet:
			attrsResu += str(attribut) + " " + str(temporDico[attribut]) +","

		attrsResu += "}"
		outputArff += attrsResu+"\n"







outputArff = outputArff.replace(",}", "}");








print(outputArff.encode('utf-8'))

