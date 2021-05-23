import java.net.*;
import java.nio.charset.StandardCharsets;
public class MyClient {

	static DataInputStream din;
	static PrintStream dout;
	static Socket s;

	public static void main(String[] args) throws IOException {
		Initialize Socket and DataStreams
		s = new Socket("localhost", 50000);
		din = new DataInputStream(s.getInputStream());
		dout = new PrintStream(s.getOutputStream(), true);
	}
}

