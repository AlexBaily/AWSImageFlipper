package com.alexbaily.imageflipper;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.AmazonSQSException;
import com.amazonaws.services.sqs.model.CreateQueueRequest;

public class main {
	
	private static final String QUEUE_NAME = "image_queue";
	
	public static void main(String[] args) {
		
		
		AWSCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
		AmazonSQS sqs = AmazonSQSClientBuilder.standard()
											.withCredentials(credentialsProvider)
											.withRegion(Regions.EU_WEST_1)
											.build();
		
		String queue_url = sqs.getQueueUrl(QUEUE_NAME).getQueueUrl();
		
		System.out.println(queue_url);
		
	}

}
