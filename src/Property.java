public class Property extends Field{
    protected int seriesID;
    protected Player owner;
    protected String option;

    boolean monopoly = false; // bruges i Plot.onLand() til at bestemme om der skal vises tilbud om at bygge

    public Property(int id, String label, int cost, int income, int serieID) {
        super(id, label, cost, income);
        this.seriesID = serieID;
    }


    @Override
    public String toString() {
        String s = super.toString();
        return s+" (serieID:"+this.seriesID+")";
    }
    @Override
    public String onLand(Player p){
        String s = super.onLand(p);
        if (this.owner == null){
            option = "buy";
            s += "\n Vil du købe "+this.getLabel()+" for "+this.cost+"kr. Din saldo nu:" +p.getBalance() + "\n Tast J for ja N for nej.";
        }else{
            option = "payRent";
            s += "\n "+this.owner+" ejer allerede "+this.getLabel();
            s += "\n Du skal betale "+this.getIncome()+" i husleje. Tast J for at acceptere:";


        }
        return s;
    }
    @Override
    protected String onAccept(Player p){
        String s="";

        if(option.equals("buy")){
            s = this.getLabel()+" er din!";

            p.buy(this.cost);
            //save deed hos spilleren
            p.addDeed(this);
            this.owner = p;

        }else if(option.equals("payRent")){
            s = "Du har betalt din husleje";
            p.pay(getIncome(),this.owner);
        }

        return s;
    }
    @Override
    protected String onReject(Player p){
        return "";
    }
}
