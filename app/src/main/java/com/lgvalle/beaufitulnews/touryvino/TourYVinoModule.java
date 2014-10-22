package com.lgvalle.beaufitulnews.touryvino;


import retrofit.RestAdapter;
import retrofit.converter.SimpleXMLConverter;

/**
 * Created by lgvalle on 31/07/14.
 */
public class TourYVinoModule {
	private static final String END_POINT = "http://www.touryvino.com/category/";
	private static final TourYVinoService service;

	static {
		// Configure an adapter for this client
		RestAdapter restAdapter = new RestAdapter.Builder()
				.setEndpoint(END_POINT)

				.setLogLevel(RestAdapter.LogLevel.BASIC)
				.setConverter(new SimpleXMLConverter())
				.build();

		// Create rest client
		service = restAdapter.create(TourYVinoService.class);
	}

	/**
	 * Hide constructor
	 */
	private TourYVinoModule() {}

	/**
	 * Expose rest client
	 */
	public static TourYVinoService getService() {
		return service;
	}

}