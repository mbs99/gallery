package de.mbs.gallery.client.model;

import com.google.gwt.query.client.builders.JsonBuilder;

import java.util.List;

public interface Order extends JsonBuilder {

  List<GalleryImage> getImages();

  void setImages(List<GalleryImage> images);

  String getGalleryName();

  void setGalleryName(String galleryName);

  String getOrderState();

  void setOrderState(String orderState);

  List<GalleryImage> getDownloadImages();

  void setDownloadImages(List<GalleryImage> images);
}
