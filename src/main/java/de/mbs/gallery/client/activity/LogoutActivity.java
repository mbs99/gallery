package de.mbs.gallery.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import de.mbs.gallery.client.ClientFactory;
import de.mbs.gallery.client.event.ChangeNavbarEvent;
import de.mbs.gallery.client.event.ENavbarType;
import de.mbs.gallery.client.place.LoginPlace;
import de.mbs.gallery.client.place.LogoutPlace;
import de.mbs.gallery.client.view.LogoutView;

public class LogoutActivity extends AbstractActivity {

  LogoutPlace place;
  ClientFactory clientFactory;
  LogoutView view;

  public LogoutActivity(LogoutPlace place, ClientFactory clientFactory) {
    super();

    this.place = place;
    this.clientFactory = clientFactory;
  }

  @Override
  public void start(AcceptsOneWidget parent, EventBus eventBus) {

    clientFactory.eventBus().fireEvent(new ChangeNavbarEvent(ENavbarType.DEFAULT));

    this.view = clientFactory.getLogoutView(this);

    parent.setWidget(view.asWidget());
  }

  @Override
  public void onStop() {
    clientFactory.getViewModel().setGallery(null);
  }

  public void goToLogin() {
    clientFactory.placeController().goTo(new LoginPlace(""));
  }
}
