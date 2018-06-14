package compas.repositories;

import compas.models.Tariff;
import compas.tariffs.TariffsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;

/**
 * Created by CLLSDJACKT013 on 09/06/2018.
 */
@Controller
public class TariffsOperations {
    @Autowired
    private TariffsRepository tariffsRepository;

    public Tariff findTransactionTariffByTransaction_type_idAndAmount(Integer transaction_type_id,  Double amount){
        return tariffsRepository.findTransactionTariffByTransaction_type_idAndAmount(transaction_type_id,amount);
    }

    public Tariff findTransactionTariffByOperation_idAndAmount(Integer operation_id,Double amount){
        return  tariffsRepository.findTransactionTariffByOperation_idAndAmount(operation_id,amount);
    }


}
