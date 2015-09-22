package com.maitianer.shuankou.model;

public class PlayerModel
{
	private String name;
	private int id, closed;

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

	public int getClosed () {
		return closed;
	}

	public void setClosed (int closed) {
		this.closed = closed;
	}

	public PlayerModel () {
		super();
	}

	public PlayerModel (int id, String name, int closed) {
		super();
		this.id = id;
		this.name = name;
		this.closed = closed;
	}

	public PlayerModel (int id, String name) {
		super();
		this.id = id;
		this.name = name;
		this.closed = 0;
	}

	public PlayerModel (String name) {
		super();
		this.id = 0;
		this.name = name;
		this.closed = 0;
	}
}
