package org.nutz.demo.bean;

public enum TT {
	T("T", 1),

	K("K", 2);

	String name;

	int value;

	/**
	 * @param name
	 * @param value
	 */
	private TT(String name, int value) {
		this.name = name;
		this.value = value;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

}
