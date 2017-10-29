package de.mbs.gallery.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class LogoutPlace extends Place {

	public LogoutPlace() {
	}

	public static class Tokenizer implements PlaceTokenizer<LogoutPlace> {
		@Override
		public String getToken(LogoutPlace place) {
			return "";
		}

		@Override
		public LogoutPlace getPlace(String token) {
			return new LogoutPlace();
		}
	}
}
