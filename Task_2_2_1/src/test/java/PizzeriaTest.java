import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

public class PizzeriaTest {
    ArrayList<Courier> couriers;
    ArrayList<Baker> bakers;
    Storage<Pizza> storage;
    Storage<String> orders;
    @Before
    public void init() throws InterruptedException {
        String[] courierNames = {"Danillo", "Artem", "Andrew", "Nastya", "Katya"};
        String[] bakerNames = {"Sif Costas", "Filip Kallias", "Erna Azaria", "Elke Kipling", "Lamprecht Nneka"};
        storage = new Storage<>(15);
        orders = new Storage<>(25);
        couriers = new ArrayList<>();
        bakers = new ArrayList<>();
        for(int i = 0; i < courierNames.length; i++){
            couriers.add(new Courier(courierNames[i], i, (i % 4) + 1, storage, WorkingState.WORK));
        }
        for(int i = 0; i < bakerNames.length; i++){
            Exp exp;
            if (i % 3 == 0){
                exp = Exp.LOW;
            }else if(i % 3 == 1){
                exp = Exp.MEDIUM;
            }else {
                exp = Exp.HIGH;
            }
            bakers.add(new Baker(bakerNames[i], i, exp, orders, storage, WorkingState.WORK));
        }
    }
    @Test
    public void simpleTest() throws InterruptedException {
        Pizzeria pizzeria = new Pizzeria(couriers, bakers, storage, orders);
        pizzeria.startJob();
        Thread.sleep(1000);
        pizzeria.stopJob();
    }
}
