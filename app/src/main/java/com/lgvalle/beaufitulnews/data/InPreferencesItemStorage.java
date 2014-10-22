package com.lgvalle.beaufitulnews.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.lgvalle.beaufitulnews.touryvino.model.Item;
import com.lgvalle.beaufitulnews.utils.PrefsManager;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lgvalle on 10/08/14.
 * <p/>
 * This aims to be a Cache for API requests.
 * Persisted objects would only live for a certain amount of time
 */


public class InPreferencesItemStorage implements ItemStorage {
	private static final long DEFAULT_CACHE_TIME = 60000;
	private static InPreferencesItemStorage instance;
	private final long cacheTime;
	private final PrefsManager prefs;

	public static ItemStorage getInstance(PrefsManager prefs) {
		if (instance == null) {
			instance = new InPreferencesItemStorage(prefs);
		}
		return instance;
	}

	private InPreferencesItemStorage(int cacheTime, PrefsManager prefs) {
		this.cacheTime = cacheTime;
		this.prefs = prefs;
	}

	private InPreferencesItemStorage(PrefsManager prefs) {
		this.cacheTime = DEFAULT_CACHE_TIME;
		this.prefs = prefs;
	}

	@Override
	public void saveItemsMap(Map<String, List<Item>> map) {
		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(map);
		prefs.saveItemsMap(json);
		prefs.saveTime();
	}

	@Override
	public Map<String, List<Item>> getItemsMap() {
		Map<String, List<Item>> map = new HashMap<String, List<Item>>();
		if (isValidDataAge()) {
			// Saved data still valid
			String json = prefs.getItemsMap();
			if (json != null) {
				Gson gson = new GsonBuilder().create();
				Type typeOfHashMap = new TypeToken<Map<String, ArrayList<Item>>>() { }.getType();
				map = gson.fromJson(json, typeOfHashMap);
			}
		} else {
			// Too old data, clear
			prefs.clearItemsMap();
		}
		return map;
	}

	/**
	 *
	 * @return True if data is still valid: time between saving and current time is less than cacheTime
	 */
	private boolean isValidDataAge() {
		long savingTime = prefs.getSavingTime();
		return (savingTime + cacheTime > System.currentTimeMillis());
	}

}
