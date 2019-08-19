package tagging;

import java.util.ArrayList;

import model.Tag;

public class SemanticTagSet {
	private ArrayList<Tag> userModel;
	private ArrayList<Tag> testSet;
	
	public SemanticTagSet() {
		
	}

	public SemanticTagSet(ArrayList<Tag> userModel, ArrayList<Tag> testSet) {
		super();
		this.userModel = userModel;
		this.testSet = testSet;
	}

	public ArrayList<Tag> getUserModel() {
		return userModel;
	}

	public void setUserModel(ArrayList<Tag> userModel) {
		this.userModel = userModel;
	}

	public ArrayList<Tag> getTestSet() {
		return testSet;
	}

	public void setTestSet(ArrayList<Tag> testSet) {
		this.testSet = testSet;
	}
}
