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

import de.mbs.gallery.client.activity.OrderActivity;
import de.mbs.gallery.client.model.GalleryImage;
import de.mbs.gallery.client.model.Order;

public class OrderView extends Composite {
	
	@UiField
	HTMLPanel orderViewPanel;
	
	OrderActivity presenter;
	
	private static OrderViewUiBinder uiBinder = GWT
			.create(OrderViewUiBinder.class);

	interface OrderViewUiBinder extends UiBinder<Widget, OrderView> {
	}
	
	public interface ImageTemplate extends SafeHtmlTemplates {
		   @Template("<div class=\"row\">"
				+ "<div class=\"three columns\">"
		   		+ "<img class=\"galleryImage\" src=\"{0}\"/></div>"
		   		+ "<div class=\"nine columns\">"
		   		+ "</div>"
				+ "</div>")
		   SafeHtml image(String imgUrl);
		}
		
	ImageTemplate imageTemplate = GWT.create(ImageTemplate.class);
	
	private static final Logger logger = Logger.getLogger("OrderView");

	public OrderView(OrderActivity activity) {
		this.presenter = activity;
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	protected void onLoad() {
		
		super.onLoad();
		
		Order order = presenter.getOrder();
		for(GalleryImage img : order.getImages()) {
			$(imageTemplate.image(createImgUrl(order.getGalleryName(), img.getId())))
				.appendTo($(orderViewPanel));
		}
	}
	
	private String createImgUrl(String galleryName, String id) {
		return GWT.getHostPageBaseURL() + "api/gallery/" + galleryName + "/" + id;
		
	}
}
