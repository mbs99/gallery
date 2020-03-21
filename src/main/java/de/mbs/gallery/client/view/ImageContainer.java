package de.mbs.gallery.client.view;

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
import de.mbs.gallery.client.GalleryResources;
import de.mbs.gallery.client.activity.GalleryActivity;
import de.mbs.gallery.client.model.GalleryImage;

import java.util.logging.Logger;

import static com.google.gwt.query.client.GQuery.$;

public class ImageContainer extends Composite {

  private static final Logger logger = Logger.getLogger("ImageContainer");

  private static ImageContainerUiBinder uiBinder = GWT.create(ImageContainerUiBinder.class);

  interface ImageContainerUiBinder extends UiBinder<Widget, ImageContainer> {}

  public interface ImageTemplate extends SafeHtmlTemplates {
    @Template(
        "<img class=\"galleryImage galleryImagePadding\" id=\"{0}\" alt=\"{0}\" src=\"{1}\"/>")
    SafeHtml image(String id, String imgUrl);
  }

  ImageTemplate imageTemplate = GWT.create(ImageTemplate.class);

  public interface VoterTemplate extends SafeHtmlTemplates {
    @Template("<div class=\"starVoter\" id=\"starVoter{0}\" />")
    SafeHtml voter(String id);
  }

  VoterTemplate voterTemplate = GWT.create(VoterTemplate.class);

  @UiField HTMLPanel imgContainer;

  private ClickHandler clickHandler;
  private GalleryActivity presenter;

  public ImageContainer(GalleryActivity presenter, ClickHandler clickHandler) {

    this.presenter = presenter;
    this.clickHandler = clickHandler;

    initWidget(uiBinder.createAndBindUi(this));
  }

  private void addImage(GalleryImage image, String url) {

    Image img = new Image();
    img.setUrl(url);
    img.setAltText(image.getFile());
    img.setStyleName("galleryImage");
    img.addStyleName("galleryImagePadding");
    img.getElement().setId(image.getId());

    imgContainer.add(img);

    StarVoter voter = new StarVoter(presenter, image);
    // $(voter).attr("id", "starVoter"+image.getId());

    imgContainer.add(voter);

    Timer timer =
        new Timer() {

          @Override
          public void run() {
            $(voter).css("display", "none");
          }
        };

    Function mouseEnter =
        new Function() {
          @Override
          public boolean f(Event e) {

            timer.cancel();

            $(voter).css("display", "block");

            return false;
          }
        };

    Function mouseLeave =
        new Function() {

          @Override
          public boolean f(Event e) {
            timer.schedule(100);

            return false;
          }
        };

    $(img).mouseenter(mouseEnter);
    $(img).mouseleave(mouseLeave);
    $(voter).mouseenter(mouseEnter);
    $(voter).mouseleave(mouseLeave);

    img.addClickHandler(clickHandler);
  }

  public void addImage(String galleryName, GalleryImage image) {

    logger.finest("addImage " + galleryName);

    addImage(image, GalleryResources.getImageUrlThumbail(galleryName, image));
  }
}
