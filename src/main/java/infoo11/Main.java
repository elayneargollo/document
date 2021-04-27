package infoo11;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JOptionPane;

public class Main {

	IDocumentTarget document = new DocumentAdapterPDF();
	String path = null;

	public void init() {

		try {

			String path = JOptionPane.showInputDialog("Informe o caminho do arquivo");

			if (path != null) {

				File file = new File(path);

				if (!file.exists()) {
					JOptionPane.showMessageDialog(null, "Informe uma caminho válido", null, JOptionPane.ERROR_MESSAGE);
					return;
				}

				document.getEditor(file);
				return;
			}

			JOptionPane.showMessageDialog(null, "Campo obrigatório", null, JOptionPane.ERROR_MESSAGE);
			return;

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Erro ao carregar arquivo", JOptionPane.ERROR_MESSAGE);
		}

	}

	@SuppressWarnings({ "deprecation", "unused" })
	public URL[] plugin() {

		String extensionFile[] = path.split("\\.");
		File file = new File("./plugin");

		String[] plugins = file.list();
		URL[] jars = new URL[plugins.length];

		for (int position = 0; position < plugins.length; position++) {

			try {
				jars[position] = (new File("./plugin/" + plugins[position])).toURL();
			} catch (MalformedURLException e) {
				JOptionPane.showMessageDialog(null, "Error verifying .jar file !");
			}
		}

		return jars;
	}

}
