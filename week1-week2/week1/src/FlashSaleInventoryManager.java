import java.util.*;

public class FlashSaleInventoryManager {

    private HashMap<String, Integer> stockMap = new HashMap<>();

    private HashMap<String, Queue<Integer>> waitingList = new HashMap<>();

    public FlashSaleInventoryManager() {
        stockMap.put("IPHONE15_256GB", 100);
        waitingList.put("IPHONE15_256GB", new LinkedList<>());
    }

    public int checkStock(String productId) {
        return stockMap.getOrDefault(productId, 0);
    }

    public synchronized String purchaseItem(String productId, int userId) {

        int stock = stockMap.getOrDefault(productId, 0);

        if (stock > 0) {
            stockMap.put(productId, stock - 1);
            return "Success, " + (stock - 1) + " units remaining";
        }
        else {
            Queue<Integer> queue = waitingList.get(productId);
            queue.add(userId);
            int position = queue.size();
            return "Added to waiting list, position #" + position;
        }
    }

    // Display waiting list
    public void showWaitingList(String productId) {
        Queue<Integer> queue = waitingList.get(productId);
        System.out.println("Waiting List: " + queue);
    }

    public static void main(String[] args) {

        FlashSaleInventoryManager manager = new FlashSaleInventoryManager();

        System.out.println("Stock: " + manager.checkStock("IPHONE15_256GB") + " units available");

        System.out.println(manager.purchaseItem("IPHONE15_256GB", 12345));
        System.out.println(manager.purchaseItem("IPHONE15_256GB", 67890));

        for (int i = 0; i < 100; i++) {
            manager.purchaseItem("IPHONE15_256GB", i);
        }

        System.out.println(manager.purchaseItem("IPHONE15_256GB", 99999));

        manager.showWaitingList("IPHONE15_256GB");
    }
}
