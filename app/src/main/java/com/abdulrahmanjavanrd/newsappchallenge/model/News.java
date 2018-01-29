package com.abdulrahmanjavanrd.newsappchallenge.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Abdulrahman.A && Abdullah.aldobaie . on 28/01/2018.
 */

public class News
{
	
	// Article Name ..
	private String articleTitle;
	//Article Image .
	private String articleImage;
	// article summary
	private String articleSummary;
	// Article section
	private String articleSection;
	// Article Date ..
	private String articleDate;
	private String articleFormattedDate;
	// Article Publisher ..
	private String articlePublisher;
	// Article URL
	private String articleUrl;
	
	// Constructor .
	public News(String articleTitle, String articleImage, String articleSummary, String articleSection, String articleDate, String articlePublisher, String articleUrl)
	{
		this.articleTitle = articleTitle;
		this.articleImage = articleImage;
		this.articleSummary = articleSummary;
		this.articleSection = articleSection;
		this.articleDate = articleDate;
		this.articlePublisher = articlePublisher;
		this.articleFormattedDate = formatDate(articlePublisher);
		this.articleUrl = articleUrl;
	}
	
	
	// Getter method .
	
	public String getArticleTitle()
	{
		return articleTitle;
	}
	
	public String getArticleImage()
	{
		return articleImage;
	}
	
	public String getArticleSummary()
	{
		return articleSummary;
	}
	
	public String getArticleSection()
	{
		return articleSection;
	}
	
	public String getArticleDate()
	{
		return articleDate;
	}
	
	public String getArticleFormattedDate()
	{
		return articleFormattedDate;
	}
	
	public String getArticlePublisher()
	{
		return articlePublisher;
	}
	
	public String getArticleUrl()
	{
		return articleUrl;
	}
	
	private String formatDate(String publicationDate)
	{
		DateFormat desiredDateFormat = new SimpleDateFormat("LLL dd, yyyy");
		DateFormat currentDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
		Date date = null;
		try
		{
			date = currentDateFormat.parse(articleDate);
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		if (date != null)
		{
			return desiredDateFormat.format(date);
		}
		// if not able to format return input
		return publicationDate;
	}
}
