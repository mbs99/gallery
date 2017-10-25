package de.mbs.gallery.client.view;

import static com.google.gwt.query.client.GQuery.$;

import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.query.client.Function;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import de.mbs.gallery.client.presenter.AppPanelPresenter;


public class ImageViewNavbar extends Composite {
	
	private static ImageViewNavbarUiBinder uiBinder = GWT
			.create(ImageViewNavbarUiBinder.class);

	interface ImageViewNavbarUiBinder extends UiBinder<Widget, ImageViewNavbar> {
	}
	
	private static final Logger logger = Logger.getLogger("ImageView");
	
	private AppPanelPresenter presenter;

	public ImageViewNavbar(AppPanelPresenter presenter) {
		
		this.presenter = presenter;
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	protected void onLoad() {
		
		super.onLoad();
		
		$("#nextImageButton").click(new Function() {
			@Override
			public boolean f(Event e) {
				
				presenter.nextImage();
				
				return false;
			}
		});
		
		$("#previousImageButton").click(new Function() {
			@Override
			public boolean f(Event e) {
				
				presenter.previousImage();
				
				return false;
			}
		});
		
		$("#galleryButton").click(new Function() {
			@Override
			public boolean f(Event e) {
				
				presenter.showGallery();
				
				return false;
			}
		});
	}
}
