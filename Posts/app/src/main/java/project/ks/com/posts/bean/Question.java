package project.ks.com.posts.bean;

import java.util.List;

public class Question extends BaseBean {

	private String title;
	
	private String detail;
	
	private User createdBy;
	
	private Long views;
	
	private Long answers;
	
	private String craetedDate;
	
	private List<Tag> tags;
	
	
	public String getTitle() {
		return title;
	}

	public String getDetail() {
		return detail;
	}

	public User getCreatedBy() {
		return createdBy;
	}
	
	public Long getViews() {
		return views;
	}

	public Long getAnswers() {
		return answers;
	}

	public String getCraetedDate() {
		return craetedDate;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
	
	public void setViews(Long views) {
		this.views = views;
	}

	public void setAnswers(Long answers) {
		this.answers = answers;
	}

	public void setCraetedDate(String craetedDate) {
		this.craetedDate = craetedDate;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

}
