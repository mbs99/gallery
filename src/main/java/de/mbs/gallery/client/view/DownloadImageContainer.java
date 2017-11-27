package de.mbs.gallery.client.view;

import static com.google.gwt.query.client.GQuery.$;

import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import de.mbs.gallery.client.model.GalleryImage;

public class DownloadImageContainer extends Composite {
	
	private static final Logger logger = Logger.getLogger(DownloadImageContainer.class.getSimpleName());
	
	private static DownloadImageContainerUiBinder uiBinder = GWT
			.create(DownloadImageContainerUiBinder.class);

	interface DownloadImageContainerUiBinder extends UiBinder<Widget, DownloadImageContainer> {
	}
	
	public interface ImageTemplate extends SafeHtmlTemplates {
		   @Template("<a href=\"{1}\" download><img class=\"galleryImage galleryImagePadding\" id=\"{0}\" alt=\"{0}\" src=\"{2}\"/></a>")
		   SafeHtml image(String id, String imgUrl, String thumbnailUrl);
		}
		
	ImageTemplate imageTemplate = GWT.create(ImageTemplate.class);
	
	@UiField
	HTMLPanel imgContainer;

	public DownloadImageContainer() {
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	private void addImage(GalleryImage image, String thumbnailUrl, String url) {
		
		$(imageTemplate.image(image.getId(), url, thumbnailUrl)).appendTo($(imgContainer));
	}

	public void addImage(String galleryName, GalleryImage image) {
		
		logger.finest("addImage " + galleryName);
		
		addImage(image,
				GWT.getHostPageBaseURL() + "api/gallery/" + galleryName + "/" + image.getId() + "?thumbnail=true",
				GWT.getHostPageBaseURL() + "api/gallery/" + galleryName + "/" + image.getId());
		
	}
}
