package com.kafka.day2;

import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import kafka.utils.ShutdownableThread;

public class Consumer extends ShutdownableThread{

	private KafkaConsumer<Integer, String> consumer;
	private String topic;
	
	
	public Consumer(String name, boolean isInterruptible) {
		super(name, isInterruptible);
		
		Properties prop=new Properties();
		
		prop.setProperty("bootstrap.servers", KafkaProperties.KAFKA_SERVER_URL+":"+KafkaProperties.KAFKA_SERVER_PORT);
		prop.setProperty("key.deserializer", IntegerDeserializer.class.getName());
		prop.setProperty("value.deserializer", StringDeserializer.class.getName());
		prop.setProperty("group.id", "MyGroup");
		prop.setProperty("session.timeout.ms","12000");
		
		consumer=new KafkaConsumer<Integer,String>(prop);
		this.topic=name;
		
		
	}
	
	@Override
	public void doWork(){
		consumer.subscribe(Collections.singleton(this.topic));
		ConsumerRecords<Integer, String> records=consumer.poll(1000);
		for(ConsumerRecord<Integer, String> record:records){
			System.out.println("received msg key->"+
								record.key()+",message"+
								record.value()+",@offset"+
								record.offset()+",partition"+
								record.partition());
		}
	}

}
