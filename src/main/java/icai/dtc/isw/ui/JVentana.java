package icai.dtc.isw.ui;

import icai.dtc.isw.client.Client;
import icai.dtc.isw.domain.Customer;
import icai.dtc.isw.domain.Gasolinera;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class JVentana extends JFrame {
    public static void main(String[] args) {
        new JVentana();
    }

    private String id;

    public JVentana() {
        super("FUEL MAP");
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 300);
        this.setResizable(true);

        // Panel superior con el título
        JPanel pnlNorte = new JPanel();
        pnlNorte.setBackground(new Color(0x2E8B57));
        JLabel lblTitulo = new JLabel("Bienvenido", SwingConstants.CENTER);
        lblTitulo.setForeground(Color.BLUE);
        lblTitulo.setFont(new Font("Times new Roman", Font.BOLD, 24));
        pnlNorte.add(lblTitulo);
        pnlNorte.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.add(pnlNorte, BorderLayout.NORTH);

        // Panel central con los botones "Registrar" y "Login"
        JPanel pnlCentro = new JPanel();
        pnlCentro.setLayout(new GridLayout(1, 2));
        pnlCentro.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        pnlCentro.setBackground(Color.BLUE);
        JButton btnRegister = new JButton("Registrar");
        JButton btnLogin = new JButton("Login");
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

    private void styleButton(JButton button) {
        button.setPreferredSize(new Dimension(150, 50));
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setBackground(new Color(0x5F9EA0));
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
            setLayout(new GridLayout(5, 2, 10, 10));

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

            add(lblName); add(txtName);
            add(lblId); add(txtId);
            add(lblEmail); add(txtEmail);
            add(lblPassword); add(txtPassword);
            add(new JLabel());
            add(btnContinuar);

            btnContinuar.addActionListener(e -> {
                String name = txtName.getText();
                String id = txtId.getText();
                String email = txtEmail.getText();
                String password = new String(txtPassword.getPassword());
                registerCustomer(email, password, name, id);
                JOptionPane.showMessageDialog(this, "Registro exitoso");
                dispose();
                new VentanaOpciones();
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

            JLabel lblEmail = new JLabel("Email:");
            JTextField txtEmail = new JTextField();
            JLabel lblPassword = new JLabel("Contraseña:");
            JPasswordField txtPassword = new JPasswordField();
            JButton btnContinuar = new JButton("Login");

            styleLabel(lblEmail);
            styleLabel(lblPassword);
            styleButton(btnContinuar);

            add(lblEmail);
            add(txtEmail);
            add(lblPassword);
            add(txtPassword);
            add(new JLabel());
            add(btnContinuar);

            btnContinuar.addActionListener(e -> {
                String email = txtEmail.getText();
                String password = new String(txtPassword.getPassword());

                if (email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.");
                } else {
                    String result = loginCustomer(email, password);
                    if ("OK".equals(result)) {
                        JOptionPane.showMessageDialog(this, "Login exitoso");
                        dispose();
                        new VentanaOpciones();
                    } else {
                        JOptionPane.showMessageDialog(this, "Error en el login");
                    }
                }
            });

            setLocationRelativeTo(null);
            setVisible(true);
        }
    }

    private class VentanaOpciones extends JFrame {
        public VentanaOpciones() {
            setTitle("Opciones");
            setSize(400, 200);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new GridLayout(2, 1));

            JButton btnVerGasolineras = new JButton("Ver todas las gasolineras");
            JButton btnBusquedaFiltrada = new JButton("Hacer una búsqueda filtrada");

            styleButton(btnVerGasolineras);
            styleButton(btnBusquedaFiltrada);

            add(btnVerGasolineras);
            add(btnBusquedaFiltrada);

            btnVerGasolineras.addActionListener(e -> new VentanaTodasLasGasolineras());
            btnBusquedaFiltrada.addActionListener(e -> new VentanaBusquedaFiltrada());

            setLocationRelativeTo(null);
            setVisible(true);
        }
    }

    private class VentanaTodasLasGasolineras extends JFrame {
        public VentanaTodasLasGasolineras() {
            setTitle("Todas las Gasolineras");
            setSize(500, 400);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout());

            // Obtener todas las gasolineras y mostrarlas en un área de texto
            JTextArea txtGasolineras = new JTextArea();
            JScrollPane scrollPane = new JScrollPane(txtGasolineras);
            add(scrollPane, BorderLayout.CENTER);
            txtGasolineras.setEditable(false);

            // Cargar datos de gasolineras
            ArrayList<Gasolinera> gasolineras = getGasolineras();
            if (gasolineras != null && !gasolineras.isEmpty()) {
                StringBuilder gasolinerasText = new StringBuilder();
                for (Gasolinera gasolinera : gasolineras) {
                    gasolinerasText.append(gasolinera.toString()).append("\n");
                }
                txtGasolineras.setText(gasolinerasText.toString());
            } else {
                txtGasolineras.setText("No se encontraron gasolineras.");
            }

            // Panel inferior con botones "Limpiar Filtros" y "Filtrar"
            JPanel pnlBotones = new JPanel();
            JButton btnLimpiarFiltros = new JButton("Limpiar Filtros");
            JButton btnFiltrar = new JButton("Filtrar");
            styleButton(btnLimpiarFiltros);
            styleButton(btnFiltrar);
            pnlBotones.add(btnLimpiarFiltros);
            pnlBotones.add(btnFiltrar);
            add(pnlBotones, BorderLayout.SOUTH);

            // Acción para limpiar filtros (recargar todas las gasolineras)
            btnLimpiarFiltros.addActionListener(e -> {
                txtGasolineras.setText(""); // Limpiar área de texto
                ArrayList<Gasolinera> allGasolineras = getGasolineras();
                if (allGasolineras != null && !allGasolineras.isEmpty()) {
                    StringBuilder text = new StringBuilder();
                    for (Gasolinera gasolinera : allGasolineras) {
                        text.append(gasolinera.toString()).append("\n");
                    }
                    txtGasolineras.setText(text.toString());
                } else {
                    txtGasolineras.setText("No se encontraron gasolineras.");
                }
            });

            // Acción para abrir la ventana de búsqueda filtrada
            btnFiltrar.addActionListener(e -> new VentanaBusquedaFiltrada());

            setLocationRelativeTo(null);
            setVisible(true);
        }
    }

    private class VentanaBusquedaFiltrada extends JFrame {
        public VentanaBusquedaFiltrada() {
            setTitle("Búsqueda Filtrada");
            setSize(400, 300);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new GridLayout(7, 2));

            JLabel lblDistancia = new JLabel("Distancia:");
            JTextField txtDistancia = new JTextField();
            JLabel lblPosX = new JLabel("Posición X:");
            JTextField txtPosX = new JTextField();
            JLabel lblPosY = new JLabel("Posición Y:");
            JTextField txtPosY = new JTextField();
            JLabel lblMaxPrecio = new JLabel("Precio Máximo:");
            JTextField txtMaxPrecio = new JTextField();
            JCheckBox chkServicio = new JCheckBox("Con servicio");
            JCheckBox chkCargador = new JCheckBox("Con cargador");
            JButton btnBuscar = new JButton("Buscar");

            styleLabel(lblDistancia);
            styleLabel(lblPosX);
            styleLabel(lblPosY);
            styleLabel(lblMaxPrecio);
            styleButton(btnBuscar);

            add(lblDistancia); add(txtDistancia);
            add(lblPosX); add(txtPosX);
            add(lblPosY); add(txtPosY);
            add(lblMaxPrecio); add(txtMaxPrecio);
            add(new JLabel("")); add(chkServicio);
            add(new JLabel("")); add(chkCargador);
            add(new JLabel("")); add(btnBuscar);

            btnBuscar.addActionListener(e -> {
                float distancia, posX, posY, maxPrecio;
                if (txtDistancia.getText().isEmpty()){
                    distancia = Float.MAX_VALUE;
                } else {
                    distancia = Float.parseFloat(txtDistancia.getText());
                }
                if (txtPosX.getText().isEmpty()){
                    posX = 0;
                } else {
                    posX = Float.parseFloat(txtPosX.getText());
                }
                if (txtPosY.getText().isEmpty()){
                    posY = 0;
                } else {
                    posY = Float.parseFloat(txtPosY.getText());
                }
                if (txtMaxPrecio.getText().isEmpty()){
                    maxPrecio = Float.MAX_VALUE;
                } else {
                    maxPrecio = Float.parseFloat(txtMaxPrecio.getText());
                }

                boolean servicio = chkServicio.isSelected();
                boolean cargador = chkCargador.isSelected();

                ArrayList<Gasolinera> gasolinerasFiltradas = getGasolinerasFiltradas(distancia, posX, posY, maxPrecio, servicio, cargador);
                mostrarGasolineras(gasolinerasFiltradas);
            });

            setLocationRelativeTo(null);
            setVisible(true);
        }

        private void mostrarGasolineras(ArrayList<Gasolinera> gasolineras) {
            StringBuilder message = new StringBuilder("Gasolineras Filtradas:\n");
            for (Gasolinera gasolinera : gasolineras) {
                message.append(gasolinera.toString()).append("\n");
            }
            JOptionPane.showMessageDialog(this, message.toString());
        }
    }

    private void styleLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setHorizontalAlignment(SwingConstants.CENTER);
    }

    public void registerCustomer(String email, String password, String name, String id) {
        Customer cu = new Customer(id, name, email, password);
        Client cliente = new Client();
        HashMap<String, Object> session = new HashMap<>();
        String context = "/setCustomer";
        session.put("Customer", cu);
        session = cliente.sentMessage(context, session);
        String result = (String) session.get("result");
        System.out.println("Resultado de la inserción: " + result);
    }

    public String loginCustomer(String email, String password) {
        Client cliente = new Client();
        HashMap<String, Object> session = new HashMap<>();
        String context = "/loginCustomer";
        session.put("email", email);
        session.put("password", password);
        session = cliente.sentMessage(context, session);
        String result = (String) session.get("result");
        System.out.println("Resultado del login: " + result);
        return result;
    }

    public ArrayList<Gasolinera> getGasolineras() {
        Client cliente = new Client();
        HashMap<String, Object> session = new HashMap<>();
        String context = "/getGasolineras";
        session = cliente.sentMessage(context, session);
        return (ArrayList<Gasolinera>) session.get("Gasolineras");
    }

    public ArrayList<Gasolinera> getGasolinerasFiltradas(float distancia, float posX, float posY, float maxPrecio, boolean servicio, boolean cargador) {
        Client cliente = new Client();
        HashMap<String, Object> session = new HashMap<>();
        String context = "/getGasolinerasFiltradas";
        session.put("distancia", distancia);
        session.put("posx", posX);
        session.put("posy", posY);
        session.put("maxPrecio", maxPrecio);
        session.put("servicio", servicio);
        session.put("cargador", cargador);
        session = cliente.sentMessage(context, session);
        return (ArrayList<Gasolinera>) session.get("Gasolineras");
    }
}
