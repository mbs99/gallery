package de.mbs.gallery.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import de.mbs.gallery.client.activity.AdminActivity;
import de.mbs.gallery.client.model.GalleryImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.google.gwt.query.client.GQuery.$;

public class AdminView extends AbstractView {

  @UiField HTMLPanel adminViewPanel;
  @UiField HTMLPanel galleryAdminViewPanel;
  @UiField HTMLPanel userAdminViewPanel;
  @UiField HTMLPanel editGalleryContainer;
  @UiField HTMLPanel downloadAdminViewPanel;
  @UiField HTMLPanel downloadImagesContainer;

  AdminActivity presenter;

  private static AdminViewUiBinder uiBinder = GWT.create(AdminViewUiBinder.class);

  interface AdminViewUiBinder extends UiBinder<Widget, AdminView> {}

  interface GallerySelectOptionTemplate extends SafeHtmlTemplates {
    @Template("<option value=\"{0}\">{0}</option>")
    SafeHtml addOption(String option);
  }

  private static final GallerySelectOptionTemplate ITEM_TEMPLATE =
      GWT.create(GallerySelectOptionTemplate.class);

  interface EditImageTemplate extends SafeHtmlTemplates {
    @Template(
        "<div class=\"editImageThumbnailContainer\"><img src=\"{0}\" class=\"editImageThumbnail\"/><input type=\"checkbox\" name=\"xxx\" value=\"{1}\" class= \"editImageCheckBox\"></div>")
    SafeHtml addImage(String url, String id);
  }

  private static final EditImageTemplate EDIT_IMAGE_TEMPLATE = GWT.create(EditImageTemplate.class);

  private static final Logger logger = Logger.getLogger("AdminView");

  public AdminView(AdminActivity activity) {
    this.presenter = activity;

    initWidget(uiBinder.createAndBindUi(this));
  }

  @Override
  protected void onLoad() {

    super.onLoad();

    presenter.getGalleriesAndUsers();

    $("#createUserButton")
        .click(
            new Function() {
              @Override
              public boolean f(Event e) {
                presenter.createUser(
                    $("#username").val(), $("#password").val(), $("#password2").val());
                return false;
              }
            });

    $("#deleteUserButton")
        .click(
            new Function() {
              @Override
              public boolean f(Event e) {
                presenter.deleteUser($("#users").val());
                return false;
              }
            });

    $("#addUserToGalleryButton")
        .click(
            new Function() {
              @Override
              public boolean f(Event e) {

                presenter.addUserToGallery($("#galleryUser").val(), $("#galleryName").val());

                return false;
              }
            });

    $("#removeUserFromGalleryButton")
        .click(
            new Function() {
              @Override
              public boolean f(Event e) {

                presenter.removeUserFromGallery($("#galleryUser").val(), $("#galleryName").val());

                return false;
              }
            });

    $("#changeAdminPwdButton")
        .click(
            new Function() {
              @Override
              public boolean f(Event e) {
                return false;
              }
            });

    $("#gallery")
        .change(
            new Function() {
              @Override
              public boolean f(Event e, Object... arg) {

                String selection = $(e.getEventTarget()).val();

                logger.finest(selection);

                String link = GWT.getHostPageBaseURL() + "gallery.html";

                $("#galleryLink").text(link);

                return false;
              }
            });

    $("#logo")
        .change(
            new Function() {
              @Override
              public boolean f(Event e) {
                final JsArray<JavaScriptObject> files = $(e).prop("files");
                presenter.changeLogo(files);
                return false;
              }
            });

    $("#emailButton")
        .click(
            new Function() {
              @Override
              public boolean f(Event e) {
                presenter.createEmail($("#email").val());
                return false;
              }
            });

    $("#deleteGalleryButton")
        .click(
            new Function() {
              @Override
              public boolean f(Event e) {
                presenter.deleteGallery($("#deleteGalleryList").val());
                return false;
              }
            });

    $("#createGalleryButton")
        .click(
            new Function() {
              @Override
              public boolean f(Event e) {
                final JsArray<JavaScriptObject> files = $("#galleryImg").prop("files");
                presenter.createGallery($("#galleryTitle").val());
                return false;
              }
            });

    $("#editGalleryButton")
        .click(
            new Function() {
              @Override
              public boolean f(Event e) {
                final JsArray<JavaScriptObject> files = $("#galleryImg").prop("files");
                presenter.addImagesToGallery($("#editGalleryList").val(), files);
                $("#loader").addClass("loader");
                $("#editGalleryButton").prop("disabled", true);
                return false;
              }
            });

    $("#editGalleryList")
        .on(
            "change",
            new Function() {
              public boolean f(Event e) {
                String selection = $(e.getEventTarget()).val();

                $(editGalleryContainer).children().remove();
                $("#deleteImagesButton").prop("display", isValidSelection(selection) ? "" : "none");

                if (isValidSelection(selection)) {
                  presenter.getGalleryImages(selection);
                }

                return false;
              }
            });

    $("#deleteImagesButton")
        .click(
            new Function() {
              public boolean f(Event e) {
                List<String> ids = new ArrayList<>();
                GQuery query = $(".editImageCheckBox");
                for (int i = 0; i < query.size(); i++) {
                  boolean isChecked = $(query.get(i)).prop("checked");
                  if (isChecked) {
                    String id = $(query.get(i)).val();
                    ids.add(id);
                  }
                }

                presenter.deleteImage($("#editGalleryList").val(), ids.toArray(new String[] {}));

                return false;
              }
            });

    $("#addDownloadImagesButton")
        .click(
            new Function() {
              @Override
              public boolean f(Event e) {
                final JsArray<JavaScriptObject> files = $("#downloadImage").prop("files");
                presenter.addDownloadImages($("#downloadGalleryList").val(), files);
                $("#loader").addClass("loader");
                $("#addDownloadImagesButton").prop("disabled", true);
                return false;
              }
            });

    $("#deleteDownloadImagesButton")
        .click(
            new Function() {
              public boolean f(Event e) {
                List<String> ids = new ArrayList<>();
                GQuery query = $(".editImageCheckBox");
                for (int i = 0; i < query.size(); i++) {
                  boolean isChecked = $(query.get(i)).prop("checked");
                  if (isChecked) {
                    String id = $(query.get(i)).val();
                    ids.add(id);
                  }
                }

                presenter.deleteDownloadImages(
                    $("#downloadGalleryList").val(), ids.toArray(new String[] {}));

                return false;
              }
            });
    $("#downloadGalleryList")
        .on(
            "change",
            new Function() {
              public boolean f(Event e) {
                String selection = $(e.getEventTarget()).val();

                $(downloadImagesContainer).children().remove();
                $("#deleteDownloadImagesButton")
                    .prop("display", showDeleteDownloadImagesButton() ? "" : "none");

                if (isValidSelection(selection)) {
                  presenter.getDownloadImages(selection);
                }

                return false;
              }
            });

    this.galleryAdminViewPanel.setVisible(false);
    this.downloadAdminViewPanel.setVisible(false);
  }

  @Override
  protected void onUnload() {
    super.onUnload();

    $("#createUserButton").off();
    $("#addUserToGalleryButton").off();
    $("#changeAdminPwdButton").off();
    $("#gallery").off();
    $("#logo").off();
    $("#emailButton").off();
    $("#deleteGalleryButton").off();
    $("#createGalleryButton").off();
    $("#editGalleryButton").off();
    $("deleteImagesButton").off();
  }

  public void onGetGalleries(String[] galleries) {

    initSelection($("#galleryName"), galleries);

    initSelection($("#gallery"), galleries);

    initSelection($("#deleteGalleryList"), galleries);

    initSelection($("#editGalleryList"), galleries);

    initSelection($("#downloadGalleryList"), galleries);
  }

  public void onGetUsers(String[] users) {

    initSelection($("#galleryUser"), users);
    initSelection($("#users"), users);
  }

  public void onAddUserToGallery() {
    logger.finest("onAddUserToGallery");

    InfoMessage.showMessage(
        $("#addUserToGalleryButton").parent(), "Benutzer wurde hinzugefügt.", 1000);
  }

  public void onFailure(String reason) {
    Window.alert(reason);
  }

  public void onCreateUser() {
    logger.finest("onCreateUser");

    resetUserFields();

    presenter.getGalleriesAndUsers();

    InfoMessage.showMessage(
        $("#createUserButton").parent(), "Benutzer erfolgreich angelegt.", 1000);
  }

  public void onRemoveUserFromGallery() {
    logger.finest("onRemoveUserFromGallery");

    InfoMessage.showMessage(
        $("#removeUserFromGalleryButton").parent(), "Benutzer wurde entfernt.", 1000);
  }

  private void resetUserFields() {
    $("#username").val("");
    $("#password").val("");
    $("#password2").val("");
  }

  public void onCreateUserFailure(String reason) {
    InfoMessage.showError($("#createUserButton").parent(), reason, 1000);
  }

  public void onRemoveUserFromGalleryFailure(String reason) {
    InfoMessage.showError($("#removeUserFromGalleryButton").parent(), reason, 1000);
  }

  public void onAddUserToGalleryFailure(String reason) {
    InfoMessage.showError($("#addUserToGalleryButton").parent(), reason, 1000);
  }

  private void initSelection(GQuery selector, String[] values) {
    selector.children().remove();
    $(ITEM_TEMPLATE.addOption("-")).appendTo(selector);
    for (String option : values) {
      $(ITEM_TEMPLATE.addOption(option)).appendTo(selector);
    }
  }

  private boolean isValidSelection(String value) {
    return !"-".equals(value);
  }

  public void onDeleteUser(String user) {

    $("#users option[value='" + user + "']").remove();
    InfoMessage.showMessage($("#deleteUserButton").parent(), "Benutzer wurde gelöscht.", 1000);
  }

  public void onDeleteUserFailure(String reason) {
    InfoMessage.showError($("#deleteUserButton").parent(), reason, 1000);
  }

  public void onChangLogoFailure(String reason) {
    InfoMessage.showError($("#logo").parent(), reason, 1000);
  }

  public void onChangeLogo() {
    InfoMessage.showMessage($("#logo").parent(), "Logo wurde gespeichert.", 1000);
  }

  public void onCreateEmailFailure(String reason) {
    InfoMessage.showError($("#email").parent(), reason, 1000);
  }

  public void onCreateEmail(String email) {
    showMessage("#email", "E-Mail wurde gespeichert.");
  }

  public void onDeleteGalleryFailure(String msg) {
    showError("#deleteGalleryList", msg);
  }

  public void onDeleteGallery(String name) {
    $("#deleteGalleryList option[value='" + name + "']").remove();
    showMessage("#deleteGalleryList", "Galerie wurde gelöscht.");
  }

  public void onCreateGallery(String name) {
    InfoMessage.showMessage($("#createGalleryButton").parent(), "Galerie wurde erstellt.", 1000);
    $("#loader").removeClass("loader");
    $("#createGalleryButton").prop("disabled", false);
  }

  public void onCreateGalleryFailure(String msg) {
    InfoMessage.showMessage(
        $("#createGalleryButton").parent(), "Fehler beim Erstellen der Galerie.", 1000);
    $("#loader").removeClass("loader");
    $("#createGalleryButton").prop("disabled", false);
  }

  public void showAdminPanel() {
    this.galleryAdminViewPanel.setVisible(false);
    this.userAdminViewPanel.setVisible(true);
    this.downloadAdminViewPanel.setVisible(false);
  }

  public void showGalleryAdminPanel() {
    this.userAdminViewPanel.setVisible(false);
    this.galleryAdminViewPanel.setVisible(true);
    this.downloadAdminViewPanel.setVisible(false);
  }

  public void onAddImagesToGalleryFailure(String reason) {
    $("#loader").removeClass("loader");

    $("#editGalleryButton").prop("disabled", false);

    InfoMessage.showMessage($("#editGalleryButton").parent(), "Fehler beim Upload.", 1000);
  }

  public void onAddImagesToGallery(String name) {
    $("#loader").removeClass("loader");

    $("#editGalleryButton").prop("disabled", false);

    $("#galleryImg").prop("files", (Object) null);

    presenter.getGalleryImages(name);
  }

  public void onGetGalleryImages(Map<String, GalleryImage> images) {

    $(editGalleryContainer).children().remove();

    for (Map.Entry<String, GalleryImage> item : images.entrySet()) {
      $(EDIT_IMAGE_TEMPLATE.addImage(item.getKey(), item.getValue().getId()))
          .appendTo(editGalleryContainer.getElement());
    }
  }

  public void onGetDownloadImagesFailure(String reason) {}

  public void onDeleteImages(String gallery) {
    $(editGalleryContainer).children().remove();
    $("#deleteImagesButton").prop("display", isValidSelection(gallery) ? "" : "none");

    if (isValidSelection(gallery)) {
      presenter.getGalleryImages(gallery);
    }
  }

  public void onDeleteImagesFailure(String reason) {}

  public void showDownloadAdminPanel() {
    this.galleryAdminViewPanel.setVisible(false);
    this.userAdminViewPanel.setVisible(false);
    this.downloadAdminViewPanel.setVisible(true);
  }

  public void onAddDownloadImages(String name) {

    $("#loader").removeClass("loader");

    $("#addDownloadImagesButton").prop("disabled", false);

    $("#downloadImage").prop("files", (Object) null);

    presenter.getDownloadImages(name);
  }

  public void onAddOrderImagesFailure(String reason) {
    $("#loader").removeClass("loader");

    $("#addOrderImagesButton").prop("disabled", false);

    $("#downloadImage").prop("files", (Object) null);

    InfoMessage.showMessage($("#addDownloadImagesButton").parent(), "Fehler beim Upload.", 1000);
  }

  public void onGetDownloadImages(Map<String, GalleryImage> images) {

    $(downloadImagesContainer).children().remove();

    for (Map.Entry<String, GalleryImage> item : images.entrySet()) {
      $(EDIT_IMAGE_TEMPLATE.addImage(item.getKey(), item.getValue().getId()))
          .appendTo(downloadImagesContainer.getElement());
    }
  }

  public void onDeleteDownloadImages(String gallery) {
    $(downloadImagesContainer).children().remove();
    $("#deleteDownloadImagesButton")
        .prop("display", showDeleteDownloadImagesButton() ? "" : "none");

    if (isValidSelection(gallery)) {
      presenter.getDownloadImages(gallery);
    }
  }

  public void onDeleteDownloadImagesFailure(String reason) {
    InfoMessage.showMessage(
        $("#deleteDownloadImagesButton").parent(), "Fehler beim L&ouml;schen.", 1000);
  }

  private boolean showDeleteDownloadImagesButton() {

    logger.log(Level.INFO, "isValidSelection:" + isValidSelection($("#downloadGalleryList").val()));

    logger.log(Level.INFO, "children:" + $(downloadImagesContainer).children().length());

    return isValidSelection($("#downloadGalleryList").val())
        && 0 != $(downloadImagesContainer).children().length();
  }
}
