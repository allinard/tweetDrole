package tweetParser;

import java.util.ArrayList;
import java.util.List;

public class Tweet {

	private String text;
	private String user;
	private List<String> hashtags;
	private boolean isRetweet;
	private boolean isRetweeted;
	private int retweetCount;
	private List<String> categories;
	private static List<String> mots = new ArrayList<String>();

	public Tweet() {
		this.hashtags = new ArrayList<String>();
		this.categories = new ArrayList<String>();
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
	
	public List<String> getMots(){
		return mots;
	}
	
	public void remplirMots (String s){
		String[] tab_mot = s.split(" ");
		for (String m : tab_mot) {
			if (!this.mots.contains(m) && !m.equals("\\s") && !m.equals("\n") && !m.contains("@")&& !m.contains("#") && !m.contains("http://")){
				m=m.replaceAll("[^a-zA-ZÈËÍ‡ '0-9]", "").toLowerCase();
		       if (!m.equals("")) this.addMots(m);
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
