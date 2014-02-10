package tweetParser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

public class ArffWriter {

	public static List<String> MOTS_DROLES;
	public static List<String> SMILEYS;
	public static List<String> ARGOT_TWITTER;
	public static List<String> PONCTUATION;
	public static TreeSet<String> MOTS;
	public static Map<String, Integer> MAPMOTS;
	private List<Tweet> listeTweets;

	public ArffWriter(List<Tweet> tweetoss) {
		this.listeTweets = tweetoss;
	}

	public void process() throws IOException {

		System.out.println("Début du process tweet to arff");
		System.out.println(" --> initialisation");
		init();

		File file = new File("classifieur/corpusTweet.arff");

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
			ARGOT_TWITTER = Files.readAllLines(
					Paths.get("corpus/input/listeArgotTwitter.txt"),
					Charset.forName("UTF-8"));
			PONCTUATION = Files.readAllLines(
					Paths.get("corpus/input/listePonctuation.txt"),
					Charset.forName("UTF-8"));
			MOTS = Tweet.getMots();
			MAPMOTS = new TreeMap<String, Integer>();
			int i=0;
			for(String mot : MOTS)
			{
				MAPMOTS.put(mot, i);
				i++;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String processListeTweets() {
		String arffContent = "";

		System.out.println(" --> process attributs");
		arffContent += "@RELATION tweet\n\n";
		for (String m : MOTS){
			arffContent += "@ATTRIBUTE "+m+" {0,1}\n";
		}
		
		//arffContent += "@ATTRIBUTE user {true,false}\n");
		//arffContent += "@ATTRIBUTE word {true,false}\n");
		arffContent += "@ATTRIBUTE hashtag {true,false}\n";
		arffContent += "@ATTRIBUTE smiley {true,false}\n";
		arffContent += "@ATTRIBUTE argoInternet {true,false}\n";
		arffContent += "@ATTRIBUTE ponctuation {absent, regulier, surnombre}\n";
		arffContent += "@ATTRIBUTE retweet {true,false}\n";
		arffContent += "@ATTRIBUTE nbRetweet NUMERIC\n";
		arffContent += "@ATTRIBUTE longeur NUMERIC\n";
		arffContent += "@ATTRIBUTE categorie {Contrepetrie, Autoderision, Misogyne, Aucune}\n";
		arffContent += "@ATTRIBUTE Drole {true,false}\n\n";
		arffContent += "@DATA\n";

		System.out.println("il y a "+listeTweets.size()+" tweets a traiter");
		int i = 1;
		for (Tweet t : listeTweets) {
			System.out.println("--> tweet "+i);
			i++;
			//processUserAttribute(arffContent, t);
			//processWordAttribute(arffContent, t);
			processMotsAttribute(arffContent, t);
			processHashtagAttribute(arffContent, t);
			processSmileyAttribute(arffContent, t);
			processArgotAttribute(arffContent, t);
			processPonctuationAttribute(arffContent, t);
			processRetweetAttribute(arffContent, t);
			processNbRetweetAttribute(arffContent, t);
			processLongeurAttribute(arffContent, t);
			processCategorieAttribute(arffContent, t);
			processDroleAttribute(arffContent, t);

			arffContent += "\n";
		}

		return arffContent;
	}

	
	private void processMotsAttribute(String arffContent, Tweet t){
		arffContent += "{";
		String[] motsTexte = t.getText().split(" ");
		for(String texte : motsTexte)
		{
			arffContent += MAPMOTS.get(texte)+" 1,";
		}

	}
	
	private void processCategorieAttribute(String arffContent, Tweet t) {
		if (t.getCategories().contains("Autodérision")) {
			arffContent += "Autoderision,";
		} else if (t.getCategories().contains("Contrepètrie")) {
			arffContent += "Contrepetrie,";
		} else if (t.getCategories().contains("Misogyne")) {
			arffContent += "Misogyne,";
		} else {
			arffContent += "Aucune,";
		}

	}

	private void processLongeurAttribute(String arffContent, Tweet t) {
		arffContent += t.getText().length()+",";
	}

	private void processNbRetweetAttribute(String arffContent, Tweet t) {
		arffContent += t.getRetweetCount()+",";
	}

	private void processRetweetAttribute(String arffContent, Tweet t) {
		arffContent += t.isRetweet() ? "true," : "false,";

	}

	private void processPonctuationAttribute(String arffContent, Tweet t) {
		if (t.getText().contains("!")) {
			int count = 0;
			for (int i = 0; i < t.getText().length(); i++)
				if (t.getText().charAt(i) == ',')
					count++;
			if (count > 0 && count <4) {
				arffContent += "regulier,";
			} else {
				arffContent += "surnombre,";
			}
		} else {
			arffContent += "absent,";
		}

	}

	private void processArgotAttribute(String arffContent, Tweet t) {
		boolean ok = false;
		
		for(String argot : ARGOT_TWITTER)
		{
			if(t.getText().toLowerCase().contains(argot))
				ok=true;
		}
		
		if (ok) {
			arffContent += "true,";
		} else {
			arffContent += "false,";
		}
	}

	private void processSmileyAttribute(String arffContent, Tweet t) {
		boolean ok = false;

		for(String smile : SMILEYS)
		{
			if(t.getText().contains(smile))
				ok=true;
		}
		
		if (ok) {
			arffContent += "true,";
		} else {
			arffContent += "false,";
		}
	}

	private void processHashtagAttribute(String arffContent, Tweet t) {
		boolean ok = false;
		for (String hashtag : t.getHashtags()) {
			for (String arg : ARGOT_TWITTER)
			{
				if(hashtag.contains(arg))
					ok=true;
			}
			for (String arg : MOTS_DROLES)
			{
				if(hashtag.contains(arg))
					ok=true;
			}
			for (String arg : SMILEYS)
			{
				if(hashtag.contains(arg))
					ok=true;
			}
		}

		if (ok) {
			arffContent += "true,";
		} else {
			arffContent += "false,";
		}
	}

	private void processWordAttribute(String arffContent, Tweet t) {
		
		boolean ok = false;

		for(String smile : ARGOT_TWITTER)
		{
			if(t.getText().toLowerCase().contains(smile))
				ok=true;
		}
		for(String smile : MOTS_DROLES)
		{
			if(t.getText().toLowerCase().contains(smile))
				ok=true;
		}
		
		if (ok) {
			arffContent += "true,";
		} else {
			arffContent += "false,";
		}
	}

	private void processDroleAttribute(String arffContent, Tweet t) {
		if (t.getCategories().contains("Drole")) {
			arffContent += "true}";
		} else {
			arffContent += "false}";
		}

	}

	private void processUserAttribute(String arffContent, Tweet t) {
		
		boolean ok = false;

		for(String smile : MOTS_DROLES)
		{
			if(t.getUser().toLowerCase().contains(smile))
				ok=true;
		}
		for(String smile : ARGOT_TWITTER)
		{
			if(t.getUser().toLowerCase().contains(smile))
				ok=true;
		}
		for(String smile : SMILEYS)
		{
			if(t.getUser().contains(smile))
				ok=true;
		}
		
		if (ok) {
			arffContent += "true,";
		} else {
			arffContent += "false,";
		}
	}

}
