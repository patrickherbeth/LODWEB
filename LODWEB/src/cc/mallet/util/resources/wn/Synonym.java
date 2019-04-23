/**
 * Java WordNet Library (JWNL)
 * See the documentation for copyright information.
 *
 * @version 1.1
 */
package cc.mallet.util.resources.wn;

import net.didion.jwnl.JWNL;
import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.PointerType;
import net.didion.jwnl.data.PointerUtils;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.data.Word;
import net.didion.jwnl.data.list.PointerTargetNodeList;
import net.didion.jwnl.data.list.PointerTargetTree;
import net.didion.jwnl.data.relationship.AsymmetricRelationship;
import net.didion.jwnl.data.relationship.Relationship;
import net.didion.jwnl.data.relationship.RelationshipFinder;
import net.didion.jwnl.data.relationship.RelationshipList;
import net.didion.jwnl.dictionary.Dictionary;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;

/** A class to demonstrate the functionality of the JWNL package. */
public class Synonym {

	public static void main(String[] args) throws JWNLException {
		
		 // Demo listing "glosses" (i.e. definitions)
        IndexWord w = WordNetHelper.getWord(POS.VERB, "eat");
   
		
		// Demo finding a list of related words (synonyms)
        w = WordNetHelper.getWord(POS.ADJECTIVE, "tired");
        findRelatedWordsDemo(w,PointerType.SIMILAR_TO);
        
        
	
	}
	
	 // This function lists related words of type of relation for a given word
    public static void findRelatedWordsDemo(IndexWord w, PointerType type) throws JWNLException {
        System.out.println("\n\nI am now going to find related words for " + w.getLemma() + ", listed in groups by sense.");
        System.out.println("We'll look for relationships of type " + type.getLabel() + ".");
        
        // Call a function that returns an ArrayList of related senses
        ArrayList a = WordNetHelper.getRelated(w,type);
        if (a.isEmpty()) {
            System.out.println("Hmmm, I didn't find any related words.");
        } else {
            // Display the words for all the senses
            for (int i = 0; i < a.size(); i++) {
                Synset s = (Synset) a.get(i);
                Word[] words = s.getWords();
                for (int j = 0; j < words.length; j++ ) {
                    System.out.print(words[j].getLemma());
                    if (j != words.length-1) System.out.print(", ");
                }
                System.out.println();
            }
        }
    }

}