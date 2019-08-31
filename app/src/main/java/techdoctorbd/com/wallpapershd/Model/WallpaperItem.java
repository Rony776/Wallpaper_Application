package techdoctorbd.com.wallpapershd.Model;

public class WallpaperItem {

    private String ImageLink;
    private String CategoryID;
    private String Description;

    public WallpaperItem() {
    }

    public WallpaperItem(String imageLink, String categoryID, String description) {
        ImageLink = imageLink;
        CategoryID = categoryID;
        Description = description;
    }

    public String getImageLink() {
        return ImageLink;
    }

    public void setImageLink(String imageLink) {
        ImageLink = imageLink;
    }

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
