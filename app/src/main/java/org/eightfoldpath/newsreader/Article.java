package org.eightfoldpath.newsreader;

public class Article {

    private String title = null;
    private String infoUrl = null;
    private String section = null;

    public Article(String title, String infoUrl, String section) {
        this.title = title;
        this.infoUrl = infoUrl;
        this.section = section;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfoUrl() {
        return infoUrl;
    }

    public void setInfoUrl(String infoUrl) {
        this.infoUrl = infoUrl;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}
