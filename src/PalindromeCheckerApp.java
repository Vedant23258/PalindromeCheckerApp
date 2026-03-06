
public class PalindromeCheckerApp{

    public boolean checkPalindrome(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }

        int start = 0;
        int end = input.length() - 1;

        while (start < end) {
            if (input.charAt(start) != input.charAt(end)) {
                return false;
            }
            start++;
            end--;
        }
        return true;
    }

}