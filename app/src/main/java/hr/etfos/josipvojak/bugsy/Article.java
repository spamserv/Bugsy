package hr.etfos.josipvojak.bugsy;

/**
 * Created by jvojak on 25.4.2016..
 */
public class Article {
    private String mTitle, mLink, mPicture_URL, mDescription, mCategory;

    public Article() {
        this.mTitle = "N/A";
        this.mLink = "https://www.google.com";
        this.mPicture_URL = "N/A";
        this.mDescription = "N/A";
        this.mCategory = "N/A";
    }

    public Article(String mTitle, String mLink, String mPicture_URL, String description, String category) {
        this.mTitle = mTitle;
        this.mLink = mLink;
        this.mPicture_URL = mPicture_URL;
        this.mDescription = description;
        this.mCategory = category;
    }

    public String getMTitle() {
        return mTitle;
    }

    public void setMTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getMLink() {
        return mLink;
    }

    public void setMLink(String mLink) {
        this.mLink = mLink;
    }

    public String getMPicture_URL() {
        return mPicture_URL;
    }

    public void setMPicture_URL(String mPicture_URL) {
        this.mPicture_URL = mPicture_URL;
    }

    public String getMDescription() {
        return mDescription;
    }

    public void setMDescription(String description) {
        this.mDescription = description;
    }

    public String getMCategory() {
        return mCategory;
    }

    public void setMCategory(String category) {
        this.mCategory = category;
    }
    public String toString() {
        return this.mTitle + "\n" + this.mDescription + "\n" + this.mCategory;
    }
}
