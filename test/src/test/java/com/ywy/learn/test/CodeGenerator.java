package com.ywy.learn.test;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ve
 * @date 2020/1/2 14:57
 */
public class CodeGenerator {

    public static final String ENCODING = "UTF-8";

    private String targetPath = "src/test/java/com/ywy/learn/test/result/";
    private String resourcePath = "src/test/resources/";

    public static void write(File source, File target, Map<String, String> params) throws TemplateException, IOException {
        getFreeMarkerConfig().setDirectoryForTemplateLoading(source.getParentFile());
        Template temp = getFreeMarkerConfig().getTemplate(source.getName());
        target.getParentFile().mkdirs();
        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(target), "UTF-8"));
        temp.process(params, writer);
        writer.close();
    }

    public static Configuration getFreeMarkerConfig() {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
        cfg.setDefaultEncoding(ENCODING);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        return cfg;
    }

    /**
     * 测试生成article相关
     */
    @Test
    public void generateArt() {
        // 所属模块
        String module = "user";
        // 所需功能,如文章Article
        List<String> aggregates = new ArrayList<String>() {{
            add("Article");
        }};
        // 在user模块下生成aggregates
        aggregates.forEach(aggregate -> {
            try {
                generate(module, aggregate);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void generate(String module, String aggregate) throws IOException, TemplateException {
        Map map = new HashMap() {{
            put("module", module);
            put("aggregate", aggregate);
        }};
        File root = new File(targetPath, module);
        // 聚合根
        File aggregateRoot = new File(root, module);
        aggregateRoot.mkdirs();
        write(new File(resourcePath, ".ftl"), new File(aggregateRoot, aggregate + ".java"), map);

        // 查询侧
        File apiRoot = new File(root, module + "-api");
        apiRoot.mkdirs();


        // web
        File webRoot = new File(root, module + "-api");
        webRoot.mkdirs();
    }
}
