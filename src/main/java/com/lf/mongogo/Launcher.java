package com.lf.mongogo;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import spark.Spark;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Hello world!
 */
public class Launcher {


    public static void main(String[] args) {

        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(Launcher.class, "/");


        Spark.get("/", (request, response) -> {
            StringWriter writer = new StringWriter();

            try {

                Map<String, Object> fruit = new HashMap<>();
                fruit.put("fruits", Arrays.asList("apple", "pear"));

                Template template = configuration.getTemplate("FruitPicker.ftl");

                template.process(fruit, writer);

            } catch (IOException |TemplateException e) {
                e.printStackTrace();
            }

            return writer;
        });

        Spark.post("/favorite_fruit", (request, response) -> {
            String fruit = request.queryParams("fruit");
            if (fruit == null) {
                return "Y No Pick a fruit!";
            } else {
                return "Y Picked a fruit: " + fruit;
            }
        });

    }
}
