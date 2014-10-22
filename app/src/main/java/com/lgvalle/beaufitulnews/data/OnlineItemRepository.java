package com.lgvalle.beaufitulnews.data;

import android.util.Log;
import com.lgvalle.beaufitulnews.touryvino.TourYVinoService;
import com.lgvalle.beaufitulnews.touryvino.model.Item;
import com.lgvalle.beaufitulnews.touryvino.model.Rss;
import com.lgvalle.beaufitulnews.touryvino.model.Section;
import retrofit.RetrofitError;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lgvalle on 10/08/14.
 */
public class OnlineItemRepository implements ItemRepository {
	private static final String TAG = OnlineItemRepository.class.getSimpleName();
	private static OnlineItemRepository instance;
	/* memory cache */
	final Map<String, List<Item>> map;
	final TourYVinoService service;
	final ItemStorage storage;


	public static ItemRepository getInstance(TourYVinoService service, ItemStorage storage) {
		if (instance == null) {
			instance = new OnlineItemRepository(service, storage);
		}
		return instance;
	}

	private OnlineItemRepository(TourYVinoService service, ItemStorage storage) {
		this.service = service;
		this.storage = storage;
		// Init memory cache with stored data
		this.map = storage.getItemsMap();
		Log.d(TAG, "[OnlineItemRepository - OnlineItemRepository] - (line 29): " + "map: " + map.size());
	}

	@Override
	public void getItemsBySection(final Section section, final Callback<List<Item>> callback) {
		// If items in memory -> return
		List<Item> items = map.get(section.getParam());
		if (items != null && !items.isEmpty()) {
			callback.success(items);
		} else {
			// Retrieve from online
			service.getPortada(section.getParam(), new retrofit.Callback<Rss>() {
				@Override
				public void success(Rss rss, retrofit.client.Response response) {
					if (rss == null) {
						Log.d(TAG, "[BeautifulPhotosPresenterImpl - success] - (line 119): " + "rss null");
					} else if (rss.getChannel() == null) {
						Log.d(TAG, "[BeautifulPhotosPresenterImpl - success] - (line 121): " + "channel null");
					}
					ArrayList<Item> items = rss.getChannel().getItem();
					// Save in memory cache
					map.put(section.getParam(), items);
					// Notify results back
					callback.success(items);
				}

				@Override
				public void failure(RetrofitError retrofitError) {
					Log.e(TAG, "[BeautifulPhotosPresenterImpl - failure] - (line 124): " + "", retrofitError);
				}
			});
		}
	}

	@Override
	public void storageItems() {
		// Save map cache in storage
		storage.saveItemsMap(map);
	}
}
