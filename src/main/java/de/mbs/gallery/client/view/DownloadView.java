package de.mbs.gallery.client.view;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import de.mbs.gallery.client.activity.DownloadActivity;
import de.mbs.gallery.client.model.Gallery;
import de.mbs.gallery.client.model.GalleryImage;


public class DownloadView extends Composite {
	
	@UiField
	HTMLPanel downloadViewPanel;
	
	DownloadActivity presenter;
	
	private static GalleryViewUiBinder uiBinder = GWT
			.create(GalleryViewUiBinder.class);

	interface GalleryViewUiBinder extends UiBinder<Widget, DownloadView> {
	}
	
	private static final Logger logger = Logger.getLogger("GalleryView");
	
	List<DownloadImageContainer> cols = new ArrayList<>();
	
	Gallery gallery;
	
	private static final int colNum = 4;

	public DownloadView(DownloadActivity downloadActivity) {
		this.presenter = downloadActivity;
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	protected void onLoad() {
		
		logger.log(Level.FINEST, "enter onLoad");
		
		super.onLoad();
		
		HTMLPanel row = new HTMLPanel("");
		row.setStyleName("row");
		downloadViewPanel.add(row);
		
		for(int i=0;i<colNum;i++) {
			cols.add(new DownloadImageContainer());
		}
		
		GalleryImage[] images = gallery.getImages();
		int col = 0;
		for(GalleryImage image : images) {
			
			if(colNum == col) {
				col = 0;
			}
			
			cols.get(col++).addImage(gallery.getName(), image);
		}
		
		for(int i=0;i<colNum;i++) {
			row.add(cols.get(i));
		}
		
		logger.log(Level.FINEST, "leave onLoad");
	}

	public void setGallery(Gallery result) {
		logger.log(Level.FINEST, "enter setGallery()");
		
		gallery = result;
		
		logger.log(Level.FINEST, "leave setGallery");
	}
}
