import java.util.Scanner;

public class PalindromeCheckerApp {
    public static void main(String[] args){
                Scanner scanner = new Scanner(System.in);

                System.out.print("Enter a string: ");
                String input = scanner.nextLine();

                String reversed = "";

                for (int i = input.length() - 1; i >= 0; i--) {
                    reversed = reversed + input.charAt(i);
                }

                if (input.equals(reversed)) {
                    System.out.println("The given string is a palindrome.");
                } else {
                    System.out.println("The given string is not a palindrome.");
                }
                scanner.close();
    }
}
