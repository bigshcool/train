package com.rl.train.generator.server;


import com.rl.train.generator.util.FreemarkerUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.HashMap;

public class ServerGenerator {
     static String servicePath = "train-[module]/src/main/java/com/rl/train/[module]/service/" ;
     
     static String prefix = "train-";
     static String pomPath = "generator/pom.xml";
    
     static {
         new File(servicePath).mkdirs();
     }

    public static void main(String[] args) throws Exception {
//        FreemarkerUtil.initConfig("test.ftl");
//        Map<String, Object> param = new HashMap<>();
//        param.put("domain", "Test");
//        FreemarkerUtil.generator(toPath + "Test.java", param);
        // 动态获取xml文件中service输出的路径
        String generatorPah = getGeneratorPah();
        String module = generatorPah.replace("src/main/resources/generator-config-", "").replace(".xml", "");
        System.out.println(module);
        servicePath = servicePath.replace("[module]", module);
        // 获取表名
        System.out.println(servicePath);
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read("generator/" + generatorPah);
        Node table = document.selectSingleNode("//table");
        Node tableName  = table.selectSingleNode("@tableName");
        Node domainObjectName = table.selectSingleNode("@domainObjectName");
        System.out.println(tableName.getText() + "/" + domainObjectName.getText());
        // 转化为模板文件中的字符
        String Domain = domainObjectName.getText();
        String domain = Domain.substring(0, 1).toLowerCase() + Domain.substring(1);
        String do_main =  domainObjectName.getText().replace("_","-");
        HashMap<String, Object> params = new HashMap<>();
        params.put("domain", domain);
        params.put("Domain", Domain);
        params.put("do_main", do_main);
        System.out.println(params);
        FreemarkerUtil.initConfig("service.ftl");
        FreemarkerUtil.generator(servicePath + Domain + "Service.java", params);
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
