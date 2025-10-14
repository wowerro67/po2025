import java.util.ArrayList;
import java.util.Random;

public class Lotto
{
    public static void main(String[] args)
    {
        ArrayList<Integer> liczby = new ArrayList<>();
        Random losowy = new Random();

        while (liczby.size() < 6)
        {
            int liczba = losowy.nextInt(49) + 1;
            if (!liczby.contains(liczba))
            {
                liczby.add(liczba);
            }
        }

        System.out.println(liczby);
    }
}

