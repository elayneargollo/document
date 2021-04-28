package infoo11;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main {

	@SuppressWarnings("unused")
	public void init() {

		String path = JOptionPane.showInputDialog("Informe o caminho do arquivo");

		if (path != null) {

			File file = new File(path);

			if (!file.exists()) {
				JOptionPane.showMessageDialog(null, "Informe uma caminho válido", null, JOptionPane.ERROR_MESSAGE);
				return;
			}

			String extensaoDoArquivo = path.substring(path.lastIndexOf(46) + 1);
			ArrayList<String> extensao = new ArrayList<String>();
			URL[] jars = loadJUrl(extensao);

			if (extensao.contains(extensaoDoArquivo)) {

				IDocumentTarget pdf = new DocumentAdapterPDF();

				try {
					
					JFrame frame = pdf.getEditor(file);
					frame.setVisible(true);
					
				} catch (IOException e) {
					e.printStackTrace();
				}

			} else {
				JOptionPane.showMessageDialog(null, "Arquivo não suportado.");
			}
		}

	}

	@SuppressWarnings("deprecation")
	public static URL[] loadJUrl(ArrayList<String> extensao) {

		File file = new File("./plugin");

		String[] plugins = file.list();
		URL[] jars = new URL[plugins.length];
		for (int position = 0; position < plugins.length; position++) {

			try {
				jars[position] = (new File("./plugin/" + plugins[position])).toURL();
				String nomeDoPlugin = jars[position].toString()
						.substring(jars[position].toString().lastIndexOf('/') + 1).replace(".jar", "");
				extensao.add(nomeDoPlugin);
			} catch (MalformedURLException e) {
				JOptionPane.showMessageDialog(null, "Error");
			}
		}

		return jars;
	}


}
