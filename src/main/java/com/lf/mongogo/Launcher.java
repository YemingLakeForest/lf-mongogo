package com.lf.mongogo;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

/**
 * Hello world!
 */
public class Launcher {


    public static void main(String[] args) {

        Spark.get("/", new Route() {
            public Object handle(Request request, Response response) throws Exception {
                return "Fuck it..";
            }
        });

    }
}
