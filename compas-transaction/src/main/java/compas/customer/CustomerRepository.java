package compas.customer;

import com.mongodb.Mongo;
import com.mongodb.MongoException;
import compas.MongoConfig.MongoConfiguration;
import compas.models.Customer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by CLLSDJACKT013 on 08/05/2018.
 */
//This will be auto-implemented by Spring into a bean called customerRepository
public  interface CustomerRepository extends CrudRepository<Customer,Long> {
/*    @Query("select customer from customer where Customer.firstName like ?1 and Customer.id_number = ?2")
    List<Customer>findCustomerByFnameAndId(String firstName,String id_number);*/
/*    ApplicationContext context = new AnnotationConfigApplicationContext(MongoConfiguration.class);
    MongoOperations mongoOperations = (MongoOperations) context.getBean("mongoTemplate");*/

 /*   public Customer saveCustomer(Customer customer){
        try{
            mongoOperations.save(customer);
            return customer;
        }
        catch (MongoException e){
            e.printStackTrace();
        }
        return new Customer();
    }*/
}
