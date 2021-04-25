package infoo11;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class DocumentPDF extends JFrame {

	private String conteudoDocument;
	private JTextField textField;
	private int pagina = 1;
	private int maxPagina;

	private static final long serialVersionUID = 1L;

	/* Método para abrir o documento */
	public void OpenDocumentPDF(File file) throws IOException {

		try (PDDocument doc = PDDocument.load(file)) {

			System.out.println(">>>>>>>>> PDF Carregado  >>>>>>>>>>>>>>>>>>");

			PDFTextStripper stripper = new PDFTextStripper();

			stripper.setStartPage(pagina);
			stripper.setEndPage(pagina);

			conteudoDocument = stripper.getText(doc);
			maxPagina = doc.getNumberOfPages();

			doc.save(file.getAbsoluteFile());
			doc.close();

			System.out.println(">>>>>>>>> PDF Fechado  >>>>>>>>>>>>>>>>>>");
		}
	}

	/* Método para editar o documento */
	public void EditDocumentPDF(final File file) throws IOException {

		/* Criação dos componentes de tela */
		JPanel contentPane = new JPanel();
		JTextArea textArea = new JTextArea();
		JButton voltarPAginaAnterior = new JButton("Anterior");
		JButton avancarProximaPagina = new JButton("Próxima");
		JButton update = new JButton("Salvar");

		/* Exibição dos componentes de tela */
		this.initComponent(contentPane);
		this.buttonPaginaAnterior(file, voltarPAginaAnterior, textArea);
		this.buttonProximaPagina(file, avancarProximaPagina, textArea);
		this.paginacao(contentPane, textArea);

		voltarPAginaAnterior.setBounds(31, 675, 117, 41);
		contentPane.add(voltarPAginaAnterior);

		/* Salvar PDF */
		this.buttonSalvarPDF(update, contentPane);

		update.setBounds(270, 675, 117, 41);
		contentPane.add(update);

		avancarProximaPagina.setBounds(545, 675, 117, 41);
		contentPane.add(avancarProximaPagina);

	}

	public void initComponent(JPanel contentPane) {

		setTitle("Document PDF Editor");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 707, 762);

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	}

	public void buttonPaginaAnterior(final File file, JButton voltarPAginaAnterior, final JTextArea textArea) {

		voltarPAginaAnterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				System.out.println(">>>>>>>>> Voltando para pagina anterior  >>>>>>>>>>>>>>>>>>");

				try {

					if (pagina - 1 <= maxPagina && pagina - 1 > 0) {
						pagina = pagina - 1;
						textField.setText(pagina + " de " + maxPagina);
						OpenDocumentPDF(file);
					}

					else {
						System.out.println(">>>>>>>>> Sem página anterior  >>>>>>>>>>>>>>>>>>\n");
						JOptionPane.showMessageDialog(null, "Não há mais paginas anteriores");
					}

				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}

				textArea.setText(conteudoDocument);
			}
		});

	}

	public void buttonProximaPagina(final File file, JButton avancarProximaPagina, final JTextArea textArea) {

		avancarProximaPagina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				System.out.println(">>>>>>>>> Avançando para próxima pagina  >>>>>>>>>>>>>>>>>>");

				try {

					if (maxPagina >= pagina + 1) {

						pagina = pagina + 1;
						textField.setText(pagina + " de " + maxPagina);

						OpenDocumentPDF(file);

					} else {
						System.out.println(">>>>>>>>> Sem próxima página  >>>>>>>>>>>>>>>>>>\n");
						JOptionPane.showMessageDialog(null, "Não há próximas páginas");
					}

				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}

				textArea.setText(conteudoDocument);
			}
		});

	}

	public void paginacao(JPanel contentPane, final JTextArea textArea) {

		textArea.setBounds(31, 45, 631, 618);
		contentPane.add(textArea);
		textArea.setToolTipText("");
		textArea.setText(conteudoDocument);

		textField = new JTextField();
		textField.setEnabled(false);
		textField.setEditable(false);
		textField.setBounds(25, 10, 60, 18);
		contentPane.add(textField);
		textField.setColumns(10);
		textField.setBackground(Color.DARK_GRAY);
		textField.setText(pagina + " de " + maxPagina);
	}

	public void buttonSalvarPDF(JButton update, final JPanel contentPane) {

		update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				System.out.println(">>>>>>>>> Salvar PDF  >>>>>>>>>>>>>>>>>>");

				Object[] options = { "Yes, please", "No, thanks", "Cancel" };

				int optionStatusFile = JOptionPane.showOptionDialog(contentPane,
						"Do you really want to change this file?", "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, options, options[2]);

				switch (optionStatusFile) {

				case 0:
					System.out.println(">>>>>>>>> PDF Editado e Salvo >>>>>>>>>>>>>>>>>>");
					dispose();
					break;
				case 1:
				case 2:
					System.out.println(">>>>>>>>> PDF Salvo >>>>>>>>>>>>>>>>>>");
					dispose();
					break;
				}

			}
		});

	}

}
