package icai.dtc.isw.ui;

import icai.dtc.isw.client.Client;
import icai.dtc.isw.domain.Customer;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Random;



public class JVentana extends JFrame {
//hola
    public static void main(String[] args) {
        new JVentana();
    }

    private String id;
    public JVentana() {
        super("FUEL MAP");
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400,300);
        this.setResizable(true);

        // Panel superior con el título
        JPanel pnlNorte = new JPanel();
        pnlNorte.setBackground(new Color(0x2E8B57));
        JLabel lblTitulo = new JLabel("Bienvenido", SwingConstants.CENTER);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Times new Roman", Font.BOLD, 24));
        pnlNorte.add(lblTitulo);
        pnlNorte.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.add(pnlNorte, BorderLayout.NORTH);

        // Panel central con los botones "Registrar" y "Login"
        JPanel pnlCentro = new JPanel();
        pnlCentro.setLayout(new GridLayout(1, 2));
        pnlCentro.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        pnlCentro.setBackground(Color.WHITE);
        JButton btnRegister = new JButton("Registrar");
        JButton btnLogin = new JButton("Login");
        //btnRegister.setPreferredSize(new Dimension(100, 50));
       // btnLogin.setPreferredSize(new Dimension(100, 50));
        styleButton(btnRegister);
        styleButton(btnLogin);

        pnlCentro.add(btnRegister);
        pnlCentro.add(btnLogin);
        this.add(pnlCentro, BorderLayout.CENTER);

        // Acciones de los botones
        btnRegister.addActionListener(e -> new VentanaRegistro());
        btnLogin.addActionListener(e -> new VentanaLogin());

        // Configuración final de la ventana principal
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    // Estilización de botones
    private void styleButton(JButton button) {
        button.setPreferredSize(new Dimension(150, 50));
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setBackground(new Color(0x5F9EA0)); // Color aguamarina
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(0x2E8B57), 2, true));
    }
    // Ventana de Registro
    private class VentanaRegistro extends JFrame {
        public VentanaRegistro() {
            setTitle("Registro de Usuario");
            setSize(400, 300);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new GridLayout(5, 2,10,10));

            // Crear los campos de entrada
            JLabel lblName = new JLabel("Nombre:");
            JTextField txtName = new JTextField();
            JLabel lblId = new JLabel("ID:");
            JTextField txtId = new JTextField();
            JLabel lblEmail = new JLabel("Email:");
            JTextField txtEmail = new JTextField();
            JLabel lblPassword = new JLabel("Contraseña:");
            JPasswordField txtPassword = new JPasswordField();
            JButton btnContinuar = new JButton("Registrar");

            styleLabel(lblName);
            styleLabel(lblId);
            styleLabel(lblEmail);
            styleLabel(lblPassword);
            styleButton(btnContinuar);
            // Añadir los componentes al formulario
            add(lblName); add(txtName);
            add(lblId); add(txtId);
            add(lblEmail); add(txtEmail);
            add(lblPassword); add(txtPassword);
            add(new JLabel()); // Espacio vacío
            add(btnContinuar);

            // Acción del botón "Registrar"
            btnContinuar.addActionListener(e -> {
                String name = txtName.getText();
                String id = txtId.getText();
                String email = txtEmail.getText();
                String password = new String(txtPassword.getPassword());
                registerCustomer(email, password, name, id);
                JOptionPane.showMessageDialog(this, "Registro exitoso");
                dispose();
            });

            setLocationRelativeTo(null);
            setVisible(true);
        }
    }

    // Ventana de Login
    private class VentanaLogin extends JFrame {
        public VentanaLogin() {
            setTitle("Login de Usuario");
            setSize(400, 200);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new GridLayout(3, 2));

            // Crear los campos de entrada
            JLabel lblEmail = new JLabel("Email:");
            JTextField txtEmail = new JTextField();
            JLabel lblPassword = new JLabel("Contraseña:");
            JPasswordField txtPassword = new JPasswordField();
            JButton btnContinuar = new JButton("Login");

            styleLabel(lblEmail);
            styleLabel(lblPassword);
            styleButton(btnContinuar);


            // Añadir los componentes al formulario
            add(lblEmail);
            add(txtEmail);
            add(lblPassword);
            add(txtPassword);
            add(new JLabel());
            add(btnContinuar);

            // Acción del botón "Login"
            btnContinuar.addActionListener(e -> {
                String email = txtEmail.getText();
                String password = new String(txtPassword.getPassword());

                //Validación de que los campos no estén vacíos
                if (email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.");
                }else{
                    String result = loginCustomer(email, password);
                    if ("OK".equals(result)) {
                        JOptionPane.showMessageDialog(this, "Login exitoso");
                    } else {
                        JOptionPane.showMessageDialog(this, "Error en el login");
                    }
                }
                dispose();
            });

            setLocationRelativeTo(null);
            setVisible(true);
        }
    }

    // Métodos para estilizar etiquetas
    private void styleLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setHorizontalAlignment(SwingConstants.CENTER);
    }

    public String recuperarInformacion() {
        Client cliente=new Client();
        HashMap<String,Object> session=new HashMap<>();
        String context="/getCustomer";
        session.put("id",id);
        session=cliente.sentMessage(context,session);
        Customer cu=(Customer)session.get("Customer");
        String nombre;
        if (cu==null) {
            nombre="Error - No encontrado en la base de datos";
        }else {
            nombre=cu.getName();
        }
        return nombre;
    }

    public void registerCustomer(String email, String password, String name, String id) {
        Customer cu=new Customer(id,name,email,password);
        Client cliente=new Client();
        HashMap<String,Object> session=new HashMap<>();
        String context="/setCustomer";
        session.put("Customer",cu);
        session=cliente.sentMessage(context,session);
        String result=(String)session.get("result");
        System.out.println("Resultado de la inserción: "+result);
    }

    public String loginCustomer(String email, String password) {
        Client cliente=new Client();
        HashMap<String,Object> session=new HashMap<>();
        String context="/loginCustomer";
        session.put("email",email);
        session.put("password",password);
        session=cliente.sentMessage(context,session);
        String result=(String)session.get("result");
        System.out.println("Resultado del login: "+result);
        return result;
    }
}
