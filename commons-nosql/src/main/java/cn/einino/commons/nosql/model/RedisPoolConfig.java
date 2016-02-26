package cn.einino.commons.nosql.model;

public class RedisPoolConfig extends RedisDatasourceConfig {

	private String password;
	private int maxTotal = 32;
	private int maxIdle = 8;
	private long maxWait = 60 * 1000;
	private boolean testOnBorrow = false;
	private boolean testOnReturn = false;
	private int timeout = 60 * 1000;

	public RedisPoolConfig() {
	}

	public final String getPassword() {
		return password;
	}

	public final void setPassword(String password) {
		this.password = password;
	}

	public final int getMaxTotal() {
		return maxTotal;
	}

	public final void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}

	public final int getMaxIdle() {
		return maxIdle;
	}

	public final void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public final long getMaxWait() {
		return maxWait;
	}

	public final void setMaxWait(long maxWait) {
		this.maxWait = maxWait;
	}

	public final boolean isTestOnBorrow() {
		return testOnBorrow;
	}

	public final void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public final boolean isTestOnReturn() {
		return testOnReturn;
	}

	public final void setTestOnReturn(boolean testOnReturn) {
		this.testOnReturn = testOnReturn;
	}

	public final int getTimeout() {
		return timeout;
	}

	public final void setTimeout(int timeout) {
		this.timeout = timeout;
	}

}
