package de.mbs.gallery.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class StarVoter extends Composite {
	
	private static StarVoterUiBinder uiBinder = GWT
			.create(StarVoterUiBinder.class);

	interface StarVoterUiBinder extends UiBinder<Widget, StarVoter> {
	}

	public StarVoter() {
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	protected void onLoad() {
		
		super.onLoad();
	}
}
