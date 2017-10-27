package de.mbs.gallery.client.view;

import static com.google.gwt.query.client.GQuery.$;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.Function;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import de.mbs.gallery.client.model.GalleryImage;
import de.mbs.gallery.client.presenter.StarVoterPresenter;

public class StarVoter extends Composite {
	
	private static StarVoterUiBinder uiBinder = GWT
			.create(StarVoterUiBinder.class);

	interface StarVoterUiBinder extends UiBinder<Widget, StarVoter> {
	}

	private StarVoterPresenter presenter;
	private GalleryImage image;
	
	public StarVoter(StarVoterPresenter presenter,GalleryImage image) {
		this.presenter = presenter;
		this.image = image;
		
		initWidget(uiBinder.createAndBindUi(this));
		
		getElement().setId("starVoter"+image.getId());
	}
	
	@Override
	protected void onLoad() {
		
		super.onLoad();
		
		if(null == image.getVote()) {
			initStars(0);
		}
		else {
			initStars(image.getVote());
		}
		
		$("#starVoter"+image.getId()+" label").text(image.getFile());
		
		$("#starVoter"+image.getId()+" span").click(new Function() {
			@Override
			public boolean f(Event e) {
				
				final String vote = $(e.getEventTarget()).attr("data-stars");
				
				updateStars(Integer.parseInt(vote));
				
				presenter.updateVote(image);
				
				return false;
			}
		});
	}
	
	@Override
	protected void onUnload() {
		
		super.onUnload();
		
		$("#starVoter"+image.getId()+" span").off();
	}
	
	private void updateStars(Integer vote) {
		if(null != image.getVote() 
				&& image.getVote().equals(vote)) {
			$("#starVoter"+image.getId()+" span").each(new Function() {
				@Override
				public void f(Element e) {						
					e.addClassName("blackStar");
					e.removeClassName("redStar");
				}
			});
			
			$("#starVoter"+image.getId()).attr("data-current-vote", "0");
			image.setVote(Integer.parseInt("0"));
		}
		else {
			$("#starVoter"+image.getId()).attr("data-current-vote", vote);
			
			image.setVote(vote);
			
			$("#starVoter"+image.getId()+" span").each(new Function() {
				@Override
				public void f(Element e) {
					
					if( vote >= Integer.parseInt($(e).attr("data-stars"))) {
						e.addClassName("redStar");
						e.removeClassName("blackStar");
					}
					else {
						e.removeClassName("redStar");
						e.addClassName("blackStar");
					}
				}
			});
		}
	}
	
	private void initStars(Integer vote) {
		$("#starVoter"+image.getId()+" span").each(new Function() {
			@Override
			public void f(Element e) {
				
				if( vote >= Integer.parseInt($(e).attr("data-stars"))) {
					e.addClassName("redStar");
					e.removeClassName("blackStar");
				}
				else {
					e.removeClassName("redStar");
					e.addClassName("blackStar");
				}
			}
		});
	}
}
