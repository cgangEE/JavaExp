import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
	static Server_thread[] a = new Server_thread[1000];
	static int idx = 0;
	
	void update(String s){
		for (int i=0; i<idx; ++i) if (a[i].isAlive()){
			a[i].out(s);
		}
	}
	public static void main(String args[]) {
		ServerSocket server = null;
		Socket you = null;

		System.out.println("Fuck up, it's begin...");
		while (true) {
			try {
				server = new ServerSocket(12306);
			} catch (IOException e1) {
				System.out.println("can't listening" + e1);
			}

			try {
				if (server == null)
					System.out.println("FT");
				you = server.accept();
				InetAddress adr = you.getInetAddress();
				System.out.println(adr);
			} catch (IOException e) {
			}

			if (you != null) {
				Server_thread t = new Server_thread(you);
				a[idx++]=t;
				t.start();
			}
		}
	}
}

class Server_thread extends Thread {
	Socket socket = null;
	DataInputStream in = null;
	DataOutputStream out = null;

	Server_thread(Socket t) {
		socket = t;
		try {
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
		}
	}
	void out(String s){
		try {
			out.writeUTF(s);
		} catch (IOException e) {}
	}

	public void run() {
		while (true) {

			try {
				String s;
				s = in.readUTF();
				System.out.println(s);
				new Server().update(s);
				
			} catch (IOException e) {
				break;
			}

		}
	}
}
