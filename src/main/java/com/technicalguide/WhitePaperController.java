package com.technicalguide;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;

@RestController
public class WhitePaperController {

    @Autowired
    private WhitePaperService whitePaperService;

    @PostMapping("/upload")
    public String uploadAndSummarize(
            @RequestParam Long campaignId,
            @RequestParam String campaignName,
            @RequestParam String uniqueId,
            @RequestParam String whitepaperHeading,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            // Check if whitepaper heading exceeds 10 words
            if (whitepaperHeading.split("\\s+").length > 10) {
                return "Error: Whitepaper heading should not exceed 10 words";
            }

            // Get input stream from uploaded file
            InputStream pdfStream = file.getInputStream();

            // Define file path for saving the uploaded file
            String filePath = "C:\\Data-April\\" + file.getOriginalFilename(); // Adjust this path

            // Invoke WhitePaperService to summarize and save the uploaded file
            whitePaperService.summarizeAndSave(campaignId, campaignName, uniqueId, whitepaperHeading, pdfStream, filePath);

            // Return success message
            return "Summarization successful";
        } catch (IOException e) {
            // Return error message if IOException occurs
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}
