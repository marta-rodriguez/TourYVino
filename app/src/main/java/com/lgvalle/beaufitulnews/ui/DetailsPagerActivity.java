package com.lgvalle.beaufitulnews.ui;

import android.support.v4.view.ViewPager;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.lgvalle.beaufitulnews.R;
import com.lgvalle.beaufitulnews.data.InPreferencesItemStorage;
import com.lgvalle.beaufitulnews.data.ItemRepository;
import com.lgvalle.beaufitulnews.data.ItemStorage;
import com.lgvalle.beaufitulnews.data.OnlineItemRepository;
import com.lgvalle.beaufitulnews.touryvino.TourYVinoModule;
import com.lgvalle.beaufitulnews.touryvino.model.Item;
import com.lgvalle.beaufitulnews.touryvino.model.Section;
import com.lgvalle.beaufitulnews.utils.BusHelper;
import com.lgvalle.beaufitulnews.utils.PrefsManager;
import com.xgc1986.parallaxPagerTransformer.ParallaxPagerTransformer;

import java.util.List;


/**
 * Main activity
 * <p/>
 * This class is on UI layer, so it's only responsible for UI interactions.
 * <p/>
 * It loads a Presenter to manage all business logic: data fetching and caching.
 * <p/>
 * The UI consist in two fragments: one with a list of photos and one for photo details.
 * <p/>
 * Finally, the activity (screen) creates a presenter and ask for photos. Results communication will happen through the event bus
 */
public class DetailsPagerActivity extends BaseActivity implements DetailsPagerScreen {
	private static final String TAG = DetailsPagerActivity.class.getSimpleName();
	public static final String INTENT_EXTRA_SECTION = "intent_extra_section";
	public static final String INTENT_EXTRA_INDEX = "intent_extra_index";
	private DetailsPagerPresenter presenter;

	@InjectView(R.id.pager)
	ViewPager pager;
	private int index;
	private Section section;

	@Override
	protected void getExtras() {
		super.getExtras();
		index = getIntent().getExtras().getInt(INTENT_EXTRA_INDEX);
		section = getIntent().getExtras().getParcelable(INTENT_EXTRA_SECTION);
	}

	@Override
	protected void onResume() {
		super.onResume();
		// Register on bus to let activity and presenter listen to events
		BusHelper.register(this);

	}

	@Override
	protected void onPause() {
		super.onPause();
		// Unregister every time activity is paused
		BusHelper.unregister(this);
	}


	@Override
	protected int getContentView() {
		return R.layout.activity_main;
	}

	@Override
	protected void initLayout() {
		ButterKnife.inject(this);
		presenter.needItems(section);
	}

	@Override
	protected void initActionBar() {
		super.initActionBar();
		getSupportActionBar().hide();
	}

	@Override
	protected void initPresenter() {
		ItemStorage storage = InPreferencesItemStorage.getInstance(PrefsManager.getInstance(this));
		ItemRepository repository = OnlineItemRepository.getInstance(TourYVinoModule.getService(), storage);

		this.presenter = new DetailsPagerPresenterImpl(repository, this);
	}

	@Override
	public void setItems(List<Item> items) {
		DetailsPagerAdapter adapter = new DetailsPagerAdapter(getSupportFragmentManager(), items, section);
		pager.setAdapter(adapter);
		pager.setPageTransformer(false, new ParallaxPagerTransformer(R.id.photo));
		// Scroll to selected item
		pager.setCurrentItem(index);
	}
}
