package com.rl.train.generator.server;


import com.rl.train.generator.util.DbUtil;
import com.rl.train.generator.util.Field;
import com.rl.train.generator.util.FreemarkerUtil;
import freemarker.template.TemplateException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ServerGenerator {
     static String toPath = "train-[module]/src/main/java/com/rl/train/[module]/[Package]/" ;
     static String vuePath = "web/src/views/main/";
     static String pomPath = "generator/pom.xml";
    
    private static boolean readOnly = false;
    
    public static void main(String[] args) throws Exception {
//        FreemarkerUtil.initConfig("test.ftl");
//        Map<String, Object> param = new HashMap<>();
//        param.put("domain", "Test");
//        FreemarkerUtil.generator(toPath + "Test.java", param);
        // 动态获取xml文件中service输出的路径
        HashMap<String, Object> params = initParams();
        gen(params,"service", "service");
        gen(params,"controller", "controller");
        gen(params, "req","saveReq");
        gen(params, "req", "queryReq");
        gen(params, "resp", "queryResp");
        genVue(params.get("do_main"),params);
    }
    
    private static void genVue(Object do_main, HashMap<String, Object> params) throws IOException, TemplateException {
        FreemarkerUtil.initConfig("vue.ftl");
        new File(vuePath).mkdirs();
        String fileName = vuePath + do_main + ".vue";
        System.out.println("开始生成: " + fileName);
        FreemarkerUtil.generator(fileName, params);
    }
    
    private static void gen(HashMap<String, Object> params, String packageName ,String target) throws IOException, TemplateException {
        FreemarkerUtil.initConfig(target  + ".ftl");
        String classPackageTail = target.substring(0, 1).toUpperCase() +  target.substring(1);
        String baseFolder = toPath.replace("[Package]", packageName);
        System.out.println(baseFolder);
        new File(baseFolder).mkdirs();
        FreemarkerUtil.generator(baseFolder + params.get("Domain") + classPackageTail +".java", params);
    }
    
    private static HashMap<String, Object> initParams() throws Exception {
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
        params.put("module", module);
        params.put("readOnly", readOnly);
        // 读取数据源
        DbUtil.url = document.selectSingleNode("//@connectionURL").getText();
        DbUtil.user = document.selectSingleNode("//@userId").getText();
        DbUtil.password = document.selectSingleNode("//@password").getText();
        // 获取表的中文
        String tableNameCn = DbUtil.getTableComment(tableName.getText());
        // 获取表的列信息
        List<Field> fieldList = DbUtil.getColumnByTableName(tableName.getText());
        Set<String> javaTypes = getJavaTypes(fieldList);
        params.put("tableNameCn", tableNameCn);
        params.put("fieldList", fieldList);
        params.put("typeSet", javaTypes);
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
    
    /**
     * 获取所有的java类型，使用Set去重
     * */
    private static Set<String> getJavaTypes(List<Field> fieldList){
        Set<String> set = new HashSet<>();
        Iterator<Field> iterator = fieldList.iterator();
        while (iterator.hasNext()){
            Field field = iterator.next();
            set.add(field.getJavaType());
        }
        return set;
    }
    
    
    
}
