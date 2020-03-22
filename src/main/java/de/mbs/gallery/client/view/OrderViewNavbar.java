package de.mbs.gallery.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.query.client.Function;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import de.mbs.gallery.client.presenter.AppPanelPresenter;

import java.util.logging.Logger;

import static com.google.gwt.query.client.GQuery.$;

public class OrderViewNavbar extends Composite {

  private static OrderViewNavbarUiBinder uiBinder = GWT.create(OrderViewNavbarUiBinder.class);

  interface OrderViewNavbarUiBinder extends UiBinder<Widget, OrderViewNavbar> {}

  private static final Logger logger = Logger.getLogger("OrderViewNavbar");

  private AppPanelPresenter presenter;
  private final Boolean readOnly;

  public OrderViewNavbar(AppPanelPresenter presenter) {

    this(presenter, Boolean.FALSE);
  }

  public OrderViewNavbar(AppPanelPresenter presenter, Boolean readOnly) {

    this.presenter = presenter;
    this.readOnly = readOnly;

    initWidget(uiBinder.createAndBindUi(this));
  }

  @Override
  protected void onLoad() {

    super.onLoad();

    $("#logoutButton")
        .click(
            new Function() {
              @Override
              public boolean f(Event e) {

                presenter.logout();

                return false;
              }
            });

    if (readOnly) {
      $("#orderButton").hide();

    } else {

      $("#checkoutButton").hide();

      $("#orderButton")
          .click(
              new Function() {
                @Override
                public boolean f(Event e) {

                  presenter.submitOrder();

                  return false;
                }
              });
    }

    $("#galleryButton")
        .click(
            new Function() {
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

    $("#filter").off();
    $("#logoutButton").off();
    $("#orderButton").off();

    logger.finest("onUnload");
  }
}
