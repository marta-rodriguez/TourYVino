package com.lgvalle.beaufitulnews.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.lgvalle.beaufitulnews.touryvino.model.Item;
import com.lgvalle.beaufitulnews.touryvino.model.Section;

/**
 * Created by lgvalle on 02/08/14.
 */
public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

	private final Section[] sections;

	public SectionsPagerAdapter(FragmentManager fm, Section[] sections) {
		super(fm);
		this.sections = sections;
	}

	@Override
	public int getCount() {
		return sections.length;
	}

	@Override
	public Fragment getItem(int i) {
		BaseElementListFragment<Item> news = NewsListFragment.newInstance(sections[i]);;
		return news;
	}
}
