package org.nutz.demo.bean;

import org.nutz.json.JsonField;
import org.nutz.lang.Lang;
import org.nutz.lang.util.NutMap;

public class R {
	@JsonField()
	TT tt = TT.T;

	NutMap t;

	String info = "sss";

	/**
	 * @return the t
	 */
	public NutMap getT() {
		return Lang.obj2nutmap(tt);
	}

	/**
	 * @param t the t to set
	 */
	public void setT(NutMap t) {
	}

	/**
	 * @return the tt
	 */
	public TT getTt() {
		return tt;
	}

	/**
	 * @param tt the tt to set
	 */
	public void setTt(TT tt) {
		this.tt = tt;
	}

	/**
	 * @return the info
	 */
	public String getInfo() {
		return info;
	}

	/**
	 * @param info the info to set
	 */
	public void setInfo(String info) {
		this.info = info;
	}
}
