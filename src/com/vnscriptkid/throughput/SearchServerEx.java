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

public class SearchServerEx {
    private static final String INPUT_FILE = "resources/throughput/war_and_peace.txt";
    public static final int NUMBER_OF_THREADS = 4;

    public static void main(String[] args) throws IOException {
        // todo: read file as bytes then store in a string called book

        // todo: start server, passing in the book
    }

    private static void startServer(String book) throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(8000), 0);

        // todo: create context `/search`, bind a handler

        // todo: create new fixed thread pool with number of threads using Executor

        // todo: force http server to use our thread pool
        httpServer.start();
    }

    // todo: this must implement HttpHandler
    private static class WordCountHandler {
        private String book;

        public WordCountHandler(String book) {
            this.book = book;
        }

        public void handle(HttpExchange httpExchange) throws IOException {
            // todo: get query out of uri, split by `=` into action and word

            // todo: guard against action, if not `word` then send response 400, exit early

            long count = 0;
            // todo: extract count word job to function

            byte[] response = Long.toString(count).getBytes();
            httpExchange.sendResponseHeaders(200, response.length);
            OutputStream outputStream = httpExchange.getResponseBody();
            outputStream.write(response);
            outputStream.close();
        }

        private long countWord(String word) {
            // todo: need to vars `count` and `startIdx`
            // todo: for each iteration, find next word, if not found, exit loop
            return 0;
        }
    }
}
