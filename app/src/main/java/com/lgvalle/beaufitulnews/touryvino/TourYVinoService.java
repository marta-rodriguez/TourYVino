package com.lgvalle.beaufitulnews.touryvino;

import com.lgvalle.beaufitulnews.touryvino.model.Rss;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by lgvalle on 31/07/14.
 */
public interface TourYVinoService {
	@GET("/{section}/feed/")
	void getPortada(@Path("section") String section, Callback<Rss> callback);

}
