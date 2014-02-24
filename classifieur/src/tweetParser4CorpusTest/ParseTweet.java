package tweetParser4CorpusTest;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class ParseTweet{

	public static void main(String args[]) throws ParserConfigurationException, SAXException, IOException{
		SAXParserFactory fabrique = SAXParserFactory.newInstance();
		SAXParser parseur = fabrique.newSAXParser();

		File fichier = new File("corpus/output/done/corpus.xml");
		DefaultHandler gestionnaire = new TweetHandler();
		parseur.parse(fichier, gestionnaire);
	}
	
	
}
