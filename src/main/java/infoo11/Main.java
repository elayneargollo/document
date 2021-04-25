package infoo11;

import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;

public class Main {
	
	IDocumentTarget document = new DocumentAdapterPDF();
	
	public void init()
	{
		try {
			
			String path = JOptionPane.showInputDialog("Informe o caminho do arquivo");
			
			if(path!=null) {
			
				File file = new File(path);
				if(!file.exists()) {
					JOptionPane.showMessageDialog(null, "Informe uma caminho válido", null,JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				document.getEditor(file);	
				return;
			}
				
			JOptionPane.showMessageDialog(null, "Campo obrigatório", null,JOptionPane.ERROR_MESSAGE);
			return;
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Erro ao carregar arquivo",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void plugin()
	{
		
	}

}
