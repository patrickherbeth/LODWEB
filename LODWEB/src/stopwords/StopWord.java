package stopwords;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StopWord {

	private List<String> stopWordsEN;
	private StringBuilder newTag;

	public StopWord() {
		this.stopWordsEN = new ArrayList<>((readFile("/stopwords/stopwords-en.txt")));
	}

	public StopWord removeFromText(List<String> words) {
		this.newTag = new StringBuilder();

		int count = 0;
		
		for (String word : words) {
			if (!word.isEmpty()) {
				count ++;
				
				String wordWithoutWhiteSpace = word.trim().replaceAll(RegularExpression.SPECIAL_CHARACTERS, "").toLowerCase();
				
				boolean okEN = !stopWordsEN.stream().anyMatch(a -> a.equalsIgnoreCase(wordWithoutWhiteSpace));

				if ((okEN) && !wordWithoutWhiteSpace.equals("\\s+") && wordWithoutWhiteSpace.length() > 1) {
					this.newTag.append(wordWithoutWhiteSpace);
					
					if(count < words.size()) {
						this.newTag.append(" ");
					}
				}
			}
		}

		return this;
	}

	public String toList() {
		return this.newTag.toString();
	}

	private Set<String> readFile(String fileName) {
		BufferedReader br = null;
		Set<String> stopWords = new HashSet<>();
		try {
			InputStream file = StopWord.class.getResourceAsStream(fileName);
			br = new BufferedReader(new InputStreamReader(file));
			String line;
			while ((line = br.readLine()) != null) {
				stopWords.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return stopWords;
	}
}