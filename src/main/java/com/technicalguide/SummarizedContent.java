package com.technicalguide;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class SummarizedContent {

	public SummarizedContent() {
		
	}
	
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "uniqueId")
	    private String uniqueId;

	    public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public SummarizedContent(String uniqueId, Long campaignId, String campaignName, String summarizedContent,
			String whitepaperHeading, String filePath) {
		super();
		this.uniqueId = uniqueId;
		this.campaignId = campaignId;
		this.campaignName = campaignName;
		this.summarizedContent = summarizedContent;
		this.whitepaperHeading = whitepaperHeading;
		this.filePath = filePath;
	}

	public Long getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(Long campaignId) {
		this.campaignId = campaignId;
	}

	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	public String getSummarizedContent() {
		return summarizedContent;
	}

	public void setSummarizedContent(String summarizedContent) {
		this.summarizedContent = summarizedContent;
	}

	public String getWhitepaperHeading() {
		return whitepaperHeading;
	}

	public void setWhitepaperHeading(String whitepaperHeading) {
		this.whitepaperHeading = whitepaperHeading;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

		@Column(name = "campaignId")
	    private Long campaignId;

	    @Column(name = "campaignName")
	    private String campaignName;

	    @Column(name = "summarizedContent")
	    private String summarizedContent;

	    @Column(name = "whitepaperHeading")
	    private String whitepaperHeading;

	    @Column(name = "filePath")
	    private String filePath;
	
}
