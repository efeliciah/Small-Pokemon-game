package assignment2;
import java.util.Objects;
import static assignment2.Pokemon.Type.Fire;

public class Pokemon {
    public enum Type{
        Fire,
        Water,
        Grass,
        Normal,
        Bug,     //Added after type-expansion request.
        Dragon,  //Added after type-expansion request.
        Electric,//Added after type-expansion request.
        Ice      //Added after type-expansion request.
    }
    final Type type;
    final int MAX_HP;
    final int maxEP=100;
    int currentHP; int Energy; String name; Skill skill; boolean knowSkill;

    public Pokemon(String name, int maxHP, String type){
        this.Energy = maxEP;
        this.name = name;
        this.type=Type.valueOf(type);
        this.MAX_HP = maxHP;
        this.currentHP =maxHP;
        this.knowSkill=false;
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Pokemon)) return false;
        Pokemon otherPokemon = (Pokemon) other;
        return MAX_HP == otherPokemon.MAX_HP && currentHP == otherPokemon.currentHP && Energy == otherPokemon.Energy && type == otherPokemon.type && Objects.equals(name, otherPokemon.name) && Objects.equals(skill, otherPokemon.skill);
    }
    public void rest(){
        if(this.currentHP!=0){
        heal(20);}
    }
    public String useItem(Item potion){
        int oldHP=this.currentHP;
        heal(potion.healPwr);
        if(oldHP==this.currentHP){
            return this.name + " could not use " + potion.name + ". HP is already full.";
        }
        return this.name +" used " + potion.name + ". It healed " + (this.currentHP-oldHP) +" HP.";
    }
    public void heal(int healing){
        this.currentHP =this.currentHP +healing;
        if(this.currentHP > MAX_HP){
            this.currentHP = MAX_HP;
        }
    }
    public void recoverEnergy(){
        if(this.currentHP !=0) {  //No effect if fainted.
            this.Energy = this.Energy + 25;
            if (this.Energy > maxEP) {
                this.Energy = maxEP;
            }
        }
    }
    public void spendEP(int drain){
        this.Energy =this.Energy -drain;
    }
    public void learnSkill(String name, int AP, int EC){
        this.skill= new Skill(name, AP, EC);
        this.knowSkill=true;
    }
    public void forgetSkill(){
        this.skill=null;
        this.knowSkill=false;
    }
    @Override
    public String toString(){
        if(knowSkill){
        return this.name + " (" + this.type.toString() + "). " +"Knows " + this.skill;
        }else{
            return this.name + " (" + this.type.toString() + ")";
        }
    }
    public String attack(Pokemon target){
        String outcome="";
        if(this.currentHP ==0){
            outcome=("Attack failed. " + this.name + " fainted.");
        }else if(target.currentHP ==0){
            outcome=("Attack failed. " + target.name + " fainted.");
        }else if(!this.knowSkill){
            outcome=("Attack failed. " + this.name + " does not know a skill.");
        }else if(this.Energy <this.skill.EC){
            outcome=("Attack failed. " + this.name + " lacks energy.");
        }else{
            outcome=(this.name + " uses " +this.skill.name+ " on " + target.name + ".");
            double dmgMultiplier=this.typeCalculator(target);       //Check if attacker is strong, weak or neither against target.
            if (dmgMultiplier==2){
                outcome=outcome+(" It is super effective!");
            }else if(dmgMultiplier==0.5){
                outcome=outcome+(" It is not very effective...");
            }
            this.spendEP(this.skill.EC);
            outcome=outcome + target.takeDamage(this.skill.AP, dmgMultiplier) ;
        }
        return outcome;
    }
    public String takeDamage(int damage, double dmgMultiplier){
        this.currentHP =this.currentHP -(int)(damage*dmgMultiplier);  //Using (int) to truncate.
        if(currentHP<0){
            this.currentHP =0;
        }
        String newHP=System.lineSeparator() + this.name + " has " + this.currentHP + " HP left.";
        if(this.currentHP ==0){
            return newHP + " " + this.name + " faints.";
        }else{
            return newHP;
        }
    }
    public double typeCalculator(Pokemon defender){
        double dmgMultiplier=1;
        if(strongAgainst(this.type, defender.type)){        //Check if attacker is strong against defender.
            dmgMultiplier=2;
        }else if(weakAgainst(this.type, defender.type)){    //Check if attacker is weak against defender.
            dmgMultiplier=0.5;
        }
        return dmgMultiplier;
    }
    public boolean strongAgainst(Type attackerType, Type defenderType){
        if(attackerType==Type.Fire){
            if(defenderType==Type.Grass || defenderType==Type.Bug || defenderType==Type.Ice){
                return true;
            }
        }else if(attackerType==Type.Grass){
            if(defenderType==Type.Water){
                return true;
            }
        }else if(attackerType==Type.Water){
            if(defenderType==Type.Fire){
                return true;
            }
        }else if(attackerType==Type.Bug){           //Added after type-expansion request.
            if(defenderType==Type.Grass){
                return true;
            }
        }else if(attackerType==Type.Dragon){        //Added after type-expansion request.
            if(defenderType==Type.Dragon){
                return true;
            }
        }else if(attackerType==Type.Electric){      //Added after type-expansion request.
            if(defenderType==Type.Water){
                return true;
            }
        }else if(attackerType==Type.Ice){           //Added after type-expansion request.
            if(defenderType==Type.Grass || defenderType==Type.Dragon){
                return true;
            }
        }

        return false;
    }
    public boolean weakAgainst(Type attackerType, Type defenderType){
        if(attackerType==Type.Fire){
            if(defenderType==Type.Dragon || defenderType==Type.Fire || defenderType==Type.Water){
                return true;
            }
        }else if(attackerType==Type.Grass){
            if(defenderType==Type.Bug || defenderType==Type.Dragon || defenderType==Type.Fire || defenderType==Type.Grass){
                return true;
            }
        }else if(attackerType==Type.Water){
            if(defenderType==Type.Dragon || defenderType==Type.Grass || defenderType==Type.Water){
                return true;
            }
        }else if(attackerType==Type.Bug){       //Added after type-expansion request.
            if(defenderType==Type.Fire){
                return true;
            }
        }else if(attackerType==Type.Electric){  //Added after type-expansion request.
            if(defenderType==Type.Dragon || defenderType==Type.Grass || defenderType==Type.Electric){
                return true;
            }
        }else if(attackerType==Type.Ice){       //Added after type-expansion request.
            if(defenderType==Type.Fire || defenderType==Type.Ice || defenderType==Type.Water){
                return true;
            }
        }
        return false;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public int getMAX_HP() {
        return MAX_HP;
    }
    public int getCurrentHP() {
        return currentHP;
    }
    public int getEnergy() {
        return Energy;
    }
    public boolean knowsSkill() {
        return knowSkill;
    }
    public String getType() {
        return type.toString();
    }
}




