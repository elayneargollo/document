package interfaces;

import java.io.File;
import java.io.IOException;
import javax.swing.JFrame;

public interface IDocumentTarget {
	public abstract void open(File file) throws IOException;
	public abstract JFrame getEditor(File file) throws IOException;
}
