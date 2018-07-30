package com.kafka.day2;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;



public class Producer extends Thread{
	private KafkaProducer<Integer,String> producer;
	private String topic;
	private boolean isAsync;
	private Boolean interrupt;
	
	private String name;
	
	public Producer(String topic,Boolean isAsync,String name){
		this.topic=topic;
		this.isAsync=isAsync;
		interrupt=false;
		this.name=name;
		
		
		Properties prop=new Properties();
		
		prop.setProperty("bootstrap.servers", KafkaProperties.KAFKA_SERVER_URL+":"+KafkaProperties.KAFKA_SERVER_PORT);
		
		prop.setProperty("client.id", KafkaProperties.PRODUCER_CLIENT_ID);	
		prop.setProperty("key.serializer", IntegerSerializer.class.getName());
		prop.setProperty("value.serializer", StringSerializer.class.getName());
		producer=new KafkaProducer<>(prop);
		
	}
	
	
	@Override
	public void run(){
		int messageNo=1;
		while(!interrupt){
			String messageString="message from producer"+this.name+"count:"+messageNo;
			if(isAsync){
				ProducerRecord<Integer,String> producerRecord=new ProducerRecord<Integer,String>(this.topic,messageNo,messageString);
				long startTime=System.currentTimeMillis();
				producer.send(producerRecord,new ProducerMessageCollable(startTime, messageNo, messageString));
				
			}else {
				ProducerRecord<Integer,String> producerRecord=new ProducerRecord<Integer,String>(this.topic,messageNo,messageString);
				try {
					producer.send(producerRecord).get();
				} catch (InterruptedException | ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			messageNo++;
		}
	}
	
	public void setInterrupt(boolean interrupt){
		this.interrupt=interrupt;
	}
	
}
