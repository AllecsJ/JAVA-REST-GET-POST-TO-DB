package model;

//This class represents the Picture model with properties large, medium, and thumbnail
public class Picture {
 public String getLarge() {
		return large;
	}

	public void setLarge(String large) {
		this.large = large;
	}

	public String getMedium() {
		return medium;
	}

	public void setMedium(String medium) {
		this.medium = medium;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

private String large;
 private String medium;
 private String thumbnail;

 // Constructor to initialize a Picture object
 public Picture(String large, String medium, String thumbnail) {
     this.large = large;
     this.medium = medium;
     this.thumbnail = thumbnail;
 }

 // Getters and setters for each field...
}

