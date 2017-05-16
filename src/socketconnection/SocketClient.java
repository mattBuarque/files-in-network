package socketconnection;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketClient extends Socket {

	private InputStream input;
	
	public SocketClient(String host, int porta) throws UnknownHostException, IOException {
		super(host, porta);
	}

	public InputStream getInput() {
		return input;
	}

	public void setInput(InputStream input) {
		this.input = input;
	}
	
	public void turnOnInput() throws IOException{
		this.input = super.getInputStream();
	}
	

	public void closeInput() throws IOException{
		shutdownInput();
		close();
	}
	
	public void receiveMessage(byte[] msg, int ini, int fim) throws IOException{
		synchronized (input) {
			input.read(msg, ini, fim);
		}
	}
	
}
