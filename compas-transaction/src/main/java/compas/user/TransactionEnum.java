package compas.user;

/**
 * Created by CLLSDJACKT013 on 17/05/2018.
 */
public class TransactionEnum {
    enum Staff{
        JONAH(1),MARTIN(2),KIBII(3);
        private int value;
        Staff(int value){
            this.value = value;
        }
    };
    public static void main(String args[]){
        for(Staff s:Staff.values()){
            System.out.println(s +" "+s.value);
        }

    }
}
