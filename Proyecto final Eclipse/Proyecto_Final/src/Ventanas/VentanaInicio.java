package Ventanas;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Datos.BaseDatos;
import Operaciones.InsercionContraseñas;
import factory.DAOFactory;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.JButton;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@SuppressWarnings("serial")
public class VentanaInicio extends JFrame {

	private JPanel contentPane;
	private static JProgressBar progressBar;
	private static JLabel informacion;
	public static BaseDatos bd = new BaseDatos();
	private static VentanaInicio frame;
	private JLabel lblNewLabel;
	public 	static DAOFactory bdFactory;
	private static JButton cerrarSesion;
	private static InsercionContraseñas contraseña = new InsercionContraseñas();
	private static File fichero  = new File ("comprobacionContraseña.txt");;

	/**
	 * Llama al método BarraProgreso para hacerla avanzar y crea un objeto de tipo BaseDatos
	 * para iniciar la conexión.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@SuppressWarnings("static-access")
			public void run() {
				try {
					frame = new VentanaInicio();
				//	frame.<setUndecorated(true);
					frame.setVisible(true);
					bdFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
					BarraProgreso();
					bd.creaConexion();
					insercionContraseña();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	/**
	 * Cierra la conexión con la base de datos si el usuario presiona el botón de "x" para cerrar la ventana en 
	 * vez de cerrar sesión.
	 */
	public VentanaInicio() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				bd.cierraConexion();  
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaInicio.class.getResource("/Imagenes/icons8-estetoscopio-48.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 487, 356);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		informacion = new JLabel("");
		informacion.setEnabled(false);
		informacion.setBounds(162, 230, 182, 14);
		contentPane.add(informacion);
		
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setForeground(new Color(0, 102, 153));
		progressBar.setBounds(35, 196, 368, 22);
		contentPane.add(progressBar);
		
		
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(VentanaInicio.class.getResource("/Imagenes/7d00640c-c299-4340-8d03-c84ea5e27cf3_200x200.png")));
		lblNewLabel_1.setBounds(114, 11, 210, 147);
		contentPane.add(lblNewLabel_1);
		
		cerrarSesion = new JButton("Cerrar sesi\u00F3n");
		cerrarSesion.setIcon(new ImageIcon(VentanaInicio.class.getResource("/Imagenes/icons8-exportar-30.png")));
		cerrarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				bd.cierraConexion();
				dispose();
			}
		});
		cerrarSesion.setEnabled(false);
		cerrarSesion.setVisible(false);
		cerrarSesion.setBounds(107, 194, 210, 23);
		contentPane.add(cerrarSesion);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(VentanaInicio.class.getResource("/Imagenes/53143755fa1f7f6113fdc5ecb8580ef4.jpg")));
		lblNewLabel.setBounds(0, 0, 471, 317);
		contentPane.add(lblNewLabel);
	
		JLabel lblImagen = new JLabel();
		lblImagen.setBounds(0, 0, 471, 317);
		
	

		
	}
	
	/**
	 * Método para hacer avanzar la barra de progreso e imprimir en ella el porcentaje avanzado.
	 * Se usa un Timer para ello.
	 */
	
	public static  void BarraProgreso() {
		Timer timer = null; // Variable del timer

		timer = new Timer(1000, new ActionListener() { // Definimos que el contador realize la funcion cada
														// 100milisegundos
			int start = 0;

			@Override
			public void actionPerformed(ActionEvent e) {
				start = start + 20; // Sumamos 1 a la variable start
				if (start == 100) { // Si start es 100 se muestra la ventana de inicio de sesión	
					informacion.setVisible(false);
					progressBar.setVisible(false);
					VentanaAutentificacion dialog = new VentanaAutentificacion();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
					cerrarSesion.setEnabled(true);
					cerrarSesion.setVisible(true);
				}
				
				informacion.setEnabled(true);
				informacion.setText("Cargando sistema...");
				progressBar.setValue(start); // Colocar el valor de start en el progressbar, cada ciclo sumara 20 hasta
												// llegar a 100
			}
		});
		timer.start();
	}
	
	public static DAOFactory getDAO() {
		return bdFactory;
	}
	
	/**
	 * Lo utilizo para ejecutar solo una vez la clase InsercionContraseñas. En la base de datos tengo una tabla llamada
	 * insercionContraseña que únicamente contiene un entero. Si este entero está a 0 significa que ningún usuario tiene
	 * asignado ninguna contraseña aún y si está a 1, significa que si la tienen.
	 * Este método comprueba si dicha variable está a 0 y en caso de que lo esté, actualiza las contraseñas y  pone la variable a 1.
	 * Así se evita que las contraseñas sean actualizadas cada vez que el usuario ejecute el programa.
	 */
	public static void insercionContraseña() {
		Connection conexion = bd.devuelveConexion();
		try {
			Statement sentencia = conexion.createStatement();
			ResultSet rs = sentencia.executeQuery("select * from insercionContraseña");
			int insertada = -1;
			while(rs.next()) {
				insertada = rs.getInt(1);
			}
			
			if(insertada == 0) {
				contraseña.creaContraseña();
				sentencia.executeUpdate("UPDATE INSERCIONCONTRASEÑA SET contraseñaInsertada = 1");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}

