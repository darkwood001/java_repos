package cn.einino.commons.util;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import cn.einino.commons.util.module.http.parser.NodeFilterOption;

public class HtmlParserUtil {

	private final NodeFilterOption firstFilterOption;
	private final List<NodeFilterOption> filterOptions;

	public HtmlParserUtil(NodeFilterOption firstFilterOption,
			List<NodeFilterOption> filterOptions) {
		this.firstFilterOption = firstFilterOption;
		this.filterOptions = filterOptions;
	}

	public Node parseHtml(byte[] src, String charset) throws ParserException {
		String content;
		try {
			content = new String(src, charset);
			return parseHtml(content, charset);
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	public Node parseHtml(String src, String charset) throws ParserException {
		Node node = null;
		Parser parser = Parser.createParser(src, charset);
		NodeList nodeList = parser.extractAllNodesThatMatch(firstFilterOption
				.getFilter());
		if (nodeList == null || nodeList.size() <= firstFilterOption.getIndex()) {
			return node;
		}
		node = nodeList.elementAt(firstFilterOption.getIndex());
		if (node != null) {
			for (NodeFilterOption option : filterOptions) {
				node = parseNode(node, option);
				if (node == null) {
					break;
				}
			}
		}
		return node;
	}

	private Node parseNode(Node parent, NodeFilterOption filterOption) {
		Node node = null;
		if (parent.getChildren() == null
				|| parent.getChildren().size() <= filterOption.getIndex()) {
			return node;
		}
		NodeList list;
		if (filterOption.getFilter() == null) {
			list = parent.getChildren();
		} else {
			list = parent.getChildren().extractAllNodesThatMatch(
					filterOption.getFilter());
		}
		if (list != null && list.size() > filterOption.getIndex()) {
			node = list.elementAt(filterOption.getIndex());
		}
		return node;
	}
}
