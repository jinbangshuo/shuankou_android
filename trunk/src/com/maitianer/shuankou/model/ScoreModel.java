package com.maitianer.shuankou.model;

public class ScoreModel
{
	private float allScore, aScore;
	private int maxLen;
	private String title;

	public float getAllScore () {
		return allScore;
	}

	public void setAllScore (float allScore) {
		this.allScore = allScore;
	}

	public float getaScore () {
		return aScore;
	}

	public void setaScore (float aScore) {
		this.aScore = aScore;
	}

	public String getTitle () {
		return title;
	}

	public void setTitle (String title) {
		this.title = title;
	}

	public int getMaxLen () {
		return maxLen;
	}

	public void setMaxLen (int maxLen) {
		this.maxLen = maxLen;
	}

	public ScoreModel (float allScore, float aScore, int maxLen, String title) {
		super();
		this.allScore = allScore;
		this.aScore = aScore;
		this.maxLen = maxLen;
		this.title = title;
	}

	public ScoreModel (String title) {
		super();
		this.allScore = 0;
		this.aScore = 0;
		this.maxLen = 0;
		this.title = title;
	}

	public ScoreModel () {
		super();
	}

}
