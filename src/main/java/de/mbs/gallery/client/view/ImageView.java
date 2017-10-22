package de.mbs.gallery.client.view;

import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

import de.mbs.gallery.client.activity.ImageActivity;
import de.mbs.gallery.client.model.GalleryImage;

import static com.google.gwt.query.client.GQuery.$;

public class ImageView extends Composite {
	
	@UiField
	HTMLPanel imageViewPanel;
	
	@UiField
	HTMLPanel imageContainer;
	
	@UiField
	HTMLPanel voterContainer;
	
	@UiField
	HTMLPanel buttonPanel;
	
	private ImageActivity presenter;
	
	private GalleryImage galleryImage;
	
	private StarVoter voter;
	
	private int index = -1;
	
	private static ImageViewUiBinder uiBinder = GWT
			.create(ImageViewUiBinder.class);

	interface ImageViewUiBinder extends UiBinder<Widget, ImageView> {
	}
	
	private static final Logger logger = Logger.getLogger("ImageView");

	public ImageView(ImageActivity activity) {
		this.presenter = activity;
		
		initWidget(uiBinder.createAndBindUi(this));
		
		voterContainer.setVisible(false);
		buttonPanel.setVisible(false);
	}
	
	@Override
	protected void onLoad() {
		
		super.onLoad();
		
		$("#nextImageButton").click(new Function() {
			@Override
			public boolean f(Event e) {
				
				presenter.nextImage(index);
				
				return false;
			}
		});
		
		$("#previousImageButton").click(new Function() {
			@Override
			public boolean f(Event e) {
				
				presenter.previousImage(index);
				
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
	
	@Override
	protected void onUnload() {
		super.onUnload();
		
		$("#nextImageButton").off();
		$("#previousImageButton").off();
		$("#galleryButton").off();
	}

	public void setImage(GalleryImage image, String url, int index) {
		this.galleryImage = image;
		this.index = index;
		
		voter = new StarVoter(galleryImage);
		voter.setStyleName("starVoterFs");
		
		Image img = new Image();
		img.setUrl(url);
		img.setAltText(galleryImage.getFilename());
		img.setStyleName("galleryImageFs");
		img.getElement().setId(galleryImage.getId());
		img.addLoadHandler(new LoadHandler() {
			
			@Override
			public void onLoad(LoadEvent event) {
				voterContainer.setVisible(true);
				buttonPanel.setVisible(true);
				
			}
		});
		
		imageContainer.add(img);
		
		voterContainer.add(voter);
		
	}
}
