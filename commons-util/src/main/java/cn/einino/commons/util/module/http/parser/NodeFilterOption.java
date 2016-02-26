package cn.einino.commons.util.module.http.parser;

import org.htmlparser.NodeFilter;

public class NodeFilterOption {

	private final int index;
	private final NodeFilter filter;

	public NodeFilterOption(int index) {
		this(index, null);
	}

	public NodeFilterOption(int index, NodeFilter filter) {
		this.index = index;
		this.filter = filter;
	}

	public int getIndex() {
		return index;
	}

	public NodeFilter getFilter() {
		return filter;
	}

}
