package net.andreu;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import javax.swing.JScrollPane;

public class App {

	private JFrame frame;
	private JTextField tbxTitol;
	private JTextField tbxUrl;
	private JComboBox<String> cbxCategoria;
	private JList<String> list;
	private DefaultListModel<String> listModel;
	
	private String[] categories = {"Cercador", "Descarregues", "Institut"};

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App window = new App();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public App() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 625, 353);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		tbxTitol = new JTextField();
		tbxTitol.setEditable(false);
		tbxTitol.setBounds(133, 21, 324, 20);
		frame.getContentPane().add(tbxTitol);
		tbxTitol.setColumns(10);
		
		JLabel lblTitol = new JLabel("Titol:");
		lblTitol.setFont(new Font("Serif", Font.PLAIN, 17));
		lblTitol.setBounds(53, 17, 46, 23);
		frame.getContentPane().add(lblTitol);
		
		JLabel lblUrl = new JLabel("Url:");
		lblUrl.setFont(new Font("Serif", Font.PLAIN, 17));
		lblUrl.setBounds(53, 52, 46, 14);
		frame.getContentPane().add(lblUrl);
		
		tbxUrl = new JTextField();
		tbxUrl.setBounds(133, 52, 324, 20);
		frame.getContentPane().add(tbxUrl);
		tbxUrl.setColumns(10);
		
		JButton btnCerca = new JButton("Cerca titol");
		btnCerca.setFont(new Font("Serif", Font.PLAIN, 17));
		btnCerca.setBounds(465, 48, 111, 23);
		frame.getContentPane().add(btnCerca);
		btnCerca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String url = tbxUrl.getText();
				if(url.equals("")){
					JOptionPane.showMessageDialog(frame,
						    "No se ha espacificat cap URL.",
						    "Warning",
						    JOptionPane.WARNING_MESSAGE);
				}else{
					obtinirTitol(url);
				}
			}
		});
		
		JLabel lblCategoria = new JLabel("Categoria:");
		lblCategoria.setFont(new Font("Serif", Font.PLAIN, 17));
		lblCategoria.setBounds(26, 79, 73, 23);
		frame.getContentPane().add(lblCategoria);
		
		cbxCategoria = new JComboBox<>(categories);
		cbxCategoria.setBounds(133, 83, 324, 20);
		frame.getContentPane().add(cbxCategoria);
		
		JButton btnAfegir = new JButton("Afegir");
		btnAfegir.setFont(new Font("Serif", Font.PLAIN, 17));
		btnAfegir.setBounds(89, 113, 83, 23);
		frame.getContentPane().add(btnAfegir);
		btnAfegir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String titol = tbxTitol.getText();;
				String url = tbxUrl.getText();
				String categoria = cbxCategoria.getSelectedItem().toString();;
				if(titol.equals("")){
					JOptionPane.showMessageDialog(frame,
						    "No se ha cercat el titol.",
						    "Warning",
						    JOptionPane.WARNING_MESSAGE);
				}else{
					listModel.addElement(titol + " ------------- " + url + " ------------- " + categoria);
					tbxTitol.setText("");
					tbxUrl.setText("");
				}
			}
		});
		
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setFont(new Font("Serif", Font.PLAIN, 17));
		btnEliminar.setBounds(257, 114, 108, 23);
		frame.getContentPane().add(btnEliminar);
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = list.getSelectedIndex();
				if(index == -1){
					JOptionPane.showMessageDialog(frame,
						    "No se ha seleccionat cap adreça.",
						    "Warning",
						    JOptionPane.WARNING_MESSAGE);
				}else{
					listModel.remove(index);
				}
			}
		});
		
		JButton btnDesar = new JButton("Desar");
		btnDesar.setFont(new Font("Serif", Font.PLAIN, 17));
		btnDesar.setBounds(453, 114, 101, 23);
		frame.getContentPane().add(btnDesar);
		btnDesar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Pagines pagines = null;
				List<Pagina> pagina = new ArrayList<Pagina>();
				for(int i=0; i<listModel.size(); i++){
					String[] taula = listModel.getElementAt(i).toString().split(" ------------- ");
					String titolPagina = taula[0];
					String urlPagina = taula[1];
					String categoriaPagina = taula[2];
					
					pagina.add((new Pagina(titolPagina, urlPagina, categoriaPagina)));
				}
				pagines = new Pagines(pagina);
				
				JAXBContext context;
				try {
					context = JAXBContext.newInstance(Pagines.class);
					Marshaller creador = context.createMarshaller();
			    	
			    	creador.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			    	//creador.marshal(pagines, System.out);
			    	creador.marshal(pagines, new File("src/main/resources/links.xml"));
				} catch (JAXBException e1) {
					e1.printStackTrace();
				}	
			}
		});
		
		listModel = new DefaultListModel<>();
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(26, 147, 550, 156);
		frame.getContentPane().add(scrollPane);
		
		list = new JList<>(listModel);
		scrollPane.setViewportView(list);
		
		carregaDades();
	}
	
	private void carregaDades() {
		File fitxer = new File("src/main/resources/links.xml");

		JAXBContext context;
		try {
			context = JAXBContext.newInstance(Pagines.class);
			Unmarshaller lector = context.createUnmarshaller();
			Pagines pagines = (Pagines) lector.unmarshal(fitxer);
			
			for(Pagina p : pagines.getPagines()){
				listModel.addElement(p.getTitol() + " ------------- " + p.getUrl() + " ------------- " + p.getCategoria());
			}
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void obtinirTitol(String url) {
		Document doc;
		try {
			tbxTitol.setText("");
			doc = Jsoup.connect(url).userAgent("Mozilla").get();
			String titol = doc.select("head").select("title").text();
			tbxTitol.setText(titol);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
