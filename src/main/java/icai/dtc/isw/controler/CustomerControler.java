package icai.dtc.isw.controler;

import java.util.ArrayList;

import icai.dtc.isw.dao.CustomerDAO;
import icai.dtc.isw.domain.Customer;

public class CustomerControler {
	CustomerDAO customerDAO=new CustomerDAO();
	public void getCustomers(ArrayList<Customer> lista) {
		customerDAO.getClientes(lista);
	}
	public Customer getCustomer(int id) {return(customerDAO.getCliente(id));}

	public Boolean checkSecret(String id, String secret) {
		Customer cu=customerDAO.getCliente(Integer.parseInt(id));
		if (cu!=null) {
			if (cu.getSecret().equals(secret)) {
				return true;
			}
		}
		return false;
	}

	public String checkEmailPassword(String email, String password) {
		ArrayList<Customer> lista=new ArrayList<Customer>();
		customerDAO.getClientes(lista);
		for (Customer customer : lista) {
			if (customer.getEmail().equals(email)) {
				if (customer.getPassword().equals(password)) {
					return customer.getSecret();
				}
			}
		}
		return null;
	}

	public void setCustomer(Customer cu) {
		customerDAO.setCliente(cu);
	}

}
