package cn.einino.commons.util.constant;

import org.htmlparser.filters.TagNameFilter;

public interface HtmlParserConstants {

	final TagNameFilter TABLE_FILTER = new TagNameFilter("table");
	final TagNameFilter TBODY_FILTER = new TagNameFilter("tbody");
	final TagNameFilter TR_FILTER = new TagNameFilter("tr");
	final TagNameFilter TD_FILTER = new TagNameFilter("td");
	final TagNameFilter FORM_FILTER = new TagNameFilter("form");
	final TagNameFilter SPAN_FILTER = new TagNameFilter("span");
	final TagNameFilter A_FILTER = new TagNameFilter("a");
	final TagNameFilter DIV_FILTER = new TagNameFilter("div");
	final TagNameFilter P_FILTER = new TagNameFilter("p");
	final TagNameFilter UL_FILTER = new TagNameFilter("ul");
	final TagNameFilter LI_FILTER = new TagNameFilter("li");
}
