package socketconnection;
import java.io.File;
import java.io.IOException;

public class Arquivo{

	private File caminho;
	private File arquivo;

	public Arquivo(String caminho) throws IOException{	
		this.caminho = new File(caminho);
		createPath();
	}

	public void createPath() throws IOException{
		if (!getCaminho().exists()) {
			getCaminho().mkdirs();		
		}
	}
	
	public void createFile() throws IOException{
		setArquivo(new File(getCaminho().getPath() + "\\lista.txt"));
		getArquivo().createNewFile();
	}

	public File getCaminho() {
		return caminho;
	}

	public void setArquivo(File arquivo) {
		this.arquivo = arquivo;
	}

	public File getArquivo() {
		return arquivo;
	}
	
	
}
