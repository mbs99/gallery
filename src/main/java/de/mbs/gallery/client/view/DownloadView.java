package de.mbs.gallery.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import de.mbs.gallery.client.activity.DownloadActivity;
import de.mbs.gallery.client.model.GalleryImage;
import de.mbs.gallery.client.model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.google.gwt.query.client.GQuery.$;

public class DownloadView extends Composite {

  @UiField HTMLPanel downloadViewPanel;

  DownloadActivity presenter;

  private static DownloadViewUiBinder uiBinder = GWT.create(DownloadViewUiBinder.class);

  interface DownloadViewUiBinder extends UiBinder<Widget, DownloadView> {}

  private static final Logger logger = Logger.getLogger("DownloadView");

  List<DownloadImageContainer> cols = new ArrayList<>();

  Order order;

  private static final int colNum = 4;

  public DownloadView(DownloadActivity downloadActivity) {
    this.presenter = downloadActivity;

    initWidget(uiBinder.createAndBindUi(this));
  }

  @Override
  protected void onLoad() {

    logger.log(Level.FINEST, "enter onLoad");

    super.onLoad();

    HTMLPanel row = new HTMLPanel("");
    row.setStyleName("row");
    downloadViewPanel.add(row);

    for (int i = 0; i < colNum; i++) {
      cols.add(new DownloadImageContainer());
    }

    List<GalleryImage> images = order.getDownloadImages();
    int col = 0;
    for (GalleryImage image : images) {

      if (colNum == col) {
        col = 0;
      }

      cols.get(col++).addImage(order.getGalleryName(), image);
    }

    for (int i = 0; i < colNum; i++) {
      row.add(cols.get(i));
    }

    logger.log(Level.FINEST, "leave onLoad");
  }

  public void setOrder(Order order) {
    logger.log(Level.FINEST, "enter setOrder()");

    this.order = order;

    logger.log(Level.FINEST, "leave setOrder");
  }

  public void onClickDownload() {
    $(downloadViewPanel)
        .append(
            $("<a href=\""
                    + GWT.getHostPageBaseURL()
                    + "api/gallery/"
                    + order.getGalleryName()
                    + "/order/download/zip"
                    + "\"/>")
                .hide()
                .click());
  }
}
