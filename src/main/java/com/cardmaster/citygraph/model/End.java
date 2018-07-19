package com.cardmaster.citygraph.model;

public class End {
	
	private final int apexIndex;
	private final End next;

	public End(int apexIndexParam, End nextArg) {
		this.apexIndex = apexIndexParam;
		this.next = nextArg;
	}

	public int getApexIndex() {
		return apexIndex;
	}

	public End getNext() {
		return next;
	}

}
