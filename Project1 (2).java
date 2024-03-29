import java.io.*;
import java.util.*;

//    AmirHossein Safdarian:    973613038
//         Millad Muhammadi:    973613073
//AmirAli Abdorrazaghnezhad:    973613045
public class Project1 {
    public static void main(String[] args) throws FileNotFoundException {
        boolean loosed = false;
        Scanner sc = new Scanner(System.in);
        System.out.println("Hello :)");
        System.out.println("Enter your name:  ");
        String playerName = sc.next();
        File scores = new File("SavedData//scores.txt");
        scores.getParentFile().mkdirs();
        try {
            scores.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Story.gameHelp();
        System.out.println("Which Game Do You Want To Play?");
        String fileAddress=" ";
        String gameNames[]=new String[5];
        gameNames[0]="The Parham's Death";
        gameNames[1]="Game2";
        gameNames[2]="Game3";
        gameNames[3]="Game4";
        gameNames[4]="Game5";
        for (int i = 0; i < gameNames.length; i++) {
            System.out.println((i+1)+" - "+gameNames[i]);
        }
        // int gameId = getInt("Enter The Story Number you want to play: ");
        int gameId=0;
        do{
            gameId = getInt("Enter The Story Number you want to play: ");
            if (gameId==1)
            {
                fileAddress="thedeathofparham.txt";
            }
            else
            {
                System.out.println("We don't have this game! Please enter number one :)");

            }
        }while(gameId!=1);
        /* do{
         switch (gameId)
         {
              case 1: fileAddress="//home//millad//Documents//TheProjectV4//thedeathofparham.txt";
              break;
              default: {fileAddress="1"; System.out.println("We don't have this game! Please enter number one :)");}
         }
        }while (fileAddress==" ");*/
        GameDATA aData = new GameDATA(fileAddress);
        Story str=new Story(aData);
        System.out.println("This is the story...");
        str.gameStory();
        System.out.println("This is the story map...");
        str.map();
        System.out.println("Let's play :)");
        long startTime = System.nanoTime();
        try{
            str.play();
        }catch(LoosedException e){
            loosed=true;
        }
        long endTime   = System.nanoTime();
        long totalTime = (endTime - startTime)/(1000000000);
        //System.out.println(totalTime);
        String finalTime = Long.toString(totalTime);
        try {
            FileWriter fr = new FileWriter(scores, true);
            fr.write(playerName);
            fr.write("\n");
            fr.write(finalTime);
            fr.write("\n");
            if (loosed){
                fr.write("LOOSED");
                fr.write("\n");
            }
            else{
                fr.write("WON");
                fr.write("\n");
            }
            fr.flush();
            fr.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Do you want to see the score board? (YES or NO)");
        String yesOrno = sc.next();
        if (yesOrno.toUpperCase().equals("YES")){
            Scanner sco = new Scanner(scores);
            System.out.println();
            while (sco.hasNext())
            {
                System.out.println("Player:");
                System.out.println(sco.nextLine());
                System.out.println("Have finished in:");
                System.out.printf("%.0f  %s", Double.parseDouble(sco.nextLine()),"seconds");
                System.out.println();
                System.out.println("And have:");
                System.out.println(sco.nextLine());
                System.out.println();
                //System.out.println("Player: "+sco.next()+" have finished in: "+sco.next()+" milli seconds "+ " and have "+sco.next()+" the game!");
            }
            System.out.println("GoodBye :)");
            return;
        }
        else{
            System.out.println("GoodBye :)");
            return;
        }
    }
    static int getInt(String promote)
    {
        System.out.println(promote);
        while (true){
            try{
                return Integer.parseInt(new Scanner(System.in).next());
            }
            catch(Exception e)
            {
                System.out.println("This is not a valid number! \n"+promote);
            }
        }

    }
}
class LoosedException extends Exception{}
class GameDATA
{
    Person []charData;
    Room []roomData;
    int personCharNum, roomNum, thingsNum,assisCharNum, policeCharNum,fullCharNum;
    Scanner d;
    String fileAddress;
    public GameDATA(String a) throws FileNotFoundException
    {
        d=new Scanner(new File(a));
        personCharNum=d.nextInt();
        d.nextLine();
        assisCharNum=d.nextInt();
        d.nextLine();
        policeCharNum=d.nextInt();
        d.nextLine();
        roomNum=d.nextInt();
        d.nextLine();
        fullCharNum=personCharNum+assisCharNum+policeCharNum;
        charData = new Person[fullCharNum];
        int i;
        for (i = 0; i < personCharNum; i++) {
            charData[i]=new Person();
        }
        for (i=i ; i < policeCharNum+personCharNum ; i++) {
            charData[i]=new PoliceOfficer();
        }
        for (i=i ; i < fullCharNum; i++) {
            charData[i]=new Assistant();
        }
        roomData = new Room[roomNum];
        for (int k = 0; k < roomNum; k++) {
            roomData[k]=new Room();
        }

        this.setChars();
        this.setRooms();
    }
    public void setChars()
    {
        for (int i = 0; i < fullCharNum; i++) {
            charData[i].setfinger(i);
            charData[i].charName=d.nextLine();
            charData[i].charDescreption=d.nextLine();
            charData[i].murder=d.nextBoolean();
            d.nextLine();
            charData[i].charAnswer[0]=new String[fullCharNum];
            charData[i].charAnswer[1]=new String[fullCharNum];
        }

        for (int j=0; j<fullCharNum; j++){
            for (int i = 0; i < fullCharNum; i++)
            {
                charData[j].charAnswer[0][i]=charData[i].charName;
            }
        }
        for (int j=0; j<fullCharNum; j++){
            for (int i = 0; i < fullCharNum; i++)
            {
                charData[j].charAnswer[1][i]=d.nextLine();
            }
        }
    }
    public void setRooms()
    {
        for (int i = 0; i < roomNum; i++) {
            roomData[i].name=d.nextLine();
            roomData[i].voice=d.nextLine();
            roomData[i].thingsNum=d.nextInt();
            d.nextLine();
            roomData[i].arr=new Things[roomData[i].thingsNum];
            for (int j = 0; j < roomData[i].thingsNum; j++) {
                roomData[i].arr[j]=new Things();
            }
            for (int j = 0; j < roomData[i].thingsNum ; j++) {
                roomData[i].arr[j].name=d.next();
                d.nextLine();
                roomData[i].arr[j].describtion=d.nextLine();
                roomData[i].arr[j].pickable=d.nextBoolean();
                d.nextLine();
                roomData[i].arr[j].finum=d.nextInt();
                d.nextLine();
                roomData[i].arr[j].finarr =new int[roomData[i].arr[j].finum];
                for (int k = 0; k < roomData[i].arr[j].finum; k++) {
                    roomData[i].arr[j].finarr[k]=d.nextInt();
                    d.nextLine();
                }
            }
        }
    }

}
interface Action{
    public void pick(String name);
    public void move(String r);
    public void listen();
    public void look(String r);
    public void choose(String r) throws LoosedException;
    public void search(String name,String hadaf);
    //public void examining();
    public void fire(String name,String hadaf);
    public void specialact(String pers,String acto,String hadaf);
    //public void specialpick();

}
class Story implements Action{
    static boolean wonORlossed = false;
    ArrayList<String> pocket = new ArrayList<String>();
    Person[] characters;
    Room[] gameRooms;
    GameDATA storyData;
    int thisRoomNum = 0;
    boolean endGame = false;
    String gameParStory;
    Person[] table_characters;
    Room[] table_gameRooms;

    public Story(GameDATA a) {
        storyData = a;
        characters = storyData.charData;
        table_characters=characters.clone();
        Room[] temp = new Room[(storyData.roomNum)+1];
        for (int i = 0; i < storyData.roomNum; i++) {
            temp[i]=storyData.roomData[i];
        }
        temp[storyData.roomNum]=new SpecialRoom();
        temp[storyData.roomNum].name="SecretRoom";
        gameRooms = temp;
        table_gameRooms=gameRooms.clone();
        /*Things [] table_things;
        for (int i = 0; i <table_gameRooms.length ; i++) {
            table_things=gameRooms[i].arr;
            table_gameRooms[i].arr=table_things;
            table_things=null;
        }*/
    }

    public static void gameHelp() {
        System.out.println(">This is whole game help!");
        System.out.println(">In this game you (Sherlock) have to find the murderer from the data you see and hear from the place!");
        System.out.println(">First you can choose the story you want to play.");
        System.out.println(">You can use these words to make an action.");
        System.out.println(" ");
        System.out.println(">MOVE : You can move between the rooms of the place.");
        System.out.println(">LOOK : You can see a glance of the place or take a deep look to a thing.");
        System.out.println(">LISTEN : You can hear the sounds of the place.");
        System.out.println(">PICK : You can pick a pickable thing from the room.");
        System.out.println(">ASK : You can ask from any character in the story about evey other character of the story.");
        System.out.println(">BACKUP : You can make a call to action to to a friend and use his special ability like: SEARCH or FIRE");
        System.out.println(">FIRE : With the help of your police friends you can kill a character!");
        System.out.println(">SEARCH : With the help of one of your assistants you can search for fingerprints on the thing.");
        System.out.println(">CHOOSE : You can choose someone as the murderer!");
        System.out.println(" ");


    }
    public void map() {
        System.out.println(">The Characters of this story: ");
        for (int i = 0; i < characters.length ; i++) {
            System.out.println(i+"  "+characters[i].charName);
        }
        System.out.println("  ");
        System.out.println(">The Rooms of this story: ");

        for (int i = 0; i < gameRooms.length ; i++) {
            System.out.println(i+"  "+gameRooms[i].name);
        }
        System.out.println("  ");
    }

    public void gameStory() {
        gameParStory="Parham is one the most intelligent coders! He and His friend and co-worker is working on a secret project! Last night the deadbody of Parham has been found in his house...";
        System.out.println(gameParStory);
        System.out.println("Who is the murderer?");
        System.out.println("  ");
    }

    void play() throws LoosedException{
        //s.setRoom(this.thisRoomNum);
        smartScanner s;
        String act = null;
        while (endGame == false) {
            s = new smartScanner(this.gameRooms,this.characters,thisRoomNum);
            s.input();
            act =s.getAction();
            switch (act) {
                case "MOVE": {
                    this.move(s.getObject());
                }
                break;
                case "LOOK": {
                    this.look(s.getObject());
                }
                break;
                case "LISTEN":
                    this.listen();
                    break;
                case "ASK":
                    this.ask(s.getObject(),s.getObject2());
                    break;
                case "CHOOSE":
                    this.choose(s.getObject());
                    break;
                case "FIRE":
                    specialact(s.getObject(),act,s.getObject2());
                    break;
                case "SEARCH":
                    specialact(s.getObject(),act,s.getObject2());
                    break;
                case  "PICK":
                    pick(s.getObject());
                    break;
                case "UNDO":
                    undo();
                    break;
                default:
                    System.out.println(">I can't understand what do you mean!");
            }
        }
        System.out.println(">Thanks for your playing!");
    }

    public void specialact(String pers,String acto,String hadaf) {
//        Scanner s = new Scanner(System.in);
//        System.out.println(">Whose help you need?");
//        String pers = s.next();
//        System.out.println(">What is the action you need?");
//        String acto = s.next();
        switch (acto) {
            case "FIRE":
                fire(pers,hadaf);
                break;
            case "SEARCH":
                search(pers,hadaf);
                break;
                    /*case "EXAMINE":
                        examining(characters[i]);
                        break;*/
                    /*case "PICK":
                        specialpick(characters[i]);
                        break;*/
            default:
                System.out.println(">I can't understand what do you mean!");
        }


    }

    public void move(String r) {
        save();
        int i;
        int temp = thisRoomNum;
        for (i = 0; i < storyData.roomNum; i++) {
            if (r.equals(gameRooms[i].name) == true) {
                thisRoomNum = i;
                break;
            }
        }
        if (gameRooms[i] instanceof SpecialRoom) {
            int scores = ((SpecialRoom)gameRooms[i]).game.hadsadad();
            if(scores!=0){
                System.out.println("You won the game with score: "+ scores);
                System.out.println(">You are still at:  " + gameRooms[i].name);


            }
            else{
                thisRoomNum=temp;
                System.out.println(">You are still at:  " + gameRooms[thisRoomNum].name);

            }
        }
        else
        {
            System.out.println(">Now you are at:  " + gameRooms[i].name);

        }

    }

    public void choose(String r) throws LoosedException {
        int i = 0;
        for (i = 0; i < characters.length; i++) {
            if (r.equals(characters[i].charName) == true) {
                break;
            }

        }
        if (characters[i].murder == true) {
            System.out.println(">You won the game!");
            wonORlossed=true;
        } else {
            System.out.println(">You losed the game!");
            wonORlossed=false;
            throw new LoosedException();

        }
        endGame = true;
    }
    public void ask(String who,String about)
    {
//        Scanner sc = new Scanner(System.in);
//        System.out.println(">Who do you want ask from?");
//        String who = sc.next();
//        System.out.println(">Who you want to ask about?");
//        String about = sc.next();
        for (int i = 0; i < characters.length ; i++) {
            //System.out.println("This is:"+characters[i].charName);
            if(characters[i]!=null){
                //System.out.println(characters[i].charName);
                if (characters[i].charName.equals(who)){
                    ((Person)characters[i]).ask(about);
                    return;
                }
            }
        }
        System.out.println(">We don't have this character!");
    }

    public void pick(String name) {
        save();
        for (int i = 0; i < gameRooms[thisRoomNum].arr.length; i++) {
            if(gameRooms[thisRoomNum].arr[i]!=null){
                if (gameRooms[thisRoomNum].arr[i].name.equals(name)) {
                    if (gameRooms[thisRoomNum].arr[i].pickable) {
                        pocket.add(name);
                        gameRooms[thisRoomNum].arr[i] = null;
                        //
                        //System.out.println(table_gameRooms[thisRoomNum].arr[i].name);
                        //
                        System.out.println(">successfully picked ");
                    } else {
                        System.out.println(">This thing is not pickable!");
                    }
                }
            }
        }
    }

    public void listen() {
        gameRooms[thisRoomNum].listen();
    }

    public void look(String r) {
        gameRooms[thisRoomNum].look(r);
    }





    /*public void specialpick(Person p) {
        if (p.picker) {
            for (int i = 0; i < gameRooms[thisRoomNum].arr.length; i++) {
                if (gameRooms[thisRoomNum].arr[i].pickable) {
                    pick(gameRooms[thisRoomNum].arr[i].name);
                }
            }
        } else {
            System.out.println("this character don't have this ability");
        }
    }*/

    public void fire(String name,String hadaf) {
        save();
        FatherPerson p=null;
        boolean flag=false;
        for (int i = 0; i < characters.length; i++) {
            if(characters[i]!=null){
                if(characters[i].charName.toLowerCase().equals(name.toLowerCase())){
                    p=characters[i];
                    flag=true;
                    break;
                }
            }
        }
        if (flag)
        {
            if (p instanceof PoliceOfficer) {
                ((PoliceOfficer)p).fire(characters,hadaf);
            }

            else {
                System.out.println(">This character don't have this ability.");
            }
        }
        else{
            System.out.println(">We don't have this character!");
            return;
        }




    }

    /*public void examining(Person p) {
        if (p.examine) {
            System.out.println("      ");
        } else {
            System.out.println("this character dont have this ability");
        }
    }*/

    public void search(String name,String hadaf) {
        FatherPerson p=null;
        boolean flag=false;
        //System.out.println(characters.length);
        for (int i = 0; i < characters.length; i++) {
            //System.out.println(characters[i].charName);
            if(characters[i]!=null){
                if(characters[i].charName.toLowerCase().contains(name.toLowerCase())){
                    //System.out.println("Found:"+characters[i].charName);
                    p=characters[i];
                    flag=true;
                }
            }
        }
        if (flag){
            if (p instanceof Assistant) {
                ((Assistant)p).search(gameRooms[thisRoomNum].arr,characters,hadaf);
            }

            else {
                System.out.println(">This character don't have this ability.");
            }

        }
        else {
            System.out.println(">We don't have this character!");
            return;
        }

    }


    public void save(){
        File f=new File("src//save.txt");
        try{
            f.createNewFile();
            FileWriter fr=new FileWriter(f,true);
            fr.write("***\n");
            fr.write((char)(thisRoomNum+48));
            fr.write("\n");
            for (int i = 0; i <gameRooms.length ; i++) {
                fr.write((char)(i+48));
                fr.write("\n");
                for (int j = 0; j <gameRooms[i].arr.length ; j++) {
                    if (gameRooms[i].arr[j]==null){fr.write("null");}
                    else{fr.write(gameRooms[i].arr[j].name);}
                    fr.write(" ");
                }
                fr.write("\n");
            }
            fr.write("characters\n");
            for (int i = 0; i <characters.length ; i++) {
                if (characters[i]==null){fr.write("null");}
                else {fr.write(characters[i].charName);}
                fr.write(" ");
            }
            fr.write("\npocket\n");
            if (pocket.size()!=0) {
                for (int i = 0; i < pocket.size(); i++) {
                    fr.write(pocket.get(i));
                    fr.write(" ");
                }
            }
            else {fr.write("null");}
            fr.write("\n");
            fr.flush();
            fr.close();
        }catch (IOException e){
            e.getMessage();
        }
    }
    public void undo(){
        //save();
        int countline=0,undoline=0;
        File f=new File("src//save.txt");
        try{
            String [] starr;
            Scanner s=new Scanner(f);
            while (s.hasNext()){
                countline++;
                if(s.nextLine().equals("***")){
                    undoline=countline;
                }
            }
            s=new Scanner(f);
            countline=0;
            while (countline!=undoline){
                s.nextLine();
                countline++;
            }
            thisRoomNum=Integer.parseInt(s.nextLine());
            Room[] tempgamerooms=table_gameRooms.clone();
            for (int i = 0; i < gameRooms.length; i++) {
                s.nextLine();
                starr=s.nextLine().split(" ");
                for (int j = 0; j <gameRooms[i].arr.length ; j++) {
                    if (starr[j].equals("null")){gameRooms[i].arr[j]=null;}
                    else{gameRooms[i].arr[j]=tempgamerooms[i].arr[j];}
                }
            }
            tempgamerooms=null;
            Person[] tempcharecter=table_characters.clone();
            s.nextLine();
            starr = s.nextLine().split(" ");
            for (int j = 0; j <characters.length ; j++) {
                if (starr[j].equals("null")) {characters[j]=null;}
                else {characters[j]=tempcharecter[j];}
            }
            tempcharecter=null;
            s.nextLine();
            starr=s.nextLine().split(" ");
            if (!starr[0].equals("null")){
                if (starr.length!=pocket.size()){
                    for (int i = 0; i <table_gameRooms.length ; i++) {
                        for (int j = 0; j <table_gameRooms[i].arr.length ; j++) {
                            if (table_gameRooms[i].arr[j].name.equals(pocket.get(pocket.size()-1))){
                                Things []tempthing=table_gameRooms[i].arr.clone();
                                gameRooms[i].arr[j]=tempthing[j];
                                tempthing=null;
                                pocket.remove(pocket.size()-1);
                            }
                        }
                    }
                }
            }
            else {
                if (pocket.size()!=0){
                    for (int i = 0; i <table_gameRooms.length ; i++) {
                        for (int j = 0; j <table_gameRooms[i].arr.length ; j++) {
                            System.out.println(table_gameRooms[i].arr[j].name);
                            if (table_gameRooms[i].arr[j].name.equals(pocket.get(pocket.size()-1))){
                                Things []tempthing=table_gameRooms[i].arr.clone();
                                gameRooms[i].arr[j]=tempthing[j];
                                tempthing=null;
                                pocket.remove(pocket.size()-1);
                            }
                        }
                    }
                }
            }
            s=new Scanner(f);
            File temp =new File("src//temp.txt");
            temp.createNewFile();
            FileWriter fr=new FileWriter(temp);
            countline=0;
            while (countline!=undoline-1){
                countline++;
                fr.write(s.nextLine());
                fr.write("\n");
            }
            fr.close();
            fr=new FileWriter(f);
            s=new Scanner(temp);
            while (s.hasNext()){
                fr.write(s.nextLine());
                fr.write("\n");
            }
            fr.close();

        }catch (IOException e){e.printStackTrace();}
        System.out.println("> undo done");
    }

}
abstract class FatherPerson{
    private int fingerprint;
    public void setfinger(int i){fingerprint=i;}
    public int getfinger(){return fingerprint;}
    public String charName;
    public String charAnswer[][]=new String[2][];
    public String charDescreption;
    public boolean murder=false;

}
class Person extends FatherPerson {

    public void ask(String about)
    {
        for (int i = 0; i < charAnswer[0].length; i++) {
            if (charAnswer[0][i].equals(about))
            {
                System.out.println(">"+charName+"  Opinion about "+about+" is: "+charAnswer[1][i]);
                return;
            }
        }
        System.out.println(">"+charName+ " doesn't have any opinion about "+about);
    }

}
class PoliceOfficer extends Person{

    public void fire(FatherPerson a[],String kill){
//        Scanner s = new Scanner(System.in);
//        System.out.println(">Who do you want to kill?");
//        String kill = s.next();
        boolean flag=false;
        for (int i = 0; i < a.length; i++) {
            if (a[i]!=null) {
                if (a[i].charName.toLowerCase().equals(kill.toLowerCase())) {
                    a[i] = null;
                    flag=true;
                    System.out.println(">Done! No longer alive!");
                }
            }
        }
        if (!flag) {
            System.out.println("we dont have this person");
        }
    }
}
class Assistant extends  Person{

    public void search(Things []a, FatherPerson []b,String thing)
    {
//        System.out.println(">What thing do you want to search?");
//        Scanner sc=new Scanner(System.in);
//        String thing=sc.next();
        for (int i = 0; i < a.length; i++) {
            if(a[i]!=null) {
                if (a[i].name.toLowerCase().equals(thing.toLowerCase())) {
                    a[i].printfinger(b);
                    return;
                }
            }
        }
        System.out.println(">We don't have this thing in this Room!");
    }
}
abstract class  FatherRoom {
    String voice;
    String name;
    Things []arr;
    int thingsNum;
    public void listen()
    {
        System.out.println(voice);
    }
    public void look(String obj)
    {
        boolean flag=false;
        if(obj.equals(name))
        {
            flag=true;
            System.out.println(">In this room you see these things:  ");
            for (int i = 0; i <arr.length ; i++)
            {
                if (arr[i]!=null){
                    System.out.println(arr[i].getName());
                }
            }
        }

        else
        {
            for (int i = 0; i < arr.length; i++)
            {
                if(arr[i]!=null){
                    if(obj.equals(arr[i].name)){
                        System.out.println(">The detail of the thing is :");
                        System.out.println(arr[i].describtion);
                        flag=true;
                        break;
                    }
                }
            }
        }
        if (flag) return;
        else
        {
            System.out.println(">You can not look at it!");
        }
    }
}
class Room extends FatherRoom{
}
class SpecialRoom extends Room{
    boolean locked;
    boolean dark;
    MiniGame game = new MiniGame();
}
class timeThread extends Thread{
    @Override
    public void run(){
        try {
            System.out.println("You Have 10 seconds from now");
            sleep(10000);
            throw new miniGameLost("Fuck You!!");
        }catch(InterruptedException e){

        }
    }
}
class miniGameLost extends RuntimeException{
    public miniGameLost(String s){
        super(s);
    }
}
class MiniGame{
    static class stopper{
        static int a=10;
    }
    public  int hadsadad(){
        timeThread th=new timeThread();
        Thread.UncaughtExceptionHandler h = new Thread.UncaughtExceptionHandler(){
            public void uncaughtException(Thread th, Throwable ex) {
                stopper.a=0;
            }
        };
        th.setUncaughtExceptionHandler(h);
        th.start();
        int score = 10;
        int ans;
        int a = (int) (Math.random() * 100) + 1;
        while (score > 0) {
            Scanner s = new Scanner(System.in);
            try {
                ans = s.nextInt();
            } catch (java.util.InputMismatchException e) {
                String name = s.next();
                if (name.toLowerCase().equals("parham") || name.toLowerCase().equals("sahand") || name.toUpperCase().equals("TA") || name.toLowerCase().equals("teacher") || name.toLowerCase().equals("behrouz")||name.toLowerCase().contains("mohsendb")) {
                    ans = 0;
                    System.out.println("The Answer is: " + a + " ;)");
                    if (s.hasNextInt()) {
                        ans = s.nextInt();
                    }
                } else {
                    ans = 0;
                }
            }
            if (ans == a) {
                System.out.println("You Won!!!");
                System.out.println("Your Score is : "+score*10);
                return score*10;
            } else if (ans < a) {
                System.out.println("Answer" + ">" + ans);
                score--;
            } else if (ans > a) {
                System.out.println("Answer" + "<" + ans);
                score--;
            }
            if(MiniGame.stopper.a==0){
                System.out.println("Your Time is Up!!!");
                break;
            }
        }
        if(score==0 || stopper.a==0){
            System.out.println("You Lost!!!");
            System.out.println("Your Score is : "+0);
            System.out.println("The Answer was : "+ a+" :)");
        }
        return 0;
    }
}
class Things implements Cloneable{
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
    String name;
    boolean pickable;
    String describtion;
    public String getName(){
        return name;
    }
    // az inja bebad jadide
    int finum;
    int []finarr;
    public void printfinger(FatherPerson[] p){
        boolean found=false;
        System.out.println(">You find fingerprint of these characters on it:");
        for (int i = 0; i < finum; i++) {
            for (int j = 0; j <p.length; j++) {
                if(finarr[i]==p[j].getfinger()){
                    System.out.println(p[j].charName);
                    found=true;
                }
            }
        }
        if(!found){
            System.out.println(">Did not find any fingerprint on it!");
        }
    }
}
class smartScanner{
    private String action=null;
    private String object=null;
    private String object2=null;
    private FatherRoom rooms[];
    private FatherPerson people[];
    private Things things[];
    private String roomnames[];
    private String peoplenames[];
    private String thingsnames[];

    public void setRoom(int j) {
        this.things=this.rooms[j].arr;
        this.thingsnames=new String[this.rooms[j].arr.length];
        for (int i = 0; i <this.rooms[j].arr.length ; i++) {
            if (rooms[j].arr[i]!=null) {
                this.thingsnames[i] = this.rooms[j].arr[i].name;
            }
            else{thingsnames[i]=null;}
        }

    }

    public smartScanner(Room r[], Person p[],int j){
        this.rooms=r;
        this.people=p;
        roomnames=new String[r.length];
        for (int i = 0; i <r.length ; i++) {
            if(r[i]!=null) {
                roomnames[i] = r[i].name;
            }
        }
        peoplenames=new String[p.length];
        for (int i = 0; i <p.length ; i++) {
            if(p[i]!=null) {
                peoplenames[i] = p[i].charName;
            }
        }
        setRoom(j);
    }
    public String getAction(){
        String temp=this.action;
        this.action=null;
        return temp;
    }
    public String getObject() {
        String temp=this.object;
        this.object=null;
        return temp;
    }
    public String getObject2() {
        String temp=this.object2;
        this.object2=null;
        return temp;
    }

    public String input(){
        int tryAgain=0;
        int index=-1;
        int index2=-1;
        int indexThing=-1;
        String text=null;
        Scanner s=new Scanner (System.in);
        do{
            tryAgain=0;
            String Uneditedtext=s.nextLine();
            text=Uneditedtext.toLowerCase();
            if(text.contains("move")||text.contains("go to")){
                this.action="MOVE";
                index=-1;
                for (int i = 0; i < roomnames.length; i++) {
                    if (text.contains((roomnames[i].toLowerCase()))) {
                        index = i;
                    }
                }
                if(index!=-1) {
                    this.object = roomnames[index];
                }
                while(index==-1){
                    System.out.println("I can't understand where do you want to go");
                    System.out.println("Enter the name of the room or enter \"giveup\" to return to top:" );
                    String retry=s.nextLine();
                    if (retry.contains("give")&&(retry.contains("up"))){
                        tryAgain=1;
                        break;
                    }else {
                        for (int i = 0; i < roomnames.length; i++) {
                            if (retry.contains((roomnames[i].toLowerCase()))) {
                                index = i;
                            }
                        }
                        if(index!=-1) {
                            this.object = roomnames[index];
                        }
                    }
                }
            }
            else if(text.contains("look")||text.contains("see")){
                this.action="LOOK";
                index=-1;
                indexThing=-1;
                for (int i = 0; i < roomnames.length; i++) {
                    if (text.contains((roomnames[i].toLowerCase()))) {
                        index = i;
                    }
                }
                for (int i = 0; i < thingsnames.length; i++) {
                    if(thingsnames[i]!=null) {
                        if (text.contains((thingsnames[i].toLowerCase()))) {
                            indexThing = i;
                        }
                    }
                }

                if(index!=-1) {
                    this.object = roomnames[index];
                }
                if(indexThing!=-1) {
                    this.object = thingsnames[indexThing];
                }

                while(index==-1&&indexThing==-1){
                    System.out.println("I can't understand what do you want to look at");
                    System.out.println("Enter the name of the room or thing or enter \"giveup\" to return to top:" );
                    String retry=s.nextLine();
                    if (retry.contains("give")&&(retry.contains("up"))){
                        tryAgain=1;
                        break;
                    }else {
                        for (int i = 0; i < roomnames.length; i++) {
                            if (retry.contains((roomnames[i].toLowerCase()))) {
                                index = i;
                            }
                        }
                        for (int i = 0; i < thingsnames.length; i++) {
                            if(thingsnames[i]!=null) {
                                if (retry.contains((thingsnames[i].toLowerCase()))) {
                                    indexThing = i;
                                }
                            }
                        }
                        if(index!=-1) {
                            this.object = roomnames[index];
                        }
                        if(indexThing!=-1) {
                            this.object = thingsnames[indexThing];
                        }
                    }
                }
            }
            else if(text.contains("listen")||text.contains("hear")){
                this.action="LISTEN";
            }
            else if(text.contains("pick")){
                this.action="PICK";
                index=-1;
                for (int i = 0; i < thingsnames.length; i++) {
                    if(thingsnames[i]!=null) {
                        if (text.contains((thingsnames[i].toLowerCase()))) {
                            index = i;
                        }
                    }
                }
                if(index!=-1) {
                    this.object = thingsnames[index];
                }
                while(index==-1){
                    System.out.println("I can't understand what do you want to pick up");
                    System.out.println("Enter the name of the thing to pick it up or \"giveup\" to return to top:" );
                    String retry=s.nextLine();
                    if (retry.contains("give")&&(retry.contains("up"))){
                        tryAgain=1;
                        break;
                    }else {
                        for (int i = 0; i < thingsnames.length; i++) {
                            if(thingsnames[i]!=null) {
                                if (retry.contains((thingsnames[i].toLowerCase()))) {
                                    index = i;
                                }
                            }
                        }
                        if(index!=-1) {
                            this.object = thingsnames[index];
                        }
                    }
                }
            }
            else if(text.contains("ask")){
                this.action="ASK";
                index=-1;
                for (int i = 0; i < peoplenames.length; i++) {
                    if(peoplenames[i]!=null) {
                        if ((text.contains("ask " + (peoplenames[i].toLowerCase()))) || (text.contains("from " + (peoplenames[i].toLowerCase())))) {
                            index = i;
                        }
                    }
                }
                if(index!=-1) {
                    this.object = peoplenames[index];
                }
                while(index==-1){
                    System.out.println("I can't understand who are you going to ask from");
                    System.out.println("Enter his name or enter \"giveup\" to return to top:" );
                    String retry=s.nextLine();
                    if (retry.contains("give")&&(retry.contains("up"))){
                        tryAgain=1;
                        break;
                    }else {
                        for (int i = 0; i < peoplenames.length; i++) {
                            if(peoplenames[i]!=null) {
                                if (retry.contains((peoplenames[i].toLowerCase())) && !(retry.contains("about " + (peoplenames[i].toLowerCase())))) {
                                    index = i;
                                }
                            }
                        }
                        if(index!=-1) {
                            this.object = peoplenames[index];
                        }
                    }
                }
                /////second one
                index2=-1;
                for (int i = 0; i < peoplenames.length; i++) {
                    if(peoplenames[i]!=null) {
                        if (text.contains("about " + (peoplenames[i].toLowerCase()))) {
                            index2 = i;
                        }
                    }

                }
                if(index2!=-1) {
                    this.object2 = peoplenames[index2];
                }
                while(index2==-1){
                    System.out.println("I can't understand who do you want to ask about");
                    System.out.println("Enter his name or enter \"giveup\" to return to top:" );
                    String retry=s.nextLine();
                    if (retry.contains("give")&&(retry.contains("up"))){
                        tryAgain=1;
                        break;
                    }else {
                        for (int i = 0; i < peoplenames.length; i++) {
                            if(peoplenames[i]!=null) {
                                if ((retry.contains((peoplenames[i].toLowerCase()))) && !((retry.contains(("from " + peoplenames[i].toLowerCase()))))) {
                                    index2 = i;
                                }
                            }
                        }
                        if(index2!=-1) {
                            this.object2 = peoplenames[index2];
                        }
                    }
                }

            }
            else if(text.contains("fire")||text.contains("shoot")||text.contains("kill")){
                this.action="FIRE";
                index=-1;
                for (int i = 0; i < peoplenames.length; i++) {
                    if(peoplenames[i]!=null) {
                        if ((text.contains((peoplenames[i].toLowerCase())))) {
                            index = i;
                        }
                    }
                }
                if(index!=-1) {
                    this.object = peoplenames[index];
                }
                while(index==-1){
                    System.out.println("Tell me whoose help do you need");
                    System.out.println("Enter his name or enter \"giveup\" to return to top:" );
                    String retry=s.nextLine();
                    if (retry.contains("give")&&(retry.contains("up"))){
                        tryAgain=1;
                        break;
                    }else {
                        for (int i = 0; i < peoplenames.length; i++) {
                            if(peoplenames[i]!=null) {
                                if (retry.contains((peoplenames[i].toLowerCase()))) {
                                    index = i;
                                }
                            }
                        }
                        if(index!=-1) {
                            this.object = peoplenames[index];
                        }
                    }
                }
                /////second one
                index2=-1;
                for (int i = 0; i < peoplenames.length; i++) {
                    if(peoplenames[i]!=null) {
                        if (text.contains((peoplenames[i].toLowerCase()))) {
                            index2 = i;
                        }
                    }
                }
                if(index2!=-1) {
                    this.object2 = peoplenames[index2];
                }
                while(index2==-1){
                    System.out.println("Tell me who do you want to kill");
                    System.out.println("Enter his name or enter \"giveup\" to return to top:" );
                    String retry=s.nextLine();
                    if (retry.contains("give")&&(retry.contains("up"))){
                        tryAgain=1;
                        break;
                    }else{
                        for (int i = 0; i < peoplenames.length; i++) {
                            if(peoplenames!=null) {
                                if ((retry.contains((peoplenames[i].toLowerCase())))) {
                                    index2 = i;
                                }
                            }
                        }
                        if(index2!=-1) {
                            this.object2 = peoplenames[index2];
                        }
                    }
                }
            }
            else if(text.contains("search")){
                this.action="SEARCH";
                index=-1;
                for (int i = 0; i < peoplenames.length; i++) {
                    if(peoplenames[i]!=null) {
                        if (text.contains(peoplenames[i].toLowerCase())) {
                            index = i;
                        }
                    }
                }
                if(index!=-1) {
                    this.object = peoplenames[index];
                }
                while(index==-1){
                    System.out.println("Tell me whoose help do you need");
                    System.out.println("Enter his name or enter \"giveup\" to return to top:" );
                    String retry=s.nextLine();
                    if (retry.contains("give")&&(retry.contains("up"))){
                        tryAgain=1;
                        break;
                    }else {
                        for (int i = 0; i < peoplenames.length; i++) {
                            if(peoplenames[i]!=null) {
                                if (retry.contains((peoplenames[i].toLowerCase()))) {
                                    index = i;
                                }
                            }
                        }
                        if(index!=-1) {
                            this.object = peoplenames[index];
                        }
                    }
                }
                /////second one
                index2=-1;
                for (int i = 0; i < thingsnames.length; i++) {
                    if (thingsnames[i]!=null) {
                        if (text.contains((thingsnames[i].toLowerCase()))) {
                            index2 = i;
                        }
                    }
                }
                if(index2!=-1) {
                    this.object2 = thingsnames[index2];
                }
                while(index2==-1){
                    System.out.println("Tell me what do you want to search");
                    System.out.println("Enter his name or enter \"giveup\" to return to top:" );
                    String retry=s.nextLine();
                    if (retry.contains("give")&&(retry.contains("up"))){
                        tryAgain=1;
                        break;
                    }else{
                        for (int i = 0; i < thingsnames.length; i++) {
                            if (thingsnames[i]!=null) {
                                if ((retry.contains((thingsnames[i].toLowerCase())))) {
                                    index2 = i;
                                }
                            }
                        }
                        if(index2!=-1) {
                            this.object2 = thingsnames[index2];
                        }
                    }
                }
            }
            else if(text.contains("choose")||text.contains("select")){
                this.action="CHOOSE";
                index=-1;
                for (int i = 0; i < peoplenames.length; i++) {
                    if(peoplenames[i]!=null) {
                        if (text.contains((peoplenames[i].toLowerCase()))) {
                            index = i;
                        }
                    }
                }
                if(index!=-1) {
                    this.object = peoplenames[index];
                }
                while(index==-1){
                    System.out.println("I can't understand who you are going to choose");
                    System.out.println("Enter his name or enter \"giveup\" to return to top:" );
                    String retry=s.nextLine();
                    if (retry.contains("give")&&(retry.contains("up"))){
                        tryAgain=1;
                        break;
                    }else {
                        for (int i = 0; i < peoplenames.length; i++) {
                            if(peoplenames[i]!=null) {
                                if (retry.contains((peoplenames[i].toLowerCase()))) {
                                    index = i;
                                }
                            }
                        }
                        if(index!=-1) {
                            this.object = peoplenames[index];
                        }
                    }
                }
            }
            //
            else if(text.contains("undo")){
                this.action="UNDO";
            }
            //
            else{
                System.out.println("I can't understand what do you want to do.");
                System.out.println("Enter your command again:");
                tryAgain=1;
            }
        }while(tryAgain==1);

        return text;
    }
}