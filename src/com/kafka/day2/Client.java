package com.kafka.day2;

public class Client {
	public static void main(String[] args) {
		Boolean isAsync=true;
		
		Producer producer=new Producer(KafkaProperties.TOPIC2,isAsync,"H e l l o 1");
		
		producer.start();
		
		System.out.println("started producer");
		try{
			Thread.sleep(5000);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		producer.setInterrupt(true);
	}

}
