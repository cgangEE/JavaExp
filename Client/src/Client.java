import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;


public class Client {
	static Socket socket = null;
	static DataInputStream in = null;
	static DataOutputStream out = null;
	static Window window = null;
	static int line = 0;
	static String name;
	static JTextField input;
	static JFrame frame;
	void receive(){
		try {
			String s = in.readUTF();
			
			window.talk.insert(s, line);
			line+=s.length();
		} catch (IOException e) {}
	}

	void send(){
		try {
			out.writeUTF(name+'\n'+window.input.getText()+"\n\n");
			window.input.setText("");
		} catch (IOException e) {}
	}
	static void getName(){
		frame = new JFrame();
		frame.setLayout(new GridLayout());
		
		
		JLabel label = new JLabel("Please Input Your Nikename");
		input = new JTextField(20);
		JButton send = new JButton("Confirmed");
		
		send.addActionListener(new GetNameButton());
		
		frame.add(label);
		frame.add(input);
		frame.add(send);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
	}
	public static void main(String args[]){
		
		try{
			socket = new Socket("localhost", 12306);
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
		}
		catch (IOException e){
			System.out.println(e);
		}
			
		
		if (socket != null){
			getName();
			
		}
	}
	static void begin(){
		name = input.getText();
		frame.dispose();   
		window = new Window();
	}
}

class Window{
	static JFrame client = null;
	static JTextArea talk = null;
	static JTextField input = null;
	static JButton send = null;
	Window(){
		if (client == null){
			client = new JFrame("Client");
		
			client.setLayout(new BorderLayout());
		
			talk = new JTextArea("", 20, 40);
			input = new JTextField(40);
			JButton send = new JButton("send");
			
			JPanel p=new JPanel();
			 
			 JScrollPane text2=new JScrollPane(talk);
			
			p.add(text2);
			
			send.addActionListener(new ButtonHandler());
			
			client.add(p, BorderLayout.NORTH);
			client.add(input,  BorderLayout.CENTER);
			client.add(send, BorderLayout.SOUTH);
			
			client.setSize(500, 500);
			
			client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			client.pack();
			client.setVisible(true);
			
			Update_thread t = new Update_thread();
			t.start();
		}
	}		
}

class Update_thread extends Thread {
	Update_thread() {
	}

	public void run() {
		while (true) {
			new Client().receive();
		}
	}
}

class GetNameButton implements ActionListener{
	public void actionPerformed(ActionEvent e){
		if (!(new Client().input.getText().equals("")))
			new Client().begin();
		
	}
}

class ButtonHandler implements ActionListener{
	public void actionPerformed(ActionEvent e){
		new Client().send();
	}
}