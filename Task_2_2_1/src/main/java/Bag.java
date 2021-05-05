import java.util.ArrayList;

public class Bag {
    private int capacity;
    private ArrayList<Pizza> pizzas;

    public int getCapacity() {
        return capacity;
    }
    public int getFreeSpace(){
        return capacity - pizzas.size();
    }
    public Bag(int capacity) {
        this.capacity = capacity;
        this.pizzas = new ArrayList<>(capacity);
    }

    public void addPizza(Pizza pizza) {
        if (getFreeSpace() != 0) {
            pizzas.add(pizza);
        } else {
            throw new ArrayStoreException("Array is full");
        }
    }

    public int getPizzasNum(){
        return pizzas.size();
    }
    public void freeBag(){
        pizzas.clear();
    }
    public String toString(){
        return pizzas.toString();
    }
}
