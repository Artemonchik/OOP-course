import java.util.concurrent.ThreadLocalRandom;

public class OrderReceiver extends Thread {
    final Storage<String> queue;
    String[] pizzaNames = {"Neapolitan Pizza", "Chicago Pizza",	"New York-Style Pizza",
            "Sicilian Pizza",	"Greek Pizza", "California Pizza",
            "Detroit Pizza","St. Louis Pizza", "Types of Pizza Crust"};
    volatile WorkingState workingState;
    public OrderReceiver(Storage<String> queue, WorkingState workingState) {
        this.queue = queue;
        this.workingState = workingState;
    }

    public void setWorkingState(WorkingState workingState) {
        this.workingState = workingState;
    }

    public void startOrderGeneration() throws InterruptedException {
        while (true){
            synchronized (queue) {
                while (queue.getFreeSpace() == 0 && workingState == WorkingState.WORK) {
                    queue.wait();
                }
                if (workingState == WorkingState.STOP) {
                    queue.notifyAll();
                    break;
                }
                if(queue.isEmpty()){
                    queue.notifyAll();
                }
                int rand = ThreadLocalRandom.current().nextInt(0, pizzaNames.length);
                queue.pushItem(pizzaNames[rand]);
            }
        }
        System.out.println("OrderReceiver finished job");
    }

    @Override
    public void run() {
        try {
            startOrderGeneration();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
