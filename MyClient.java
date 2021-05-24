import java.net.*;
import java.nio.charset.StandardCharsets;
public class MyClient {

	static DataInputStream din;
	static PrintStream dout;
	static Socket s;
	
	static String response;
	static int nRec = 0, nLen = 0;
	
	static String largest=" ";
	
	public static void main(String[] args) throws Exception {
		Initialize Socket and DataStreams
		s = new Socket("localhost", 50000);
		din = new DataInputStream(s.getInputStream());
		dout = new PrintStream(s.getOutputStream(), true);
		send("HELO");
		send("AUTH " + System.getProperty("user.name"));
		send("REDY");
		
		send("QUIT");
		
		din.close();
		dout.close();
		s.close();
	}
	public static String findLargest() throws IOException{
		send("GETS All");
		String temp=response;
		String[] data=temp.split(" ");
		nRec = Integer.parseInt(data[1]);
		nLen = Integer.parseInt(data[2]);
		send("OK");
		temp = response;
		send("OK");
		String[] servers = temp.split("\n");
		String name="";
		int disk_size=0;
		for(String i : servers) {
			String[] j=i.split(" ");
			String n = j[0];
			int ds = Integer.parseInt(j[4].trim());
			if(ds>disk_size) {
				name = n;
				disk_size=ds;
			}
		}
		return name+" 0";
	}
	
	//	Extract information about Job into an integer array
	public static int[] getJobInfo() {
		String[] info = response.split(" ");
		int[] data = new int[info.length - 1];
		for (int i = 1; i < info.length; i++) {
			data[i - 1] = Integer.parseInt(info[i]);
		}
		return data;
	}
	//	Send message to server
	public static void send(String str) throws IOException {
		dout.print(str);
		dout.flush();
		System.out.println("Client: " + str);
		receive();
	}
	//	Read message from server
	public static void receive() throws IOException {
		int SIZE = Math.max(1000, nRec * nLen + 1);
		byte[] bytes = new byte[SIZE];
		din.read(bytes);
		String str = new String(bytes, StandardCharsets.UTF_8);
		System.out.println("Server: "+str);
		response = str.trim();
	}
}

