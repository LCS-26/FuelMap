package icai.dtc.isw.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;



import icai.dtc.isw.configuration.PropertiesISW;
import icai.dtc.isw.domain.Customer;
import icai.dtc.isw.domain.Gasolinera;
import icai.dtc.isw.message.Message;

public class Client {
	private String host;
	private int port;

	public Client(String host, int port) {
		this.host=host;
		this.port=port;
	}
	public Client() {
		this.host = PropertiesISW.getInstance().getProperty("host");
		this.port = Integer.parseInt(PropertiesISW.getInstance().getProperty("port"));
	}
	public HashMap<String, Object> sentMessage(String Context, HashMap<String, Object> session) {
		//Configure connections
		//String host = PropertiesISW.getInstance().getProperty("host");
		//int port = Integer.parseInt(PropertiesISW.getInstance().getProperty("port"));

		System.out.println("Host: "+host+" port"+port);
		//Create a cliente class
		//Client cliente=new Client(host, port);
		
		//HashMap<String,Object> session=new HashMap<String, Object>();
		//session.put("/getCustomer","");
		
		Message mensajeEnvio=new Message();
		Message mensajeVuelta=new Message();
		mensajeEnvio.setContext(Context);///getCustomer"
		mensajeEnvio.setSession(session);
		this.sent(mensajeEnvio,mensajeVuelta);
		
		
		switch (mensajeVuelta.getContext()) {
			case "/getCustomersResponse":
				ArrayList<Customer> customerList=(ArrayList<Customer>)(mensajeVuelta.getSession().get("Customer"));
				 for (Customer customer : customerList) {			
						System.out.println("He leído el id: "+customer.getId()+" con nombre: "+customer.getName());
					} 
				break;
			case "/getCustomerResponse":
				session=mensajeVuelta.getSession();
				Customer customer =(Customer) (session.get("Customer"));
				if (customer!=null) {
					System.out.println("He leído el id: " + customer.getId() + " con nombre: " + customer.getName());
				}else {
					System.out.println("No se ha recuperado nada de la base de datos");
				}
				break;

			case "/setCustomerResponse":
				session=mensajeVuelta.getSession();
				String result=(String) session.get("result");
				System.out.println("Resultado de la inserción: "+result);
				break;

			case "/loginCustomerResponse":
				session=mensajeVuelta.getSession();
				String resultLogin=(String) session.get("result");
				System.out.println("Resultado del login: "+resultLogin);
				break;

			case "/getGasolinerasResponse":
				session=mensajeVuelta.getSession();
				ArrayList<Gasolinera> gasolinerasList=(ArrayList<Gasolinera>)(mensajeVuelta.getSession().get("Gasolineras"));
				for (Gasolinera gasolinera : gasolinerasList) {
					System.out.println("He recibido la gasolinera: "+gasolinera.getNombre());
				}
				break;
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																
			case "/getGasolinerasFiltradasResponse":
				session=mensajeVuelta.getSession();
				ArrayList<Gasolinera> gasolinerasListFiltradas=(ArrayList<Gasolinera>)(mensajeVuelta.getSession().get("GasolinerasFiltradas"));
				for (Gasolinera gasolinera : gasolinerasListFiltradas) {
					System.out.println("He recibido la gasolinera: "+gasolinera.getNombre());
				}
				break;

			default:

				System.out.println("\nError a la vuelta");
				break;
		
		}
		//System.out.println("3.- En Main.- El valor devuelto es: "+((String)mensajeVuelta.getSession().get("Nombre")));
		return session;
	}
	


	public void sent(Message messageOut, Message messageIn) {
		try {

			System.out.println("Connecting to host " + host + " on port " + port + ".");

			Socket echoSocket = null;
			OutputStream out = null;
			InputStream in = null;

			try {
				echoSocket = new Socket(host, port);
				in = echoSocket.getInputStream();
				out = echoSocket.getOutputStream();
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
				
				//Create the objetct to send
				objectOutputStream.writeObject(messageOut);
				
				// create a DataInputStream so we can read data from it.
		        ObjectInputStream objectInputStream = new ObjectInputStream(in);
		        Message msg=(Message)objectInputStream.readObject();
		        messageIn.setContext(msg.getContext());
		        messageIn.setSession(msg.getSession());
		        /*System.out.println("\n1.- El valor devuelto es: "+messageIn.getContext());
		        String cadena=(String) messageIn.getSession().get("Nombre");
		        System.out.println("\n2.- La cadena devuelta es: "+cadena);*/
				
			} catch (UnknownHostException e) {
				System.err.println("Unknown host: " + host);
				System.exit(1);
			} catch (IOException e) {
				System.err.println("Unable to get streams from server");
				/*System.exit(1);*/
			}		

			/** Closing all the resources */
			out.close();
			in.close();			
			echoSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}