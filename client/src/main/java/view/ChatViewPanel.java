package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.newdawn.slick.state.transition.HorizontalSplitTransition;
import org.newdawn.slick.util.Log;

import model.Player;

import utility.Constants;
import view.communication.ClientHandler;

import chat.ChatReceiver;
import chat.ChatSender;
import chat.OneToOneReceiver;
import chat.OneToOneSender;
	
public class ChatViewPanel {
	
	//PlayerButtonPanel playerButtonPanel;
	private static JPanel chatViewPanel;
	private static JTabbedPane tab;
	private static JPanel commonChat;
	private JPanel gameChat;
	private JPanel chatPanel;
	private JPanel commonChatPanel;
	private JPanel gameChatPannel;
	
	
	private static JList buddyList;
	private JList gameBuddyList;
	
	private JScrollPane buddyScroll;
	private JScrollPane gameBuddyScroll;
	private String budList[] = {"Mayur", "Shruthi", "Pranav"};
	private String gameBudList[] ={"Rohan","Charan","Ridhima"};
	private static List<String> activeUsers = new ArrayList<String>();
	private ChatSender chatSender;
	
	public ChatViewPanel(PlayerButtonPanel playerButtonPanelArg){
		//this.playerButtonPanel = playerButtonPanelArg;
		chatViewPanel = new JPanel();
		commonChat= new JPanel();
		gameChat = new JPanel();
		
		commonChatPanel = new JPanel();
		gameChatPannel = new JPanel();
		//chatViewPanel.setBackground(new Color(50, 150, 50));
		
		tab = new JTabbedPane();
		commonChat = new JPanel();
		gameChat = new JPanel();
		/*try{
			activeUsers = ClientHandler.getActiveUsers( Constants.HOST, Constants.PATH + "/getActiveUsers");
		}catch(Exception ex){
			JOptionPane.showConfirmDialog(null, "Users list could not be loaded!");
		}*/
		
		buddyList = new JList();
		buddyList.setListData(activeUsers.toArray());
		buddyList.addMouseListener(new MouseAdapter() {
		 public void mouseClicked(MouseEvent evt) {
		        JList list = (JList)evt.getSource();
		        if (evt.getClickCount() == 2) {
		            int index = list.locationToIndex(evt.getPoint());
		            if(buddyList.getModel().getElementAt(index).equals(Player.getInstance().getUsername())){
		            	return;
		            }
		            chatSender.sendMessage(":"+buddyList.getModel().getElementAt(index)+":"+Player.getInstance().getUsername());
		            createChatTab(buddyList.getModel().getElementAt(index)+":"+Player.getInstance().getUsername());		   
		        } 
		    }
		});
		buddyScroll = new JScrollPane(buddyList);
		
		gameBuddyList = new JList();
		gameBuddyList.setListData(gameBudList);
		gameBuddyScroll = new JScrollPane(gameBuddyList);
		chatSender = new ChatSender("CHAT");
		ChatPanel commChatPanel=new ChatPanel(chatSender);
		commonChatPanel = commChatPanel.getChatPanel();
		new ChatReceiver(commChatPanel);
		
		commonChat.setLayout(new GridLayout(1,2));
		commonChat.add(buddyScroll);
		commonChat.add(commonChatPanel);
	
//		ChatSender oneSender=new ChatSender("GAME");
//		ChatPanel gameChatPanel=new ChatPanel(oneSender);
//		gameChatPannel = gameChatPanel.getChatPanel();
//		//new OneToOneReceiver(topicName, gameChatPanel)
//		
//		gameChat.setLayout(new GridLayout(1,2));
//		gameChat.add(gameBuddyScroll);
//		gameChat.add(gameChatPannel);
		
		tab.addTab("Common Chat", commonChat);
		
		
		chatViewPanel.setLayout(new BorderLayout());
		chatViewPanel.add(tab, BorderLayout.CENTER);

		
	}
	

	public void setChatViewPanel(JPanel chatViewPanel){
		this.chatViewPanel = chatViewPanel;
	}
	
	public JPanel getChatViewPanel(){
		return chatViewPanel;
	}
	
	public static void setOnlineUsersList(List<String> users){
		activeUsers = users;
		buddyList.setListData(activeUsers.toArray());
		chatViewPanel.revalidate();
		chatViewPanel.repaint();
		commonChat.revalidate();
		commonChat.repaint();
	}
	
	public static void createChatTab(String topic){
		int count = tab.getTabCount();
		for(int i = 1; i<count;i++){
			if(tab.getTitleAt(i).equals(topic)){
				return;
			}
		}
		OneToOneSender oneToOneSender = new OneToOneSender(topic);
        ChatPanel oneToOneChatPanel = new ChatPanel(oneToOneSender);
        OneToOneReceiver oneToOneReceiver = new OneToOneReceiver(topic, oneToOneChatPanel);
        tab.addTab(topic, oneToOneChatPanel.getChatPanel());
        tab.setTabComponentAt(tab.getTabCount()-1, new ButtonTabComponent(tab, topic));
        tab.setSelectedIndex(tab.getTabCount()-1);
		
	}

}
