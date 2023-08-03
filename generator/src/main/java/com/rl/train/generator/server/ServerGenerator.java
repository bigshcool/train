package com.rl.train.generator.server;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.HashMap;

public class ServerGenerator {
     static String toPath = "generator/src/main/java/com/rl/train/generator/test/" ;
     static String pomPath = "generator/pom.xml";
    
     static {
         new File(toPath).mkdirs();
     }

    public static void main(String[] args) throws Exception {
//        FreemarkerUtil.initConfig("test.ftl");
//        Map<String, Object> param = new HashMap<>();
//        param.put("domain", "Test");
//        FreemarkerUtil.generator(toPath + "Test.java", param);
        String generatorPah = getGeneratorPah();
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read("generator/" + generatorPah);
        Node table = document.selectSingleNode("//table");
        Node tableName  = table.selectSingleNode("@tableName");
        Node domainObjectName = table.selectSingleNode("@domainObjectName");
        System.out.println(tableName.getText() + "/" + domainObjectName.getText());
    
    }
    
    private static String getGeneratorPah() throws DocumentException {
        SAXReader saxReader = new SAXReader();
        HashMap<String, String> map = new HashMap<>();
        map.put("pom", "http://maven.apache.org/POM/4.0.0");
        saxReader.getDocumentFactory().setXPathNamespaceURIs(map);
        Document document = saxReader.read(pomPath);
        Node node = document.selectSingleNode("//pom:configurationFile");
        return node.getText();
    }
    
    
}
