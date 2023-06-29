package effective.chapter4.item18;

public class Tiger {

    private Animal animal;

    public Tiger(Animal animal) {
        this.animal = animal;
    }

    public void cry() {
        System.out.println("Tiger crying");
    }

    public Animal getAnimal() {
        return animal;
    }
}
