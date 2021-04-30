package documentPDF;

import java.io.File;
import java.io.IOException;
import javax.swing.JFrame;

import interfaces.IDocumentTarget;

public class DocumentAdapterPDF extends DocumentPDF implements IDocumentTarget {

	public DocumentAdapterPDF(File file) {
		super(file);
	}

	private static final long serialVersionUID = 1L;

	public void open(File file) throws IOException {
		this.OpenDocumentPDF(file);
	}

	public JFrame getEditor(File file) throws IOException {
		return this.EditDocumentPDF(file);
	}

}
