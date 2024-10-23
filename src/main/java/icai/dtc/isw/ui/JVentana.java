package icai.dtc.isw.ui;

import icai.dtc.isw.client.Client;
import icai.dtc.isw.domain.Customer;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Random;
//bsgdfbuz
public class JVentana extends JFrame {
//hola
    public static void main(String[] args) {
        new JVentana();
    }

    private String id;
    public JVentana() {
        super("FUEL MAP");
        this.setLayout(new BorderLayout());

        //Pongo un panel arriba con el título
        JPanel pnlNorte = new JPanel();
        JLabel lblTitulo = new JLabel("Registarse", SwingConstants.CENTER);
        pnlNorte.add(lblTitulo);
        this.add(pnlNorte, BorderLayout.NORTH);

        //Pongo un panel en el centro con los campos
        JPanel pnlCentro = new JPanel();
        pnlCentro.setLayout(new GridLayout(1,6));
        JLabel lblRegister = new JLabel("Register");
        JLabel lblLogin = new JLabel("Login");
        JButton btnRegister = new JButton("Register");
        JButton btnLogin = new JButton("Login");

        JPanel pnlRegister = new JPanel();
        pnlRegister.setLayout(new GridLayout(4,2));

        JLabel lblName = new JLabel("Name");
        JTextField txtName = new JTextField();
        JLabel lblId = new JLabel("Id");
        JTextField txtId = new JTextField();
        JLabel lblEmail = new JLabel("Email");
        JTextField txtEmail = new JTextField();
        JLabel lblPassword = new JLabel("Password");
        JTextField txtPassword = new JTextField();

        pnlRegister.add(lblName);
        pnlRegister.add(txtName);
        pnlRegister.add(lblId);
        pnlRegister.add(txtId);
        pnlRegister.add(lblEmail);
        pnlRegister.add(txtEmail);
        pnlRegister.add(lblPassword);
        pnlRegister.add(txtPassword);

        JPanel pnlLogin = new JPanel();
        pnlLogin.setLayout(new GridLayout(2,2));

        JLabel lblEmailLogin = new JLabel("Email");
        JTextField txtEmailLogin = new JTextField();
        JLabel lblPasswordLogin = new JLabel("Password");
        JTextField txtPasswordLogin = new JTextField();

        pnlLogin.add(lblEmailLogin);
        pnlLogin.add(txtEmailLogin);
        pnlLogin.add(lblPasswordLogin);
        pnlLogin.add(txtPasswordLogin);

        pnlCentro.add(lblRegister);
        pnlCentro.add(pnlRegister);
        pnlCentro.add(btnRegister);
        pnlCentro.add(lblLogin);
        pnlCentro.add(pnlLogin);
        pnlCentro.add(btnLogin);

        btnRegister.addActionListener(e -> {
            String email=txtEmail.getText();
            String password=txtPassword.getText();
            String name=txtName.getText();
            String id=txtId.getText();
            registerCustomer(email,password,name,id);
        });

        btnLogin.addActionListener(e -> {
            String email=txtEmailLogin.getText();
            String password=txtPasswordLogin.getText();
            String result=loginCustomer(email,password);
            if (result.equals("OK")) {
                JOptionPane.showMessageDialog(this,"Bienvenido");
            }else {
                JOptionPane.showMessageDialog(this,"Error en el login");
            }
        });

        this.add(pnlCentro, BorderLayout.CENTER);

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
