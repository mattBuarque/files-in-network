package ftpconnection;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import org.apache.commons.net.ftp.FTP;

public class FTPClient extends org.apache.commons.net.ftp.FTPClient{

	private String host;
	private int port;
	private String login;
	private String senha;

	public FTPClient(String host, int port) {
		super();
		setHost(host);
		setPort(port);
	}

	public void conectar(){
		try {
			connect(getHost(), getPort());
		} catch (SocketException e) {
			System.out.println("Erro ao se conectar com o servidor.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Erro de Leitura e Gravação de dados.");
			e.printStackTrace();
		}
	}

	public void auth(){
		try {
			boolean success = login(getLogin(), getSenha());
			if (!success) {
				System.out.println("Erro ao se autenticar com o servidor.");
				return;
			}
			System.out.println("Login efetuado com sucesso!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setTypeOfFile(int tipo){
		try {
			switch (tipo) {
			case 1:
				setFileType(FTP.BINARY_FILE_TYPE);
				break;
			case 2:
				setFileType(FTP.ASCII_FILE_TYPE);
				break;
			case 3:
				setFileType(FTP.EBCDIC_FILE_TYPE);
				break;
			default:
				break;
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void retrieveFromFile(String arquivo , String path) throws IOException{
		File file = new File(path);
		file.mkdirs();
		file = new File(path + "\\" + arquivo);
		file.createNewFile();
		OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
		boolean success = retrieveFile(arquivo, out);
		if (success) {
			System.out.println("Arquivo recebido com sucesso!");
		}else{
			System.out.println("Erro ao realizar retrieve pelo servidor!");
		}
		out.flush();
		out.close();
		return;
	}
	
	public void retrieveFStream(String arquivo, String url) throws IOException{
		try {
			InputStream in = retrieveFileStream(arquivo);
			File file = new File(url);
			file.mkdirs();
			OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
			byte[] buffer = new byte[1024];
			for (int i = 0; (i = in.read(buffer)) != -1;) {
				out.write(buffer, 0, i);
			}
			System.out.println("Arquivo recebido com sucesso! path: " + url);
			out.flush();
			out.close();			
			return;
		} catch (IOException e) {
			System.out.println("Erro ao realizar retrieve pelo servidor!");
		}
	}
	
	public void retrieveFStream(String arquivo) throws IOException{
		try {
			InputStream in = super.retrieveFileStream(arquivo);
			ByteArrayOutputStream result = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int length;
			for (int i = 0; (i = in.read(buffer)) != -1;) {
				result.write(buffer, 0, i);
			}
			result.flush();
			result.close();
			completePendingCommand();
			System.out.println("Arquivo recebido com sucesso!\t");
			System.out.println("");
			System.out.println("--Inicio");
			System.out.println(result.toString("UTF-8"));			
			System.out.println("--Fim");
			System.out.println("");
			return;
			
		} catch (IOException e) {
			System.out.println("Erro ao realizar retrieve pelo servidor!");
			e.printStackTrace();
		}
	}
	
	public void disconnectServer() {
		try {
			disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void showServerReply(){
		String[] replies = getReplyStrings();
		if (replies != null && replies.length > 0) {
			for (String s : replies) {
				System.out.println("SERVIDOR: " + s);
			}
		}
	}

	public void setActiveFTP(){
		enterLocalActiveMode();
		System.out.println("Modo conexão FTP ativa ativado.");
	}

	public void setPassiveFTP(){
		enterLocalPassiveMode();
		System.out.println("Modo conexão FTP passiva ativado.");
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
