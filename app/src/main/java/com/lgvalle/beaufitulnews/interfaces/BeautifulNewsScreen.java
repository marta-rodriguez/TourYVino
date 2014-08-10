package com.lgvalle.beaufitulnews.interfaces;

import com.lgvalle.beaufitulnews.elpais.model.Item;
import com.lgvalle.beaufitulnews.elpais.model.Section;

import java.util.List;

/**
 * Created by lgvalle on 22/07/14.
 * <p/>
 * Interface for main activity.
 * Just a simple example to illustrate how it's works
 */
public interface BeautifulNewsScreen {
	/**
	 * Show error on UI
	 *
	 * @param errorId Error resource id
	 */
	void showError(int errorId);

	/**
	 * Update UI title
	 *
	 * @param titleId Title resource id
	 */
	void updateTitle(int titleId);


	public void openDetails(int index, List<Item> items, Section section);

}