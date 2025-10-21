public class CodingBat {


    public boolean sleepIn(boolean weekday, boolean vacation) {
        if (!weekday || vacation) {
            return true;
        }

        return false;

    }

    public int diff21(int n) {
        if(n>21)
            return (n - 21)*2;
        return 21 - n;
    }

    public String helloName(String name) {
        return "Hello " + name + "!";
    }

    public int countEvens(int[] nums) {
        int count = 0;
        for (int num : nums) {
            if (num % 2 == 0)
                count++;
        }
        return count;
    }


}
