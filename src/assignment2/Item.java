package assignment2;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Objects;

public class Item {
    String name;
    int healPwr;
    double weight;

    public Item(String name, int healPwr, double weight) {
        this.name = name;
        this.healPwr = healPwr;
        this.weight = weight;
    }
    @Override
    public String toString(){
        DecimalFormat df = new DecimalFormat("0.00");           //Found this method on stackoverflow for truncating.
        df.setRoundingMode(RoundingMode.DOWN);
        return this.name + " heals " + this.healPwr + " HP. " + "(" + df.format(this.weight) + ")";
    }
    @Override
    public boolean equals(Object otherItem) {
        if (this == otherItem) return true;
        if (!(otherItem instanceof Item)) return false;
        Item item = (Item) otherItem;
        return healPwr == item.healPwr && weight==item.weight  && Objects.equals(name, item.name);
    }
}
