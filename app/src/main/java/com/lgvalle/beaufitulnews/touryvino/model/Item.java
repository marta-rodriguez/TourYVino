package com.lgvalle.beaufitulnews.touryvino.model;

import android.os.Parcel;
import android.os.Parcelable;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lgvalle on 31/07/14.
 */
@Root(strict = false)
public class Item implements Parcelable {


	@Element(required = false)
	private String title;

	@ElementList(entry = "description", inline = true, required = false)
	@Namespace(reference = "http://purl.org/rss/1.0/modules/content/", prefix = "content")
	private List<String> description;
	@Element(required = false)
	private String pubDate;
	@Element(required = false)
	private String link;


	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public List<String> getDescription() {
		return description;
	}

	public void setDescription(List<String> description) {
		this.description = description;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public String getImageURLSmall() {
		return "http://www.touryvino.com/wp-content/uploads/2014/08/header-logo.png";
	}

	/**
	 * Biggest image possible
	 *
	 * @return
	 */
	public String getImageURLLarge() {
		return "http://www.touryvino.com/wp-content/uploads/2014/08/header-logo.png";
	}


	public Item() {
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.title);
		dest.writeStringList(this.description);
		dest.writeString(this.pubDate);
		dest.writeString(this.link);
	}

	private Item(Parcel in) {
		this.title = in.readString();
		this.description = new ArrayList();
		in.readStringList(this.description);
		this.pubDate = in.readString();
		this.link = in.readString();
	}

	public static final Creator<Item> CREATOR = new Creator<Item>() {
		public Item createFromParcel(Parcel source) {
			return new Item(source);
		}

		public Item[] newArray(int size) {
			return new Item[size];
		}
	};
}
