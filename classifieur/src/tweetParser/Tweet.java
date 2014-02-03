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
	
	@Override
	public String toString() {
		return "Tweet [text=" + text + ", user=" + user + ", hashtags="
				+ hashtags + ", isRetweet=" + isRetweet + ", isRetweeted="
				+ isRetweeted + ", retweetCount=" + retweetCount
				+ ", categories=" + categories + "]";
	}

}
