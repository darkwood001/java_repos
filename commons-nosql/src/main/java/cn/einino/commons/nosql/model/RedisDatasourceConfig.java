package cn.einino.commons.nosql.model;

public class RedisDatasourceConfig {

	private String host;
	private int port = 6379;

	public RedisDatasourceConfig() {
	}

	public final String getHost() {
		return host;
	}

	public final void setHost(String host) {
		this.host = host;
	}

	public final int getPort() {
		return port;
	}

	public final void setPort(int port) {
		this.port = port;
	}

}
