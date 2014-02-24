package tweetParser4CorpusTest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.python.antlr.PythonParser.return_stmt_return;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ArffWriter {

	public static List<String> MOTS_DROLES;
	public static List<String> SMILEYS;
	public static List<String> SMILEYSCONTENT;
	public static List<String> SMILEYSPASCONTENT;
	public static List<String> ARGOT_TWITTER;
	public static List<String> EXCLAMATION;
	public static TreeSet<String> MOTS;
	public static Map<String, Integer> MAPMOTS;
	private static List<Tweet> listeTweets;
	private static int NOMBREATTRIBUTS;
	public static List<Tweet> listeTweetsTest;
	
	/** Constructeur privé */
	private ArffWriter()
	{}
	 
	/** Instance unique pré-initialisée */
	private static ArffWriter INSTANCE = new ArffWriter();
	 
	/** Point d'accès pour l'instance unique du singleton */
	public static ArffWriter getInstance()
	{	return INSTANCE;
	}
	
	public static void setListeTweets(List<Tweet> tweetoss) {
		listeTweets = tweetoss;
	}

	public void process() throws IOException, ParserConfigurationException, SAXException {

		System.out.println("Début du process tweet to arff");
		System.out.println(" --> initialisation");
		init();

		File file = new File("classifieur/corpusTweetTest.arff");

		// if file doesnt exists, then create it
		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);

		bw.write(processListeTweets());
		bw.close();
		System.out.println(" --> fin");

	}

	private void init() {
		try {
			MOTS_DROLES = Files.readAllLines(
					Paths.get("corpus/input/listeMotsHumour.txt"),
					Charset.forName("UTF-8"));
			SMILEYS = Files.readAllLines(
					Paths.get("corpus/input/listeSmileys.txt"),
					Charset.forName("UTF-8"));
			SMILEYSCONTENT = Files.readAllLines(
					Paths.get("corpus/input/listeSmileysContents.txt"),
					Charset.forName("UTF-8"));
			SMILEYSPASCONTENT = Files.readAllLines(
					Paths.get("corpus/input/listeSmileysPasContents.txt"),
					Charset.forName("UTF-8"));
			ARGOT_TWITTER = Files.readAllLines(
					Paths.get("corpus/input/listeArgotTwitter.txt"),
					Charset.forName("UTF-8"));
			EXCLAMATION = Files.readAllLines(
					Paths.get("corpus/input/listePonctuation.txt"),
					Charset.forName("UTF-8"));
			MOTS = Tweet.getMots();
			MAPMOTS = new TreeMap<String, Integer>();
			NOMBREATTRIBUTS = 0;
			for (String mot : MOTS) {
				MAPMOTS.put(mot, NOMBREATTRIBUTS);
				NOMBREATTRIBUTS = MAPMOTS.size();
			}
			for (String smile : SMILEYS){
				MAPMOTS.put(smile, NOMBREATTRIBUTS);
				NOMBREATTRIBUTS = MAPMOTS.size();
			}
			for (String argot : ARGOT_TWITTER){
				MAPMOTS.put(argot, NOMBREATTRIBUTS);
				NOMBREATTRIBUTS = MAPMOTS.size();
			}
			NOMBREATTRIBUTS = MAPMOTS.size()-1;

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String processListeTweets() throws ParserConfigurationException, SAXException, IOException {
		String arffContent = "";

		System.out.println(" --> process attributs");
		arffContent += "@RELATION tweet\n\n";
		for(Map.Entry<String,Integer> entry : MAPMOTS.entrySet()) {
			  arffContent += "@ATTRIBUTE " + entry.getKey() + " {0,1}\n";
		}

		arffContent += "@ATTRIBUTE TweetDroleHashtag {\"true\",\"false\"}\n";
		arffContent += "@ATTRIBUTE TweetDroleSmileyPasContent {\"true\",\"false\"}\n";
		arffContent += "@ATTRIBUTE TweetDroleSmileyContent {\"true\",\"false\"}\n";
		arffContent += "@ATTRIBUTE TweetDroleExclamation {\"absent\", \"regulier\", \"surnombre\"}\n";
		arffContent += "@ATTRIBUTE TweetDroleRetweet {\"true\",\"false\"}\n";
		arffContent += "@ATTRIBUTE TweetDroleNbRetweet NUMERIC\n";
		arffContent += "@ATTRIBUTE TweetDroleLongeur NUMERIC\n";
		arffContent += "@ATTRIBUTE TweetDroleNbMots NUMERIC\n";
		arffContent += "@ATTRIBUTE TweetDroleDrole {\"true\",\"false\"}\n\n";
		arffContent += "@DATA\n";

		System.out
				.println("il y a " + listeTweets.size() + " tweets a traiter");
		int i = 1;
		
		
		SAXParserFactory fabrique = SAXParserFactory.newInstance();
		SAXParser parseur = fabrique.newSAXParser();
		File fichier = new File("corpus/output/corpusTest/corpusTest.xml");
		DefaultHandler gestionnaire = new TweetHandler4Test();
		parseur.parse(fichier, gestionnaire);
		
		
		
		for (Tweet t : listeTweetsTest) {
			System.out.println("--> tweet " + i);
			i++;
			arffContent += processMotsAttribute(arffContent, t);
			arffContent += processHashtagAttribute(arffContent, t);
			arffContent += processSmileyPasContentAttribute(arffContent, t);
			arffContent += processSmileyContentAttribute(arffContent, t);
			arffContent += processPonctuationAttribute(arffContent, t);
			arffContent += processRetweetAttribute(arffContent, t);
			arffContent += processNbRetweetAttribute(arffContent, t);
			arffContent += processLongeurAttribute(arffContent, t);
			arffContent += processNbMotsAttribute(arffContent, t);
			arffContent += processDroleAttribute(arffContent, t);

			arffContent += "\n";
		}

		return arffContent;
	}

	private String processNbMotsAttribute(String arffContent, Tweet t) {
		NOMBREATTRIBUTS++;
		String[] motsTexte = t.getText().split(" ");
		return NOMBREATTRIBUTS+" "+motsTexte.length + ",";	}

	private String processMotsAttribute(String arffContent, Tweet t) {
		String truc = "{";
		String[] motsTexte = t.getText().split(" ");
		TreeSet<Integer> monSet = new TreeSet<Integer>();
		for (String texte : motsTexte) {
			texte = texte.toLowerCase();
			texte = texte.replaceAll("[^a-zA-Zéèêîàùûç0-9 ']", "");
			if (MAPMOTS.get(texte) != null) {
				monSet.add(MAPMOTS.get(texte));
			}
		}
		for(Integer el : monSet){
			truc += String.valueOf(el) + " 1,";
		}
		return truc;
	}

	private String processLongeurAttribute(String arffContent, Tweet t) {
		NOMBREATTRIBUTS++;
		return NOMBREATTRIBUTS+" "+t.getText().length() + ",";
	}

	private String processNbRetweetAttribute(String arffContent, Tweet t) {
		NOMBREATTRIBUTS++;
		return NOMBREATTRIBUTS+" "+t.getRetweetCount() + ",";
	}

	private String processRetweetAttribute(String arffContent, Tweet t) {
		NOMBREATTRIBUTS++;
		return t.isRetweet() ? NOMBREATTRIBUTS+" \"true\"," : NOMBREATTRIBUTS+" \"false\",";

	}

	private String processPonctuationAttribute(String arffContent, Tweet t) {
		NOMBREATTRIBUTS++;
		if (t.getText().contains("!")) {
			int count = 0;
			for (int i = 0; i < t.getText().length(); i++)
				if (t.getText().charAt(i) == ',')
					count++;
			if (count > 0 && count < 4) {
				return NOMBREATTRIBUTS+" \"regulier\",";
			} else {
				return NOMBREATTRIBUTS+" \"surnombre\",";
			}
		} else {
			return NOMBREATTRIBUTS+" \"absent\",";
		}

	}

	private String processSmileyContentAttribute(String arffContent, Tweet t) {
		boolean ok = false;

		for (String smile : SMILEYSCONTENT) {
			if (t.getText().contains(smile))
				ok = true;
		}
		NOMBREATTRIBUTS++;
		if (ok) {
			return NOMBREATTRIBUTS+" \"true\",";
		} else {
			return NOMBREATTRIBUTS+" \"false\",";
		}
	}
	
	private String processSmileyPasContentAttribute(String arffContent, Tweet t) {
		boolean ok = false;

		for (String smile : SMILEYSPASCONTENT) {
			if (t.getText().contains(smile))
				ok = true;
		}
		NOMBREATTRIBUTS++;
		if (ok) {
			return NOMBREATTRIBUTS+" \"true\",";
		} else {
			return NOMBREATTRIBUTS+" \"false\",";
		}
	}

	private String processHashtagAttribute(String arffContent, Tweet t) {
		boolean ok = false;
		for (String hashtag : t.getHashtags()) {
			for (String arg : ARGOT_TWITTER) {
				if (hashtag.contains(arg))
					ok = true;
			}
			for (String arg : MOTS_DROLES) {
				if (hashtag.contains(arg))
					ok = true;
			}
			for (String arg : SMILEYS) {
				if (hashtag.contains(arg))
					ok = true;
			}
		}
		NOMBREATTRIBUTS++;
		if (ok) {
			return NOMBREATTRIBUTS+" \"true\",";
		} else {
			return NOMBREATTRIBUTS+" \"false\",";
		}
	}

	private String processDroleAttribute(String arffContent, Tweet t) {
		int i = NOMBREATTRIBUTS++;
		i++;
		NOMBREATTRIBUTS -= 9;
		if (t.getCategories().contains("Drole")) {
			return i+" \"true\"}";
		} else {
			return i+" \"false\"}";
		}

	}

	public static List<Tweet> getListeTweetsTest() {
		return listeTweetsTest;
	}

	public static void setListeTweetsTest(List<Tweet> listeTweetsTest) {
		ArffWriter.listeTweetsTest = listeTweetsTest;
	}

}
