TweetHoover
===============

Hoover for getting all tweets from a given hashtag or user.


Quick Start
-----------

1) Requirement

	You will need :
	- Java 7


2) Input queries

	There are 2 input files :
	- input/drole.txt, where all queries (separated by new line) to get humoristic tweets are
	- input/pasDrole.txt, where all queries (separated by new line) to get non-humoristic tweets are



3) Run

	java -Dtwitter4j.debug=true -Dtwitter4j.oauth.consumerKey=7OLCYqpSKU4ULvYkwXFtxw -Dtwitter4j.oauth.consumerSecret=I0k2MZJ646IsN4fYpAr2y1SmUjQrrBSIMpaUvBGdLc -Dtwitter4j.oauth.accessToken=2308182841-BowFdlhc9taP1pwG1k9IuYms20IMHow0A7xDMaf -Dtwitter4j.oauth.accessTokenSecret=7eLheEqqkddrYYpN6sqzjvsl5vQQzGE3EII03Zmdk9hQC -cp lib/twitter4j-core-3.0.5.jar bin/tweetHoover/GetTweet


4) Results

	There are 2 output files :
 	- output/tweetsDroles.xml, where all humoristic tweets are with informations
	- output/tweetsPasDroles.xml, where all non-humoristic tweets are with informations



