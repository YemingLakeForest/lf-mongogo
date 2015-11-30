package com.lf.mongogo;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import spark.Spark;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Hello world!
 */
public class Launcher {


    public static void main(String[] args) {

        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(Launcher.class, "/");


        Spark.get("/mongogo", (request, response) -> {
            StringWriter writer = new StringWriter();

            try {
                Template template = configuration.getTemplate("hello.ftl");


                Map<String, Object> helloMap = new HashMap<>();
                helloMap.put("name", "Yeming");

                template.process(helloMap, writer);

            } catch (IOException |TemplateException e) {
                e.printStackTrace();
            }

            return writer;
        });

    }
}
