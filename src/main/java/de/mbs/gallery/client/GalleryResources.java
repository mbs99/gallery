package de.mbs.gallery.client;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQ;
import com.google.gwt.query.client.js.JsUtils;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.query.client.plugins.ajax.Ajax.Settings;
import de.mbs.gallery.client.model.*;

public class GalleryResources {

  public static String getImageUrl(String galleryName, GalleryImage image) {
    return GWT.getHostPageBaseURL() + "api/gallery/" + galleryName + "/images/" + image.getId();
  }

  public static String getImageUrl(Gallery gallery, GalleryImage image) {
    return getImageUrl(gallery.getName(), image);
  }

  public static String getImageUrlThumbail(Gallery gallery, GalleryImage image) {
    return GalleryResources.getImageUrlThumbail(gallery.getName(), image);
  }

  public static String getImageUrlThumbail(String galleryName, GalleryImage image) {
    return GalleryResources.getImageUrl(galleryName, image) + "?thumbnail=true";
  }

  public static String getImageUrlDownload(
      String galleryName, GalleryImage image, boolean thumbnail) {

    String url =
        GWT.getHostPageBaseURL()
            + "api/gallery/"
            + galleryName
            + "/order/download/"
            + image.getId();

    if (thumbnail) {
      url += "?thumbnail=true";
    }

    return url;
  }

  public static String getImageUrlDownload(Gallery gallery, GalleryImage image, boolean thumbnail) {
    return getImageUrlDownload(gallery.getName(), image, thumbnail);
  }

  public void getGallery(String id, Callback<Gallery, String> callback) {
    Ajax.get(GWT.getHostPageBaseURL() + "api/gallery/" + id)
        .done(
            new Function() {
              @Override
              public Object f(Object... args) {

                Gallery gallery = GQ.create(Gallery.class);

                gallery = gallery.parse((String) args[0]);

                callback.onSuccess(gallery);

                return null;
              }
            })
        .fail(
            new Function() {
              @Override
              public void f() {
                callback.onFailure("Fehler beim Laden der Galerie!");
              }
            });
  }

  public void login(Authorization authorization, Callback<Authorization, String> callback) {
    Ajax.post(GWT.getHostPageBaseURL() + "api/login", authorization)
        .done(
            new Function() {
              @Override
              public Object f(Object... args) {

                Authorization auth = GQ.create(Authorization.class);

                auth = auth.parse((String) args[0]);

                callback.onSuccess(auth);

                return null;
              }
            })
        .fail(
            new Function() {
              @Override
              public void f() {

                callback.onFailure("Fehler bei der Anmeldung");
              }
            });
  }

  public void saveGallery(Gallery gallery, Callback<Void, String> callback) {

    Settings settings = Ajax.createSettings();
    settings.setUrl(GWT.getHostPageBaseURL() + "api/gallery/" + gallery.getName());
    settings.setType("post");
    settings.setData(gallery);
    settings.setDataType("json");
    settings.setContentType("application/json");

    Ajax.ajax(settings)
        .done(
            new Function() {
              @Override
              public Object f(Object... args) {

                callback.onSuccess(null);

                return null;
              }
            })
        .fail(
            new Function() {
              @Override
              public void f() {
                callback.onFailure("");
              }
            });
  }

  public void logout(Callback<Void, String> callback) {
    Ajax.post(GWT.getHostPageBaseURL() + "api/logout", null)
        .done(
            new Function() {
              @Override
              public Object f(Object... args) {

                callback.onSuccess(null);

                return null;
              }
            })
        .fail(
            new Function() {
              @Override
              public void f() {
                callback.onFailure("Fehler bei der Abmeldung!");
              }
            });
  }

  public void getUsers(Callback<String[], String> callback) {
    Settings settings = Ajax.createSettings();
    settings.setUrl(GWT.getHostPageBaseURL() + "api/admin/users");
    settings.setType("get");

    Ajax.ajax(settings)
        .done(
            new Function() {
              @Override
              public Object f(Object... args) {

                UsernameList users = GQ.create(UsernameList.class, (String) args[0]);

                callback.onSuccess(users.getUsers());

                return null;
              }
            })
        .fail(
            new Function() {
              @Override
              public void f() {
                callback.onFailure("");
              }
            });
  }

  public void getGalleries(Callback<String[], String> callback) {
    Settings settings = Ajax.createSettings();
    settings.setUrl(GWT.getHostPageBaseURL() + "api/galleries");
    settings.setType("get");

    Ajax.ajax(settings)
        .done(
            new Function() {
              @Override
              public Object f(Object... args) {

                GalleryList galleries = GQ.create(GalleryList.class);

                galleries.parse((String) args[0]);

                callback.onSuccess(galleries.getGalleries());

                return null;
              }
            })
        .fail(
            new Function() {
              @Override
              public Object f(Object... args) {

                callback.onFailure((String) args[0]);

                return null;
              }
            });
  }

  public void addUserToGallery(String user, String gallery, Callback<Void, String> callback) {
    Settings settings = Ajax.createSettings();
    settings.setUrl(GWT.getHostPageBaseURL() + "/api/admin/gallery/" + gallery + "/user/" + user);
    settings.setType("post");

    Ajax.ajax(settings)
        .done(
            new Function() {
              @Override
              public Object f(Object... args) {

                callback.onSuccess(null);

                return null;
              }
            })
        .fail(
            new Function() {
              @Override
              public Object f(Object... args) {

                callback.onFailure((String) args[0]);

                return null;
              }
            });
  }

  public void createUser(UserAccount user, Callback<Void, String> callback) {
    Settings settings = Ajax.createSettings();
    settings.setUrl(GWT.getHostPageBaseURL() + "/api/admin/user");
    settings.setType("post");
    settings.setData(user);
    settings.setDataType("json");
    settings.setContentType("application/json");

    Ajax.ajax(settings)
        .done(
            new Function() {
              @Override
              public Object f(Object... args) {

                callback.onSuccess(null);

                return null;
              }
            })
        .fail(
            new Function() {
              @Override
              public Object f(Object... args) {

                callback.onFailure((String) args[0]);

                return null;
              }
            });
  }

  public void submitOrder(Gallery gallery, Callback<Void, String> callback) {
    Settings settings = Ajax.createSettings();
    settings.setUrl(GWT.getHostPageBaseURL() + "api/gallery/" + gallery.getName() + "/order");
    settings.setType("post");
    settings.setData("");
    settings.setDataType("json");
    settings.setContentType("application/json");

    Ajax.ajax(settings)
        .done(
            new Function() {
              @Override
              public Object f(Object... args) {

                callback.onSuccess(null);

                return null;
              }
            })
        .fail(
            new Function() {
              @Override
              public void f() {
                callback.onFailure("");
              }
            });
  }

  public void removeUserFromGallery(String user, String gallery, Callback<Void, String> callback) {
    Settings settings = Ajax.createSettings();
    settings.setUrl(GWT.getHostPageBaseURL() + "/api/admin/gallery/" + gallery + "/user/" + user);
    settings.setType("delete");

    Ajax.ajax(settings)
        .done(
            new Function() {
              @Override
              public Object f(Object... args) {

                callback.onSuccess(null);

                return null;
              }
            })
        .fail(
            new Function() {
              @Override
              public Object f(Object... args) {

                callback.onFailure((String) args[0]);

                return null;
              }
            });
  }

  public void saveImage(String gallery, GalleryImage img, Callback<Void, String> callback) {
    Settings settings = Ajax.createSettings();
    settings.setUrl(
        GWT.getHostPageBaseURL() + "/api/gallery/" + gallery + "/images/" + img.getId());
    settings.setType("put");
    settings.setData(img);
    settings.setDataType("json");
    settings.setContentType("application/json");

    Ajax.ajax(settings)
        .done(
            new Function() {
              @Override
              public Object f(Object... args) {

                callback.onSuccess(null);

                return null;
              }
            })
        .fail(
            new Function() {
              @Override
              public Object f(Object... args) {

                callback.onFailure((String) args[0]);

                return null;
              }
            });
  }

  public void isDownloadReady(String galleryName, Callback<String, String> callback) {

    Settings settings = Ajax.createSettings();
    settings.setUrl(GWT.getHostPageBaseURL() + "api/gallery/" + galleryName + "/order/download");
    settings.setType("head");

    Ajax.ajax(settings)
        .done(
            new Function() {
              @Override
              public Object f(Object... args) {

                callback.onSuccess(settings.getUrl());

                return null;
              }
            })
        .fail(
            new Function() {
              @Override
              public void f() {
                callback.onFailure("Fehler...");
              }
            });
  }

  public void deleteUser(String user, Callback<Void, String> callback) {
    Settings settings = Ajax.createSettings();
    settings.setUrl(GWT.getHostPageBaseURL() + "/api/admin/user/" + user);
    settings.setType("delete");

    Ajax.ajax(settings)
        .done(
            new Function() {
              @Override
              public Object f(Object... args) {

                callback.onSuccess(null);

                return null;
              }
            })
        .fail(
            new Function() {
              @Override
              public Object f(Object... args) {

                callback.onFailure((String) args[0]);

                return null;
              }
            });
  }

  public void changeLogo(JsArray<JavaScriptObject> files, Callback<Void, String> callback) {
    Settings settings = Ajax.createSettings();
    settings.setUrl(getNormalizedHostPageBaseURL() + "/api/logo");
    settings.setType("post");

    JavaScriptObject formData = JsUtils.jsni("eval", "new FormData()");
    JsUtils.jsni(formData, "append", "logo", files.get(0));
    settings.setData(formData);

    Ajax.ajax(settings)
        .done(
            new Function() {
              @Override
              public Object f(Object... args) {

                callback.onSuccess(null);

                return null;
              }
            })
        .fail(
            new Function() {
              @Override
              public Object f(Object... args) {

                callback.onFailure((String) args[0]);

                return null;
              }
            });
  }

  private String getNormalizedHostPageBaseURL() {
    String baseUrl = GWT.getHostPageBaseURL();
    if (baseUrl.endsWith("/")) {
      baseUrl = baseUrl.substring(0, baseUrl.lastIndexOf('/'));
    }
    return baseUrl;
  }

  public void createEmail(Email email, Callback<Void, String> callback) {
    Settings settings = Ajax.createSettings();
    settings.setUrl(getNormalizedHostPageBaseURL() + "/api/email");
    settings.setType("post");
    settings.setData(email);
    settings.setDataType("json");
    settings.setContentType("application/json");

    Ajax.ajax(settings)
        .done(
            new Function() {
              @Override
              public Object f(Object... args) {

                callback.onSuccess(null);

                return null;
              }
            })
        .fail(
            new Function() {
              @Override
              public void f() {
                callback.onFailure("Fehler beim Speichern.");
              }
            });
  }

  public void deleteGallery(String name, Callback<Void, String> callback) {
    Settings settings = Ajax.createSettings();
    settings.setUrl(getNormalizedHostPageBaseURL() + "/api/admin/gallery/" + name);
    settings.setType("delete");

    Ajax.ajax(settings)
        .done(
            new Function() {
              @Override
              public Object f(Object... args) {

                callback.onSuccess(null);

                return null;
              }
            })
        .fail(
            new Function() {
              @Override
              public Object f(Object... args) {

                callback.onFailure((String) args[0]);

                return null;
              }
            });
  }

  public void createGallery(String name, Callback<Void, String> callback) {
    Settings settings = Ajax.createSettings();
    settings.setUrl(getNormalizedHostPageBaseURL() + "/api/admin/gallery?name=" + name);
    settings.setType("post");

    Ajax.ajax(settings)
        .done(
            new Function() {
              @Override
              public Object f(Object... args) {

                callback.onSuccess(null);

                return null;
              }
            })
        .fail(
            new Function() {
              @Override
              public Object f(Object... args) {

                callback.onFailure((String) args[0]);

                return null;
              }
            });
  }

  public void addImagesToGallery(
      String name, JsArray<JavaScriptObject> files, Callback<Void, String> callback) {
    Settings settings = Ajax.createSettings();
    settings.setUrl(getNormalizedHostPageBaseURL() + "/api/admin/gallery/" + name);
    settings.setType("post");

    JavaScriptObject formData = JsUtils.jsni("eval", "new FormData()");
    for (int i = 0; i < files.length(); i++) {
      JsUtils.jsni(formData, "append", "images[]", files.get(i));
    }

    settings.setData(formData);

    Ajax.ajax(settings)
        .done(
            new Function() {
              @Override
              public Object f(Object... args) {

                callback.onSuccess(null);

                return null;
              }
            })
        .fail(
            new Function() {
              @Override
              public Object f(Object... args) {

                callback.onFailure((String) args[0]);

                return null;
              }
            });
  }

  public void deleteImage(String gallery, String[] imageIds, Callback<Void, String> callback) {

    this.deleteDeleteImage(
        getNormalizedHostPageBaseURL() + "/api/admin/gallery/" + gallery, imageIds, callback);
  }

  public void addOrderImages(
      String name, JsArray<JavaScriptObject> files, Callback<Void, String> callback) {
    Settings settings = Ajax.createSettings();
    settings.setUrl(
        getNormalizedHostPageBaseURL() + "/api/admin/gallery/" + name + "/order/download");
    settings.setType("post");

    JavaScriptObject formData = JsUtils.jsni("eval", "new FormData()");
    for (int i = 0; i < files.length(); i++) {
      JsUtils.jsni(formData, "append", "images[]", files.get(i));
    }

    settings.setData(formData);

    Ajax.ajax(settings)
        .done(
            new Function() {
              @Override
              public Object f(Object... args) {

                callback.onSuccess(null);

                return null;
              }
            })
        .fail(
            new Function() {
              @Override
              public Object f(Object... args) {

                callback.onFailure((String) args[0]);

                return null;
              }
            });
  }

  public void getOrder(String id, Callback<Order, String> callback) {
    Ajax.get(GWT.getHostPageBaseURL() + "api/gallery/" + id + "/order")
        .done(
            new Function() {
              @Override
              public Object f(Object... args) {

                Order order = GQ.create(Order.class);

                order = order.parse((String) args[0]);

                callback.onSuccess(order);

                return null;
              }
            })
        .fail(
            new Function() {
              @Override
              public void f() {
                callback.onFailure("Fehler beim Laden der Bestellung!");
              }
            });
  }

  public void deleteDownloadImage(
      String gallery, String[] imageIds, Callback<Void, String> callback) {

    this.deleteDeleteImage(
        getNormalizedHostPageBaseURL() + "/api/admin/gallery/" + gallery + "/order/download",
        imageIds,
        callback);
  }

  private void deleteDeleteImage(
      String baseUrl, String[] imageIds, Callback<Void, String> callback) {
    Settings settings = Ajax.createSettings();
    StringBuilder urlBuffer = new StringBuilder();
    urlBuffer.append(baseUrl);
    urlBuffer.append("?");
    int i = 0;
    for (String id : imageIds) {
      urlBuffer.append("id");
      urlBuffer.append(i++);
      urlBuffer.append("=");
      urlBuffer.append(id);
      urlBuffer.append("&");
    }
    String url = urlBuffer.toString();
    url = url.substring(0, url.lastIndexOf('&'));
    settings.setUrl(url);
    settings.setType("delete");

    Ajax.ajax(settings)
        .done(
            new Function() {
              @Override
              public Object f(Object... args) {

                callback.onSuccess(null);

                return null;
              }
            })
        .fail(
            new Function() {
              @Override
              public Object f(Object... args) {

                callback.onFailure((String) args[0]);

                return null;
              }
            });
  }
}
