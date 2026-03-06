import java.util.Scanner;

public class PalindromeCheckerApp {
    public static void main(String[] args){
        boolean ispalindrome = true;
        String text = "madam";
        for (int i =0;i<text.length()/2;i++){
            if(text.charAt(i) != text.charAt(text.length()-1-i)){
                ispalindrome = false;
                break;
            }
        }
        System.out.println("Input Text:"+text);
        System.out.println("Is it a Palindrome? " + ispalindrome);
    }
}
