#!/usr/bin/env python
# -*- coding: utf-8 -*-
from __future__ import print_function
from nltk.stem import *
from nltk.stem.snowball import SnowballStemmer
from stemmer import StemmerType
from stemmer import stemmer

class stemmer(StemmerType, object):
    ''' Cost Calculator Utility '''

    def __init__(self):
        print(Initializing)
        pass

    # The implementation for the definition contained in the Java interface
    def getStems(self, listeInput):

	listeOutput = []
	stemmerObject = SnowballStemmer("french", ignore_stopwords=True)


	for el in listeInput:
		listeOutput.append(stemmerObject.stem(el))

	setStem = list(set(listeOutput))
	

	for stem in setStem:
		print(stem)


        return setStem




