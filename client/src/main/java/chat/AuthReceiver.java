package chat;

import javax.jms.Connection;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import view.PlayerButtonPanel;
import utility.Constants;
public class AuthReceiver implements Runnable {

	private MessageConsumer consumer;

	public AuthReceiver(String QueueName) {
		try {
			// Create a ConnectionFactory
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(Constants.ActiveMQConnect);

			// Create a Connection
			Connection connection = connectionFactory.createConnection();
			connection.start();


			// connection.setExceptionListener((ExceptionListener) this);

			// Create a Session
			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);

			// Create the destination (Topic or Queue)
			//Destination destination = session.createQueue("CHAT");
			Queue queue= session.createQueue(QueueName);
			
			// Create a MessageConsumer from the Session to the Topic or Queue
			consumer = session.createConsumer(queue);
			Thread chatReceiverThread=new Thread(this);
			chatReceiverThread.start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	@Override
	public void run() {
		while (true) {
			try {
				// Wait for a message
				
				Message message = consumer.receive();
				if (message instanceof TextMessage) {
					TextMessage textMessage = (TextMessage) message;
					String text = textMessage.getText();
					
					//TODO received messgage Authentivated:username . Do login 
				
					//Design.getInstance().updateChatWindow(text);

				}
			} catch (Exception e) {
			}
		}
	}
}