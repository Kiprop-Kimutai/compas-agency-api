package compas.transaction;

/**
 * Created by CLLSDJACKT013 on 17/05/2018.
 */
public class TransactionEnum {
    enum TransactionTypes{
        CASH_TRANSACTION(1),INQUIRY(2),REQUEST(3),FUND_TRANSFERS(4);
        int index;

        TransactionTypes(int index){
            this.index = index;
        }
    }
}
