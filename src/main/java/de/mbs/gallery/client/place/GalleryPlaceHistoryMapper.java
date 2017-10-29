package de.mbs.gallery.client.place;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({GalleryPlace.Tokenizer.class,
	LoginPlace.Tokenizer.class,
	AdminPlace.Tokenizer.class,
	ImagePlace.Tokenizer.class,
	LogoutPlace.Tokenizer.class})
public interface GalleryPlaceHistoryMapper extends PlaceHistoryMapper
{
}