package project.ks.com.posts.bean;

/**
 * Created by Gopal on 05-Nov-16.
 */
public class SelectedTag extends Tag {

    public SelectedTag() {
    }

    public SelectedTag(Tag tag, Boolean isChecked) {

        this.setName(tag.getName());
        this.setId(tag.getId());
        this.setDescription(tag.getDescription());
        this.isChecked = isChecked;
    }

    public SelectedTag(Tag tag) {

        this.setName(tag.getName());
        this.setId(tag.getId());
        this.setDescription(tag.getDescription());
        this.isChecked = Boolean.FALSE;
    }

    Boolean isChecked ;

    public Boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Boolean checked) {
        isChecked = checked;
    }
}
