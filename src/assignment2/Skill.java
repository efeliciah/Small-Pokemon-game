package assignment2;
import java.util.Objects;

public class Skill {
    String name;
    int AP;
    int EC;

    public Skill(String name, int AP, int EC) {
        this.name = name;
        this.AP = AP;
        this.EC = EC;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Skill)) return false;
        Skill skill = (Skill) o;
        return AP == skill.AP && EC == skill.EC && Objects.equals(name, skill.name);
    }
    @Override
    public String toString() {
        return  name + " - " +
                "AP: " + AP +
                " EC: " + EC;
    }
}
