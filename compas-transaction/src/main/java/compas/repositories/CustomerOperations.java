package compas.repositories;

import compas.customer.CustomerRDBMSRepository;
import compas.customer.CustomerRepository;
import compas.customer.FingerPrintRepository;
import compas.models.Customer;
import compas.models.CustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;

/**
 * Created by CLLSDJACKT013 on 09/06/2018.
 */
@Controller
public class CustomerOperations {
    @Autowired
    private CustomerRDBMSRepository customerRDBMSRepository;
    @Autowired
    private FingerPrintRepository fingerPrintRepository;
    private CustomerRepository customerRepository = new CustomerRepository();

    public Customer findActiveCustomerByIDNumber(String id_number) {
        return customerRDBMSRepository.findActiveCustomerByIDNumber(id_number);
    }

    public void saveCustomer(CustomerRequest customerRequest) {
        customerRepository.saveCustomer(customerRequest);
    }
}
