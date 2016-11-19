package project.ks.com.posts.bean;

import java.util.List;

public class User extends BaseBean{

	public User() {
	}

	public User(String firstName, String lastName, String email, String password, String profile, List<Tag> tags, String createdDate) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.profile = profile;
		this.tags = tags;
		this.createdDate = createdDate;
	}

	private String firstName;
	
	private String lastName;
	
	private String profile;
	
	private String email;
	
	private String password;
	
	private List<Tag> tags;
	
	private String createdDate;

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getProfile() {
		return profile;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getCreatedDate() {
		return createdDate;
	}
	
	public List<Tag> getTags() {
		return tags;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

}
