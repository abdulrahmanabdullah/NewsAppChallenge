package com.abdulrahmanjavanrd.newsappchallenge.model;

/**
 * @author  Abdulrahman.A && Abdullah.aldobaie . on 28/01/2018.
 */

public class News {

    // Article Name ..
    private String articleTitle;
    //Article Image .
    private String articleImage ;
    // article summary
    private String articleSummary ;
    // Article section
    private String articleSection ;
    // Article Date ..
    private String articleDate ;
    // Article Publisher ..
    private String articlePublisher ;


    // web uri ..
    private String articleUri ;

    // Constructor .
    public News(String articleTitle, String articleImage, String articleSummary, String articleSection, String articleDate, String articlePublisher,String uri) {
        this.articleTitle = articleTitle;
        this.articleImage = articleImage;
        this.articleSummary = articleSummary;
        this.articleSection = articleSection;
        this.articleDate = articleDate;
        this.articlePublisher = articlePublisher;
        this.articleUri = uri ;
    }


    // Getter method .

    public String getArticleTitle() {
        return articleTitle;
    }

    public String getArticleImage() {
        return articleImage;
    }

    public String getArticleSummary() {
        return articleSummary;
    }

    public String getArticleSection() {
        return articleSection;
    }

    public String getArticleDate() {
        return articleDate;
    }

    public String getArticlePublisher() {
        return articlePublisher;
    }

    public String getArticleUri() {
        return articleUri;
    }
}
