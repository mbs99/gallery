package de.mbs.gallery.client.presenter;

import com.google.gwt.core.client.Callback;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import de.mbs.gallery.client.ClientFactory;
import de.mbs.gallery.client.Constants;
import de.mbs.gallery.client.event.*;
import de.mbs.gallery.client.model.Authorization;
import de.mbs.gallery.client.place.GalleryPlace;
import de.mbs.gallery.client.place.LogoutPlace;
import de.mbs.gallery.client.view.AppPanel;
import de.mbs.gallery.client.view.DownloadViewNavbar;

public class AppPanelPresenter {

  ClientFactory factory;
  private AppPanel view;

  public AppPanelPresenter(ClientFactory clientFactory) {
    this.factory = clientFactory;

    clientFactory.eventBus().addHandler(ChangeNavbarEvent.TYPE, this::handleChangeNavbar);

    clientFactory.eventBus().addHandler(ChangeFilterEvent.TYPE, this::handleChangeFilter);
    clientFactory.eventBus().addHandler(EnableFilterEvent.TYPE, this::handleEnableFilter);
  }

  public void setFilter(String filter) {

    factory.getViewModel().setFilter(filter);

    Place currentPlace = factory.placeController().getWhere();
    if (currentPlace instanceof GalleryPlace) {
      GalleryPlace place = new GalleryPlace(((GalleryPlace) currentPlace).getId(), filter);

      factory.placeController().goTo(place);
    }
  }

  public void showGallery() {
    factory.eventBus().fireEvent(new MenuItemEvent(EMenuItem.SHOW_GALLERY));
  }

  public void previousImage() {
    factory.eventBus().fireEvent(new MenuItemEvent(EMenuItem.SHOW_PREVIOUS_IMAGE));
  }

  public void nextImage() {
    factory.eventBus().fireEvent(new MenuItemEvent(EMenuItem.SHOW_NEXT_IMAGE));
  }

  public void setView(AppPanel view) {
    this.view = view;
  }

  private void handleChangeNavbar(ChangeNavbarEvent event) {
    switch (event.getNavbarType()) {
      case GALLERY_VIEW:
        {
          view.setNavbar(factory.getGalleryViewNavbar(this));
        }
        break;
      case IMAGE_VIEW:
        {
          view.setNavbar(factory.getImageViewNavbar(this));
        }
        break;

      case ADMIN_VIEW:
        {
          view.setNavbar(factory.getAdminViewNavbar(this));
        }
        break;

      case ORDER_VIEW:
        {
          view.setNavbar(factory.getOrderViewNavbar(this));
        }
        break;

      case CHECKOUT_VIEW:
        {
          view.setNavbar(factory.getCheckoutViewNavbar(this));
        }
        break;

      case DOWNLOAD_VIEW:
        {
          DownloadViewNavbar navBar = factory.getDownloadViewNavbar(this);
          view.setNavbar(navBar);
        }
        break;

      case READONLY_ORDER_VIEW:
        {
          view.setNavbar(factory.getReadOnlyOrderViewNavbar(this));
        }
        break;

      default:
        view.setNavbar(factory.getDefaultNavbar(this));
        break;
    }
  }

  private void handleChangeFilter(ChangeFilterEvent event) {
    view.setFilter(event.getFilter());
  }

  private void handleEnableFilter(EnableFilterEvent event) {
    view.enableFilter(event.getEnableFilter());
  }

  public void logout() {
    factory
        .galleryResources()
        .logout(
            new Callback<Void, String>() {

              @Override
              public void onSuccess(Void result) {
                Authorization auth = factory.getAuthorization();
                auth.setUser(null);
                auth.setPassword(null);

                Cookies.removeCookie(Constants.SESSION_COOKIE_NAME, "/");

                factory.placeController().goTo(new LogoutPlace());
              }

              @Override
              public void onFailure(String reason) {
                Window.alert(reason);

                factory.placeController().goTo(new LogoutPlace());
              }
            });
  }

  public void goToOrderPlace() {
    factory.eventBus().fireEvent(new MenuItemEvent(EMenuItem.SHOW_ORDER));
  }

  public void submitOrder() {
    factory.eventBus().fireEvent(new MenuItemEvent(EMenuItem.SUBMIT_ORDER));
  }

  public void zipDownload() {
    factory.eventBus().fireEvent(new MenuItemEvent(EMenuItem.ZIP_DOWNLOAD));
  }

  public void showAdminPanel() {
    factory.eventBus().fireEvent(new MenuItemEvent(EMenuItem.SHOW_ADMIN_PANEL));
  }

  public void showGalleryAdminPanel() {
    factory.eventBus().fireEvent(new MenuItemEvent(EMenuItem.SHOW_GALLERY_ADMIN_PANEL));
  }

  public void showOrderAdminPanel() {
    factory.eventBus().fireEvent(new MenuItemEvent(EMenuItem.SHOW_ORDER_ADMIN_PANEL));
  }

  public void goToDownloadPlace() {
    factory.eventBus().fireEvent(new MenuItemEvent(EMenuItem.SHOW_DOWNLOAD_VIEW));
  }
}
