package infoo11;

import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

public interface IDocumentTarget {
	public abstract void open(File file) throws IOException;
	public abstract void getEditor(File file) throws IOException;
}
