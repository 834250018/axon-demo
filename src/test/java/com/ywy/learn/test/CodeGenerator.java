package com.ywy.learn.test;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class CodeGenerator {

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
            log.error("已存在文件,不允许覆盖!!! " + target.getAbsolutePath());
            return;
        }
        Template temp = getFreeMarkerConfig().getTemplate(moduleName);
        target.getParentFile().mkdirs();
        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(target), ENCODING));
        temp.process(params, writer);
        writer.close();
        log.info("生成代码成功: " + target.getAbsolutePath());
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
            log.error("已存在文件,不允许覆盖!!! " + target.getAbsolutePath());
            return;
        }
        Template temp = getFreeMarkerConfig().getTemplate(moduleName);
        target.getParentFile().mkdirs();
        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(target), ENCODING));
        temp.process(params, writer);
        writer.close();
        log.info("生成代码成功: " + target.getAbsolutePath());
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

    /**
     * 测试生成article相关
     */
    @Test
    public void generateArt() {
        // 所属模块
        String module = "admin";
        // 所需功能,如文章Article
        List<String> aggregates = new ArrayList<String>() {{
            add("admin");
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
        // 事件侧
        String aggregateRoot = module + "/" + javaPath + projectPath + "command/" + aggregate + "/";
        writeJava(".java.ftl", aggregateRoot, map);
        writeJava("Config.java.ftl", aggregateRoot, map);
        writeJava("Handle.java.ftl", aggregateRoot, map);

        write("build.gradle.ftl", new File(module, "build.gradle"), map);

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

        write("api.build.gradle.ftl", new File(module + "-api/", "build.gradle"), map);

        // 查询侧
        String entryRoot = "query/" + javaPath + projectPath + "query/entry/";
        writeJava("Entry.java.ftl", entryRoot, map);
        String listenerRoot = "query/" + javaPath + projectPath + "query/listener/";
        writeJava("Listener.java.ftl", listenerRoot, map);
        String repositoryRoot = "query/" + javaPath + projectPath + "query/repository/";
        writeJava("EntryRepository.java.ftl", repositoryRoot, map);

        // web
        writeJava("AdminController.java.ftl", "web/" + javaPath + projectPath + "web/controller/admin/", map);
        writeJava("UserController.java.ftl", "web/" + javaPath + projectPath + "web/controller/user/", map);
    }
}
