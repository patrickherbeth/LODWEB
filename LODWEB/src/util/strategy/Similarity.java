package util.strategy;

import java.util.List;

import model.Cenario;
import net.didion.jwnl.JWNLException;

public interface Similarity {

	public void choiceOfSimilarity(List<Cenario> cenarios,Cenario cenario, int userId, int limitTag);
	
}
