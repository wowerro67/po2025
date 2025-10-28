package animals;

public class Snake extends Animal {

    public Snake(String name) {
        this.name = name;
        this.legs = 0;
    }

    @Override
    public String getDescription() {
        return "Wąż o imieniu " + name + " ma " + legs + " nóg.";
    }
}
