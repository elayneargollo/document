package documentPDF;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.text.PDFTextStripper;

public class DocumentPDF extends JFrame {

	private int page = 1;
	private int totalPages;
	private PDFTextStripper stripper;
	private PDDocument document;
	private JTextPane textArea;
	private JTextField textField;
	private static final long serialVersionUID = 1L;

	protected DocumentPDF(File file) {
		
		try {
			
			this.document = PDDocument.load(file);
			this.stripper = new PDFTextStripper();
			this.totalPages = document.getNumberOfPages();
			this.textArea = new JTextPane();
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}

	}
	
	@SuppressWarnings("deprecation")
	public void OpenDocumentPDF(File file) throws IOException {

		final int NUM_MINIMUM_PAG = 1;
		String pageText = JOptionPane.showInputDialog("Edit Document");

		if (pageText != null) {
			String pageNumber = JOptionPane.showInputDialog("Number page document edit");
			
			if (pageNumber != null) {
				int pagechosen = Integer.parseInt(pageNumber) - NUM_MINIMUM_PAG;

				if (pagechosen >= totalPages || pagechosen < 0) {
					JOptionPane.showMessageDialog(null, "Choose a valid number", "Error", JOptionPane.ERROR_MESSAGE);
					dispose();
					return;
				}

				PDPage page = document.getPage(pagechosen);
				PDFont font = PDType1Font.TIMES_ROMAN;
				PDPageContentStream contentStream = new PDPageContentStream(document, page, true, true);

				contentStream.beginText();
				contentStream.setFont(font, 12);
				contentStream.newLineAtOffset(100, 700);
				contentStream.showText(pageText);
				contentStream.endText();
				contentStream.close();

				document.save(file);
				document.close();
			}
		} else {
			JOptionPane.showMessageDialog(null, "Operation canceled", "Notice", JOptionPane.CANCEL_OPTION);
		}

		JOptionPane.showMessageDialog(null, "Successfully edited file", null, JOptionPane.INFORMATION_MESSAGE);

	}

	public JFrame EditDocumentPDF(final File file) throws IOException {
		
	    JFrame frameEditorDocument = new JFrame();   
		JPanel contentPane = new JPanel();
		JButton voltarPAginaAnterior = new JButton("Anterior");
		JButton avancarProximaPagina = new JButton("Próxima");
		JButton update = new JButton("Edit");

		this.initComponent(contentPane);
		this.buttonPaginaAnterior(file, voltarPAginaAnterior, contentPane);
		this.buttonProximaPagina(file, avancarProximaPagina, contentPane);
		this.getPageContentStart(file, contentPane);
		this.buttonEditarPDF(update, contentPane, file);
		this.textArea.setText(updateDocumentForPage(1));

		voltarPAginaAnterior.setBounds(12, 802, 150, 41);
		contentPane.add(voltarPAginaAnterior);
		update.setBounds(257, 802, 150, 41);
		contentPane.add(update);
		avancarProximaPagina.setBounds(489, 802, 150, 41);
		contentPane.add(avancarProximaPagina);
		frameEditorDocument.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
		
	    return frameEditorDocument;

	}
	
	private String updateDocumentForPage(int page) throws IOException
	{
		stripper.setStartPage(page);
		stripper.setEndPage(page);
		
		return stripper.getText(document);
	}
	
	private void initComponent(JPanel contentPane) {

		setTitle("Document PDF Editor");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 653, 889);
		setResizable(false);

		contentPane.setBackground(new Color(173, 216, 230));
		contentPane.setForeground(new Color(0, 153, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
	}

	private void buttonPaginaAnterior(final File file, JButton voltarPAginaAnterior, final JPanel contentPane) {

		voltarPAginaAnterior.setIcon(new ImageIcon("/home/elayne/Imagens/icons8-seta-longa-à-esquerda-32.png"));
		
		voltarPAginaAnterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (page - 1 <= totalPages && page - 1 > 0) {
					page = page - 1;
					
					try {
						textArea.setText(updateDocumentForPage(page));
						textField.setText(" " + page + " in " + totalPages);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				} else {
					JOptionPane.showMessageDialog(null, "No more previous pages");
				}

			}
		});
	}

	private void buttonProximaPagina(final File file, JButton avancarProximaPagina, final JPanel contentPane) {

		avancarProximaPagina.setIcon(new ImageIcon("/home/elayne/Imagens/icons8-direita-32.png"));
		
		avancarProximaPagina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				if (totalPages >= page + 1) {
					page = page + 1;
				
					try {
						textArea.setText(updateDocumentForPage(page));
						textField.setText(" " + page + " in " + totalPages);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
				} else {
					JOptionPane.showMessageDialog(null, "No next pages ", "Notice",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

	}

	private void getPageContentStart(final File file, JPanel contentPane) throws InvalidPasswordException, IOException {

		textArea.setBounds(12, 51, 627, 745);
		contentPane.add(textArea);
		textArea.setToolTipText("");
		textArea.setEditable(false);
		textArea.setEnabled(false);
		textArea.setDisabledTextColor(SystemColor.inactiveCaptionText);
		textField = new JTextField();
		textField.setEnabled(false);
		textField.setEditable(false);
		textField.setBounds(12, 9, 60, 30); 
		contentPane.add(textField);
		textField.setColumns(10);
		textField.setDisabledTextColor(SystemColor.inactiveCaptionText);
		
		StyledDocument doc = textArea.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setLineSpacing(center, 0.3F); 
		StyleConstants.setFontSize(center, 12); 
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_JUSTIFIED);
		doc.setParagraphAttributes(1, doc.getLength(), center, false);
		
		textField.setText(" " + page + " in " + totalPages);
	}

	private void buttonEditarPDF(JButton update, final JPanel contentPane, final File file) {

		update.setIcon(new ImageIcon("/home/elayne/Imagens/icons8-salvar-como-16.png"));
		
		update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					OpenDocumentPDF(file);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}

				dispose();
			}
		});

	}

}