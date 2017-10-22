package de.mbs.gallery.client.view;

import java.util.logging.Logger;


import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
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
	
	private ImageActivity presenter;
	
	private GalleryImage galleryImage;
	private String url;
	
	private StarVoter voter;
	
	private static ImageViewUiBinder uiBinder = GWT
			.create(ImageViewUiBinder.class);

	interface ImageViewUiBinder extends UiBinder<Widget, ImageView> {
	}
	
	private static final Logger logger = Logger.getLogger("ImageView");

	public ImageView(ImageActivity activity) {
		this.presenter = activity;
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	protected void onLoad() {
		
		super.onLoad();
		
		Image img = new Image();
		img.setUrl(url);
		img.setAltText(galleryImage.getFilename());
		img.setStyleName("galleryImage");
		img.getElement().setId(galleryImage.getId());
		
		$(img).appendTo($("#imageContainer"));
		
		$(voter).appendTo($("#imageVoterContainer"));
	}

	public void setImage(GalleryImage image, String url) {
		this.galleryImage = image;
		this.url = url;
		voter = new StarVoter(galleryImage);
	}
}
