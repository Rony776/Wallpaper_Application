package techdoctorbd.com.wallpapershd.Model;

public class WallpaperItem {

    private String ImageLink;
    private String CategoryID;

    public WallpaperItem() {
    }

    public WallpaperItem(String imageLink, String categoryID) {
        this.ImageLink = imageLink;
        this.CategoryID = categoryID;
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
}
