package com.vnscriptkid.throughput;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SearchServerFinal {
    private static final String INPUT_FILE = "resources/throughput/war_and_peace.txt";
    public static final int NUMBER_OF_THREADS = 4;

    public static void main(String[] args) throws IOException {
        String book = new String(Files.readAllBytes(Paths.get(INPUT_FILE)));

        startServer(book);
    }

    private static void startServer(String book) throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(8000), 0);

        httpServer.createContext("/search", new WordCountHandler(book));

        Executor executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

        httpServer.setExecutor(executor);
        httpServer.start();
    }

    private static class WordCountHandler implements HttpHandler {
        private String book;

        public WordCountHandler(String book) {
            this.book = book;
        }

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String query = httpExchange.getRequestURI().getQuery();

            String[] keyValue =  query.split("=");

            String action = keyValue[0];
            String word = keyValue[1];

            if (!action.equals("word")) {
                httpExchange.sendResponseHeaders(400, 0);
                return;
            }

            long count = countWord(word);

            byte[] response = Long.toString(count).getBytes();
            httpExchange.sendResponseHeaders(200, response.length);
            OutputStream outputStream = httpExchange.getResponseBody();
            outputStream.write(response);
            outputStream.close();
        }

        private long countWord(String word) {
            long count = 0;
            int index = 0;
            while (index >= 0) {
                index = this.book.indexOf(word, index);

                while (index >= 0) {
                    count++;
                    index++;
                }
            }

            return count;
        }
    }
}
