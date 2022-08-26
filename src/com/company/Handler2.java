package com.company;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;

import static com.company.WorkingMethods.*;

class Handler2 {
    static void handleRequestForApps(HttpExchange exchange) {
        try {
            exchange.getResponseHeaders().add("Content-Type", "text/plain; charset=utf-8");
            int response = 200;
            int length = 0;
            exchange.sendResponseHeaders(response, length);

            try (PrintWriter writer = getWriterFrom(exchange)) {
                String method = exchange.getRequestMethod();
                URI uri = exchange.getRequestURI();
                String ctxPath = exchange.getHttpContext().getPath();
                String address = String.valueOf(exchange.getResponseCode());

                write(writer, "HTTP method: ", method);
                write(writer, "Information: ", uri.toString());
                write(writer, "Done via: ", ctxPath);
                write(writer, "Address", address);
                writeHeaders(writer, "Request: ", exchange.getRequestHeaders());
                writeData(writer, exchange);
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
