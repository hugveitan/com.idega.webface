package com.idega.webface.test;

import java.util.ListResourceBundle;

/**
 * @author al
 */
public class TestBundle_sv_SE extends ListResourceBundle {

	static final Object[][] contents = {
		{"name", "Namn"},
		{"author", "F�rfattare"},
		{"company", "F�retag"},
		{"edit_article", "Redigera artikel"},
		{"article_headline_empty", "Rubrik m�ste anges."},
		{"article_body_empty", "Artikeltext m�ste anges."}
	};

	public Object[][] getContents() {
	  return contents;
	}
}
