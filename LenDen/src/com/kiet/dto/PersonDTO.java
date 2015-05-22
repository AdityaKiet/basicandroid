package com.kiet.dto;

public class PersonDTO {
	private String name;
	private int giveAmount;
	private int takeAmount;
	private int id;

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

	public int getGiveAmount() {
		return giveAmount;
	}

	public void setGiveAmount(int giveAmount) {
		this.giveAmount = giveAmount;
	}

	public int getTakeAmount() {
		return takeAmount;
	}

	public void setTakeAmount(int takeAmount) {
		this.takeAmount = takeAmount;
	}

	public String toString() {
		return "PersonDTO [name=" + name + ", giveAmount=" + giveAmount
				+ ", takeAmount=" + takeAmount + ", id=" + id + "]";
	}

}
