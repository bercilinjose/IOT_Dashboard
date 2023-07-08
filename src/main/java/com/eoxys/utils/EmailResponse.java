package com.eoxys.utils;

import javax.mail.event.TransportListener;

import java.util.Arrays;

import javax.mail.event.TransportEvent;

public class EmailResponse implements TransportListener {

	@Override
	public void messageDelivered(TransportEvent e) {
		System.out.println("Message delivered successfully.");
		System.out.println("Message: " + e.getMessage());
		System.out.println("Valid addresses: " + Arrays.toString(e.getValidSentAddresses()));
	}

	@Override
	public void messageNotDelivered(TransportEvent e) {
		System.out.println("Message delivery failed.");
		System.out.println("Message: " + e.getMessage());
		System.out.println("Invalid addresses: " + Arrays.toString(e.getInvalidAddresses()));
	}

	@Override
	public void messagePartiallyDelivered(TransportEvent e) {
		System.out.println("Message delivery partially failed.");
		System.out.println("Message: " + e.getMessage());
		System.out.println("Valid addresses: " + Arrays.toString(e.getValidSentAddresses()));
		System.out.println("Invalid addresses: " + Arrays.toString(e.getInvalidAddresses()));
		System.out.println("Valid unsent addresses: " + Arrays.toString(e.getValidUnsentAddresses()));
	}
}
