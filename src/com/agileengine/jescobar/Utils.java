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

/**
 * Utility class with all necessary methods to use HTML Analyzer
 */
final class Utils {
    private Utils() {
    }

    /**
     * Gets the elementsID into the HTML File
     * @param htmlFile the html file to read
     * @param targetElementId the element to find
     * @return the Element object
     * @throws IOException
     */
    static Element findElementById(File htmlFile, String targetElementId) throws IOException {
        Document doc = getDocument(htmlFile);
        return doc.getElementById(targetElementId);

    }

    /**
     * Obtains all the element attributes in a String format.
      * @param element the element to convert.
     * @return a String with all attributes of an element
     */
    static String stringifiedAttributesElement(Element element) {
        return element.attributes().asList().stream()
                .map(attr -> attr.getKey() + " = " + attr.getValue())
                .collect(Collectors.joining(", "));
    }

    /**
     * Gets the path into the HTML of the input element.
     * @param element the desired element to print the path
     * @return a String with the entire path of the element
     */
    static String getElementPath(Element element) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = element.parents().size() - 1; i >= 0; i--) {
            Element parent = element.parents().get(i);
            stringBuilder.append(parent.tag()).append(">");
        }
        stringBuilder.append(element);
        return stringBuilder.toString();
    }

    /**
     * Obtains a List of Elements using a Tag element criteria.
     * @param html the html to explore
     * @param tag the tag to search.
     * @return a list of elements using the same thag
     * @throws IOException
     */
    static List<Element> getElementsByTag(File html, Tag tag) throws IOException {
        Document document = getDocument(html);
        return document.getElementsByTag(tag.getName());
    }

    /**
     * Return a Document object to perform HTML elements actions.
     * @param htmlFile the html file
     * @return a Document object
     * @throws IOException
     */
    private static Document getDocument(File htmlFile) throws IOException {
        return Jsoup.parse(htmlFile, StandardCharsets.UTF_8.name(), htmlFile.getAbsolutePath());
    }

    /**
     * Uses a Normalized Levenshtein algorithm to calculate String distance to detect similar elements
     * @param source the String with the element we want to compare
     * @param elementsByTag the list of elements of the same type to compare similar Strings
     * @return a Element close related with the source element
     */
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
