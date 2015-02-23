import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import java.io.IOException;
import java.io.*;

public class RedditImageDownloader {
	//absolute path to folder you want to store your images in
	static String FOLDER;
	//absolute path to folder you want to store your images in
	static String FOLDER1;
	//absolute path to textfile (.txt) you want to store metadata in
	static String TEXTFILE;
	//absolute path to textfile (.txt) you want to store metadata in
	static String TEXTFILE1;
	//URL to reddit user's page
	static String REDDIT_USER_URL;

	
	public static void main(String[] args) throws IOException {
	//	LinksToUsersAndNextPage("http://www.reddit.com/r/pics");
		LinksToImagesOnUserPages(REDDIT_USER_URL);

	}
	
	//Subreddit URL (e.g. "http://www.reddit.com/r/pics" from which to collect links to all users who submitted
	public static void LinksToUsersAndNextPage (String subreddit) throws IOException {
		Document sr= Jsoup.connect(subreddit).get();
		String title= sr.title();
		System.out.println(title);
		//~= means use regex
		//Selects for attribute class and value "author id" or "help-section help-organic", which is the line that separates
		//the links to the moderators userpages and the regular users who posted links. We are looking for the latter.
		Elements users= sr.select("[class~=(author id|help-section help-organic)]");
		Elements nextpage= sr.select("a[rel=nofollow next");
		//creates a directory called hello
		File folder= new File(FOLDER);
		folder.mkdir();
		File file= new File(TEXTFILE);
		BufferedWriter bw= new BufferedWriter(new FileWriter(file, true));
		boolean separator= false;
		for (Element user: users) {
			if (separator) {
			System.out.println(user.text());
			System.out.println(user.attr("href"));
			bw.write("Username:" + user.text());
			bw.newLine();
			bw.write("URL to Userpage:" + user.attr("href"));
			bw.newLine();
			}
			if (user.text().equals("This area shows new and upcoming links. Vote on links here to help them become popular, and click the forwards and backwards buttons to view more.")) 
			{separator=true;}
			
			
		}
		for (Element next: nextpage) {
		System.out.println("Link to next page is:");
		System.out.println(next.attr("href"));
		bw.newLine();
		
		bw.write("Next Page:" + next.attr("href"));
		}
		bw.close();
	}
	
	//reads URLs to image files and downloads them and puts them into the folder, making sure not to create duplicates
	public static void ImageDownloader (File file) {
		
	}
	
	public static void LinksToImagesOnUserPages(String userpage) throws IOException {
		Document up= Jsoup.connect(userpage).get();
		String title= up.title();
		System.out.println(title);
		Elements imagelink= up.select("a[href^=http://imgur].thumbnail");
		Elements nextpage= up.select("a[rel=nofollow next");
		File folder= new File(FOLDER1);
		folder.mkdir();
		File file= new File(TEXTFILE1);
		
		BufferedWriter bw= new BufferedWriter(new FileWriter(file, true));
		for (Element image: imagelink) {
			System.out.println(image.attr("href"));
			bw.write(image.attr("href"));
			bw.newLine();
		}
		for (Element np: nextpage) {
			System.out.println(np.attr("href"));
			bw.write(np.attr("href"));
		}
		bw.close();
		
	}
}
