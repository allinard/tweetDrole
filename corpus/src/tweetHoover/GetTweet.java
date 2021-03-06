package tweetHoover;

import twitter4j.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Alexis Linard
 * 
 */
public class GetTweet {

	private static final int MAXCAPTEDTWEETS = 300;
	public static Document DOC;
	public static Element ROOT_ELEMENT;
	public static String FILE_INPUT_QUERIES_DROLE = "corpus/input/drole.txt";
	public static String FILE_INPUT_QUERIES_PAS_DROLE = "corpus/input/pasDrole.txt";
	public static String FILE_INPUT_CORPUS_TEST = "corpus/input/corpustest.txt";
	public static String FILE_OUTPUT_XML_DROLE = "corpus/output/tweetsDroles.xml";
	public static String FILE_OUTPUT_XML_PAS_DROLE = "corpus/output/tweetsPasDroles.xml";
	public static String FILE_OUTPUT_XML_CORPUS_TEST = "corpus/output/corpusTest.xml";

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// Instance of twitter researcher
		Twitter twitter = new TwitterFactory().getInstance();
		try {

			//processFunnyRequests(twitter);

			//processNonFunnyRequests(twitter);

			processCorpusTest(twitter);

			System.exit(0);

		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to search tweets: " + te.getMessage());
			System.exit(-1);
		} catch (TransformerException te) {
			te.printStackTrace();
			System.exit(-1);
		} catch (ParserConfigurationException te) {
			te.printStackTrace();
			System.exit(-1);
		}
	}

	private static void processCorpusTest(Twitter twitter)
			throws ParserConfigurationException, TwitterException,
			TransformerFactoryConfigurationError,
			TransformerConfigurationException, TransformerException {
		// Initalization for XML output file
		initializeXMLOutput();

		for (String q : getEachQuery4CorpusTest()) {

			List<Categories> listeCategories = new ArrayList<Categories>();
			listeCategories.add(Categories.TEST);

			// First param of Paging() is the page number, second is the number
			// per page (this is capped around 200 I think.
			Paging paging = new Paging(1, 100);
			List<Status> statuses = twitter.getUserTimeline(q, paging);

			// build the xml part for each tweet
			for (Status tweet : statuses) {
				buildXML(tweet, listeCategories);
			}
			writeXMLFile(FILE_OUTPUT_XML_CORPUS_TEST);

		}
	}

	private static void processNonFunnyRequests(Twitter twitter)
			throws ParserConfigurationException, TwitterException,
			TransformerFactoryConfigurationError,
			TransformerConfigurationException, TransformerException {
		// Initalization for XML output file
		initializeXMLOutput();

		for (String q : getEachNonFunnyQuery()) {

			List<Categories> listeCategories = new ArrayList<Categories>();
			listeCategories.add(Categories.PASDROLE);

			try {
				// First param of Paging() is the page number, second is the
				// number
				// per page (this is capped around 200 I think.
				Paging paging = new Paging(1, MAXCAPTEDTWEETS);
				List<Status> statuses = twitter.getUserTimeline(q, paging);

				// build the xml part for each tweet
				for (Status tweet : statuses) {
					buildXML(tweet, listeCategories);
				}

			}
			// si il s'agit de #
			catch (TwitterException e) {
				// The query for getting tweets
				Query query = new Query(q);

				// The result of the query
				QueryResult result;

				do {
					// the query is executed here
					result = twitter.search(query);

					// gets all the tweets from results
					List<Status> tweets = result.getTweets();

					// build the xml part for each tweet
					for (Status tweet : tweets) {
						buildXML(tweet, listeCategories);
					}
				} while ((query = result.nextQuery()) != null);
			}

		}

		// write le XML file and exit
		writeXMLFile(FILE_OUTPUT_XML_PAS_DROLE);
	}

	private static void processFunnyRequests(Twitter twitter)
			throws ParserConfigurationException, TwitterException,
			TransformerFactoryConfigurationError,
			TransformerConfigurationException, TransformerException {
		// Initalization for XML output file
		initializeXMLOutput();

		for (String q : getEachFunnyQuery()) {

			List<Categories> listeCategories = new ArrayList<Categories>();
			listeCategories.add(Categories.DROLE);

			if (q.contains("BCitation") || q.contains("Blonde")
					|| q.contains("blonde")) {
				listeCategories.add(Categories.MYSOGYNE);
			}
			if (q.contains("viedemerde")) {
				listeCategories.add(Categories.AUTODERISION);
			}
			if (q.contains("contrepetrie") || q.contains("contrepétrie")) {
				listeCategories.add(Categories.CONTREPETRIE);
			}
			if (q.contains("Raciste")) {
				listeCategories.add(Categories.RACISTE);
			}

			// pour les @
			try {
				// First param of Paging() is the page number, second is the
				// number per page.
				Paging paging = new Paging(1, MAXCAPTEDTWEETS);
				List<Status> statuses = twitter.getUserTimeline(q, paging);
				// build the xml part for each tweet
				for (Status tweet : statuses) {
					buildXML(tweet, listeCategories);
				}
			}
			// si il s'agit de #
			catch (TwitterException e) {
				// The query for getting tweets
				Query query = new Query(q);

				// The result of the query
				QueryResult result;

				do {
					// the query is executed here
					result = twitter.search(query);

					// gets all the tweets from results
					List<Status> tweets = result.getTweets();

					// build the xml part for each tweet
					for (Status tweet : tweets) {
						buildXML(tweet, listeCategories);
					}
				} while ((query = result.nextQuery()) != null);
			}

		}

		// write le XML file and exit
		writeXMLFile(FILE_OUTPUT_XML_DROLE);
	}

	/**
	 * 
	 * @param doc
	 * @throws TransformerFactoryConfigurationError
	 * @throws TransformerConfigurationException
	 * @throws TransformerException
	 */
	private static void writeXMLFile(String requiredOutput)
			throws TransformerFactoryConfigurationError,
			TransformerConfigurationException, TransformerException {
		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(DOC);
		StreamResult resultXml = new StreamResult(new File(requiredOutput));

		transformer.transform(source, resultXml);
	}

	/**
	 * 
	 * @return
	 * @throws ParserConfigurationException
	 */
	private static void initializeXMLOutput()
			throws ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		// root elements
		DOC = docBuilder.newDocument();
		ROOT_ELEMENT = DOC.createElement("tweets");
		DOC.appendChild(ROOT_ELEMENT);
	}

	/**
	 * 
	 * @param tweet
	 */
	private static void buildXML(Status tweet, List<Categories> listeCategories) {
		// tweet elements
		Element tweetEl = DOC.createElement("tweet");
		ROOT_ELEMENT.appendChild(tweetEl);

		// syntax if using attributes
		// tweet.setAttribute("id", "1");

		// source element
		Element user = DOC.createElement("user");
		user.appendChild(DOC.createTextNode(tweet.getUser().getScreenName()));
		tweetEl.appendChild(user);

		// text element
		Element text = DOC.createElement("text");
		text.appendChild(DOC.createTextNode(tweet.getText()));
		tweetEl.appendChild(text);

		// hashtags element
		Element hashtags = DOC.createElement("hashtags");
		for (HashtagEntity hE : tweet.getHashtagEntities()) {
			Element hashtag = DOC.createElement("hashtag");
			hashtag.appendChild(DOC.createTextNode(hE.getText()));
			hashtags.appendChild(hashtag);
		}
		tweetEl.appendChild(hashtags);

		// is retweet element
		Element retweet = DOC.createElement("retweet");
		retweet.appendChild(DOC.createTextNode(tweet.isRetweet() ? "true"
				: "false"));
		tweetEl.appendChild(retweet);

		// is retweeted element
		Element retweeted = DOC.createElement("retweeted");
		retweeted.appendChild(DOC.createTextNode(tweet.isRetweeted() ? "true"
				: "false"));
		tweetEl.appendChild(retweeted);

		// retweetCount element
		Element retweetCount = DOC.createElement("retweetCount");
		retweetCount.appendChild(DOC.createTextNode(String.valueOf(tweet
				.getRetweetCount())));
		tweetEl.appendChild(retweetCount);

		// categories element
		Element categories = DOC.createElement("categories");
		for (Categories c : listeCategories) {
			Element cat = DOC.createElement("categorie");
			cat.appendChild(DOC.createTextNode(c.toString()));
			categories.appendChild(cat);
		}
		tweetEl.appendChild(categories);

	}

	private static List<String> getEachFunnyQuery() {
		try {
			return Files.readAllLines(Paths.get(FILE_INPUT_QUERIES_DROLE),
					Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		return null;
	}

	private static List<String> getEachNonFunnyQuery() {
		try {
			return Files.readAllLines(Paths.get(FILE_INPUT_QUERIES_PAS_DROLE),
					Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		return null;
	}

	private static List<String> getEachQuery4CorpusTest() {
		try {
			return Files.readAllLines(Paths.get(FILE_INPUT_CORPUS_TEST),
					Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		return null;
	}
}
