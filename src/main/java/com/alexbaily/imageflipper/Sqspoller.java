package com.alexbaily.imageflipper;

import java.util.List;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;

public class Sqspoller implements Runnable {
	
	private static final String QUEUE_NAME = "image_queue";
	
	
	public void run() {
		while(true) {
		
			//Using a builder to return the queue, credentials are pulled from local machine.
			AWSCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
			AmazonSQS sqs = AmazonSQSClientBuilder.standard()
												.withCredentials(credentialsProvider)
												.withRegion(Regions.EU_WEST_1)
												.build();
			//Debugging: Checking to make sure I have return the right queue.
			String queue_url = sqs.getQueueUrl(QUEUE_NAME).getQueueUrl();
			System.out.println(queue_url);
			
			//Receive the messages via the ReceiveMessageResult class.
			ReceiveMessageResult message = sqs.receiveMessage(queue_url);
			if(message.getMessages().size() > 0) {
				//getMessages() returns a list of messages.
				List<Message> messageList = message.getMessages();
				
				//for-each to loop through each message and see what they contain.
				for(Message imageInfo : messageList) {
					//Getting the contents of the message
					String contents = imageInfo.getBody();
					System.out.println(contents);
					//Getting the message Handle for deletion.
					String messageHandle =  imageInfo.getReceiptHandle();
					sqs.deleteMessage(queue_url, messageHandle);
			
				}
			}
			else {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
				
		}
	}
}
