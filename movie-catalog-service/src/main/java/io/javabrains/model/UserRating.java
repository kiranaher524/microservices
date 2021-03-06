package io.javabrains.model;

import java.util.List;

public class UserRating {
	
	private String userId;
	private List<Rating> ratings;

	public UserRating() {
		super();
	}

	public UserRating(List<Rating> ratings) {
		super();
		this.ratings = ratings;
	}

	public List<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "UserRating [userId=" + userId + ", ratings=" + ratings + "]";
	}
}
