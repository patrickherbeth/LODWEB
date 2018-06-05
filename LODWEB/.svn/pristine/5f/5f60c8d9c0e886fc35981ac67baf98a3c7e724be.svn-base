package parser;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import fm.last.musicbrainz.coverart.CoverArt;
import fm.last.musicbrainz.coverart.CoverArtArchiveClient;
import fm.last.musicbrainz.coverart.impl.DefaultCoverArtArchiveClient;

public class Parser {

	
	
	
	public static void main(String[] args) throws IOException {
		try {
			
			//System.out.println(Parser.getImageCoverFromGoogle("book_Sound_of_the_Beast".replace("_","+")));
			System.out.println(Parser.getSinopse("Monkeys"));	
			//System.out.println(Parser.getImageCoverFromWikiFile("https://en.wikipedia.org/wiki/File:SIU_temp_book_1945.jpg"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static final String GOOGLE_SEARCH_URL = "https://www.google.com/search?site=";
	
	
	public static String getImageCoverFromGoogle(String query)  {

		String returnPath = null;
		String firstReturn = null;

		String searchURL = GOOGLE_SEARCH_URL + "&q=" + query + "+jpg";
		// without proper User-Agent, we will get 403 error
		Document doc = null;
		try {
			doc = Jsoup.connect(searchURL).userAgent("Mozilla/5.0").get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// below will print HTML data, save it to a file and open in browser to
		// compare
		// System.out.println(doc.html());

		// If google search results HTML change the <h3 class="r" to <h3
		// class="r1"
		// we need to change below accordingly
		Elements results = doc.select("h3.r > a");
		int count = 0;
		for (Element result : results) {
			String linkHref = result.attr("href");
			// String linkText = result.text();
			String finalS = linkHref.substring(7, linkHref.indexOf("&"));
			System.out.println("finalS::" + finalS);
			if (  (finalS.startsWith("https:") || finalS.startsWith("http:")) && (finalS.endsWith(".jpg"))) {
				if (count==0) {
					firstReturn = finalS;
				}
				// System.out.println("Text::" + linkText + ", URL::" + finalS);
				if (finalS.startsWith("https://en.wikipedia.org")) {
					returnPath = getImageCoverFromWikiFile(finalS);
					// System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaText::" +
					// linkText + ", URL::" + finalS);
				}else if (finalS.startsWith("http://spongebob.wikia.com/wiki/File:")) {
					returnPath = finalS;
					// System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaText::" +
					// linkText + ", URL::" + finalS);
				}else if (finalS.startsWith("http://nickelodeon.wikia.com/wiki/File:")) {
					returnPath = finalS;
					// System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaText::" +
					// linkText + ", URL::" + finalS);
				}					
			}
			count++;
		}
		
		if (returnPath==null) {
			returnPath = firstReturn;
		}

		return returnPath;
	}
	
	
	public static String getMusicByMID(String query) {
		String music = null;
		CoverArtArchiveClient client = new DefaultCoverArtArchiveClient();
		
		UUID mbid = UUID.fromString("76df3287-6cda-33eb-8e9a-044b5e15ffdd");
	
		CoverArt coverArt = null;
		try {
		  coverArt = client.getByMbid(mbid);
		  if (coverArt != null) {
			  music = coverArt.getImages().get(0).toString();
/*		    for (CoverArtImage coverArtImage : coverArt.getImages()) {
		      File output = new File(mbid.toString() + "_" + coverArtImage.getId() + ".jpg");
		      FileUtils.copyInputStreamToFile(coverArtImage.getImage(), output);
		    }*/
		  }
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		return music;
	
	}
	
	public static String getMusic(String query) {
		String musicCover = null;
		try {
			URL url = new URL(
					"http://ws.audioscrobbler.com/2.0/?method=album.search&album="+query+"&api_key=162048263882e228d9b3b7d74dc24e52&format=json");
							
			URLConnection connection = url.openConnection();

			String line;
			StringBuilder builder = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}

			JSONObject json = new JSONObject(builder.toString());

			Object object = json.getJSONObject("results").getJSONObject("albummatches").getJSONArray("album").getJSONObject(0).getJSONArray("image").getJSONObject(3).get("#text");
			if (object.equals(JSONObject.NULL) && json.getJSONObject("results").getJSONObject("albummatches").getJSONArray("album").getJSONObject(1)!=null) {
				object = json.getJSONObject("results").getJSONObject("albummatches").getJSONArray("album").getJSONObject(1).getJSONArray("image").getJSONObject(3).get("#text");
			} else if (object.equals(JSONObject.NULL) && json.getJSONObject("results").getJSONObject("albummatches").getJSONArray("album").getJSONObject(2)!=null) {
				object = json.getJSONObject("results").getJSONObject("albummatches").getJSONArray("album").getJSONObject(2).getJSONArray("image").getJSONObject(3).get("#text");
			} else if (object.equals(JSONObject.NULL) && json.getJSONObject("results").getJSONObject("albummatches").getJSONArray("album").getJSONObject(3)!=null) {
				object = json.getJSONObject("results").getJSONObject("albummatches").getJSONArray("album").getJSONObject(3).getJSONArray("image").getJSONObject(3).get("#text");
			}
			
			if (!object.equals(JSONObject.NULL)) {
			
				musicCover = ((String)object);
			}
			

		} catch (Exception e) {
			e.fillInStackTrace();
			musicCover =  null;
			
		}

		return musicCover;
	}	
		
		public static String getBook(String query) {
		String bookCover = null;
		try {
			URL url = new URL(
					"http://openlibrary.org/search.json?q="
							+ query);
			URLConnection connection = url.openConnection();

			String line;
			StringBuilder builder = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}

			JSONObject json = new JSONObject(builder.toString());

			Object object = json.getJSONArray("docs").getJSONObject(0).get("cover_i");
			if (object.equals(JSONObject.NULL) && json.getJSONArray("docs").getJSONObject(1)!=null) {
				object = json.getJSONArray("docs").getJSONObject(1).get("cover_i");
			} else if (object.equals(JSONObject.NULL) && json.getJSONArray("docs").getJSONObject(2)!=null) {
				object = json.getJSONArray("docs").getJSONObject(2).get("cover_i");
			} else if (object.equals(JSONObject.NULL) && json.getJSONArray("docs").getJSONObject(3)!=null) {
				object = json.getJSONArray("docs").getJSONObject(3).get("cover_i");
			}
			
			if (!object.equals(JSONObject.NULL)) {
				Integer coverId = ((Integer)object);
				bookCover = "http://covers.openlibrary.org/b/id/"+coverId+"-L.jpg";
			}
			

		} catch (Exception e) {
		
			bookCover =  null;
			
		}

		return bookCover;
	}
		
		
		public static String getSinopse(String query) {
			String imageUrl = null;
			try {
				URL url = new URL(
						"https://api.themoviedb.org/3/search/movie?api_key=656f17aacace948719243a88ab9797b1&query="
								+ query);
				URLConnection connection = url.openConnection();

			 //System.out.println("https://api.themoviedb.org/3/search/movie?api_key=656f17aacace948719243a88ab9797b1&query="+ query);
				
				String line;
				StringBuilder builder = new StringBuilder();
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}

				JSONObject json = new JSONObject(builder.toString());

				Object object = json.getJSONArray("results").getJSONObject(0).get("overview");
				if (object.equals(JSONObject.NULL) && json.getJSONArray("results").getJSONObject(1)!=null) {
					object = json.getJSONArray("results").getJSONObject(1).get("overview");
				} else if (object.equals(JSONObject.NULL) && json.getJSONArray("results").getJSONObject(2)!=null) {
					object = json.getJSONArray("results").getJSONObject(2).get("overview");
				} else if (object.equals(JSONObject.NULL) && json.getJSONArray("results").getJSONObject(3)!=null) {
					object = json.getJSONArray("results").getJSONObject(3).get("overview");
				}
				
				if (!object.equals(JSONObject.NULL)) {
					imageUrl = ((String)object).toString();
				}
				

			} catch (Exception e) {
			
				imageUrl =  null;
				
			}

			return imageUrl;
		}
		
	
	public static String getImage(String query) {
		String imageUrl = null;
		try {
			URL url = new URL(
					"https://api.themoviedb.org/3/search/movie?api_key=656f17aacace948719243a88ab9797b1&query="
							+ query);
			URLConnection connection = url.openConnection();

			String line;
			StringBuilder builder = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}

			JSONObject json = new JSONObject(builder.toString());

			Object object = json.getJSONArray("results").getJSONObject(0).get("poster_path");
			if (object.equals(JSONObject.NULL) && json.getJSONArray("results").getJSONObject(1)!=null) {
				object = json.getJSONArray("results").getJSONObject(1).get("poster_path");
			} else if (object.equals(JSONObject.NULL) && json.getJSONArray("results").getJSONObject(2)!=null) {
				object = json.getJSONArray("results").getJSONObject(2).get("poster_path");
			} else if (object.equals(JSONObject.NULL) && json.getJSONArray("results").getJSONObject(3)!=null) {
				object = json.getJSONArray("results").getJSONObject(3).get("poster_path");
			}
			
			if (!object.equals(JSONObject.NULL)&&((String)object).toLowerCase().endsWith(".jpg")) {
				imageUrl = ((String)object).toString();
			}
			

		} catch (Exception e) {
		
			imageUrl =  null;
			
		}

		return imageUrl;
	}
	
	public static void getImageTest() {
        try{
           URL url = new URL("https://api.themoviedb.org/3/search/movie?api_key=656f17aacace948719243a88ab9797b1&query=*the*godfather");
            URLConnection connection = url.openConnection();

            String line;
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while((line = reader.readLine()) != null) {
                builder.append(line);
            }

            JSONObject json = new JSONObject(builder.toString());
            
            System.out.println(json.getJSONArray("results").getJSONObject(0).getString("poster_path"));
            
            String imageUrl = json.getJSONObject("page").getJSONArray("results").getJSONObject(0).getString("url");

            BufferedImage image = ImageIO.read(new URL(imageUrl));
            JOptionPane.showMessageDialog(null, "", "", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(image));
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Failure", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
	
	public void test2() throws IOException{
	WebClient webClient = new WebClient();
	webClient.getOptions().setPopupBlockerEnabled(false);
	webClient.getOptions().setCssEnabled(false);
	webClient.getOptions().setAppletEnabled(false);
	webClient.getOptions().setJavaScriptEnabled(false);
	HtmlPage page = webClient.getPage("https://www.facebook.com/search/pages/?q=pages%20fred.durao%20like");
	//String originalHtml = page.getWebResponse().getContentAsString();
	String originalHtml = page.asText();
	System.out.println(originalHtml);
	}
	
	
	public void test() throws IOException{
		String url = "https://www.facebook.com/search/pages/?q=pages%20paulo.durao%20like";
		String ua = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_2) AppleWebKit/537.33 (KHTML, like Gecko) Chrome/27.0.1438.7 Safari/537.33";
		Document doc = Jsoup.connect(url).userAgent(ua).timeout(1000*1000).get();
		
		System.out.println(doc.data());
		
		//Document doc = Jsoup.parse(url);
	
		// move the hidden commented out html into the DOM proper:
		//Elements hiddenElements = doc.select("comment");
		Elements hiddenElements = doc.getAllElements();
		for (Element hidden: hiddenElements) {
		    for (org.jsoup.nodes.Node child: hidden.childNodesCopy()) {
		        if (child instanceof Comment) {
		        	System.out.println(((Comment) child).getData());
		            hidden.append(((Comment) child).getData()); // comment data parsed as html
		        }
		    }
		}
	
		Elements articles = doc.select("div[role=article]");
		for (Element article: articles) {
		    if (article.select("span.userContent").size() > 0) {
		        String text = article.select("span.userContent").text();
		        String imgUrl = article.select("div.photo img").attr("abs:src");
		        System.out.println(String.format("%s\n%s\n\n", text,imgUrl));
		    }
	}
	}
	
	public static String getImageCoverFromWikiFile(String url)  {
		String returnPath = null;
		// without proper User-Agent, we will get 403 error
		Document doc = null;
		try {
			doc = Jsoup.connect(url).userAgent("Mozilla/5.0").get();
		} catch (IOException e) {
			return returnPath;
		}
		Elements results = doc.select("a[href*=//upload.wikimedia.org/wikipedia/]");
		for (Element result : results) {
			String linkHref = result.attr("href");
			if ( linkHref.endsWith(".jpg")) {
				returnPath = "https:"+linkHref;
			}
		}
		return returnPath;
	}		

}