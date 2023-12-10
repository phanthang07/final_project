package kma.com.company.Threads;

import kma.com.company.Model.Block;
import kma.com.company.ServiceData.BlockchainData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class PeerClient extends Thread {

    private Queue<Integer> queue = new ConcurrentLinkedDeque<>();

    public PeerClient() {
        this.queue.add(4000);
        this.queue.add(3000);
    }

    @Override
    public void run() {
        while (true) {
            try (Socket socket = new Socket("localhost", queue.peek())) {
                System.out.println("Sending blockchain object on port: " + queue.peek());
                queue.add(queue.poll());
                socket.setSoTimeout(5000);

                ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream());

                LinkedList<Block> blockChain = BlockchainData.getInstance().getCurrentBlockChain();
                objectOutput.writeObject(blockChain); //we use objectOutput to send our  blockchain object to our peer.

                LinkedList<Block> returnedBlockchain = (LinkedList<Block>) objectInput.readObject();
                System.out.println(" RETURNED BC LedgerId = " + returnedBlockchain.getLast().getLedgerId() +
                        " Size= " + returnedBlockchain.getLast().getTransactionLedger().size());
                BlockchainData.getInstance().getBlockchainConsensus(returnedBlockchain);
                Thread.sleep(2000);

            } catch (SocketTimeoutException e) {
                System.out.println("The socket timed out");
                queue.add(queue.poll());
            } catch (IOException e) {
                System.out.println("Client Error: " + e.getMessage() + " -- Error on port: " + queue.peek());
                queue.add(queue.poll());
            } catch (InterruptedException | ClassNotFoundException e) {
                e.printStackTrace();
                queue.add(queue.poll());
            }
        }
    }
}