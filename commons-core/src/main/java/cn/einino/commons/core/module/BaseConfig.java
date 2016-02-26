package cn.einino.commons.core.module;

public abstract class BaseConfig {

	private long lastModify = -1;

	public final long getLastModify() {
		return lastModify;
	}

	public final void setLastModify(long lastModify) {
		this.lastModify = lastModify;
	}

}
