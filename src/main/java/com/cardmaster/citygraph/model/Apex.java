package com.cardmaster.citygraph.model;

public class Apex<T> {
	private final T member;
	private volatile End aList;

	public Apex(T t, End objectList) {
		this.member = t;
		this.setAList(objectList);
	}

	public T getMember() {
		return member;
	}

	public End getAList() {
		return aList;
	}

	public void setAList(End objectList) {
		this.aList = objectList;
	}

}
