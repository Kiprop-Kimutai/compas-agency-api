package compas.transaction;

/**
 * Created by CLLSDJACKT013 on 9/12/2018.
 */
public class BillPaymentsController {

    /**
     * -process bill payment request
     * -take a bill payment request as it is, and determine type
     * -process the menus as would a USSD app
     * -important arguments are; utility name and utility operation action and menu level
     *
     * switch(utility.name.toUpperCase()){
     *     case GOTV:
     *     switch(utility_operation_action.toUpperCase()){
     *         case TOPUP:
     *         break;
     *         case SELECT_OTHER_BOUQUET:
     *          switch()
     *         break;
     *         default:
     *         throw new RuntimeException("Invalid selection");
     *     }
     *     break;
     *     case DSTV:
     *     break;
     *     case UMEME:
     *     break;
     *     case NWSC:
     *     break;
     *     case URA:
     *     break;
     * }
     *
     * if(menu level =2,utility name)
     * if(utility_name ==ura){
     * switch(utility_operation_action.tolowerCase()){
     *     case VALIDE_WITH_PRN:
     *     break;
     *     case VALIDATE_WITHOUT_PRN:
     *     break;
     *     case PAYMENT_WITH_PRN:
     *     break;
     *     case PAYMENT_WITHOUT_PRN:
     *     break;
     * }
     * }
     */
}
