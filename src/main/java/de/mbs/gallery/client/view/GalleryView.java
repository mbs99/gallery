package de.mbs.gallery.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import de.mbs.gallery.client.activity.GalleryActivity;
import de.mbs.gallery.client.model.Gallery;
import de.mbs.gallery.client.model.GalleryImage;

import java.util.logging.Level;
import java.util.logging.Logger;


public class GalleryView extends Composite {
	
	@UiField
	HTMLPanel galleryViewPanel;
	
	GalleryActivity presenter;
	
	private static GalleryViewUiBinder uiBinder = GWT
			.create(GalleryViewUiBinder.class);

	interface GalleryViewUiBinder extends UiBinder<Widget, GalleryView> {
	}
	
	private static final Logger logger = Logger.getLogger("GalleryView");
	
	Gallery gallery;
	
	private static final int colNum = 4;

	public GalleryView(GalleryActivity galleryActivity) {
		this.presenter = galleryActivity;
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	protected void onLoad() {
		
		logger.log(Level.FINEST, "enter onLoad");
		
		super.onLoad();
		
		HTMLPanel row = createRow();
		galleryViewPanel.add(row);
		
		ClickHandler clickHandler = event -> {
			Image targetImage = (Image)event.getSource();
			if(null !=targetImage) {
				presenter.clickImage(targetImage.getElement().getId());
			}

		};
		
		GalleryImage[] images = gallery.getImages();
		int col = 0;
		for(GalleryImage image : images) {
			
			if(0 == col ||
					colNum == col) {
				col = 0;
				row = createRow();
				galleryViewPanel.add(row);
			}
			ImageContainer imageContainer = new ImageContainer(presenter, clickHandler);
			imageContainer.addImage(gallery.getName(), image);
			row.add(imageContainer);
			col++;
		}
		
		logger.log(Level.FINEST, "leave onLoad");
	}

	public void setGallery(Gallery result) {
		logger.log(Level.FINEST, "enter setGallery()");
		
		gallery = result;
		
		logger.log(Level.FINEST, "leave setGallery");
	}

	public void onSaveGallery() {
		logger.log(Level.FINEST, "enter onSaveGallery()");
		logger.log(Level.FINEST, "leave onSaveGallery");
	}

	public void onSubmitOrder() {
		logger.log(Level.FINEST, "enter onSubmitOrder()");
		logger.log(Level.FINEST, "leave onSubmitOrder");
	}

	private HTMLPanel createRow() {
		HTMLPanel row = new HTMLPanel("");
		row.setStyleName("row");

		return row;
	}
}
