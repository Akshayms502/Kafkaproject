package com.kafka.train;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;



public class KafkaProducerClient {
	public static void main(String[] args){
		Properties prop=new Properties();
		
		prop.setProperty("bootstrap.servers", "localhost:9092");
		prop.setProperty("key.serializer", StringSerializer.class.getName());
		prop.setProperty("value.serializer", StringSerializer.class.getName());
		prop.setProperty("acks", "1");
		prop.setProperty("retries", "3");
		
		
		Producer<String, String> producer=new KafkaProducer<String,String>(prop);
		for(int i=0;i<20;i++){
		ProducerRecord<String, String> producerRecord=new ProducerRecord<String,String>("topicA","1","testing message from java"+i);
		System.out.println("message sent"+ i);
		producer.send(producerRecord);
		}
		producer.flush();
		producer.close();
		
	}

}
