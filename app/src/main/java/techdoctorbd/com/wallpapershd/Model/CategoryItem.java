package techdoctorbd.com.wallpapershd.Model;

public class CategoryItem {

    private String name;
    private String ImageLink;

    public CategoryItem() {
    }

    public CategoryItem(String name, String imageLink) {
        this.name = name;
        this.ImageLink = imageLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageLink() {
        return ImageLink;
    }

    public void setImageLink(String imageLink) {
        ImageLink = imageLink;
    }

}
