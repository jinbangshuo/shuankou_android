package com.maitianer.shuankou.model;

public class ResultModel
{
	private int id;
	private String name;
	private String score;
	private String mark;

	public int getId () {
		return id;
	}

	public void setId (int id) {
		this.id = id;
	}

	public String getName () {
		return name;
	}

	public void setName (String name) {
		this.name = name;
	}

	public String getScore () {
		return score;
	}

	public void setScore (String score) {
		this.score = score;
	}

	public String getMark () {
		return mark;
	}

	public void setMark (String mark) {
		this.mark = mark;
	}

	public ResultModel (int id, String name, String score, String mark) {
		super();
		this.id = id;
		this.name = name;
		this.score = score;
		this.mark = mark;
	}

}
