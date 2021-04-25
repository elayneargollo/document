package infoo11;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import menu.Sobre;

@SuppressWarnings("serial")
public class TelaInicial extends JFrame {

	private JPanel contentPane;
	private JButton btnOpen;
	private JLabel lblOpen;
	private JButton btnLinkProjectGitHub;
	private JLabel lblProject;
	private JButton btnHome;
	private JLabel lblHome;
	private JButton btnSobre;
	private JLabel lblSobre;
	private JButton btnSair;
	private JLabel lblSair;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaInicial frame = new TelaInicial();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public TelaInicial() {

		initComponent();
		createButtonOpenDocument();
		createButtonLinkGitHub();
		createButtonHome();
		createButtonSobre();
		createButtonSair();
	}

	public void initComponent() {
		setResizable(false);
		setTitle("Editor de Documento");
		setBackground(new Color(100, 149, 237));
		setIconImage(Toolkit.getDefaultToolkit().getImage("/home/elayne/Imagens/fundo.jpg"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 741, 412);
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(173, 216, 230));
		contentPane.setForeground(new Color(0, 153, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	}

	public void createButtonOpenDocument() {

		btnOpen = new JButton("");

		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				Main main = new Main();
				main.init();
			}
		});

		btnOpen.setIcon(new ImageIcon(TelaInicial.class.getResource("/images/icons8-abrir-visualização-em-nova-aba-64.png")));
		btnOpen.setBounds(283, 41, 179, 105);
		contentPane.add(btnOpen);

		lblOpen = new JLabel("Open Document");
		lblOpen.setBounds(309, 145, 153, 34);
		contentPane.add(lblOpen);
	}

	public void createButtonLinkGitHub() {
		btnLinkProjectGitHub = new JButton("");
		btnLinkProjectGitHub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				URI link;

				try {

					link = new URI("https://github.com/elayneargollo/document");
					Desktop.getDesktop().browse(link);
					return;
				} catch (URISyntaxException | IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		btnLinkProjectGitHub.setIcon(new ImageIcon(TelaInicial.class.getResource("/images/icons8-link-64.png")));
		btnLinkProjectGitHub.setBounds(122, 236, 184, 105);
		contentPane.add(btnLinkProjectGitHub);

		lblProject = new JLabel("Project Link");
		lblProject.setBounds(155, 340, 115, 34);
		contentPane.add(lblProject);
	}

	public void createButtonHome() {
		btnHome = new JButton("");
		btnHome.setIcon(new ImageIcon(TelaInicial.class.getResource("/images/icons8-casa-64.png")));
		btnHome.setBounds(39, 41, 179, 105);
		contentPane.add(btnHome);

		lblHome = new JLabel("Home");
		lblHome.setBounds(109, 145, 49, 34);
		contentPane.add(lblHome);

	}

	public void createButtonSobre() {

		btnSobre = new JButton("");
		btnSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Sobre sobre = new Sobre();
				sobre.setVisible(true);
			}
		});

		btnSobre.setIcon(new ImageIcon(TelaInicial.class.getResource("/images/icons8-ponto-de-interrogação-64.png")));
		btnSobre.setBounds(519, 41, 179, 105);
		contentPane.add(btnSobre);

		lblSobre = new JLabel("Sobre");
		lblSobre.setBounds(575, 145, 73, 34);
		contentPane.add(lblSobre);

	}

	public void createButtonSair() {
		btnSair = new JButton("");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				dispose();
				System.exit(0);
			}
		});

		btnSair.setIcon(new ImageIcon(TelaInicial.class.getResource("/images/icons8-cursor-da-mão-64.png")));
		btnSair.setBounds(418, 236, 179, 105);
		contentPane.add(btnSair);

		lblSair = new JLabel("Sair");
		lblSair.setBounds(497, 340, 49, 34);
		contentPane.add(lblSair);
	}

}
