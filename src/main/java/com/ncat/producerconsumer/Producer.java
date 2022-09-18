package com.ncat.producerconsumer;

import com.ncat.producerconsumer.DataQueue;
import com.ncat.producerconsumer.Message;
import com.ncat.producerconsumer.util.ThreadUtil;

import javax.xml.crypto.Data;

public class Producer implements Runnable {
    private final DataQueue dataQueue;
    private volatile boolean runFlag;

    private static int idSequence = 0;

    public Producer(DataQueue dataQueue) {
        this.dataQueue = dataQueue;
        this.runFlag = true;
    }

    @Override
    public void run() {
        produce();
    }

    public void produce() {
        while(runFlag) {
            Message message = generateMessage();
            while(dataQueue.isFull()) {
                try {
                    dataQueue.waitOnFull();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }

            if (!runFlag) {
                break;
            }
            dataQueue.addMessage(message);
            dataQueue.notifyAllForEmpty();
        }
        System.out.println("Producer Stopped");
    }

    public Message generateMessage() {
        Message message = new Message(++idSequence, Math.random());
        System.out.printf("[%s] Generated Message. Id: %d, Data: %f\n", Thread.currentThread().getName(), message.getId(), message.getData());

        ThreadUtil.sleep((long) (message.getData() * 100));
        return message;
    }

    public void stop() {
        runFlag = false;
        dataQueue.notifyAllForFull();
    }
}
