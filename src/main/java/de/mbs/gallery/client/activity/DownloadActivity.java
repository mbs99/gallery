package de.mbs.gallery.client.activity;

import com.google.gwt.core.client.Callback;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.HandlerRegistration;
import de.mbs.gallery.client.ClientFactory;
import de.mbs.gallery.client.GalleryResources;
import de.mbs.gallery.client.event.ChangeNavbarEvent;
import de.mbs.gallery.client.event.EMenuItem;
import de.mbs.gallery.client.event.ENavbarType;
import de.mbs.gallery.client.event.MenuItemEvent;
import de.mbs.gallery.client.model.Gallery;
import de.mbs.gallery.client.model.GalleryImage;
import de.mbs.gallery.client.model.Order;
import de.mbs.gallery.client.place.DownloadPlace;
import de.mbs.gallery.client.place.ImagePlace;
import de.mbs.gallery.client.view.DownloadView;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class DownloadActivity extends AbstractGalleryActivity<DownloadPlace, DownloadView> {

  private List<HandlerRegistration> handlerRegistration = new ArrayList<>();

  private static final Logger logger = Logger.getLogger(DownloadActivity.class.getName());

  public DownloadActivity(DownloadPlace place, ClientFactory clientFactory) {
    super(place, clientFactory);
    this.model = clientFactory.getViewModel();
  }

  public DownloadActivity(ClientFactory clientFactory) {

    super(null, clientFactory);

    this.model = clientFactory.getViewModel();
  }

  public void setPlace(DownloadPlace place) {
    this.place = place;
  }

  @Override
  public void start(AcceptsOneWidget parent, EventBus eventBus) {

    if (isAuthorized()) {

      handlerRegistration.add(
          clientFactory
              .eventBus()
              .addHandler(
                  MenuItemEvent.TYPE,
                  event -> {
                    if (event.getItem() == EMenuItem.ZIP_DOWNLOAD) {

                      view.onClickDownload();
                    }
                  }));

      clientFactory.eventBus().fireEvent(new ChangeNavbarEvent(ENavbarType.DOWNLOAD_VIEW));

      this.view = clientFactory.downloadView(this);
      GalleryResources res = clientFactory.galleryResources();

      res.getOrder(
          place.getId(),
          new Callback<Order, String>() {
            @Override
            public void onFailure(String reason) {
              Window.alert(reason);
            }

            @Override
            public void onSuccess(Order order) {
              view.setOrder(order);
              parent.setWidget(view.asWidget());
            }
          });
    } else {
      redirectToLogin();
    }
  }

  @Override
  public void onStop() {

    if (null != handlerRegistration) {
      for (HandlerRegistration iter : handlerRegistration) {
        iter.removeHandler();
      }
    }

    logger.finest("onStop");
  }

  public void clickImage(String id) {
    Gallery gallery = model.getGallery(place.getId());
    GalleryImage selectedImage = null;
    for (GalleryImage iter : gallery.getImages()) {
      if (iter.getId().equals(id)) {
        selectedImage = iter;
        break;
      }
    }
    if (null != selectedImage) {
      clientFactory.placeController().goTo(new ImagePlace(gallery.getName(), id, ""));
    }
  }
}
