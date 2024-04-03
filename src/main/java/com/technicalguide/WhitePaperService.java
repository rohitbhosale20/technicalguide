package com.technicalguide;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.IOException;
import java.io.InputStream;

@Service
public class WhitePaperService {

    @Autowired
    private SummarizedContentRepository summarizedContentRepository;

    public void summarizeAndSave(Long campaignId, String campaignName, String uniqueId, String whitepaperHeading2, InputStream pdfStream, String filePath) throws IOException {
        if (summarizedContentRepository.existsByUniqueId(uniqueId)) {
            throw new IllegalArgumentException("Unique ID already exists");
        }

        PDDocument document = PDDocument.load(pdfStream);
        PDFTextStripper stripper = new PDFTextStripper();
        String text = stripper.getText(document);
        document.close();

        // Extract whitepaper heading
        String whitepaperHeading = extractWhitepaperHeading(text);

        // Perform summarization using the provided logic
        String summarizedText = summarizeText(text);

        SummarizedContent summarizedContent = new SummarizedContent();
        summarizedContent.setCampaignId(campaignId);
        summarizedContent.setCampaignName(campaignName);
        summarizedContent.setUniqueId(uniqueId);
        summarizedContent.setWhitepaperHeading(whitepaperHeading);
        summarizedContent.setSummarizedContent(summarizedText);
        summarizedContent.setFilePath(filePath);

        summarizedContentRepository.save(summarizedContent);
    }

    private String summarizeText(String text) {
        // Split the text into paragraphs
        String[] paragraphs = text.split("\n\n|\r\n\r\n");

        // Initialize summary
        StringBuilder summary = new StringBuilder();

        // Limit to the first 5 paragraphs
        int numParagraphs = Math.min(5, paragraphs.length);

        // Loop through the first 5 paragraphs and extract important sentences
        for (int i = 0; i < numParagraphs; i++) {
            // Remove unnecessary whitespace
            String paragraph = paragraphs[i].trim();

            // Check if the paragraph is not empty
            if (!paragraph.isEmpty()) {
                // Tokenize the paragraph into sentences
                String[] sentences = paragraph.split("(?<!\\w\\.\\w.)(?<![A-Z][a-z]\\.)(?<=\\.|\\?)\\s");

                // Choose the most relevant sentence from each paragraph and append to the summary
                if (sentences.length > 0) {
                    summary.append(sentences[0]).append(". "); // Append the first sentence

                    // Check if the summarized text exceeds 235 words
                    if (summary.toString().split("\\s+").length > 235) {
                        // Trim the summary to 235 words
                        String[] words = summary.toString().split("\\s+");
                        StringBuilder trimmedSummary = new StringBuilder();
                        int wordCount = 0;
                        for (String word : words) {
                            if (wordCount >= 235) {
                                break;
                            }
                            trimmedSummary.append(word).append(" ");
                            wordCount++;
                        }
                        return trimmedSummary.toString().trim();
                    }
                }
            }
        }

        // Return the summarized text
        return summary.toString();
    }

    private String extractWhitepaperHeading(String text) {
        // Split the text into paragraphs
        String[] paragraphs = text.split("\n\n|\r\n\r\n");

        // Iterate through the paragraphs
        for (String paragraph : paragraphs) {
            // Remove unnecessary whitespace
            paragraph = paragraph.trim();

            // Check if the paragraph is not empty and contains significant text
            if (!paragraph.isEmpty() && paragraph.length() > 10) {
                // Assuming the heading is in the first significant paragraph
                return paragraph;
            }
        }

        // If no suitable heading found, return an empty string or null
        return "";
    }
}
