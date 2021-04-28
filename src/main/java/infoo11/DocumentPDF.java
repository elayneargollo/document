package infoo11;

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
import javax.swing.JScrollBar;
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

	private JTextField textField;
	private int pagina = 1;
	private int maxPagina;
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("deprecation")
	public void OpenDocumentPDF(File file) throws IOException {

		final int NUM_MINIMO_PAG = 1;

		try (PDDocument doc = PDDocument.load(file)) {
			String pageText = JOptionPane.showInputDialog("Edit Document");

			if (pageText != null) {
				String pageNumber = JOptionPane.showInputDialog("Number page document edit");
				if (pageNumber != null) {
					int pag = Integer.parseInt(pageNumber) - NUM_MINIMO_PAG;

					if (pag >= maxPagina || pag < 0) {
						JOptionPane.showMessageDialog(null, "Escolha uma numeração válida", "Erro ao salvar",
								JOptionPane.ERROR_MESSAGE);
						dispose();
						return;
					}

					PDPage page = doc.getPage(pag);
					PDFont font = PDType1Font.TIMES_ROMAN;
					PDPageContentStream contentStream = new PDPageContentStream(doc, page, true, true);

					contentStream.beginText();
					contentStream.setFont(font, 12);
					contentStream.newLineAtOffset(100, 700);
					contentStream.showText(pageText);
					contentStream.endText();
					contentStream.close();

					doc.save(file);
					doc.close();
				}
			} else {
				JOptionPane.showMessageDialog(null, "Operação cancelada", "Aviso", JOptionPane.CANCEL_OPTION);
			}

			JOptionPane.showMessageDialog(null, "Arquivo editado com Sucesso", null, JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Erro ao salvar", JOptionPane.ERROR_MESSAGE);
		}

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
		this.paginacao(file, contentPane);

		voltarPAginaAnterior.setBounds(12, 802, 150, 41);
		contentPane.add(voltarPAginaAnterior);

		this.buttonEditarPDF(update, contentPane, file);

		update.setBounds(257, 802, 150, 41);
		contentPane.add(update);

		avancarProximaPagina.setBounds(489, 802, 150, 41);
		contentPane.add(avancarProximaPagina);

		frameEditorDocument.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
		
	    return frameEditorDocument;

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

				try {

					if (pagina - 1 <= maxPagina && pagina - 1 > 0) {
						pagina = pagina - 1;
						paginacao(file, contentPane);
					} else {
						JOptionPane.showMessageDialog(null, "Não há mais paginas anteriores");
					}
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Erro ao retornar para página",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});

	}

	private void buttonProximaPagina(final File file, JButton avancarProximaPagina, final JPanel contentPane) {

		avancarProximaPagina.setIcon(new ImageIcon("/home/elayne/Imagens/icons8-direita-32.png"));
		avancarProximaPagina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (maxPagina >= pagina + 1) {
					pagina = pagina + 1;

					try {
						paginacao(file, contentPane);
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Não há próximas páginas", "Aviso",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

	}

	private void paginacao(final File file, JPanel contentPane) throws InvalidPasswordException, IOException {

		JTextPane textArea = new JTextPane();
		String conteudoDocument;

		try (PDDocument doc = PDDocument.load(file)) {

			PDFTextStripper stripper = new PDFTextStripper();

			stripper.setStartPage(pagina);
			stripper.setEndPage(pagina);

			conteudoDocument = stripper.getText(doc);
			maxPagina = doc.getNumberOfPages();
			
			doc.close();

		}

		textArea.setBounds(12, 51, 627, 745);
		contentPane.add(textArea);
		textArea.setToolTipText("");
		textArea.setText(conteudoDocument);
		textArea.setEditable(false);
		textArea.setEnabled(false);
		textArea.setDisabledTextColor(SystemColor.inactiveCaptionText);

		textField = new JTextField();
		textField.setEnabled(false);
		textField.setEditable(false);
		
		 JScrollBar s = new JScrollBar();  
		 s.setBounds(12, 51, 627, 745);  
		 contentPane.add(s);  

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
		
		textField.setText(" " + pagina + " de " + maxPagina);
	}

	private void buttonEditarPDF(JButton update, final JPanel contentPane, final File file) {

		update.setIcon(new ImageIcon("/home/elayne/Imagens/icons8-salvar-como-16.png"));
		
		update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					OpenDocumentPDF(file);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
				}

				dispose();
			}
		});

	}

}