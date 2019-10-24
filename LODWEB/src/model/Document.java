package model;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Document {

	private int id;
	private String name;
	private int rating;
	private Set<Tag> tags;
	private double totalCategoriesEqualsUserModel;
	private double similarityJaccard;

	public Document(String name) {
		this.name = name;
	}

	public Document(int id, String name, int rating) {
		this.id = id;
		this.name = name;
		this.rating = rating;
		this.tags = new HashSet<>();
	}
	
	public Document(int id,int rating) {
		this.id = id;
		this.rating = rating;
		this.tags = new HashSet<>();
	}
	
	public Document(int id, String name) {
		this.id = id;
		this.name = name;
		this.rating = rating;
		this.tags = new HashSet<>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public void addNewTag(Tag tag) {
		this.tags.add(tag);
	}

	public Set<Tag> getTagsWithLimit(int limit) {
		return this.tags.stream().sorted(Comparator.comparingDouble(Tag::getScore).reversed()).limit(limit)
				.collect(Collectors.toSet());
	}

	public double getTotalCategoriesEqualsUserModel() {
		return totalCategoriesEqualsUserModel;
	}

	public void addTotalCategoriesEqualsUserModel(double total) {
		this.totalCategoriesEqualsUserModel += total;
	}

	public String[] getArrayOfStringTags(int limit) {
		String[] arrayOfTags = new String[limit];

		int i = 0;

		for (Tag tag : getTagsWithLimit(limit)) {
			arrayOfTags[i] = tag.getName();
			i++;
		}

		return arrayOfTags;
	}

	public double getSimilarityJaccard() {
		return similarityJaccard;
	}

	public void setSimilarityJaccard(double similarityJaccard) {
		this.similarityJaccard = similarityJaccard;
	}
}
