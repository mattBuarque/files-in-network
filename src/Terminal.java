import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.util.Scanner;

import org.apache.commons.net.ftp.FTPReply;

import ftpconnection.FTPClient;
import socketconnection.Arquivo;
import socketconnection.SocketClient;

public class Terminal {

	public static final byte ACTIVE_MODE = 1;
	public static final byte PASSIVE_MODE = 2;

	private FTPClient ftpClient;
	private SocketClient socketClient;
	private Arquivo socketFile;

	public Terminal() {
	}

	protected void initSocketConnection(Scanner s) {
		try {
			s = new Scanner(System.in);
			System.out.println("Informe o host de conexão:");
			String host = s.nextLine();
			System.out.println("Informe a porta do servidor:");
			s = new Scanner(System.in);
			int porta = s.nextInt();
			setSocketClient(new SocketClient(host, porta));
			System.out.println("Conexão estabelecida");
			setSocketFile(new Arquivo("c:\\Arquivo\\Recebido\\"));
			getSocketClient().turnOnInput();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void socketGetFile() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(getSocketClient().getInput()));
			getSocketFile().createFile();
			String line;
			PrintWriter writer = new PrintWriter(getSocketFile().getArquivo());
			boolean recebendo = true;

			System.out.println("Aguardando...");
			while ((line = reader.readLine()) != null) {
				writer.println(line);
				if (recebendo) {
					System.out.println("Recebendo arquivo....");
					recebendo = false;
				}
				if (line.toString().equals(".")) {
					System.out.println("Gravação de arquivo finalizada!");
					writer.flush();
				}
			}
			System.out.println("Fechando Conexão...");
			getSocketClient().closeInput();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void initFtpConnection(Scanner s) {
		s = new Scanner(System.in);
		System.out.println("Informe o host de conexão:");
		String host = s.nextLine();
		System.out.println("Informe a porta do servidor:");
		s = new Scanner(System.in);
		int porta = s.nextInt();
		setFtpClient(new FTPClient(host, porta));
		System.out.println("Conectando...");
		ftpConnect();
		int replyCode = ftpClient.getReplyCode();
		if (!FTPReply.isPositiveCompletion(replyCode)) {
			System.out.println("Falha na operação. Código de resposta: " + replyCode);
			return;
		}
		System.out.println("Conexão estabelecida com sucesso!");
		return;
	}

	protected void authFtp(Scanner s) {
		s = new Scanner(System.in);
		System.out.println("Informe o usurio: ");
		getFtpClient().setLogin(s.nextLine());
		System.out.println("Informe a senha: ");
		getFtpClient().setSenha(s.nextLine());
		getFtpClient().auth();

	}

	protected void setFileType(int tipo) {
		if (!getTipo(tipo).equals("0")) {
			System.out.println("Tipo " + getTipo(tipo) + " definido com sucesso.");
			getFtpClient().setTypeOfFile(tipo);
			return;
		}
		System.out.println("Tipo de arquivo desconhecido.");
	}

	protected void setEnterMode(byte mode) {
		if (mode == ACTIVE_MODE) {
			getFtpClient().setActiveFTP();
			return;
		} else {
			getFtpClient().setPassiveFTP();
			return;
		}
	}

	private String getTipo(int tipo) {
		switch (tipo) {
		case 1:
			return "Binário";
		case 2:
			return "ASCII";
		case 3:
			return "EBCDIC";
		default:
			return "0";
		}
	}

	protected void retrieveFromFile(String remote, String path) {
		try {
			System.out.println("Retrieve para local modo 2 invocado:");
			getFtpClient().retrieveFromFile(remote, path);
			return;
		} catch (Exception e) {
		}
	}

	protected void retrieveFStream(String remote, String path) {
		try {
			System.out.println("Retrieve para local modo  1 invocado:");
			getFtpClient().retrieveFStream(remote, path);
			return;
		} catch (Exception e) {
		}
	}

	protected void retrieveFStream(String remote) {
		try {
			System.out.println("Retrieve invocado:");
			getFtpClient().retrieveFStream(remote);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void disconnectServer() {
		getFtpClient().disconnectServer();
	}

	private void ftpConnect() {
		getFtpClient().conectar();
	}

	private FTPClient getFtpClient() {
		return ftpClient;
	}

	private void setFtpClient(FTPClient client) {
		this.ftpClient = client;
	}

	public SocketClient getSocketClient() {
		return socketClient;
	}

	public void setSocketClient(SocketClient socketClient) {
		this.socketClient = socketClient;
	}

	public Arquivo getSocketFile() {
		return socketFile;
	}

	public void setSocketFile(Arquivo socketFile) {
		this.socketFile = socketFile;
	}
}
