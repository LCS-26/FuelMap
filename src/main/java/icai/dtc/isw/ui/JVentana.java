package icai.dtc.isw.ui;
import icai.dtc.isw.client.Client;
import icai.dtc.isw.domain.Customer;
import icai.dtc.isw.domain.Gasolinera;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class JVentana extends JFrame {
    private boolean isLoggedIn = false; // Bandera de estado de login
    public ArrayList<Gasolinera> listaGasolineras = new ArrayList<>();
    public static void main(String[] args) {
        new JVentana();
    }

    public JVentana() {
        super("FUEL MAP");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setResizable(true);

        // Estilo global para botones y etiquetas
        UIManager.put("Button.background", new Color(0xFFA500));
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Label.font", new Font("Arial", Font.PLAIN, 14));

        // Panel superior con el título
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(new Color(0x005EB8));
        JLabel lblTitle = new JLabel("Fuel Map", SwingConstants.CENTER);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 32));
        pnlHeader.add(lblTitle, BorderLayout.CENTER);

        // Panel de navegación lateral
        JPanel pnlSidebar = new JPanel();
        pnlSidebar.setLayout(new GridLayout(5, 1, 10, 10));
        pnlSidebar.setBackground(new Color(0xEEEEEE));
        pnlSidebar.setPreferredSize(new Dimension(200, getHeight()));

        JButton btnRegister = new JButton("Registrar");
        JButton btnLogin = new JButton("Login");
        JButton btnAllStations = new JButton("Ver todas las gasolineras");
        JButton btnSearchMap = new JButton("Buscar en el mapa");
        JButton btnExit = new JButton("Salir");

        styleButton(btnRegister);
        styleButton(btnLogin);
        styleButton(btnAllStations);
        styleButton(btnSearchMap);
        styleButton(btnExit);

        pnlSidebar.add(btnRegister);
        pnlSidebar.add(btnLogin);
        pnlSidebar.add(btnAllStations);
        pnlSidebar.add(btnSearchMap);
        pnlSidebar.add(btnExit);

        // Panel principal central (contenedor)
        JPanel pnlMain = new JPanel(new CardLayout());
        pnlMain.setBackground(Color.WHITE);

        // Panel de pie de página
        JPanel pnlFooter = new JPanel();
        pnlFooter.setBackground(new Color(0x005EB8));
        JLabel lblFooter = new JLabel("Fuel Map © 2024 - Todos los derechos reservados");
        lblFooter.setForeground(Color.WHITE);
        pnlFooter.add(lblFooter);

        // Agregar componentes al contenedor principal
        add(pnlHeader, BorderLayout.NORTH);
        add(pnlSidebar, BorderLayout.WEST);
        add(pnlMain, BorderLayout.CENTER);
        add(pnlFooter, BorderLayout.SOUTH);

        // Eventos de navegación
        btnRegister.addActionListener(e -> new VentanaRegistro());
        btnLogin.addActionListener(e -> new VentanaLogin());

        // Solo permite acceder a estas opciones si isLoggedIn es verdadero
        btnAllStations.addActionListener(e -> {
            if (isLoggedIn) {
                listaGasolineras = getGasolineras();
                new VentanaTodasLasGasolineras(listaGasolineras);
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, inicie sesión o regístrese para acceder a esta opción.");
            }
        });

        btnSearchMap.addActionListener(e -> {
            if (isLoggedIn) {
                new VentanaMapa();
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, inicie sesión o regístrese para acceder a esta opción.");
            }
        });

        // Intentar cargar la imagen
        ImageIcon imagenmapa = new ImageIcon("src/main/resources/imagenpp.png");
        Image imageEscala = imagenmapa.getImage().getScaledInstance(800, 800, Image.SCALE_SMOOTH);
        ImageIcon iconScale = new ImageIcon(imageEscala);
        JLabel etiquetImage = new JLabel(iconScale);
        etiquetImage.setPreferredSize(new Dimension(800, 800));

        // Añadir la imagen al centro del BorderLayout
        add(etiquetImage, BorderLayout.CENTER);

        btnExit.addActionListener(e -> System.exit(0));

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(0x005EB8), 2, true));
    }

    private void styleLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setHorizontalAlignment(SwingConstants.CENTER);
    }

    // Ventana de Registro
    private class VentanaRegistro extends JFrame {
        public VentanaRegistro() {
            setTitle("Registro de Usuario");
            setSize(400, 300);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new GridLayout(6, 2, 10, 10));

            JLabel lblName = new JLabel("Nombre:");
            JTextField txtName = new JTextField();
            JLabel lblId = new JLabel("ID:");
            JTextField txtId = new JTextField();
            JLabel lblEmail = new JLabel("Email:");
            JTextField txtEmail = new JTextField();
            JLabel lblPassword = new JLabel("Contraseña:");
            JPasswordField txtPassword = new JPasswordField();
            JButton btnContinuar = new JButton("Registrar");
            JButton btnVolver = new JButton("Volver");

            styleLabel(lblName);
            styleLabel(lblId);
            styleLabel(lblEmail);
            styleLabel(lblPassword);
            styleButton(btnContinuar);
            styleButton(btnVolver);

            add(lblName); add(txtName);
            add(lblId); add(txtId);
            add(lblEmail); add(txtEmail);
            add(lblPassword); add(txtPassword);
            add(btnVolver);
            add(btnContinuar);

            btnContinuar.addActionListener(e -> {
                String name = txtName.getText();
                String id = txtId.getText();
                String email = txtEmail.getText();
                String password = new String(txtPassword.getPassword());
                registerCustomer(email, password, name, id);
                JOptionPane.showMessageDialog(this, "Registro exitoso");
                isLoggedIn = true; // Marca al usuario como registrado
                dispose();
            });

            btnVolver.addActionListener(e -> dispose());

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
            setLayout(new GridLayout(4, 2));

            JLabel lblEmail = new JLabel("Email:");
            JTextField txtEmail = new JTextField();
            JLabel lblPassword = new JLabel("Contraseña:");
            JPasswordField txtPassword = new JPasswordField();
            JButton btnContinuar = new JButton("Login");
            JButton btnVolver = new JButton("Volver");

            styleLabel(lblEmail);
            styleLabel(lblPassword);
            styleButton(btnContinuar);
            styleButton(btnVolver);

            add(lblEmail);
            add(txtEmail);
            add(lblPassword);
            add(txtPassword);
            add(btnVolver);
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
                        isLoggedIn = true; // Marca al usuario como logueado
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Error en el login");
                    }
                }
            });

            btnVolver.addActionListener(e -> dispose());

            setLocationRelativeTo(null);
            setVisible(true);
        }
    }

    // Ventana para mostrar todas las gasolineras
    private class VentanaTodasLasGasolineras extends JFrame {
        private JTextArea txtGasolineras;

        public VentanaTodasLasGasolineras(ArrayList<Gasolinera> listaGasolineras) {
            setTitle("Todas las Gasolineras");
            setSize(600, 400);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout());

            txtGasolineras = new JTextArea();
            JScrollPane scrollPane = new JScrollPane(txtGasolineras);
            add(scrollPane, BorderLayout.CENTER);
            txtGasolineras.setEditable(false);

            cargarGasolineras(getGasolineras());

            JPanel pnlBotones = new JPanel();
            JButton btnLimpiarFiltros = new JButton("Limpiar Filtros");
            JButton btnFiltrar = new JButton("Filtrar");
            JButton btnVolver = new JButton("Volver");
            styleButton(btnLimpiarFiltros);
            styleButton(btnFiltrar);
            styleButton(btnVolver);
            pnlBotones.add(btnLimpiarFiltros);
            pnlBotones.add(btnFiltrar);
            pnlBotones.add(btnVolver);
            add(pnlBotones, BorderLayout.SOUTH);

            btnLimpiarFiltros.addActionListener(e -> cargarGasolineras(listaGasolineras));
            btnFiltrar.addActionListener(e -> new VentanaBusquedaFiltrada(this));
            btnVolver.addActionListener(e -> dispose());

            setLocationRelativeTo(null);
            setVisible(true);
        }

        public void cargarGasolineras(ArrayList<Gasolinera> gasolineras) {
            txtGasolineras.setText("");
            System.out.println(gasolineras.size());
            if (gasolineras != null && !gasolineras.isEmpty()) {
                StringBuilder gasolinerasText = new StringBuilder();
                for (Gasolinera gasolinera : gasolineras) {
                    gasolinerasText.append(gasolinera.toString()).append("\n");
                }
                txtGasolineras.setText(gasolinerasText.toString());
            } else {
                txtGasolineras.setText("No se encontraron gasolineras.");
            }
        }
    }

    // Ventana de búsqueda filtrada para filtrar gasolineras
    private class VentanaBusquedaFiltrada extends JFrame {
        private VentanaTodasLasGasolineras ventanaGasolineras;

        public VentanaBusquedaFiltrada(VentanaTodasLasGasolineras ventanaGasolineras) {
            //this.ventanaGasolineras = ventanaGasolineras;

            setTitle("Búsqueda Filtrada");
            setSize(400, 300);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new GridLayout(8, 2));

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
            JButton btnVolver = new JButton("Volver");

            styleLabel(lblDistancia);
            styleLabel(lblPosX);
            styleLabel(lblPosY);
            styleLabel(lblMaxPrecio);
            styleButton(btnBuscar);
            styleButton(btnVolver);

            add(lblDistancia); add(txtDistancia);
            add(lblPosX); add(txtPosX);
            add(lblPosY); add(txtPosY);
            add(lblMaxPrecio); add(txtMaxPrecio);
            add(new JLabel("")); add(chkServicio);
            add(new JLabel("")); add(chkCargador);
            add(btnVolver);
            add(btnBuscar);

            btnBuscar.addActionListener(e -> {
                float distancia;
                float posX;
                float posY;
                float maxPrecio;

                if (txtDistancia == null){
                    distancia = 0;
                } else {
                    distancia = Float.parseFloat(txtDistancia.getText());
                }

                if (txtPosX == null){
                    posX = 0;
                } else {
                    posX = Float.parseFloat(txtPosX.getText());
                }

                if (txtPosY == null){
                    posY = 0;
                } else {
                    posY = Float.parseFloat(txtPosY.getText());
                }

                if (txtMaxPrecio == null){
                    maxPrecio = 0;
                } else {
                    maxPrecio = Float.parseFloat(txtMaxPrecio.getText());
                }

                boolean servicio = chkServicio.isSelected();
                boolean cargador = chkCargador.isSelected();

                listaGasolineras = getGasolinerasFiltradas(distancia, posX, posY, maxPrecio, servicio, cargador);
                //listaGasolineras = getGasolineras();

                ventanaGasolineras.cargarGasolineras(listaGasolineras);
                //new VentanaTodasLasGasolineras(listaGasolineras);

                dispose();
            });

            btnVolver.addActionListener(e -> dispose());

            setLocationRelativeTo(null);
            setVisible(true);
        }
    }

    // Ventana para mostrar el mapa
    private class VentanaMapa extends JFrame {
        public VentanaMapa() {
            setTitle("Mapa de Gasolineras");
            setSize(600, 400);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout());

            // Intentar cargar la imagen
            ImageIcon imagenmapa = new ImageIcon("src/main/resources/mapa.png");
            Image imageEscala = imagenmapa.getImage().getScaledInstance(800, 800, Image.SCALE_SMOOTH);
            ImageIcon iconScale = new ImageIcon(imageEscala);
            JLabel etiquetImage = new JLabel(iconScale);
            etiquetImage.setPreferredSize(new Dimension(800, 800));

            // Añadir la imagen al centro del BorderLayout
            add(etiquetImage, BorderLayout.CENTER);

            // Crear y añadir el botón Volver
            JButton btnVolver = new JButton("Volver");
            styleButton(btnVolver);
            add(btnVolver, BorderLayout.SOUTH);

            // Acción del botón Volver para cerrar la ventana
            btnVolver.addActionListener(e -> dispose());

            setLocationRelativeTo(null);
            setVisible(true);
        }
    }
    // Métodos de cliente y manejo de gasolineras (sin cambios)
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
        return (ArrayList<Gasolinera>) session.get("GasolinerasFiltradas");
    }
}
