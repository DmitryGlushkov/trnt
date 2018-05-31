package com.vooqa.trnt.collector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Starter {

    public static void main(String[] args) {
        new Starter().start();

    }

    void start() {
        List<String> bootStrapNodes = getBootstrapNodes();
        if (!bootStrapNodes.isEmpty()) {
            String node = bootStrapNodes.get(0);
            try {
                new DhtCollector(node).collect();
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
    }

    private List<String> getBootstrapNodes() {
        final InputStream inputStream = Starter.class.getResourceAsStream("./assets/bootstrap-nodes.txt");
        if (inputStream != null) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                return reader.lines().collect(Collectors.toList());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Collections.emptyList();
    }

}
