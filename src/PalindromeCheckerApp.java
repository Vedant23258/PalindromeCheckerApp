import java.util.Deque;
import java.util.ArrayDeque;

public class PalindromeCheckerApp{

    public static void main(String[] args) {

        String input = "refer";

            Deque<Character> deque = new ArrayDeque<>();

        for (char c : input.toCharArray()) {
            deque.addLast(c);
        }

        boolean isPalindrome = true;

        while (deque.size() > 1) {
            char front = deque.removeFirst();
            char rear = deque.removeLast();

            if (front != rear) {
                isPalindrome = false;
                break;
            }
        }

        System.out.println("Input : " + input);
        System.out.println("Is Palindrome?    : " + isPalindrome);
    }

}