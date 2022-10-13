
import java.util.ArrayList;

public class Game {
    public FileIO fileIO = new FileIO();
    private TextUI textUI = new TextUI();
    private ArrayList<Player> players = new ArrayList();
    private Board board;
    private Player currentPlayer;

    public void gameSetup() {
        //**********************
        // Load af spiller data
        // **********************
        ArrayList<String> data =  fileIO.readGameData();
        if(data == null){
            data = textUI.getUserInput("Skriv spillernavn. Tast Q for at quitte",6);
        }
        this.createPlayers(data);
        //**********************
        // Load af felt data og bygge boardet
        // **********************

        String[] fieldData = fileIO.readBoardData();

        /*TODO: tilføj en linie der minder om den lige oven over.
           Her skal der dog loades Chance-kort data istedet for felt data (jvf. Task 2.d)
         * */
        String[] chanceData = fileIO.readChanceData();


        /*TODO: ændr dette konstruktor kald til Board, sådan at  Chance-kort data kommer med som argument (jvf. Task 2.d) */
        board = new Board(fieldData, chanceData);







        runGame();

    }

    public void saveGame(){
        FileIO.writeGameData(players);
    }

    private void runGame(){

        String input = "";

        int count = 0;

        while(!input.equalsIgnoreCase("q")) {

            currentPlayer = players.get(count);
            count++;
            throwAndMove();
            input = textUI.getUserInput("Fortsætte(C) eller Afslutte (Q) ?");
            if(count == players.size()){
                count = 0;
            }
        }

    }
    private void throwAndMove(){

        System.out.println("Det er "+currentPlayer.getName()+"'s tur. \n"
                +currentPlayer
                + "står på felt "
                +currentPlayer.getPosition());

        int result = 4;//Dice.rollDiceSum();//Det er denne linie du skal ændre for at teste forskellige felter!


        int newPos = currentPlayer.updatePos(result);
        Field f = board.getField(newPos);
        landAndAct(f);
    }

    private void landAndAct(Field f){

        String optionMsg = f.onLand(currentPlayer);
        String choice = textUI.getUserInput(optionMsg);
        String msg = f.processChoice(choice, currentPlayer);
        textUI.displayMessage(msg+  "\n STATUS:"+currentPlayer);

    }

    private void createPlayers(ArrayList<String> data){

        for (String s : data) {
            String[] values = s.replaceAll(" ","").split(","); //split arrayet så vi får adskildt de to værdier
            int balance;

            if (values.length > 1) {
                balance = Integer.parseInt(values[1]);
            } else {
                balance = Integer.parseInt("30000");
            }
            Player p = new Player(values[0], balance); // brug de to værdier til at lave en ny Player instans
            players.add(p);                            // tilføj Player instansen til array'et af spillere
        }

    }

    public void displayPlayers() {
        for (Player p:players) {
            System.out.println(p);
        }
    }
}
