public class Baker extends Thread{
    private String name;
    private int number;
    private Exp experience;
    private final Storage<String> orderQueue;
    private final Storage<Pizza> storage;
    volatile private WorkingState workingState;
    public Baker(String name, int number, Exp experience, Storage<String> orderQueue, Storage<Pizza> storage, WorkingState workingState){
        this.workingState = workingState;
        this.name = name;
        this.number = number;
        this.experience = experience;
        this.orderQueue = orderQueue;
        this.storage = storage;
    }
    public void setWorkingState(WorkingState workingState){
        this.workingState = workingState;
    }
    public void startJob() throws InterruptedException {
        while (true){
            String order;
            synchronized (orderQueue){
                while (orderQueue.isEmpty() && workingState == WorkingState.WORK){
                    orderQueue.wait();
                }
                if(workingState == WorkingState.STOP){
                    orderQueue.notifyAll();
                    break;
                }
                if(orderQueue.getFreeSpace() == 0){
                    orderQueue.notifyAll();
                }
                order = orderQueue.popItem();
            }
            System.out.format("(%d)%s started to cook order: %s\n",number, name, order);
//            Thread.sleep((Exp.maxExp() - experience.getVal() + 1) * 400);
            synchronized (storage){
                while (storage.getFreeSpace() == 0 && workingState == WorkingState.WORK){
                    storage.wait();
                }
                if(workingState == WorkingState.STOP){
                    storage.notifyAll();
                    break;
                }
                if(storage.isEmpty()){
                    storage.notifyAll();
                }
                System.out.format("(%d)%s finished cooking order: %s\n",number, name, order);
                storage.pushItem(new Pizza(order));
            }
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

