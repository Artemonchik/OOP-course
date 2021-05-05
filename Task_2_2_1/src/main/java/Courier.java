public class Courier extends Thread{
    private String name;
    private int number;
    private Bag bag;
    private final Storage<Pizza> storage;
    volatile private WorkingState workingState;
    public Courier(String name, int number, int capacity, Storage<Pizza> storage, WorkingState workingState){
        this.workingState = workingState;
        this.number = number;
        this.name = name;
        this.bag = new Bag(capacity);
        this.storage = storage;
    }
    public void setWorkingState(WorkingState workingState){
        this.workingState = workingState;
    }
    public void startJob() throws InterruptedException {
        while (true){
            synchronized (storage){
                while (storage.isEmpty() && workingState == WorkingState.WORK){
                    storage.wait();
                }
                if(workingState == WorkingState.STOP){
                    storage.notifyAll();
                    break;
                }
                if(storage.getFreeSpace() == 0){
                    storage.notifyAll();
                }
                while (bag.getFreeSpace() != 0 && !storage.isEmpty()){
                    bag.addPizza(storage.popItem());
                }
            }
            System.out.format("(%d) %s took pizzas %s and going to deliver them\n", number, name, bag.toString());
//            Thread.sleep(1300);
            System.out.format("(%d) %s delivered pizzas\n", number, name);
            bag.freeBag();
        }
        System.out.format("(%d) %s finished job pizzas\n", number, name);
    }

    @Override
    public void run() {
        try {
            startJob();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
