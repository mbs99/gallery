package de.mbs.gallery.client.view;

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
import de.mbs.gallery.client.GalleryResources;
import de.mbs.gallery.client.activity.OrderActivity;
import de.mbs.gallery.client.model.EOrderState;
import de.mbs.gallery.client.model.GalleryImage;
import de.mbs.gallery.client.model.Order;

import java.util.Optional;
import java.util.logging.Logger;

import static com.google.gwt.query.client.GQuery.$;

public class OrderView extends Composite {

  @UiField HTMLPanel orderViewPanel;

  OrderActivity presenter;

  private static final String DELETE_BUTTON_ID_PREFIX = "deleteItem";
  private static final String ORDER_ITEM_ID_PREFIX = "orderItem";
  private static final String COMMENTS_ID_PREFIX = "textarea";

  private static OrderViewUiBinder uiBinder = GWT.create(OrderViewUiBinder.class);

  interface OrderViewUiBinder extends UiBinder<Widget, OrderView> {}

  public interface OrderItemTemplate extends SafeHtmlTemplates {
    @Template(
        "<div class=\"row\" id=\""
            + ORDER_ITEM_ID_PREFIX
            + "{1}\">"
            + "<div class=\"three columns\">"
            + "<img class=\"galleryImage\" src=\"{2}\"/>"
            + "<button id=\""
            + DELETE_BUTTON_ID_PREFIX
            + "{1}\">Bild entfernen</button>"
            + "</div>"
            + "<div class=\"nine columns\">"
            + "<span>Bildnummer {0}</span>"
            + "<label for=\"textarea{1}\">Anmerkungen, W&uuml;nsche usw.</label>"
            + "<textarea id=\""
            + COMMENTS_ID_PREFIX
            + "{1}\" class=\"u-full-width\">{3}</textarea>"
            + "</div>"
            + "</div>")
    SafeHtml image(String imgTitle, String imgId, String imgUrl, String comment);
  }

  OrderItemTemplate orderItemTemplate = GWT.create(OrderItemTemplate.class);

  public interface ReadonlyOrderItemTemplate extends SafeHtmlTemplates {
    @Template(
        "<div class=\"row\" id=\""
            + ORDER_ITEM_ID_PREFIX
            + "{1}\">"
            + "<div class=\"three columns\">"
            + "<img class=\"galleryImage\" src=\"{2}\"/>"
            + "</div>"
            + "<div class=\"nine columns\">"
            + "<span>Bildnummer {0}</span>"
            + "<label for=\"textarea{1}\">Anmerkungen, W&uuml;nsche usw.</label>"
            + "<textarea disabled id=\""
            + COMMENTS_ID_PREFIX
            + "{1}\" class=\"u-full-width\">{3}</textarea>"
            + "</div>"
            + "</div>")
    SafeHtml image(String imgTitle, String imgId, String imgUrl, String comment);
  }

  ReadonlyOrderItemTemplate readonlyOrderItemTemplate = GWT.create(ReadonlyOrderItemTemplate.class);

  private static final Logger logger = Logger.getLogger("OrderView");

  public OrderView(OrderActivity activity) {
    this.presenter = activity;

    initWidget(uiBinder.createAndBindUi(this));
  }

  @Override
  protected void onLoad() {

    super.onLoad();

    Order order = presenter.getOrder();

    EOrderState orderState = EOrderState.OPEN;
    if (null != order.getOrderState()) {
      orderState = EOrderState.safeParseValue(order.getOrderState());
    }

    updateOrderState(orderState);

    boolean readOnly = !orderState.equals(EOrderState.OPEN);

    for (GalleryImage img : order.getImages()) {

      if (readOnly) {
        $(readonlyOrderItemTemplate.image(
                img.getFile(),
                img.getId(),
                GalleryResources.getImageUrl(order.getGalleryName(), img),
                Optional.ofNullable(img.getComments()).orElse("")))
            .appendTo($(orderViewPanel));
      } else {
        $(orderItemTemplate.image(
                img.getFile(),
                img.getId(),
                GalleryResources.getImageUrl(order.getGalleryName(), img),
                Optional.ofNullable(img.getComments()).orElse("")))
            .appendTo($(orderViewPanel));

        $("#" + DELETE_BUTTON_ID_PREFIX + img.getId())
            .click(
                new Function() {
                  @Override
                  public boolean f(Event e) {

                    presenter.removeImage(img.getId());

                    return false;
                  }
                });
      }
    }
  }

  @Override
  protected void onUnload() {
    super.onUnload();

    Order order = presenter.getOrder();
    for (GalleryImage img : order.getImages()) {

      $("#" + DELETE_BUTTON_ID_PREFIX + img.getId()).off();
    }
  }

  public void bindToModel() {
    logger.finest("onSubmitOrder");

    Order order = presenter.getOrder();
    for (GalleryImage img : order.getImages()) {
      Optional.ofNullable($("#" + COMMENTS_ID_PREFIX + img.getId()).val())
          .filter(comment -> !comment.isEmpty())
          .ifPresent(img::setComments);
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

  private void updateOrderState(EOrderState state) {
    if (null != state) {
      switch (state) {
        case SUBMIT:
          {
            $("#orderStatus").text("erfolgreich versendet");
          }
          break;

        case WIP:
          {
            $("#orderStatus").text("erfolgreich versendet");
          }
          break;

        case DOWNLOAD:
          {
            $("#orderStatus").text("erfolgreich versendet");
          }
          break;

        default:
          $("#orderStatus").text("offen");
      }
    }
  }
}
