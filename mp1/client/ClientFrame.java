package mp1.client;

import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.*; 
import javax.swing.text.DefaultCaret;

import java.awt.*;
import java.awt.event.*;

import mp1.server.ClientData;

public class ClientFrame extends JFrame{

	private final String WINDOW_TEXT = "Chat Box";
	private final String USER_PANEL_TEXT = "Online Users";
	private final String SEND_BUTTON_TEXT = "Send";

	private JTextArea messages_box;
	private JTextArea users_box;
	
	private JTextField chat_box;

	private JButton send_button;

	private JLabel messages_label;
	private JLabel users_label;

	private JPanel send_panel;
	private JPanel button_panel;
	private JPanel messages_panel;
	private JPanel users_panel;
	private JPanel center_panel;
	private JPanel bottom_panel;

	private JScrollPane messages_scroll_pane;
	private JScrollPane users_scroll_pane;

	private Border text_border;


	public ClientFrame(String title){
		super(title);

		messages_box = new JTextArea();
		messages_box.setEditable(false);
		messages_box.setWrapStyleWord(true);
		DefaultCaret caret = (DefaultCaret)messages_box.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		users_box = new JTextArea();
		users_box.setEditable(false);
		users_box.setWrapStyleWord(true);

		chat_box = new JTextField();

		send_button = new JButton(SEND_BUTTON_TEXT);
		messages_label = new JLabel(WINDOW_TEXT);
		users_label = new JLabel(USER_PANEL_TEXT);

		messages_scroll_pane = new JScrollPane(messages_box);
		users_scroll_pane = new JScrollPane(users_box);

		send_panel = new JPanel();
		button_panel = new JPanel();
		messages_panel = new JPanel();
		users_panel = new JPanel();
		center_panel = new JPanel();
		bottom_panel = new JPanel();

		button_panel.setLayout(new BorderLayout());
		center_panel.setLayout(new BoxLayout(center_panel, BoxLayout.LINE_AXIS));
		bottom_panel.setLayout(new BoxLayout(bottom_panel, BoxLayout.PAGE_AXIS));
		send_panel.setLayout(new BoxLayout(send_panel, BoxLayout.LINE_AXIS));
		messages_panel.setLayout(new BoxLayout(messages_panel, BoxLayout.PAGE_AXIS));
		users_panel.setLayout(new BoxLayout(users_panel, BoxLayout.PAGE_AXIS));

		text_border = BorderFactory.createLineBorder(Color.black);
		messages_box.setBorder(text_border);	
		users_box.setBorder(text_border);	

		setMinimumSize(new Dimension(1000, 600));
		setLayout(new BorderLayout());

		button_panel.add(send_button, BorderLayout.CENTER);
		button_panel.setPreferredSize(new Dimension(100,50));
		button_panel.setMaximumSize(new Dimension(100,50));

		send_panel.add(Box.createRigidArea(new Dimension(25, 0)));
		send_panel.add(chat_box);
		send_panel.add(Box.createRigidArea(new Dimension(10, 0)));
		send_panel.add(button_panel);
		send_panel.add(Box.createRigidArea(new Dimension(25, 0)));

		messages_panel.add(messages_label);
		messages_panel.add(messages_scroll_pane);

		users_panel.setMaximumSize(new Dimension(250,Integer.MAX_VALUE));
		users_panel.setPreferredSize(new Dimension(250,Integer.MAX_VALUE));
		users_panel.add(users_label);
		users_panel.add(users_scroll_pane);		

		center_panel.add(Box.createRigidArea(new Dimension(25, 0)));
		center_panel.add(messages_panel);
		center_panel.add(Box.createRigidArea(new Dimension(10, 0)));
		center_panel.add(users_panel);
		center_panel.add(Box.createRigidArea(new Dimension(25, 0)));

		bottom_panel.add(Box.createRigidArea(new Dimension(0,10)));
		bottom_panel.add(send_panel);
		bottom_panel.add(Box.createRigidArea(new Dimension(0,10)));


		add(bottom_panel, BorderLayout.PAGE_END);
		add(center_panel, BorderLayout.CENTER);

		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		chat_box.requestFocus();
	}

	public void addMessage(String msg){
		messages_box.append(msg+"\n");
	}

	public void updateUserList(List<ClientData> users){
		users_box.setText("");
		for(ClientData user: users){
			String status = (user.getStatus().equals(""))?"":"-" + user.getStatus();
			users_box.append(user.getName()+ status +"\n");
		}
	}

	public String getSendMessage(){
		return chat_box.getText();
	}

	public void clearChatBox(){
		chat_box.setText("");
	}

	public void setSendActionListener(ActionListener listener){
		send_button.addActionListener(listener);
		chat_box.addActionListener(listener);
	}

}