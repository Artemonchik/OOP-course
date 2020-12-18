import java.util.ArrayList;
import java.util.HashMap;

public class PartiallyOrderedSet<T> {
    HashMap<T, Node> nodes = new HashMap<T, Node>(10);

    private void ensureElemInSet(T elem) {
        if (!nodes.containsKey(elem)) {
            nodes.put(elem, new Node(elem));
        }
    }

    public PartiallyOrderedSet() {

    }

    private PartiallyOrderedSet(HashMap<T, Node> nodes) {
        this.nodes = (HashMap<T, Node>) nodes.clone();
    }

    /**
     * Insert {@code elem1} >= {@code elem2} in PartiallyOrdered Set
     * On every insertion it makes transitive closure
     *
     * @param elem1 elem1 is greater element
     * @param elem2 elem2 is less element
     */
    public void insert(T elem1, T elem2) {
        ensureElemInSet(elem1);
        ensureElemInSet(elem2);
        Node node1 = nodes.get(elem1);
        Node node2 = nodes.get(elem2);
        if (elem1 == elem2) {
            return;
        }
        if (greater(elem2, elem1)) {
            throw new IllegalArgumentException("elem2 is already greater then elem1");
        }
        if (greater(elem1, elem2)) {
            return;
        }
        node1.lessElements.add(node2);
        node2.greaterElements.add(node1);
    }

    /**
     * Insert the element in partially ordered set
     * @param elem element wants to be inserted
     */
    public void insert(T elem) {
        insert(elem, elem);
    }

    /**
     * Delete element from the set
     *
     * @param elem element want to be deleted
     */
    public void delete(T elem) {
        if (!nodes.containsKey(elem)) {
            return;
        }
        Node node = nodes.get(elem);
        ArrayList<Node> greaterElements = node.greaterElements;
        ArrayList<Node> lessElements = node.lessElements;
        node.lessElements = new ArrayList<>();
        node.greaterElements = new ArrayList<>();
        for (Node greaterNode : greaterElements) {
            greaterNode.lessElements.remove(node);
        }
        for (Node lessNode : lessElements) {
            lessNode.greaterElements.remove(node);
        }
        for (Node lessNode : lessElements) {
            for (Node greaterNode : greaterElements) {
                insert(greaterNode.elem, lessNode.elem);
            }
        }
        nodes.remove(elem);
    }

    /**
     * @param elem element wanted to be found
     * @return true if element belongs to Set, false otherwise
     */
    public boolean search(T elem) {
        return nodes.containsKey(elem);
    }

    /**
     * @return all minimal elements in the set
     */
    public ArrayList<T> minimal() {
        ArrayList<T> result = new ArrayList<>();
        for (Node node : nodes.values()) {
            if (node.lessElements.size() == 0) {
                result.add(node.elem);
            }
        }
        return result;
    }

    /**
     * @return all maximal elements in the set
     */
    public ArrayList<T> maximum() {
        ArrayList<T> result = new ArrayList<>();
        for (Node node : nodes.values()) {
            if (node.greaterElements.size() == 0) {
                result.add(node.elem);
            }
        }
        return result;
    }

    /**
     * @return linear order of the all elements. If elem1 >= elem2 then result.indexOf(elem1) >= result.indexOf(elem2)
     */
    public ArrayList<T> topSort() {
        ArrayList<T> result = new ArrayList<>();
        PartiallyOrderedSet<T> pos = new PartiallyOrderedSet<T>(nodes);
        while (!pos.isEmpty()) {
            ArrayList<T> mins = pos.minimal();
            for (T min : mins) {
                pos.delete(min);
            }
            result.addAll(mins);
        }
        return result;
    }

    /**
     * @return true if set is empty, false otherwise
     */
    public boolean isEmpty() {
        return nodes.size() == 0;
    }

    /**
     * @param elem1 element that wanted to be compared with elem2
     * @param elem2 element that wanted to be compared with elem1
     * @return true if elem1 >= elem2 false otherwise
     */
    public boolean greater(T elem1, T elem2) {
        Node node1 = nodes.get(elem1);
        Node node2 = nodes.get(elem2);
        if (node1 == null || node2 == null) {
            throw new IllegalArgumentException("Some of the elements are not in set");
        }
        if (elem1 == elem2) {
            return true;
        }
        for (Node lessElement : node1.lessElements) {
            if (lessElement.elem == elem2) {
                return true;
            }
            if (greater(lessElement.elem, elem2)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return true if the set is linear, false otherwise
     */
    boolean isLinear() {
        ArrayList<T> minimals = minimal();
        if (minimals.size() > 1) {
            return false;
        }
        if (minimal().size() == 0) {
            return true;
        }
        for (Node node = nodes.get(minimals.get(0)); node.greaterElements.size() != 0; node = node.greaterElements.get(0)) {
            if (node.greaterElements.size() > 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return true if the set is lattice, false otherwise
     */
    boolean isLattice() {
        return minimal().size() == maximum().size() && maximum().size() == 1;
    }

    private class Node {
        T elem;
        ArrayList<Node> greaterElements = new ArrayList<>(5);
        ArrayList<Node> lessElements = new ArrayList<>(5);

        Node(T elem) {
            this.elem = elem;
        }
    }
}
