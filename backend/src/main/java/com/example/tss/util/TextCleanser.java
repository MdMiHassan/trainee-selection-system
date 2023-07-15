package com.example.tss.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class TextCleanser {
    private final Pattern tagPattern = Pattern.compile("<[^>]*>");

    public String removeHtmlTags(String htmlText) {
        return tagPattern.matcher(htmlText).replaceAll("");
    }

    public String removeHtmlTagsJsoup(String htmlText) {
        return Jsoup.parse(htmlText).text();
    }
}
