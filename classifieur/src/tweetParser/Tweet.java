package tweetParser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

public class Tweet {

	private String text;
	private String user;
	private List<String> hashtags;
	private boolean isRetweet;
	private boolean isRetweeted;
	private int retweetCount;
	private List<String> categories;
	private static TreeSet<String> mots = new TreeSet<String>();
	private static List<String> MOTS_VIDES;

	public Tweet() {
		this.hashtags = new ArrayList<String>();
		this.categories = new ArrayList<String>();
		initMotVide();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public List<String> getHashtags() {
		return hashtags;
	}

	public void setHashtags(List<String> hashtags) {
		this.hashtags = hashtags;
	}

	public boolean isRetweet() {
		return isRetweet;
	}

	public void setRetweet(boolean isRetweet) {
		this.isRetweet = isRetweet;
	}

	public boolean isRetweeted() {
		return isRetweeted;
	}

	public void setRetweeted(boolean isRetweeted) {
		this.isRetweeted = isRetweeted;
	}

	public int getRetweetCount() {
		return retweetCount;
	}

	public void setRetweetCount(int retweetCount) {
		this.retweetCount = retweetCount;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public void addHashtag(String s){
		this.hashtags.add(s);
	}
	
	public void addCategorie(String s){
		this.categories.add(s);
	}
	
	public void addMots(String s){
		this.mots.add(s);
	}
	
	public static TreeSet<String> getMots(){
		return mots;
	}
	
	public static void initMotVide(){
		try {
			MOTS_VIDES = Files.readAllLines(Paths.get("corpus/input/listeMotsVides.txt"), Charset.forName("UTF-8"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void remplirMots (String s){
		s = s.replaceAll("[-]", " ");
		String[] tab_mot = s.split(" ");
		for (String m : tab_mot) {
			m = m.toLowerCase();
			if ( !m.equals("\\s")&& !m.contains("http://") && !m.equals("\n") && !m.contains("@") && !m.contains("#") && !m.equals("")){
				m = m.replaceAll("[^a-zA-Zéèêîàùû0-9 ']", "");
				if(!mots.contains(m) && !m.equals("\n") && !MOTS_VIDES.contains(m)) mots.add(m);
			}
		}	
	}
	
	
	@Override
	public String toString() {
		return "Tweet [text=" + text + ", user=" + user + ", hashtags="
				+ hashtags + ", isRetweet=" + isRetweet + ", isRetweeted="
				+ isRetweeted + ", retweetCount=" + retweetCount
				+ ", categories=" + categories + "]";
	}

}
