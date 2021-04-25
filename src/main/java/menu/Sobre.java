package menu;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextPane;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class Sobre extends JFrame {

	private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sobre frame = new Sobre();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Sobre() {
		initComponent();
		createButtonConfirmation();
	}
	
	public void initComponent()
	{
		setResizable(false);
		setTitle("Sobre");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 370, 364);
		contentPane = new JPanel();

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		contentPane.setBackground(new Color(173, 216, 230));
		contentPane.setForeground(new Color(0, 153, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		JTextPane informacaoSobre = new JTextPane();
		informacaoSobre.setEditable(false);
		informacaoSobre.setEnabled(false);
		informacaoSobre.setText("Implementar um framework para editores de documentos, com especialização para visualização da primeira página de arquivos PDF.");
		informacaoSobre.setBounds(43, 164, 279, 73);
		informacaoSobre.setDisabledTextColor(SystemColor.inactiveCaptionText);
		informacaoSobre.setBackground(new Color(173, 216, 230));
		contentPane.add(informacaoSobre);
		
		JLabel lblLabel = new JLabel("");
		lblLabel.setIcon(new ImageIcon("/home/elayne/Imagens/logo_ifba_transp.png"));
		lblLabel.setBounds(30, 12, 357, 125);
		contentPane.add(lblLabel);
	}
	
	public void createButtonConfirmation()
	{
		JButton btnNewButton = new JButton("Ok");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
			}
		});
		
		btnNewButton.setBounds(114, 269, 117, 37);
		contentPane.add(btnNewButton);	
	}
}
