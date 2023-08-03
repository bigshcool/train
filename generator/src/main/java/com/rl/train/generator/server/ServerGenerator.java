package com.rl.train.generator.server;


import com.rl.train.generator.util.FreemarkerUtil;
import freemarker.template.TemplateException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ServerGenerator {
     static String toPath = "train-[module]/src/main/java/com/rl/train/[module]/[Package]/" ;
     
     static String pomPath = "generator/pom.xml";
     
    public static void main(String[] args) throws Exception {
//        FreemarkerUtil.initConfig("test.ftl");
//        Map<String, Object> param = new HashMap<>();
//        param.put("domain", "Test");
//        FreemarkerUtil.generator(toPath + "Test.java", param);
        // 动态获取xml文件中service输出的路径
        HashMap<String, Object> params = initParams();
        gen(params,"service");
        gen(params,"controller");
    }
    
    private static void gen(HashMap<String, Object> params, String target) throws IOException, TemplateException {
        FreemarkerUtil.initConfig(target  + ".ftl");
        String classPackageTail = target.substring(0, 1).toUpperCase() +  target.substring(1);
        String baseFolder = toPath.replace("[Package]", target);
        new File(baseFolder).mkdirs();
        FreemarkerUtil.generator(baseFolder + params.get("Domain") + classPackageTail +".java", params);
    }
    
    private static HashMap<String, Object> initParams() throws DocumentException {
        String generatorPah = getGeneratorPah();
        String module = generatorPah.replace("src/main/resources/generator-config-", "").replace(".xml", "");
        System.out.println(module);
        toPath = toPath.replace("[module]", module);
        // 获取表名
        System.out.println(toPath);
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read("generator/" + generatorPah);
        Node table = document.selectSingleNode("//table");
        Node tableName  = table.selectSingleNode("@tableName");
        Node domainObjectName = table.selectSingleNode("@domainObjectName");
        System.out.println(tableName.getText() + "/" + domainObjectName.getText());
        // 转化为模板文件中的字符
        String Domain = domainObjectName.getText();
        String domain = Domain.substring(0, 1).toLowerCase() + Domain.substring(1);
        String do_main =  tableName.getText().replace("_","-");
        HashMap<String, Object> params = new HashMap<>();
        params.put("domain", domain);
        params.put("Domain", Domain);
        params.put("do_main", do_main);
        System.out.println(params);
        return params;
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
