package cn.einino.commons.util.module.http;

public class RouteInfo {

	private String host;
	private int port;
	private int total = 10;

	public RouteInfo() {
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

	public final int getTotal() {
		return total;
	}

	public final void setTotal(int total) {
		this.total = total;
	}

}
