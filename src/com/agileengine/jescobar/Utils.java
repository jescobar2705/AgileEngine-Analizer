package com.agileengine.jescobar;

import info.debatty.java.stringsimilarity.NormalizedLevenshtein;
import info.debatty.java.stringsimilarity.interfaces.NormalizedStringDistance;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

final class Utils {
    private Utils() {
    }

    static Element findElementById(File htmlFile, String targetElementId) throws IOException {
        Document doc = getDocument(htmlFile);
        return doc.getElementById(targetElementId);

    }

    static String stringifiedAttributesElement(Element element) {
        return element.attributes().asList().stream()
                .map(attr -> attr.getKey() + " = " + attr.getValue())
                .collect(Collectors.joining(", "));
    }

    static String getElementPath(Element element) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = element.parents().size() - 1; i >= 0; i--) {
            Element parent = element.parents().get(i);
            stringBuilder.append(parent.tag()).append(">");
        }
        stringBuilder.append(element);
        return stringBuilder.toString();
    }

    static List<Element> getElementsByTag(File html, Tag tag) throws IOException {
        Document document = getDocument(html);
        return document.getElementsByTag(tag.getName());
    }

    private static Document getDocument(File htmlFile) throws IOException {
        return Jsoup.parse(htmlFile, StandardCharsets.UTF_8.name(), htmlFile.getAbsolutePath());
    }


    static Element getSimilarElement(String source, List<Element> elementsByTag) {
        NormalizedStringDistance detector = new NormalizedLevenshtein();

        double acceptableDistance = 1.0;
        Element similarElement = null;
        for (Element element : elementsByTag) {
            double compareDistance = detector.distance(source, stringifiedAttributesElement(element));
            if (compareDistance < acceptableDistance) {
                acceptableDistance = compareDistance;
                similarElement = element;
            }
        }
        return similarElement;

    }
}
