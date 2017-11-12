package de.mbs.gallery.client.view;

import static com.google.gwt.query.client.GQuery.$;

import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.query.client.Function;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
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

	private static final String DELETE_BUTTON_ID_PREFIX = "deleteItem";
	private static final String ORDER_ITEM_ID_PREFIX = "orderItem";
	private static final String COMMENTS_ID_PREFIX = "textarea";

	private static OrderViewUiBinder uiBinder = GWT.create(OrderViewUiBinder.class);

	interface OrderViewUiBinder extends UiBinder<Widget, OrderView> {
	}

	public interface OrderItemTemplate extends SafeHtmlTemplates {
		@Template("<div class=\"row\" id=\"" + ORDER_ITEM_ID_PREFIX + "{1}\">" + "<div class=\"three columns\">"
				+ "<img class=\"galleryImage\" src=\"{2}\"/>" + "<button id=\"" + DELETE_BUTTON_ID_PREFIX
				+ "{1}\">Bild entfernen</button>" + "</div>" + "<div class=\"nine columns\">"
				+ "<span>Bildnummer {0}</span>" + "<label for=\"textarea{1}\">Anmerkungen, W&uuml;nsche usw.</label>"
				+ "<textarea id=\""+COMMENTS_ID_PREFIX+"{1}\" class=\"u-full-width\"></textarea>" + "</div>" + "</div>")
		SafeHtml image(String imgTitle, String imgId, String imgUrl);
	}

	OrderItemTemplate orderItemTemplate = GWT.create(OrderItemTemplate.class);

	private static final Logger logger = Logger.getLogger("OrderView");

	public OrderView(OrderActivity activity) {
		this.presenter = activity;

		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	protected void onLoad() {

		super.onLoad();

		Order order = presenter.getOrder();
		for (GalleryImage img : order.getImages()) {
			$(orderItemTemplate.image(img.getFile(), img.getId(), createImgUrl(order.getGalleryName(), img.getId())))
					.appendTo($(orderViewPanel));

			$("#" + DELETE_BUTTON_ID_PREFIX + img.getId()).click(new Function() {
				@Override
				public boolean f(Event e) {

					presenter.removeImage(img.getId());

					return false;
				}
			});
		}
	}

	@Override
	protected void onUnload() {
		super.onUnload();

		Order order = presenter.getOrder();
		for (GalleryImage img : order.getImages()) {

			$("#" + DELETE_BUTTON_ID_PREFIX + img.getFile()).off();
		}
	}

	private String createImgUrl(String galleryName, String id) {
		return GWT.getHostPageBaseURL() + "api/gallery/" + galleryName + "/" + id;

	}

	public void bindToModel() {
		logger.finest("onSubmitOrder");
		
		Order order = presenter.getOrder();
		for (GalleryImage img : order.getImages()) {

			String comment = $("#" + COMMENTS_ID_PREFIX + img.getFile()).val();
			if("" != comment) {
				img.setComments(comment);
			}
		}
		
		presenter.updateOrder(order);

	}

	public void onRemoveImage(String id) {
		$("#" + ORDER_ITEM_ID_PREFIX + id).remove();
	}

	public void onError(String reason) {
		$("#orderStatus").text("Fehler - " + reason);
		
	}

	public void onSubmitOrder() {
		$("#orderStatus").text("erfolgreich versendet");
		
	}
}
