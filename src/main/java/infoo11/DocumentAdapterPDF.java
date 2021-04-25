package infoo11;

import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

public class DocumentAdapterPDF extends DocumentPDF implements IDocumentTarget {

	private static final long serialVersionUID = 1L;

	public void open(File file) throws IOException {
		this.OpenDocumentPDF(file);
	}

	public void getEditor(File file) throws IOException {
		this.EditDocumentPDF(file);
	}

}
