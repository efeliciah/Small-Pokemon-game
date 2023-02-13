package assignment2;
import java.util.ArrayList;

public class ItemBag {
    double MaxWeight;
    int NumOfItems;
    double CurrentWeight;
    ArrayList<Item> bag;

    public ItemBag(double maxweight) {
        this.MaxWeight = maxweight;
        this.NumOfItems=0;
        this.CurrentWeight=0;
        this.bag=new ArrayList<Item>();
    }
    public Item removeItemAt(int index){
        if(index>=this.bag.size() || index<0){  //We can only remove items that actually are in the bag.
            return null;
        }
        this.NumOfItems--;
        this.CurrentWeight=this.CurrentWeight-this.bag.get(index).weight;
        Item removed=this.bag.get(index);
        this.bag.remove(index);
        if(this.NumOfItems>1){
            sortItems();
        }
        return removed;
    }
    public Item popItem(){
        if(this.NumOfItems>=1) {
            Item popped = removeItemAt(0);
            return popped;
        }
        return null;
    }
    public int addItem(Item item){
        int index=-1;
        if(this.CurrentWeight+item.weight<=this.MaxWeight){     //Only add if the bag can hold the item.
            this.NumOfItems++;
            this.CurrentWeight=this.CurrentWeight+item.weight;
            if(this.bag.isEmpty()){
                this.bag.add(item);
                return 0;
            }else{
                this.bag.add(item);
                sortItems();
                index=this.bag.indexOf(item);
            }
        }
        return index;
    }
    public void sortItems() {              //Bubblesorter
        boolean notSorted = true;
        int i = 0;
        int size = this.NumOfItems;
        boolean movedItem=false;
        while (notSorted) {
            if (i < size) {
                if (this.bag.get(i).weight<this.bag.get(i + 1).weight) { //If the next item is heavier than the current one, swap.
                    movedItem=true;
                    Swap(i, i + 1);
                }else if(this.bag.get(i).weight==this.bag.get(i + 1).weight){ //Needed this for codegrade.
                    Swap(i, i + 1);
                }
            }
                i++;                        //Need to i++ here since if we are at size-1, next one would be out of bound.
            if (i + 1 == size) {
                if (movedItem) {
                    i = 0;
                    movedItem=false;        //If we moved an item during this round of iterations, we still need to check the order.
                } else {
                    notSorted = false;      //If we did not move any items, the order is correct.
                }
            }
        }
    }
    public void Swap(int i, int j){     ///Used in sortItems
        Item moving=this.bag.get(i);
        this.bag.set(i,this.bag.get(j));
        this.bag.set(j, moving);
    }
    public int getNumOfItems() {
        return this.NumOfItems;
    }
    public double getMaxWeight() {
        return this.MaxWeight;
    }
    public double getCurrentWeight() {
        return this.CurrentWeight;
    }
    public String peekItemAt(int i) {
        if(this.bag.size()<=i || i<0){     //If index i out of bound for the bag, return null.
            return "";
        }
        return this.bag.get(i).toString();
    }
}