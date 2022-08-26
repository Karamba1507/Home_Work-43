package com.company;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class WorkingMethods {
    static HttpServer makeServer() throws IOException {
        String host = "localhost"; //127.0.0.1
        InetSocketAddress address = new InetSocketAddress(host, 4545);

        System.out.printf("Server is created: http://%s:%s%n",
                address.getHostName(),
                address.getPort());


        HttpServer server = HttpServer.create(address, 50);
        System.out.println("Good luck!");

        return server;
    }

    static void initRoutes(HttpServer server) {
        server.createContext("/", Handler1::handleRequest);
        server.createContext("/apps", Handler2::handleRequestForApps);
        server.createContext("/apps/profile", Handler3::handleRequestForProfile);
    }


    static void writeHeaders(PrintWriter writer, String type, Headers headers) {
        write(writer, type, " ");
        headers.forEach((k, v) -> write(writer, "\t" + k, v.toString()));
    }

    static void write(Writer writer, String msg, String method) {
        String data = String.format("%s:%s%n%n", msg, method);

        try {
            writer.write(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static PrintWriter getWriterFrom(HttpExchange exchange) {
        OutputStream output = exchange.getResponseBody();
        Charset charset = StandardCharsets.UTF_8;
        return new PrintWriter(output, false, charset);
    }

    private static BufferedReader getReader(HttpExchange exchange) {
        InputStream input = exchange.getRequestBody();
        Charset charset = StandardCharsets.UTF_8;
        InputStreamReader isr = new InputStreamReader(input, charset);
        return new BufferedReader(isr);
    }

    static void writeData(Writer writer, HttpExchange exchange) {
        try (BufferedReader reader = getReader(exchange)) {
            if (!reader.ready()) return;

            write(writer, "Data block", " ");
            reader.lines().forEach(e -> write(writer, "\t", e));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
