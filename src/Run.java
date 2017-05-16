import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

import ftpconnection.FTPClient;

public class Run {

	private static boolean ftpConn = false;
	private static boolean socketConn = false;

	public static void main(String[] args) {

		Terminal console = new Terminal();

		System.out.println("\r----Transfer files in the Network. written by Matheus Buarque----");
		System.out.println("--------------------------(05-05-2017)---------------------------");
		System.out.println("Type 1 for Socket Connection or 2 for FTP Connection.");
		System.out.println("");
		Scanner sc = new Scanner(System.in);
		String command = sc.nextLine();
		readConnCommand(console, command, sc);
	}

	private static void readConnCommand(Terminal console, String command, Scanner sc) {
		if (command.equals("1")) {
			System.out.println("Socket Connection is selected.");
			waitSocketCommand(console, sc);
		} else {
			System.out.println("FTP Connection is selected.");
			waitFtpCommand(console, sc);
		}

	}

	private static void readFtpCommand(Terminal console, String command, Scanner sc) {

		switch (command) {
		case "init ftpconnection":
			console.initFtpConnection(sc);
			waitFtpCommand(console, sc);
			break;
		case "ftpconnection -auth":
			console.authFtp(sc);
			waitFtpCommand(console, sc);
			break;
		case "ftpconnection -filetype 1":
			console.setFileType(1);
			waitFtpCommand(console, sc);
			break;
		case "ftpconnection -filetype 2":
			console.setFileType(2);
			waitFtpCommand(console, sc);
			break;
		case "ftpconnection -filetype 3":
			console.setFileType(3);
			waitFtpCommand(console, sc);
			break;
		case "ftpconnection -mode active":
			console.setEnterMode(Terminal.ACTIVE_MODE);
			waitFtpCommand(console, sc);
			break;
		case "ftpconnection -mode passive":
			console.setEnterMode(Terminal.PASSIVE_MODE);
			waitFtpCommand(console, sc);
			break;
		case "quit":
			System.out.println("Fechando aplicação....");
			System.exit(0);
			break;
		case "ftpconnection -c":
			System.out.println("Fechando conexão com servidor....");
			console.disconnectServer();
			waitFtpCommand(console, sc);
			break;
		default:
			try {
				// Retrieve
				// Retrieve modo2
				if (!command.substring(0, command.indexOf("-f")).equals("ftpconnection -ret --m")) {

					// CHECAR ESTRUTURA

					if (command.indexOf("-d") == -1) {
						String arquivo = command.substring(command.indexOf("-f") + 3, command.lastIndexOf("'"))
								.replaceAll("'", "");
						console.retrieveFStream(arquivo);
						waitFtpCommand(console, sc);
					}
					String arquivo = command.substring(command.indexOf("-f") + 3, command.indexOf("-d")).trim()
							.replaceAll("'", "");
					String caminho = command.substring(command.indexOf("-d") + 2, command.length()).trim()
							.replaceAll("'", "");

					console.retrieveFromFile(arquivo, caminho);
					waitFtpCommand(console, sc);
				} else if (!command.substring(0, command.indexOf("-f")).equals("ftpconnection -ret")) {
					// Retrieve modo1

					String arquivo = command.substring(command.indexOf("-f") + 3, command.lastIndexOf("'"))
							.replaceAll("'", "");
					String caminho = command.substring(command.indexOf("-d") + 2, command.length()).trim()
							.replaceAll("'", "");

					console.retrieveFStream(arquivo, caminho);
					waitFtpCommand(console, sc);
				}
			} catch (Exception e) {
				System.out.println("Comando não reconhecido!");
				waitFtpCommand(console, sc);
			}
			break;
		}
		System.out.println("Comando não reconhecido!");
		waitFtpCommand(console, sc);
	}

	private static void readSocketCommand(Terminal console, String command, Scanner sc) {
		switch (command) {
		case "init socketconnection":
			console.initSocketConnection(sc);
			waitSocketCommand(console, sc);
			break;
		case "socketconnection -getfile":
			console.socketGetFile();
			waitSocketCommand(console, sc);
			break;
		default:
			break;
		}
	}

	private static void waitFtpCommand(Terminal console, Scanner sc) {
		sc = new Scanner(System.in);
		System.out.println("Digite um comando:");
		String command = sc.nextLine();
		readFtpCommand(console, command, sc);
	}

	private static void waitSocketCommand(Terminal console, Scanner sc) {
		sc = new Scanner(System.in);
		System.out.println("Digite um comando:");
		String command = sc.nextLine();
		readSocketCommand(console, command, sc);
	}

}
