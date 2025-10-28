package zadania;

import animals.*;
import java.util.Random;

public class Zoo {

    private Animal[] animals = new Animal[100];
    private Random random = new Random();

    public Zoo() {
        fillZoo();
    }

    private void fillZoo() {
        for (int i = 0; i < animals.length; i++) {
            int rand = random.nextInt(3);

            switch (rand) {
                case 0:
                    animals[i] = new Dog("Pies_" + i);
                    break;
                case 1:
                    animals[i] = new Parrot("Papuga_" + i);
                    break;
                case 2:
                    animals[i] = new Snake("Waz_" + i);
                    break;
            }
        }
    }

    public int getTotalLegs() {
        int sum = 0;
        for (Animal animal : animals) {
            if (animal != null) {
                sum += animal.getLegs();
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        Zoo zoo = new Zoo();

        System.out.println("\nSuma nóg w zoo: " + zoo.getTotalLegs());
    }
    
}

