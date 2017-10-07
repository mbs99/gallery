package de.mbs.gallery.client.view;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import de.mbs.gallery.client.activity.GalleryActivity;


public class GalleryView extends Composite {
	
	@UiField
	HTMLPanel galleryViewPanel;
	
	GalleryActivity presenter;
	
	private static GalleryViewUiBinder uiBinder = GWT
			.create(GalleryViewUiBinder.class);

	interface GalleryViewUiBinder extends UiBinder<Widget, GalleryView> {
	}
	
	private static final Logger logger = Logger.getLogger("GalleryView");
	
	List<ImageContainer> cols = new ArrayList<>();
	List<String> images = new ArrayList<>();

	public GalleryView(GalleryActivity galleryActivity) {
		this.presenter = galleryActivity;
		
		initWidget(uiBinder.createAndBindUi(this));
		
		images.add("8301.jpg");
		images.add("8303.jpg");
		images.add("8304.jpg");
		images.add("8307.jpg");
		images.add("8308.jpg");
		images.add("8310.jpg");
		images.add("8311.jpg");
		
		ImageContainer container0 = new ImageContainer();
		ImageContainer container1 = new ImageContainer();
		ImageContainer container2 = new ImageContainer();
		ImageContainer container3 = new ImageContainer();
		
		cols.add(container0);
		cols.add(container1);
		cols.add(container2);
		cols.add(container3);
		
		int count = Math.abs(Random.nextInt() % 50);
		
		int col = 0;
		int image = 0;
		while(count > 0) {
			
			cols.get(col).addImage(images.get(image).replaceAll("\\.", "_"), "/" + images.get(image));
			
			if(col == cols.size() -1) {
				col = 0;
			}
			else {
				col++;
			}
			
			if(image == images.size() -1) {
				image = 0;
			}
			else {
				image++;
			}
			
			count--;
		}
	}
	
	@Override
	protected void onLoad() {
		
		super.onLoad();
		
		HTMLPanel row = new HTMLPanel("");
		row.setStyleName("row");
		galleryViewPanel.add(row);
		
		for(ImageContainer iter : cols) {
			row.add(iter);
		}
		
		/*$(".galleryImage").mouseenter(new Function() {
			
			@Override
			public boolean f(Event e) {
				
				if (!e.getType().equals("mouseenter") || JsEventHelper.eventTriggeredByChilds(e)) {
                    return false;
                }
				
				e.preventDefault();
				e.stopPropagation();
				
				
				String id = $(e.getEventTarget()).prop("id");
				
				$("#starVoter"+id).css("display", "block");
				
				return false;
			};
		}
		);
		
		$(".galleryImage").mouseleave(new Function() {
			
			@Override
			public boolean f(Event e) {
				
				if (!e.getType().equals("mouseleave") || JsEventHelper.eventTriggeredByChilds(e)) {
                    return false;
                }
				
				e.preventDefault();
				e.stopPropagation();
				
				String id = $(e.getEventTarget()).prop("id");
				
				$("#starVoter"+id).css("display", "none");
				
				return false;
			};
		}
		);*/
		
		/*
		$(".galleryImage").hover(new Function() {
			@Override
			public boolean f(Event e, Object... arg) {
				
				e.preventDefault();
				e.stopPropagation();
				
				String id = $(e.getEventTarget()).prop("id");
				
				$("#starVoter"+id).css("display", "block");
				
				return false;
			}
		}, new Function() {
			@Override
			public boolean f(Event e, Object... arg) {
				
				e.preventDefault();
				e.stopPropagation();
				
				String id = $(e.getEventTarget()).prop("id");
				
				$("#starVoter"+id).css("display", "none");
				
				return false;
			}
		});*/
		
		//$(templates.thumbnailContainer("thumbnailRow0")).insertBefore($("#thumbnailsContainer"));
		
		//$("#thumbnailRow0").append(thumbnailTemplate.thumbnail("thumbnail0", "/8301.jpg"));
		//$("#thumbnailRow0").append(thumbnailTemplate.thumbnail("thumbnail1", "/8303.jpg"));
		//$("#thumbnailRow0").append(thumbnailTemplate.thumbnail("thumbnail2", "/8304.jpg"));
		//$("#thumbnailRow0").append(thumbnailTemplate.thumbnail("thumbnail3", "/8307.jpg"));
		
		/*$(voterTemplate.voter("voter0", starTemplate.star("one star"),
				starTemplate.star("two stars"),
				starTemplate.star("three stars"),
				starTemplate.star("four stars"),
				starTemplate.star("five stars"))
		).appendTo($("#thumbnail0"));*/
		
		//$(templates.thumbnailContainer("thumbnailRow1")).insertBefore($("#thumbnailsContainer"));
		
		//$("#thumbnailRow1").append(thumbnailTemplate.thumbnail("thumbnail4", "/8308.jpg"));
		//$("#thumbnailRow1").append(thumbnailTemplate.thumbnail("thumbnail5", "/8310.jpg"));
		//$("#thumbnailRow1").append(thumbnailTemplate.thumbnail("thumbnail6", "/8311.jpg"));
		
		
		/*
		$("#five_stars").click(new Function() {
			@Override
			public void f() {
				if(! $("#five_stars").hasClass("redStar")) {
					
					$("#one_star").addClass("redStar");
					$("#two_stars").addClass("redStar");
					$("#three_stars").addClass("redStar");
					$("#four_stars").addClass("redStar");
					$("#five_stars").addClass("redStar");
					
				}
				else {
					$("#one_star").removeClass("redStar");
					$("#two_stars").removeClass("redStar");
					$("#three_stars").removeClass("redStar");
					$("#four_stars").removeClass("redStar");
					$("#five_stars").removeClass("redStar");
				}
			}
		});
		
		$("#four_stars").click(new Function() {
			@Override
			public void f() {
				if(! $("#four_stars").hasClass("redStar")) {
					
					$("#one_star").addClass("redStar");
					$("#two_stars").addClass("redStar");
					$("#three_stars").addClass("redStar");
					$("#four_stars").addClass("redStar");
					
				}
				else {
					$("#five_stars").removeClass("redStar");
				}
			}
		});
		
		$("#three_stars").click(new Function() {
			@Override
			public void f() {
				if(! $("#three_stars").hasClass("redStar")) {
					
					$("#one_star").addClass("redStar");
					$("#two_stars").addClass("redStar");
					$("#three_stars").addClass("redStar");
					
				}
				else {
					
					$("#four_stars").removeClass("redStar");
					$("#five_stars").removeClass("redStar");
				}
			}
		});
		
		$("#two_stars").click(new Function() {
			@Override
			public void f() {
				if(! $("#two_stars").hasClass("redStar")) {
					
					$("#one_star").addClass("redStar");
					$("#two_stars").addClass("redStar");
					
				}
				else {
					$("#three_stars").removeClass("redStar");
					$("#four_stars").removeClass("redStar");
					$("#five_stars").removeClass("redStar");
				}
			}
		});
		
		$("#one_star").click(new Function() {
			@Override
			public void f() {
				if(! $("#one_star").hasClass("redStar")) {
					
					$("#one_star").addClass("redStar");
					
				}
				else {
					$("#two_stars").removeClass("redStar");
					$("#three_stars").removeClass("redStar");
					$("#four_stars").removeClass("redStar");
					$("#five_stars").removeClass("redStar");
				}
			}
		});
		*/
	}
}
