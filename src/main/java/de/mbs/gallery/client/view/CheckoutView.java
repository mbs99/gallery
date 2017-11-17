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

import de.mbs.gallery.client.activity.CheckoutActivity;
import de.mbs.gallery.client.model.EOrderState;
import de.mbs.gallery.client.model.Order;

public class CheckoutView extends Composite {

	@UiField
	HTMLPanel checkoutViewPanel;

	CheckoutActivity presenter;

	private static CheckoutViewUiBinder uiBinder = GWT.create(CheckoutViewUiBinder.class);

	interface CheckoutViewUiBinder extends UiBinder<Widget, CheckoutView> {
	}

	public interface DownloadImageArchiveTemplate extends SafeHtmlTemplates {
		@Template("<p>Ihre Bestellung steht &uuml;ber diesen <a href=\"{0}\">Link</a> zum Download bereit.</p>")
		SafeHtml archiveLink(String url);
	}

	DownloadImageArchiveTemplate downloadImageArchiveTemplate = GWT.create(DownloadImageArchiveTemplate.class);

	private static final Logger logger = Logger.getLogger("CheckoutView");

	public CheckoutView(CheckoutActivity activity) {
		this.presenter = activity;

		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	protected void onLoad() {

		super.onLoad();

		Order order = presenter.getOrder();
		
		EOrderState orderState = EOrderState.OPEN;
		if(null != order.getOrderState()) {
			orderState = EOrderState.valueOf(order.getOrderState());
		}
		
		if(EOrderState.SUBMIT.equals(orderState)
				|| EOrderState.DOWNLOAD.equals(orderState)) {
			presenter.getDownloadUrl();
		}

	}

	@Override
	protected void onUnload() {
		super.onUnload();
		logger.finest("enter onUnload(");

		logger.finest("exit onUnload(");
	}

	public void onGetDownloadUrl(String url) {
		
		$("#checkoutMsg").children().remove();
		$(downloadImageArchiveTemplate.archiveLink(url)).appendTo($("#checkoutMsg"));
		
	}
}
