package DomainModels;

public class Ingredient {
    private String name;
    private Integer amount;
    private String amountUnit;

    public Ingredient(String name, Integer amt, String amtUnit){
        this.name = name;
        amount = amt;
        amountUnit = amtUnit;
    }

    public String getName(){
        return name;
    }

    public int getAmount(){
        return amount;
    }

    public String getUnit(){
        return amountUnit;
    }

    public void setName(String newName){
        this.name = newName;
    }

    public void setAmount(int newAmt){
        this.amount = newAmt;
    }

    public void setAmountUnit(String newUnit){
        this.amountUnit = newUnit;
    }
}