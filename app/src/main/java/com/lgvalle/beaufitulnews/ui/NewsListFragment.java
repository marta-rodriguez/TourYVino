package com.lgvalle.beaufitulnews.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.OnItemClick;
import com.lgvalle.beaufitulnews.R;
import com.lgvalle.beaufitulnews.touryvino.model.Item;
import com.lgvalle.beaufitulnews.touryvino.model.Section;
import com.lgvalle.beaufitulnews.events.ItemChosenEvent;
import com.lgvalle.beaufitulnews.events.ItemsAvailableEvent;
import com.lgvalle.beaufitulnews.events.RequestingMoreItemsEvent;
import com.lgvalle.beaufitulnews.utils.BusHelper;
import com.lgvalle.beaufitulnews.utils.Renderer;
import com.squareup.otto.Subscribe;

/**
 * Created by lgvalle on 02/08/14.
 */
public class NewsListFragment extends BaseElementListFragment<Item> {
	private static final String TAG = NewsListFragment.class.getSimpleName();
	private static final String INTENT_EXTRA_SECTION = "section";
	private Section section;

	@Override
	protected Renderer<Item> getRenderer() {
		return new NewsItemRenderer();
	}

	public static NewsListFragment newInstance(Section section) {
		NewsListFragment f = new NewsListFragment();
		Bundle args = new Bundle();
		args.putParcelable(INTENT_EXTRA_SECTION, section);
		f.setArguments(args);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.section = getArguments().getParcelable(INTENT_EXTRA_SECTION);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
		// Empty list? Ask for some items!
		if (adapter.isEmpty()) {
			BusHelper.post(new RequestingMoreItemsEvent(section));
		}
	}

	@Override
	protected void initLayout() {
		super.initLayout();
		//list.setBackgroundColor(section.getColor());
	}

	@Subscribe
	public void onItemsAvailableEvent(ItemsAvailableEvent<Item, Section> event) {
		if (event.getSection().equals(section)) {
			super.onItemsAvailableEvent(event);
		}
	}

	/**
	 * Click on a gallery item
	 *
	 * @param position Position of clicked item
	 */
	@OnItemClick(R.id.photo_list)
	public void onItemClick(int position) {
		BusHelper.post(new ItemChosenEvent(section, adapter.getItem(position)));
	}
}
