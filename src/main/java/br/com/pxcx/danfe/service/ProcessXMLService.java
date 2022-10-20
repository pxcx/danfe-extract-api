package br.com.pxcx.danfe.service;

import br.com.pxcx.danfe.error.InvalidUsageException;
import br.com.pxcx.danfe.type.DanfeExtractItem;
import br.com.pxcx.danfe.error.XMLProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProcessXMLService {
    private Document xml;
    private DocumentBuilderFactory xmlHandlerFactory;
    private DocumentBuilder xmlHandler;
    private Logger logger = LoggerFactory.getLogger(ProcessXMLService.class);

    private List<DanfeExtractItem> output;

    public void setXMLFile(File xmlFile) {
        xmlHandlerFactory = DocumentBuilderFactory.newInstance();
        try {
            // process XML securely, avoid attacks like XML External Entities (XXE)
            xmlHandlerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            xmlHandler = xmlHandlerFactory.newDocumentBuilder();
            xml = xmlHandler.parse(xmlFile);
            xml.getDocumentElement().normalize();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new XMLProcessingException("XML Processing failed: " + e.getMessage(), e);
        }
    }

    public void validate() {
        if (xml == null) {
            throw new InvalidUsageException("XML File is mandatory for XML Processing step.");
        }
    }

    public void execute() {
        validate();
        output = new ArrayList<>();
        NodeList detailsList = xml.getElementsByTagName("det");
        for (int i = 0; i < detailsList.getLength(); i++) {
            Node detailsNode = detailsList.item(i);
            if (detailsNode.getNodeType() == Node.ELEMENT_NODE) {
                Element detailsElement = (Element) detailsNode;
                Element productElement = (Element) detailsElement.getElementsByTagName("prod").item(0);
                Element taxElement = (Element) detailsElement.getElementsByTagName("imposto").item(0);
                output.add(mapElementsToExtractItem(productElement, taxElement));
            }
        }
    }

    private DanfeExtractItem mapElementsToExtractItem(Element productElement, Element taxElement) {
        DanfeExtractItem extractedItem = new DanfeExtractItem();
        Element icmsDetailsElement = getICMSDetailsElement(taxElement);

        extractedItem.setDescription(getValueFromProductElement(productElement, "xProd"));
        extractedItem.setNcm(getValueFromProductElement(productElement, "NCM"));
        extractedItem.setCest(getValueFromProductElement(productElement, "CEST"));
        extractedItem.setCfop(getValueFromProductElement(productElement, "CFOP"));

        extractedItem.setCst(getCst(taxElement));
        extractedItem.setOrigem(getOrigFromICMS(icmsDetailsElement));

        extractedItem.setAliqIcms(getPercentValueForICMS(icmsDetailsElement));
        extractedItem.setAliqPis(getPercentValueForPIS(taxElement));
        extractedItem.setAliqCofins(getPercentValueForCOFINS(taxElement));

        return extractedItem;
    }

    private String getValueFromProductElement(Element product, String key) {
        if (product.getElementsByTagName(key).getLength() > 0) {
            return product.getElementsByTagName(key).item(0).getTextContent();
        }
        return null;
    }

    private Element getICMSDetailsElement(Element taxElement) {
        Element icmsElement = (Element) taxElement.getElementsByTagName("ICMS").item(0);
        Element icmsDetailsElement = (Element) icmsElement.getFirstChild();
        return icmsDetailsElement;
    }

    private String getPercentValueForICMS(Element icmsDetailsElement) {
        if (icmsDetailsElement.getElementsByTagName("pICMS").getLength() > 0) {
            return icmsDetailsElement.getElementsByTagName("pICMS").item(0).getTextContent();
        }
        return null;
    }

    private String getOrigFromICMS(Element icmsDetailsElement) {
        return icmsDetailsElement.getElementsByTagName("orig").item(0).getTextContent();
    }

    private String getCst(Element taxElement) {
        NodeList cstInICMS = getICMSDetailsElement(taxElement).getElementsByTagName("CST");
        if (cstInICMS.getLength() > 0) {
            return cstInICMS.item(0).getTextContent();
        } else {
            Element pisElement = (Element) taxElement.getElementsByTagName("PIS").item(0);
            Element pisDetailsElement = (Element) pisElement.getFirstChild();
            return pisDetailsElement.getElementsByTagName("CST").item(0).getTextContent();
        }
    }

    private String getPercentValueForPIS(Element taxElement) {
        Element pisElement = (Element) taxElement.getElementsByTagName("PIS").item(0);
        if (pisElement.getElementsByTagName("PISAliq").getLength() > 0) {
            Element pisDetailsElement = (Element) pisElement.getElementsByTagName("PISAliq").item(0);
            return pisDetailsElement.getElementsByTagName("pPIS").item(0).getTextContent();
        }
        if (pisElement.getElementsByTagName("PISOutr").getLength() > 0) {
            Element pisDetailsElement = (Element) pisElement.getElementsByTagName("PISOutr").item(0);
            if (pisDetailsElement.getElementsByTagName("pPIS").getLength() > 0) {
                return pisDetailsElement.getElementsByTagName("pPIS").item(0).getTextContent();
            }
            return pisDetailsElement.getElementsByTagName("vAliqProd").item(0).getTextContent();
        }
        Boolean isNT = pisElement.getElementsByTagName("PISNT").getLength() > 0;
        return isNT ? "NT" : null;
    }

    private String getPercentValueForCOFINS(Element taxElement) {
        Element cofinsElement = (Element) taxElement.getElementsByTagName("COFINS").item(0);
        if (cofinsElement.getElementsByTagName("COFINSAliq").getLength() > 0) {
            Element cofinsDetailsElement = (Element) cofinsElement.getElementsByTagName("COFINSAliq").item(0);
            return cofinsDetailsElement.getElementsByTagName("pCOFINS").item(0).getTextContent();
        }
        if (cofinsElement.getElementsByTagName("COFINSOutr").getLength() > 0) {
            Element cofinsDetailsElement = (Element) cofinsElement.getElementsByTagName("COFINSOutr").item(0);
            if (cofinsDetailsElement.getElementsByTagName("pCOFINS").getLength() > 0) {
                return cofinsDetailsElement.getElementsByTagName("pCOFINS").item(0).getTextContent();
            }
            return cofinsDetailsElement.getElementsByTagName("vAliqProd").item(0).getTextContent();
        }
        Boolean isNT = cofinsElement.getElementsByTagName("COFINSNT").getLength() > 0;
        return isNT ? "NT" : null;
    }

    public List<DanfeExtractItem> getOutput() {
        return output;
    }
}
