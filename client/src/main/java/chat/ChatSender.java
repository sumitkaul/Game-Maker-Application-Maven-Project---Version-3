package chat;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

public class ChatSender implements Runnable {

	private MessageProducer producer;
	private static boolean messagePresent = false;
	private static String message;
	private Session session;

	public ChatSender() {
		try {
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
					"tcp://129.79.247.5:61616");

			// Create a Connection
			Connection connection = connectionFactory.createConnection();
			connection.start();

			// Create a Session
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			// Create the destination (Topic or Queue)
			//Destination destination = session.createQueue("CHAT");
			Topic topic= session.createTopic("CHAT");

			// Create a MessageProducer from the Session to the Topic or Queue
			producer = session.createProducer(topic);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		} catch (Exception e) {

		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				if (messagePresent) {
					TextMessage textMessage = session.createTextMessage(message);
					producer.send(textMessage);
					messagePresent = false;

				}
				Thread.currentThread().sleep(100);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static void sendMessage(String text) {
		message = text;
		messagePresent = true;
	}

}
