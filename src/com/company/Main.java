package com.company;

import com.sun.net.httpserver.HttpServer;

import java.io.*;

public class Main {

    public static void main(String[] args) {
        try {
            HttpServer server = WorkingMethods.makeServer();
            server.start();
            WorkingMethods.initRoutes(server);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
