import java.util.ArrayList;

public class Pizzeria {
    ArrayList<Courier> couriers;
    ArrayList<Baker> bakers;
    Storage<Pizza> storage;
    Storage<String> orders;
    OrderReceiver orderReceiver;
    public Pizzeria(ArrayList<Courier> couriers, ArrayList<Baker> bakers, Storage<Pizza> storage, Storage<String> orders) {
        this.couriers = couriers;
        this.bakers = bakers;
        this.storage = storage;
        this.orders = orders;
    }

    public void startJob(){
        for(Courier courier: couriers){
            courier.start();
        }
        for(Baker baker: bakers){
            baker.start();
        }
        orderReceiver = new OrderReceiver(orders, WorkingState.WORK);
        orderReceiver.start();
    }
    public void stopJob() throws InterruptedException {
        for(Courier courier: couriers){
            courier.setWorkingState(WorkingState.STOP);
        }
        for(Baker baker: bakers){
            baker.setWorkingState(WorkingState.STOP);
        }
        orderReceiver.setWorkingState(WorkingState.STOP);
        for(Courier courier: couriers){
            courier.join();
        }
        for(Baker baker: bakers){
            baker.join();
        }
        orderReceiver.join();
    }
}
