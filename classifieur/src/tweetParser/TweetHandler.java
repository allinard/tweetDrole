package tweetParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class TweetHandler extends DefaultHandler {
	// résultats de notre parsing
	private List<Tweet> listeTweets;
	private Tweet tweet;
	// buffer nous permettant de récupérer les données
	private StringBuffer buffer;
	// simple constructeur
	public TweetHandler() {
		super();
	}

	// détection d'ouverture de balise
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (qName.equals("tweets")) {
			listeTweets = new ArrayList<Tweet>();
		} else if (qName.equals("tweet")) {
			tweet = new Tweet();
		} else {
			buffer = new StringBuffer();
		} 
		
	}

	// détection fin de balise
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		
		if (qName.equals("tweets")) {
		} else if (qName.equals("tweet")) {
			listeTweets.add(tweet);
		} else if (qName.equals("user")) {
			tweet.setUser(buffer.toString());
		} else if (qName.equals("text")) {
			tweet.setText(buffer.toString());
			tweet.remplirMots(buffer.toString());
		} else if (qName.equals("hashtags")) {

		} else if (qName.equals("hashtag")) {
			tweet.addHashtag(buffer.toString());

		} else if (qName.equals("retweet")) {
			tweet.setRetweet(buffer.toString().equals("true")?true:false);
		} else if (qName.equals("retweeted")) {
			tweet.setRetweeted(buffer.toString().equals("true")?true:false);
		} else if (qName.equals("retweetCount")) {
			tweet.setRetweetCount(Integer.valueOf(buffer.toString()));
		} else if (qName.equals("categories")) {

		} else if (qName.equals("categorie")) {
			tweet.addCategorie(buffer.toString());
		}
		else {
			// erreur, on peut lever une exception
			throw new SAXException("Balise " + qName + " inconnue.");
		}
	}

	// détection de caractères
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String lecture = new String(ch, start, length);
		if (buffer != null)
			buffer.append(lecture);
	}

	// début du parsing
	public void startDocument() throws SAXException {
		System.out.println("Début du parsing");
	}

	// fin du parsing
	public void endDocument() throws SAXException {
		System.out.println("Fin du parsing");
		ArffWriter arffWriter = new ArffWriter(listeTweets);
		try {
			arffWriter.process();
		} catch (IOException e) {
			e.printStackTrace();
		}		

	}
}
