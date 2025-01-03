package icai.dtc.isw.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import icai.dtc.isw.configuration.PropertiesISW;
import icai.dtc.isw.controler.CustomerControler;
import icai.dtc.isw.controler.GasolineraController;
import icai.dtc.isw.domain.Customer;
import icai.dtc.isw.domain.Gasolinera;
import icai.dtc.isw.message.Message;

public class SocketServer extends Thread {
	public static int port = Integer.parseInt(PropertiesISW.getInstance().getProperty("port"));

	protected Socket socket;

	private SocketServer(Socket socket) {
		this.socket = socket;
		//Configure connections
		System.out.println("New client connected from " + socket.getInetAddress().getHostAddress());
		start();
	}

	public void run() {
		InputStream in = null;
		OutputStream out = null;
		try {
			in = socket.getInputStream();
			out = socket.getOutputStream();
			
			//first read the object that has been sent
			ObjectInputStream objectInputStream = new ObjectInputStream(in);
		    Message mensajeIn= (Message)objectInputStream.readObject();
		    //Object to return informations 
		    ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
		    Message mensajeOut=new Message();
			HashMap<String,Object> session=mensajeIn.getSession();
			CustomerControler customerControler;
		    switch (mensajeIn.getContext()) {
		    	case "/getCustomers":
		    		customerControler=new CustomerControler();
		    		ArrayList<Customer> lista=new ArrayList<Customer>();
		    		customerControler.getCustomers(lista);
		    		mensajeOut.setContext("/getCustomersResponse");
		    		//HashMap<String,Object> session=new HashMap<String, Object>();
		    		session.put("Customers",lista);
		    		mensajeOut.setSession(session);
		    		objectOutputStream.writeObject(mensajeOut);		    		
		    		break;
				case "/getCustomer":
					int id= (int) session.get("id");
					customerControler=new CustomerControler();
					Customer cu=customerControler.getCustomer(id);
					if (cu!=null){
						System.out.println("id:"+cu.getId());
					}else {
						System.out.println("No encontrado en la base de datos");
					}

					mensajeOut.setContext("/getCustomerResponse");
					session.put("Customer",cu);
					mensajeOut.setSession(session);
					objectOutputStream.writeObject(mensajeOut);
					break;

				case "/loginCustomer":
					String email=(String) session.get("email");
					String password=(String) session.get("password");
					customerControler=new CustomerControler();
					String secret=customerControler.checkEmailPassword(email,password);
					if (secret!=null){
						System.out.println("Secret:"+secret);
						session.put("secret",secret);
						session.put("result","OK");
					}else {
						System.out.println("No encontrado en la base de datos");
						session.put("result","KO");
					}
					mensajeOut.setContext("/loginCustomerResponse");
					mensajeOut.setSession(session);
					objectOutputStream.writeObject(mensajeOut);
					break;

				case "/setCustomer":
					Customer cu1=(Customer) session.get("Customer");
					customerControler=new CustomerControler();
					customerControler.setCustomer(cu1);
					mensajeOut.setContext("/setCustomerResponse");
					session.put("result","OK");
					mensajeOut.setSession(session);
					objectOutputStream.writeObject(mensajeOut);
					break;

				case "/getGasolineras":
					GasolineraController gasolineraController=new GasolineraController();
					ArrayList<Gasolinera> listaGasolineras=new ArrayList<Gasolinera>();
					gasolineraController.getGasolineras(listaGasolineras);
					mensajeOut.setContext("/getGasolinerasResponse");
					session.put("Gasolineras",listaGasolineras);
					mensajeOut.setSession(session);
					objectOutputStream.writeObject(mensajeOut);
					break;

				case "/getGasolinerasFiltradas":
					float distancia=(float) session.get("distancia");
					float posx=(float) session.get("posx");
					float posy=(float) session.get("posy");
					float maxPrecio=(float) session.get("maxPrecio");
					boolean servicio=(boolean) session.get("servicio");
					boolean cargador=(boolean) session.get("cargador");
					gasolineraController=new GasolineraController();
					ArrayList<Gasolinera> listaGasolinerasFiltradas=gasolineraController.getGasolinerasFiltradas(distancia, posx, posy, maxPrecio, servicio, cargador);
					mensajeOut.setContext("/getGasolinerasFiltradasResponse");
					session.put("GasolinerasFiltradas",listaGasolinerasFiltradas);
					mensajeOut.setSession(session);
					objectOutputStream.writeObject(mensajeOut);
					break;

		    	default:
		    		System.out.println("\nParámetro no encontrado");
		    		break;
		    }
		    
		    //Lógica del controlador 
		    //System.out.println("\n1.- He leído: "+mensaje.getContext());
		    //System.out.println("\n2.- He leído: "+(String)mensaje.getSession().get("Nombre"));
		    
		    
		    
		    //Prueba para esperar
		    /*try {
		    	System.out.println("Me duermo");
				Thread.sleep(15000);
				System.out.println("Me levanto");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			// create an object output stream from the output stream so we can send an object through it
			/*ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
			
			//Create the object to send
			String cadena=((String)mensaje.getSession().get("Nombre"));
			cadena+=" añado información";
			mensaje.getSession().put("Nombre", cadena);
			//System.out.println("\n3.- He leído: "+(String)mensaje.getSession().get("Nombre"));
			objectOutputStream.writeObject(mensaje);*
			*/

		} catch (IOException ex) {
			System.out.println("Unable to get streams from client");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
				socket.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		System.out.println("SocketServer Example - Listening port "+port);
		ServerSocket server = null;
		try {
			server = new ServerSocket(port);
			while (true) {
				/**
				 * create a new {@link SocketServer} object for each connection
				 * this will allow multiple client connections
				 */
				new SocketServer(server.accept());
			}
		} catch (IOException ex) {
			System.out.println("Unable to start server.");
		} finally {
			try {
				if (server != null)
					server.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}