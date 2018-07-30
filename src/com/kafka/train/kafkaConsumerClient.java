package com.kafka.train;

import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

public class kafkaConsumerClient {
	public static void main(String[] args){
		Properties prop=new Properties();
		
		prop.setProperty("bootstrap.servers", "localhost:9092");
		prop.setProperty("key.deserializer", StringDeserializer.class.getName());
		prop.setProperty("value.deserializer", StringDeserializer.class.getName());
		
		prop.setProperty("group.id", "FirstGroup");
		prop.setProperty("session.timeout.ms", "30000");
		prop.setProperty("auto.offset.reset", "earliest");
		
		
		KafkaConsumer<String, String> kafkaConsumer=new KafkaConsumer<String,String>(prop);
		
		
		kafkaConsumer.subscribe(Arrays.asList("topicA"));
		
		while(true){
			ConsumerRecords<String, String> consumerRecord=kafkaConsumer.poll(1000);
			
			System.out.println("count no of msg got"+consumerRecord.count());
			
			for(ConsumerRecord<String, String> temp:consumerRecord){
				System.out.println("key:"+temp.key()+",Value: "+
									temp.value()+",partition:"+ temp.partition()+" "+
									temp.topic()+",Time:"+
									new Date(temp.timestamp()));
				
			}
			try{
				Thread.sleep(100);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}

}
