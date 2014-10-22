package com.lgvalle.beaufitulnews.touryvino.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by lgvalle on 31/07/14.
 */
@Root(strict = false)
public class Rss {

	@Element
	private Channel channel;


	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

}
