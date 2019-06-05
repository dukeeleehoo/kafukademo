package com.li.providertest;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 *
 * Producer采用默认分区方式将消息散列的发送到各个分区当中
 *
 */



public class ProducerTestDemo {


    public static void main(String[] args) throws InterruptedException {
        Properties props = new Properties();
        //设置kafka集群的地址
        props.put("bootstrap.servers", "192.168.17.128:9092,192.168.17.130:9092,192.168.17.131:9092");
        //ack模式，all是最慢但最安全的
        props.put("acks", "-1");
        //失败重试次数
        props.put("retries", 0);
        //每个分区未发送消息总字节大小（单位：字节），超过设置的值就会提交数据到服务端
        props.put("batch.size", 10);
        //props.put("max.request.size",10);
        //消息在缓冲区保留的时间，超过设置的值就会被提交到服务端
        props.put("linger.ms", 10000);
        //整个Producer用到总内存的大小，如果缓冲区满了会提交数据到服务端
        //buffer.memory要大于batch.size，否则会报申请内存不足的错误
        props.put("buffer.memory", 10240);
        //序列化器
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 100; i++) {
            producer.send(new ProducerRecord<String, String>("testtopic", Integer.toString(i), "dd:" + i));
            //Thread.sleep(1000000);
        }
        producer.close();
    }




}
