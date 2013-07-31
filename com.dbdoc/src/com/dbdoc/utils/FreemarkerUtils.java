package com.dbdoc.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;


/***
 * 使用Freemarker根据模板导出文件
 * @author moonights
 *
 */
public class FreemarkerUtils {
        private static Configuration configuration;
        private static final String ENCODING = "UTF-8";
        
        static {
        	init();
        }
        private FreemarkerUtils() {
        	
        }
        private static void init() {
        	configuration = new Configuration();
            TemplateLoader[] loaders = {new ClassTemplateLoader(FreemarkerUtils.class, File.separator)};
            configuration.setTemplateLoader(new MultiTemplateLoader(loaders));
            configuration.setDefaultEncoding(FreemarkerUtils.ENCODING);
        }

        public static void writeTemplate(String templateUrl, Map<String,?> value, Writer writer) {
            try {
                Template template = configuration.getTemplate(templateUrl);
                template.setEncoding(FreemarkerUtils.ENCODING);
                template.process(value, writer, ObjectWrapper.BEANS_WRAPPER);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        public static void writeTemplateToFile(String templateUrl, Map<String,?> value, String path) {
            Writer writer = null;
            File file = FileUtils.getFile(path);
            try {
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), FreemarkerUtils.ENCODING));
                writeTemplate(templateUrl, value, writer);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        /***
         * 测试方法 
         * @author moonights
         * @param args
         */
        public static void main(String[] args) {
			Map propMap=new HashMap();
			propMap.put("test", "hello world");
			FreemarkerUtils.writeTemplateToFile("template/test.ftl", propMap, "d:/test.jsp");
		}

}