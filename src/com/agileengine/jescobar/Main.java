package com.agileengine.jescobar;

import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {


    public static void main(String[] args) throws IOException {
        // write your code here

        String baseHTMLFilePath;
        String compareHTMLFilePath;
        String elementId;

        if (args.length < 3) {
            System.out.println("******************* IMPORTANT *******************");
            System.out.println("You must provide the following args: 1) The Origin HTML File Path  2) The HTML file path to Compare  3) The element ID to find");
        } else {

            baseHTMLFilePath = args[0];
            compareHTMLFilePath = args[1];
            elementId = args[2];

            Main analyzer = new Main();
            Element elementFound = analyzer.findElementInFile(baseHTMLFilePath, compareHTMLFilePath, elementId);
            if (elementFound == null) {
                System.err.println("NO ELEMENT WAS FOUND WITH THE ENTERED ID");
            } else {
                System.out.println("Similar Element found PATH:" + Utils.getElementPath(elementFound));
            }
        }
    }

    /**
     * Finds the element in both Base HTML and comparable HTML file
     *
     * @param baseHTML    the base file
     * @param compareHTML the file to compare
     * @param elementId   to element to find
     * @return the element found
     * @throws IOException
     */
    private Element findElementInFile(String baseHTML, String compareHTML, String elementId) throws IOException {

        Element baseElement = Utils.findElementById(new File(baseHTML), elementId);
        Element similarElement = null;
        if (baseElement != null) {
            System.out.println("Element Base PATH:" + Utils.getElementPath(baseElement));
            Tag baseElementTag = baseElement.tag();
            List<Element> elements = Utils.getElementsByTag(new File(compareHTML), baseElementTag);
            String baseElementAttributes = Utils.stringifiedAttributesElement(baseElement);
            similarElement = Utils.getSimilarElement(baseElementAttributes, elements);

        }
        return similarElement;

    }


}
