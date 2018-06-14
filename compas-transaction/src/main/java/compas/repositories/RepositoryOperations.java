package compas.repositories;

import compas.accounts.AccountsRepository;
import compas.models.Account;
import compas.models.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Created by CLLSDJACKT013 on 09/06/2018.
 */
public class RepositoryOperations  {

    public RepositoryOperations( ){

    }
    public class AccountRepositoryOperations extends AccountsOperations{

    }
    public class AgentOperationsRepository extends AgentOperations {

    }
    public class AuthenticationOperationsRepository extends AuthenticationOperations{

    }

    public class BankOperationsRepository  extends BankOperations{

    }
    public class CurrencyOperationRepositories extends CurrencyOperations{

    }
    public class CustomerOperationsRepository extends CustomerOperations{

    }

    public class DeviceOperationsRepository extends DeviceOperations{

    }
    public class NextofKinOperationsRepository extends NextofKinOperations{

    }

    public class TariffsOperationsRepository extends TariffsOperations{

    }

    public class TransactionOperationsRepository extends TransactionOperations{
        public TransactionOperationsRepository(){}
    }

    public  class UserOperationsRepository extends UserOperations{

    }

    public class  UtilitiesOperationsRepository extends UtilitiesOperations{

    }

}
