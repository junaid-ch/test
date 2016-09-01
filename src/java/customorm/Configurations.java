/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customorm;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author junaid.ahmad
 */
public class Configurations {
// JDBC driver name and database URL

    private static String JDBC_DRIVER;
    private static String DB_URL;

    //  Database credentials
    private static String USER_NAME;
    private static String PASSWORD;

    public static void configure() {
        try {
            URL url = Configurations.class.getResource("DBConfig.xml");
            File fXmlFile = new File(url.getPath());
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            JDBC_DRIVER = doc
                    .getElementsByTagName("JDBC_DRIVER")
                    .item(0)
                    .getTextContent().trim();
            DB_URL = doc
                    .getElementsByTagName("DB_URL")
                    .item(0)
                    .getTextContent().trim();
            USER_NAME = doc
                    .getElementsByTagName("USER_NAME")
                    .item(0)
                    .getTextContent().trim();
            PASSWORD = doc
                    .getElementsByTagName("PASSWORD")
                    .item(0)
                    .getTextContent().trim();

        } catch (SAXException | IOException | ParserConfigurationException ex) {
            Logger.getLogger(Configurations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the JDBC_DRIVER
     */
    public static String getJDBC_DRIVER() {
        return JDBC_DRIVER;
    }

    /**
     * @return the DB_URL
     */
    public static String getDB_URL() {
        return DB_URL;
    }

    /**
     * @return the USER_NAME
     */
    public static String getUSER_NAME() {
        return USER_NAME;
    }

    /**
     * @return the PASSWORD
     */
    public static String getPASSWORD() {
        return PASSWORD;
    }

}
