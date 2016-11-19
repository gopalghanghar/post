package project.ks.com.posts.bean;

public class Tag extends BaseBean {

	public Tag() {
	}

	public Tag(String tagName) {
		name = tagName;
	}

	public Tag(SelectedTag selectedTag) {
		this.setId(selectedTag.getId());
		this.name = selectedTag.getName();
		this.description = selectedTag.getDescription();
	}

	private String name;
	
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

}
