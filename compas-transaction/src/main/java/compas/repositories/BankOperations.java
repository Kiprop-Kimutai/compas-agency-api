package compas.repositories;

import compas.bank.BankRepository;
import compas.bank.BranchRepository;
import compas.models.Bank;
import compas.models.Bank_Branch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;

/**
 * Created by CLLSDJACKT013 on 09/06/2018.
 */@Controller
public class BankOperations {
     @Autowired
    private BankRepository bankRepository;
     @Autowired
    private BranchRepository branchRepository;

    public Bank findBankById(@Param("Id") Integer Id){
        return bankRepository.findBankById(Id);
    }

    public Bank_Branch findBankByBranchId(Integer Id){
        return branchRepository.findBankById(Id);
    }


}
