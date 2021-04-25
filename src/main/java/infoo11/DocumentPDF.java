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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.text.PDFTextStripper;

public class DocumentPDF extends JFrame {

	private String conteudoDocument;
	private JTextField textField;
	private int pagina = 1;
	private int maxPagina;

	private static final long serialVersionUID = 1L;

	/* Método para abrir o documento */
	@SuppressWarnings("deprecation")
	public void OpenDocumentPDF(File file) throws IOException {
		
		final int NUM_MINIMO_PAG = 1;
		
		try(PDDocument doc = PDDocument.load(file))
		{
			String pageText = JOptionPane.showInputDialog("Edit Document");
			
			if(pageText != null)
			{
				String pageNumber = JOptionPane.showInputDialog("Number page document edit");
				if(pageNumber != null)
				{
					int pag = Integer.parseInt(pageNumber) - NUM_MINIMO_PAG;

					if(pag >= maxPagina || pag < 0)
					{
						JOptionPane.showMessageDialog(null, "Escolha uma numeração válida", "Erro ao salvar", JOptionPane.ERROR_MESSAGE);
						dispose();
						return;
					}

					PDPage page = doc.getPage(pag);
					PDFont font = PDType1Font.TIMES_ROMAN;
					PDPageContentStream contentStream = new PDPageContentStream(doc, page, true,true);
					
					contentStream.beginText();
					contentStream.setFont( font, 12 );
					contentStream.newLineAtOffset(100, 700);
					contentStream.showText(pageText);
					contentStream.endText();
					contentStream.close();
					
					doc.save(file);
					doc.close();
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Operação cancelada", "Aviso",JOptionPane.CANCEL_OPTION);
			}
			
			JOptionPane.showMessageDialog(null, "Arquivo editado com Sucesso", null, JOptionPane.INFORMATION_MESSAGE);
		}
		catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Erro ao salvar", JOptionPane.ERROR_MESSAGE);
		}		
		
	}

	/* Método para editar o documento */
	public void EditDocumentPDF(final File file) throws IOException {

		/* Criação dos componentes de tela */
		JPanel contentPane = new JPanel();
		JButton voltarPAginaAnterior = new JButton("Anterior");
		JButton avancarProximaPagina = new JButton("Próxima");
		JButton update = new JButton("Edit");

		/* Exibição dos componentes de tela */
		this.initComponent(contentPane);
		this.buttonPaginaAnterior(file, voltarPAginaAnterior, contentPane);
		this.buttonProximaPagina(file, avancarProximaPagina, contentPane);
		this.paginacao(file,contentPane);

		voltarPAginaAnterior.setBounds(31, 675, 150, 41);
		contentPane.add(voltarPAginaAnterior);

		/* Salvar PDF */
		this.buttonSalvarPDF(update, contentPane, file);

		update.setBounds(270, 675, 150, 41);
		contentPane.add(update);

		avancarProximaPagina.setBounds(515, 675, 150, 41);
		contentPane.add(avancarProximaPagina);	

	}

	public void initComponent(JPanel contentPane) {

		setTitle("Document PDF Editor");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 707, 762);

		contentPane.setBackground(new Color(173, 216, 230));
		contentPane.setForeground(new Color(0, 153, 255));

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	}

	public void buttonPaginaAnterior(final File file, JButton voltarPAginaAnterior, final JPanel contentPane) {

		voltarPAginaAnterior.setIcon(new ImageIcon("/home/elayne/Imagens/icons8-seta-longa-à-esquerda-32.png"));

		voltarPAginaAnterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				System.out.println(">>>>>>>>> Voltando para pagina anterior  >>>>>>>>>>>>>>>>>>");

				try {

					if (pagina - 1 <= maxPagina && pagina - 1 > 0) {
						pagina = pagina - 1;

						paginacao(file, contentPane);
					}

					else {
						System.out.println(">>>>>>>>> Sem página anterior  >>>>>>>>>>>>>>>>>>\n");
						JOptionPane.showMessageDialog(null, "Não há mais paginas anteriores");
					}

				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Erro ao retornar para página",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});

	}

	public void buttonProximaPagina(final File file, JButton avancarProximaPagina,  final JPanel contentPane) {

		avancarProximaPagina.setIcon(new ImageIcon("/home/elayne/Imagens/icons8-direita-32.png"));
	
		avancarProximaPagina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				System.out.println(">>>>>>>>> Avançando para próxima pagina  >>>>>>>>>>>>>>>>>>");

				if (maxPagina >= pagina + 1) {

					pagina = pagina + 1;

					try {
						paginacao(file, contentPane);
					} catch (IOException e) {
						e.printStackTrace();
					}

				} else {
					System.out.println(">>>>>>>>> Sem próxima página  >>>>>>>>>>>>>>>>>>\n");
					JOptionPane.showMessageDialog(null, "Não há próximas páginas\"", "Aviso",JOptionPane.INFORMATION_MESSAGE);
				}

			}
		});

	}

	public void paginacao(final File file, JPanel contentPane) throws InvalidPasswordException, IOException {
	
		JTextArea textArea = new JTextArea();
		
		try (PDDocument doc = PDDocument.load(file)) {
	
			PDFTextStripper stripper = new PDFTextStripper();
	
			stripper.setStartPage(pagina);
			stripper.setEndPage(pagina);
	
			conteudoDocument = stripper.getText(doc);
			maxPagina = doc.getNumberOfPages();
	
			doc.close();

		}

		textArea.setBounds(31, 45, 631, 620);
		contentPane.add(textArea);
		textArea.setToolTipText("");
		textArea.setText(conteudoDocument);
		textArea.setEditable(false);
		textArea.setEnabled(false);
		textArea.setDisabledTextColor(SystemColor.inactiveCaptionText);

		textField = new JTextField();
		textField.setEnabled(false);
		textField.setEditable(false);

		textField.setBounds(30, 10, 60, 30);
		contentPane.add(textField);
		textField.setColumns(10);
		textField.setDisabledTextColor(SystemColor.inactiveCaptionText);

		textField.setText(" " + pagina + " de " + maxPagina);
	}

	public void buttonSalvarPDF(JButton update, final JPanel contentPane, final File file) {

		update.setIcon(new ImageIcon("/home/elayne/Imagens/icons8-salvar-como-16.png"));

		update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					OpenDocumentPDF(file);
				} catch (IOException e) {
					e.printStackTrace();
				}

				dispose();
			}
		});

	}

}