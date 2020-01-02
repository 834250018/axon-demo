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

    /**
     * 测试生成article相关
     */
    @Test
    public void generateArt() {
        // 所属模块
        String module = "user";
        // 所需功能,如文章Article
        List<String> aggregates = new ArrayList<String>() {{
            add("article");
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

    public static final String ENCODING = "UTF-8";

    private static String resourcePath = "src/test/resources/";
    private static String javaPath = "src/main/java/";
    private static String projectPath = "com/ywy/learn/";

    /**
     * 定制方法,在输出文件夹中输出文件,名称为:params.get("aggregate")首字母大写 + 模板名称去掉结尾.ftl
     *
     * @param moduleName 模板名称
     * @param targetPath 输出文件路径
     * @param params     参数
     * @throws TemplateException
     * @throws IOException
     */
    public static void writeJava(String moduleName, String targetPath, Map<String, String> params) throws TemplateException, IOException {
        File target = new File(targetPath, up(params.get("aggregate")) + moduleName.replace(".ftl", ""));
        if (target.exists() && target.isFile()) {
            throw new RuntimeException("已存在文件,不允许覆盖!!! " + target.getAbsolutePath());
        }
        Template temp = getFreeMarkerConfig().getTemplate(moduleName);
        target.getParentFile().mkdirs();
        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(target), ENCODING));
        temp.process(params, writer);
        writer.close();
    }

    /**
     * 渲染数据
     *
     * @param moduleName 模板名称
     * @param target     输出文件
     * @param params     参数
     * @throws TemplateException
     * @throws IOException
     */
    public static void write(String moduleName, File target, Map<String, String> params) throws TemplateException, IOException {
        if (target.exists() && target.isFile()) {
            throw new RuntimeException("已存在文件!!! " + target.getAbsolutePath());
        }
        Template temp = getFreeMarkerConfig().getTemplate(moduleName);
        target.getParentFile().mkdirs();
        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(target), ENCODING));
        temp.process(params, writer);
        writer.close();
    }

    public static Configuration getFreeMarkerConfig() throws IOException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_19);
        cfg.setDefaultEncoding(ENCODING);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setDirectoryForTemplateLoading(new File(resourcePath));
        return cfg;
    }

    public static String up(String str) {
        char[] chars = str.toCharArray();
        if (chars[0] >= 97 && chars[0] <= 122) {
            chars[0] -= 32;
        }
        return String.valueOf(chars);
    }

    public static String down(String str) {
        char[] chars = str.toCharArray();
        if (chars[0] >= 65 && chars[0] <= 90) {
            chars[0] += 32;
        }
        return String.valueOf(chars);
    }

    public void generate(String module, String aggregate) throws IOException, TemplateException {
        Map map = new HashMap() {{
            put("module", module);
            put("aggregate", aggregate);
        }};
        // 事件侧
        String aggregateRoot = module + "/" + javaPath + projectPath + "command/" + aggregate + "/";
        writeJava(".java.ftl", aggregateRoot, map);
        writeJava("Config.java.ftl", aggregateRoot, map);
        writeJava("Handle.java.ftl", aggregateRoot, map);

        File gradle = new File(module, "build.gradle");
        if (!gradle.exists() && !!gradle.isFile()) {
            write("build.gradle.ftl", gradle, map);
        }

        // 命令
        String commandRoot = module + "-api/" + javaPath + projectPath + "command/" + aggregate + "/api/command/";
        writeJava("CreateCommand.java.ftl", commandRoot, map);
        writeJava("UpdateCommand.java.ftl", commandRoot, map);
        writeJava("RemoveCommand.java.ftl", commandRoot, map);
        // 事件
        String eventRoot = module + "-api/" + javaPath + projectPath + "command/" + aggregate + "/api/event/";
        writeJava("CreatedEvent.java.ftl", eventRoot, map);
        writeJava("UpdatedEvent.java.ftl", eventRoot, map);
        writeJava("RemovedEvent.java.ftl", eventRoot, map);

        File gradle1 = new File(module + "-api/", "build.gradle");
        if (!gradle1.exists() && !!gradle1.isFile()) {
            write("api.build.gradle.ftl", gradle1, map);
        }

        // 查询侧
        String entryRoot = "query/" + javaPath + projectPath + "query/" + "/entry/";
        writeJava("Entry.java.ftl", entryRoot, map);
        String listenerRoot = "query/" + javaPath + projectPath + "query/" + "/listener/";
        writeJava("Listener.java.ftl", listenerRoot, map);
        String repositoryRoot = "query/" + javaPath + projectPath + "query/" + "/repository/";
        writeJava("EntryRepository.java.ftl", repositoryRoot, map);


//        File webRoot = new File(root, module + "-api");
//        webRoot.mkdirs();
    }
}
