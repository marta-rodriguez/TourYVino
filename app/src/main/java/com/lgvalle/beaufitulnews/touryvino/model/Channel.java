package com.lgvalle.beaufitulnews.touryvino.model;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

/**
 * Created by lgvalle on 31/07/14.
 */
@Root(strict = false)
public class Channel {

	@Element
	private String title;
	@Element
	private String description;
	@Element
	private String lastBuildDate;

	@ElementList(inline = true)
	private ArrayList<Item> item;

	@Element
	private String language;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public String getLastBuildDate() {
		return lastBuildDate;
	}

	public void setLastBuildDate(String lastBuildDate) {
		this.lastBuildDate = lastBuildDate;
	}

	public ArrayList<Item> getItem() {
		return item;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
}
