package icai.dtc.isw.ui;

import icai.dtc.isw.client.Client;
import icai.dtc.isw.domain.Customer;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Random;

public class JVentana extends JFrame {

    public static void main(String[] args) {
        new JVentana();
    }

    private int id;
    public JVentana() {
        super("FUEL MAP");
        this.setLayout(new BorderLayout());
        //Pongo un panel arriba con el título
        JPanel pnlNorte = new JPanel();

        JPanel pnlNorteNorte = new JPanel();
        JLabel lblTitulo = new JLabel("Registarse", SwingConstants.CENTER);
        pnlNorte.add(pnlNorteNorte, BorderLayout.NORTH);

        JPanel pnlNorteCentro = new JPanel();


        JPanel pnlNorteSur = new JPanel();
        JButton btnRegis = new JButton("Crear cuenta");
        pnlNorte.add(pnlNorteSur, BorderLayout.SOUTH);

        lblTitulo.setFont(new Font("Courier", Font.BOLD, 20));
        pnlNorte.add(lblTitulo);
        pnlNorte.setLayout(new GridLayout(4, 2));
        pnlNorte.add

        this.add(pnlNorte, BorderLayout.NORTH);

        //Pongo el panel central el botón
        JPanel pnlCentro = new JPanel();
        JLabel lblId = new JLabel("Introduzca el id", SwingConstants.CENTER);
        JTextField txtId = new JTextField();
        txtId.setBounds(new Rectangle(250,150,250,150));
        txtId.setHorizontalAlignment(JTextField.LEFT);

        JLabel lblPass = new JLabel("Introduzca la constraseña", SwingConstants.CENTER);
        JTextField txtPass = new JTextField();
        txtPass.setBounds(new Rectangle(250,150,250,150));
        txtPass.setHorizontalAlignment(JTextField.LEFT);

        JButton btnLogIn = new JButton("Log in");


        pnlCentro.add(lblId);
        pnlCentro.add(txtId);
        pnlCentro.add(lblPass);
        pnlCentro.add(txtPass);
        pnlCentro.add(btnLogIn);
        pnlCentro.setLayout(new BoxLayout(pnlCentro, BoxLayout.	X_AXIS));
        this.add(pnlCentro, BorderLayout.CENTER);

        //El Sur lo hago para recoger el resultado
        JPanel pnlSur = new JPanel();
        JLabel lblResultado = new JLabel("El resultado obtenido es: ", SwingConstants.CENTER);
        JTextField txtResultado = new JTextField();
        txtResultado.setBounds(new Rectangle(250,150,250,150));
        txtResultado.setEditable(false);
        txtResultado.setHorizontalAlignment(JTextField.LEFT);
        pnlSur.add(lblResultado);
        pnlSur.add(txtResultado);
        //Añado el listener al botón
        btnLogIn.addActionListener(actionEvent -> {
            id=Integer.parseInt(txtId.getText());
            txtResultado.setText(recuperarInformacion());
        });
        pnlSur.setLayout(new BoxLayout(pnlSur, BoxLayout.X_AXIS));
        this.add(pnlSur,BorderLayout.SOUTH);

        this.setSize(550,120);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
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
