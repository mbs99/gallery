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

public class AdminViewNavbar extends Composite {

  private static AdminViewNavbarUiBinder uiBinder = GWT.create(AdminViewNavbarUiBinder.class);

  interface AdminViewNavbarUiBinder extends UiBinder<Widget, AdminViewNavbar> {}

  private static final Logger logger = Logger.getLogger("AdminView");

  private AppPanelPresenter presenter;

  public AdminViewNavbar(AppPanelPresenter presenter) {

    this.presenter = presenter;

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

    $("#galleryAdminButton")
        .click(
            new Function() {
              @Override
              public boolean f(Event e) {

                presenter.showGalleryAdminPanel();

                return false;
              }
            });

    $("#adminButton")
        .click(
            new Function() {
              @Override
              public boolean f(Event e) {

                presenter.showAdminPanel();

                return false;
              }
            });

    $("#orderAdminButton")
        .click(
            new Function() {
              @Override
              public boolean f(Event e) {

                presenter.showOrderAdminPanel();

                return false;
              }
            });
  }
}
