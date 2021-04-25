package infoo11;

import java.io.File;
import java.io.IOException;

public interface IDocumentTarget {
	public abstract void open(File file) throws IOException;
	public abstract void getEditor(File file) throws IOException;
}
