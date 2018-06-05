package model;

public class HitRate {
	
	

	public HitRate(int top1, int top3, int top5, int top10, int top15, int top20, int top25, int top30, int after,
			String similarityMethod) {
		super();
		this.top1 = top1;
		this.top3 = top3;
		this.top5 = top5;
		this.top10 = top10;
		this.top15 = top15;
		this.top20 = top20;
		this.top25 = top25;
		this.top30 = top30;
		this.after = after;
		this.similarityMethod = similarityMethod;
	}
	public int getTop1() {
		return top1;
	}
	public void setTop1(int top1) {
		this.top1 = top1;
	}
	public int getTop3() {
		return top3;
	}
	public void setTop3(int top3) {
		this.top3 = top3;
	}
	public int getTop5() {
		return top5;
	}
	public void setTop5(int top5) {
		this.top5 = top5;
	}
	public int getTop10() {
		return top10;
	}
	public void setTop10(int top10) {
		this.top10 = top10;
	}
	public int getTop15() {
		return top15;
	}
	public void setTop15(int top15) {
		this.top15 = top15;
	}
	public int getTop20() {
		return top20;
	}
	public void setTop20(int top20) {
		this.top20 = top20;
	}
	public int getTop25() {
		return top25;
	}
	public void setTop25(int top25) {
		this.top25 = top25;
	}
	public int getTop30() {
		return top30;
	}
	public void setTop30(int top30) {
		this.top30 = top30;
	}
	public int getAfter() {
		return after;
	}
	public void setAfter(int after) {
		this.after = after;
	}
	public String getSimilarityMethod() {
		return similarityMethod;
	}
	public void setSimilarityMethod(String similarityMethod) {
		this.similarityMethod = similarityMethod;
	}

	private int top1;
	private int top3;
	private int top5;
	private int top10;
	private int top15;	
	private int top20;
	private int top25;
	private int top30;
	private int after;
	private String similarityMethod;	
}
