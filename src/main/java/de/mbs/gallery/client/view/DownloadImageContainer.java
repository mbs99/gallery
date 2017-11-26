package de.mbs.gallery.client.view;

import static com.google.gwt.query.client.GQuery.$;

import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

import de.mbs.gallery.client.activity.DownloadActivity;
import de.mbs.gallery.client.model.GalleryImage;

public class DownloadImageContainer extends Composite {
	
	private static final Logger logger = Logger.getLogger(DownloadImageContainer.class.getSimpleName());
	
	private static DownloadImageContainerUiBinder uiBinder = GWT
			.create(DownloadImageContainerUiBinder.class);

	interface DownloadImageContainerUiBinder extends UiBinder<Widget, DownloadImageContainer> {
	}
	
	public interface ImageTemplate extends SafeHtmlTemplates {
		   @Template("<img class=\"galleryImage galleryImagePadding\" id=\"{0}\" alt=\"{0}\" src=\"{1}\"/>")
		   SafeHtml image(String id, String imgUrl);
		}
		
	ImageTemplate imageTemplate = GWT.create(ImageTemplate.class);
	
	public interface DownloaderTemplate extends SafeHtmlTemplates {
		   @Template("<div class=\"starVoter\" id=\"download{0}\"><a class=\"button\" href=\"{1}\" download>Download</a></div>")
		   SafeHtml download(String id, String url);
		}
		
	DownloaderTemplate downloaderTemplate = GWT.create(DownloaderTemplate.class);
	
	@UiField
	HTMLPanel imgContainer;
	
	private ClickHandler clickHandler;
	private DownloadActivity presenter;

	public DownloadImageContainer(DownloadActivity presenter, ClickHandler clickHandler) {
		
		this.presenter = presenter;
		this.clickHandler = clickHandler;
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	private void addImage(GalleryImage image, String thumbnailUrl, String url) {
		
		Image img = new Image();
		img.setUrl(thumbnailUrl);
		img.setAltText(image.getFile());
		img.setStyleName("galleryImage");
		img.addStyleName("galleryImagePadding");
		img.getElement().setId(image.getId());
		
		imgContainer.add(img);
		
		SafeHtml downloader = downloaderTemplate.download(image.getId(), url);
		
		$(downloader).appendTo($(imgContainer));
		
		Timer timer = new Timer() {

			@Override
			public void run() {
				$("#download"+image.getId()).css("display", "none");				
			}
		};
		
		Function mouseEnter = new Function() {
			@Override
			public boolean f(Event e) {
				
				timer.cancel();
				
				$("#download"+image.getId()).css("display", "block");
				
				return false;
			}
		};
		
		Function mouseLeave = new Function() {
			
			@Override
			public boolean f(Event e) {
				timer.schedule(100);
				
				return false;
			}
		};
		
		$(img).mouseenter(mouseEnter);	
		$(img).mouseleave(mouseLeave);
		$("#download"+image.getId()).mouseenter(mouseEnter);	
		$("#download"+image.getId()).mouseleave(mouseLeave);
		
		img.addClickHandler(clickHandler);
	}

	public void addImage(String galleryName, GalleryImage image) {
		
		logger.finest("addImage " + galleryName);
		
		addImage(image,
				GWT.getHostPageBaseURL() + "api/gallery/" + galleryName + "/" + image.getId(),
				GWT.getHostPageBaseURL() + "api/gallery/" + galleryName + "/" + image.getId());
		
	}
}
