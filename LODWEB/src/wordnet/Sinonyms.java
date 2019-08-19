package wordnet;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import net.didion.jwnl.*;
import net.didion.jwnl.data.*;
import net.didion.jwnl.dictionary.*;

public class Sinonyms {

	public static void main(String[] args) throws IOException, JWNLException {

		getSinonymous("tired");

	}
	
	public static ArrayList<String> getSinonymous(String tag) throws JWNLException {
				
		try {
			JWNL.initialize(new FileInputStream("C:/BACKUP/wordnet/properties.xml"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JWNLException e) {
			e.printStackTrace();
		}
		// Create dictionary object
		final Dictionary dictionary = Dictionary.getInstance();
		
		 // Sinônimos
		IndexWord word = dictionary.getIndexWord(POS.ADJECTIVE, tag);
		return findRelatedWordsDemo(word, PointerType.SIMILAR_TO);
		
	}

	// This function lists related words of type of relation for a given word
	// returns an ArrayList of sinonymos
	public static ArrayList<String> findRelatedWordsDemo(IndexWord w, PointerType type) throws JWNLException {
	

		// Call a function that returns an ArrayList of related senses
		ArrayList a = WordNetHelper.getRelated(w, type);
		
		ArrayList<String> sinonymes = new ArrayList<String>();
		
		
		if (a != null) {
			// System.out.println("Não encontrou Sinônimos.");
							
			for (int i = 0; i < a.size(); i++) {
				Synset s = (Synset) a.get(i);
				Word[] words = s.getWords();
				String[] arrayPalavras = null;
				
				for (int j = 0; j < words.length; j++) {
					//System.out.print(words[j].getLemma());

					arrayPalavras = words[j].getLemma().split(",");
					for (int p = 0; p < arrayPalavras.length; p++) {
						sinonymes.add(words[j].getLemma());
					}

				}
				
			}
		}
	
		return sinonymes;
	}
	
	
	
}
