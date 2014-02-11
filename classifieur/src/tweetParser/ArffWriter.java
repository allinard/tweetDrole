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

public class ArffWriter {

	public static List<String> MOTS_DROLES;
	public static List<String> SMILEYS;
	public static List<String> ARGOT_TWITTER;
	public static List<String> PONCTUATION;
	public static TreeSet<String> MOTS;
	public static Map<String, Integer> MAPMOTS;
	private List<Tweet> listeTweets;
	private static int NOMBREATTRIBUTS;

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
			NOMBREATTRIBUTS = 0;
			for (String mot : MOTS) {
				MAPMOTS.put(mot, NOMBREATTRIBUTS);
				NOMBREATTRIBUTS++;
				
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String processListeTweets() {
		String arffContent = "";

		System.out.println(" --> process attributs");
		arffContent += "@RELATION tweet\n\n";
		for (String m : MOTS) {
			arffContent += "@ATTRIBUTE " + m + " {0,1}\n";
		}

		// arffContent += "@ATTRIBUTE user {true,false}\n");
		// arffContent += "@ATTRIBUTE word {true,false}\n");
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

		System.out
				.println("il y a " + listeTweets.size() + " tweets a traiter");
		int i = 1;
		for (Tweet t : listeTweets) {
			System.out.println("--> tweet " + i);
			i++;
			// processUserAttribute(arffContent, t);
			// processWordAttribute(arffContent, t);
			arffContent += processMotsAttribute(arffContent, t);
			arffContent += processHashtagAttribute(arffContent, t);
			arffContent += processSmileyAttribute(arffContent, t);
			arffContent += processArgotAttribute(arffContent, t);
			arffContent += processPonctuationAttribute(arffContent, t);
			arffContent += processRetweetAttribute(arffContent, t);
			arffContent += processNbRetweetAttribute(arffContent, t);
			arffContent += processLongeurAttribute(arffContent, t);
			arffContent += processCategorieAttribute(arffContent, t);
			arffContent += processDroleAttribute(arffContent, t);

			arffContent += "\n";
		}

		return arffContent;
	}

	private String processMotsAttribute(String arffContent, Tweet t) {
		String truc = "{";
		String[] motsTexte = t.getText().split(" ");
		TreeSet<Integer> monSet = new TreeSet<Integer>();
		for (String texte : motsTexte) {
			texte = texte.toLowerCase();
			if (MAPMOTS.get(texte) != null) {
				monSet.add(MAPMOTS.get(texte));
				
			}
		}
		for(Integer el : monSet){
			truc += String.valueOf(el) + " 1,";
		}
		return truc;
	}

	private String processCategorieAttribute(String arffContent, Tweet t) {
		NOMBREATTRIBUTS++;
		if (t.getCategories().contains("Autodérision")) {
			return NOMBREATTRIBUTS+" Autoderision,";
		} else if (t.getCategories().contains("Contrepètrie")) {
			return NOMBREATTRIBUTS+" Contrepetrie,";
		} else if (t.getCategories().contains("Misogyne")) {
			return NOMBREATTRIBUTS+" Misogyne,";
		} else {
			return NOMBREATTRIBUTS+" Aucune,";
		}

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
		return t.isRetweet() ? NOMBREATTRIBUTS+" true," : NOMBREATTRIBUTS+" false,";

	}

	private String processPonctuationAttribute(String arffContent, Tweet t) {
		NOMBREATTRIBUTS++;
		if (t.getText().contains("!")) {
			int count = 0;
			for (int i = 0; i < t.getText().length(); i++)
				if (t.getText().charAt(i) == ',')
					count++;
			if (count > 0 && count < 4) {
				return NOMBREATTRIBUTS+" regulier,";
			} else {
				return NOMBREATTRIBUTS+" surnombre,";
			}
		} else {
			return NOMBREATTRIBUTS+" absent,";
		}

	}

	private String processArgotAttribute(String arffContent, Tweet t) {
		boolean ok = false;

		for (String argot : ARGOT_TWITTER) {
			if (t.getText().toLowerCase().contains(argot))
				ok = true;
		}
		NOMBREATTRIBUTS++;
		if (ok) {
			return NOMBREATTRIBUTS+" true,";
		} else {
			return NOMBREATTRIBUTS+" false,";
		}
	}

	private String processSmileyAttribute(String arffContent, Tweet t) {
		boolean ok = false;

		for (String smile : SMILEYS) {
			if (t.getText().contains(smile))
				ok = true;
		}
		NOMBREATTRIBUTS++;
		if (ok) {
			return NOMBREATTRIBUTS+" true,";
		} else {
			return NOMBREATTRIBUTS+" false,";
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
			return NOMBREATTRIBUTS+" true,";
		} else {
			return NOMBREATTRIBUTS+" false,";
		}
	}

	private String processWordAttribute(String arffContent, Tweet t) {

		boolean ok = false;

		for (String smile : ARGOT_TWITTER) {
			if (t.getText().toLowerCase().contains(smile))
				ok = true;
		}
		for (String smile : MOTS_DROLES) {
			if (t.getText().toLowerCase().contains(smile))
				ok = true;
		}

		if (ok) {
			return "true,";
		} else {
			return "false,";
		}
	}

	private String processDroleAttribute(String arffContent, Tweet t) {
		int i = NOMBREATTRIBUTS++;
		NOMBREATTRIBUTS -= 9;
		if (t.getCategories().contains("Drole")) {
			return i+" true}";
		} else {
			return i+" false}";
		}

	}

	private void processUserAttribute(String arffContent, Tweet t) {

		boolean ok = false;

		for (String smile : MOTS_DROLES) {
			if (t.getUser().toLowerCase().contains(smile))
				ok = true;
		}
		for (String smile : ARGOT_TWITTER) {
			if (t.getUser().toLowerCase().contains(smile))
				ok = true;
		}
		for (String smile : SMILEYS) {
			if (t.getUser().contains(smile))
				ok = true;
		}

		if (ok) {
			arffContent += "true,";
		} else {
			arffContent += "false,";
		}
	}

}
