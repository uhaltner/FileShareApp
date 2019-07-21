package com.group2.FileShare;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.group2.FileShare.Authentication.AuthenticationSessionManager;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.List;
import java.util.ArrayList;

public class PublicAccess {

    private static PublicAccess uniqueInstance = null;
    private  List<String> publicList;
    private Logger logger;


    public static PublicAccess instance()
    {
        if (null == uniqueInstance)
        {
            uniqueInstance = new PublicAccess();
            uniqueInstance.setPublicList();

        }
        return uniqueInstance;
    }

    PublicAccess() {
        publicList = new ArrayList<String>();
        logger = LogManager.getLogger(AuthenticationSessionManager.class);
    }

    private void setPublicList() {

        try
        {
            String fileName = "public_acc.xml";
            //ClassLoader classLoader = getClass().getClassLoader();
            File file = new ClassPathResource(fileName).getFile(); //new File(classLoader.getResource(fileName).getFile());
            
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            DocumentBuilder dBuilder = factory.newDocumentBuilder();
            Document document = dBuilder.parse(file);
            document.getDocumentElement().normalize();

            NodeList nList = document.getElementsByTagName("label");
            if(nList != null) {
                for (int temp = 0; temp < nList.getLength(); temp++) {
                    Node node = nList.item(temp);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) node;
                        publicList.add(eElement.getAttribute("key"));
                    }
                }
            } else {
            	 logger.log(Level.ERROR, "File is empty");
            }
        } catch (Exception e) {
            logger.log(Level.ERROR, "Failed to load file: ", e.getMessage());
            
        }
    }


    public boolean isPublic(String reqUri) {
        boolean isPublic = false;
        for (int i = 0; i < publicList.size(); i++) {
            if(reqUri.contains(publicList.get(i))) {
                isPublic = true;
                break;
            }
        }
        return isPublic;
    }
}
