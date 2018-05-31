package com.vooqa.trnt.collector;

import com.vooqa.trnt.bencoder.BencoderTool;
import com.vooqa.trnt.collector.krpc.DHTQuery;

import java.io.ByteArrayInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Map;

public class DhtCollector {

    private static int PORT = 6881;

    private final String bootstrapNode;
    private final String collectorId;
    private final  DatagramSocket socket;

    public DhtCollector(String bootstrapNode) throws SocketException{
        this.socket = new DatagramSocket(6884);
        this.bootstrapNode = bootstrapNode;
        this.collectorId = Generator.randomNodeId();
    }

    public void collect() {

        // ping
        Map<String, Object> pingResponse = ping();
        String ID = (String) ((Map) pingResponse.get("r")).get("id");

        // find nide
        Map<String, Object> findNodeResponse = findNode(ID);
        String nodes = (String)((Map)findNodeResponse.get("r")).get("nodes");

        System.out.println(ID);
        System.out.println(ID.length());
        System.out.println(nodes);
        System.out.println(nodes.length());
    }

    private Map<String, Object> ping() {
        return sendRequest(DHTQuery.pingRequest(collectorId));
    }

    private Map<String, Object> findNode(String nodeId) {
        return sendRequest(DHTQuery.findNodeRequest(collectorId, nodeId));
    }

    private Map<String, Object> sendRequest(Map<String, Object> queryBody) {
        try {
            byte[] senBuffer = BencoderTool.encode(queryBody);
            byte[] receiveBuffer = new byte[2048];
            DatagramPacket sendPacket = new DatagramPacket(senBuffer, senBuffer.length, InetAddress.getByName(bootstrapNode), PORT);
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.send(sendPacket);
            socket.receive(receivePacket);

            byte[] receivedData = Arrays.copyOf(receivePacket.getData(), receivePacket.getLength());
            ByteArrayInputStream byteInputStream = new ByteArrayInputStream(receivedData);
            return BencoderTool.decode(byteInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
