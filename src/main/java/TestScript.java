import org.dreambot.api.Client;
import org.dreambot.api.input.Mouse;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.combat.Combat;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.friend.Friends;
import org.dreambot.api.methods.input.Camera;
import org.dreambot.api.methods.input.Keyboard;
import org.dreambot.api.methods.item.GroundItems;
import org.dreambot.api.methods.login.LoginUtility;
import org.dreambot.api.methods.magic.Magic;
import org.dreambot.api.methods.magic.Normal;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.tabs.Tabs;
import org.dreambot.api.methods.widget.Widgets;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.methods.world.Worlds;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.worldhopper.WorldHopper;
import org.dreambot.api.randoms.RandomEvent;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.listener.ChatListener;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.Player;
import org.dreambot.api.data.GameState;
import org.dreambot.api.wrappers.items.GroundItem;
import org.dreambot.api.wrappers.items.Item;
import org.dreambot.api.wrappers.widgets.WidgetChild;
import org.dreambot.api.wrappers.widgets.message.Message;


import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static org.dreambot.api.Client.seededRandom;
import static org.dreambot.api.methods.interactive.Players.all;
import java.time.LocalTime; // import the LocalTime class
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.dreambot.api.methods.interactive.Players.getLocal;

//https://twitter.com/keyboardosrs/status/1196548086513983488?lang=en
// 93 hops per hr / 1.55 hops per minute / 390 hops for ~20 hrs
// 10 minutes to hop 57 worlds / ~9 loops / 90 minutes / 4.2 hours to recover to full hops
//check if in combat, if person kills, add them to a database
// message other bot, and read chat and log off the other bot
@ScriptManifest(author = "Swanny", description = "cosmicBots", name = "Team Cosmic Picker Bots", category = Category.MONEYMAKING, version = 1.0)
public class TestScript extends AbstractScript implements ChatListener {
    private List<Integer> worldList = Arrays.asList(301, 308, 316, 326, 335, 371, 379, 380, 382, 383, 384, 394, 397, 398, 399, 417, 418, 430, 431, 433, 434, 435, 436, 437,451, 452, 453, 454, 455, 456, 469, 470, 471, 475, 476, 483, 497, 498, 499, 500, 501, 537, 542, 543, 544, 545, 546, 547, 552, 553, 554, 555, 556, 557, 562, 563,571,575);
    List <World> worldList2 = Worlds.all(w -> w.isF2P() && !w.isBeta() && !w.isDeadmanMode() && !w.isFreshStart() && !w.isLastManStanding() && !w.isLeagueWorld() && !w.isHighRisk() && !w.isPVP() && !w.isTournamentWorld() && w.getMinimumLevel() == 0);
    private List<Integer> totalList = Arrays.asList(372, 381, 393, 413, 414, 419, 427, 432, 468, 530, 561, 577);
    private List<Integer> smallList = Arrays.asList(308, 316, 326, 335, 371, 379, 380, 382, 383, 394, 397, 398, 399);
//    private List<Integer> smallList2 = Arrays.asList(308, 316, 326, 335, 371, 379, 380, 382, 383, 394, 397, 398, 399);

    Area lumbyArea =  new Area(3217, 3225, 3226, 3211);
    long startTime = System.nanoTime();
    private static Graphics g;
    private String worldToHop= "Botttom";
    private boolean isRunning;
    int loopNum = 0;
    int loopNumCycle = 0;
    int forcedLog = 0;
    Boolean missingRune;
    Boolean whichAccount;
    public int totalRuneCollected  = 0;
    List<Integer> filterWorlds = new ArrayList<Integer>();
    
    String runeType = "Cosmic rune"; //Cosmic rune, Staff of earth

    int runeNum = 3;
    List<Integer> finalWorlds = new ArrayList<Integer>();
  //one accounts die, next account logs out?, perfect loggin in miliseconds/ multi threading? walk to bank?
//    13 is open     rr?
    //0 is at mossy//use distance for logoff time
    //use json instead of txt


    public void onStart () {
//        createGUI();
        Mouse.setAlwaysHop(false);
        getRandomManager().disableSolver(RandomEvent.LOGIN);
        for (int i=0; i < worldList2.size(); i++) {
            filterWorlds.add(Integer.parseInt(worldList2.get(i).toString().substring(1,4)));
        }
        filterWorlds.add(326);
//        filterWorlds.add(326);
        Collections.sort(filterWorlds);
        filterWorlds.remove(0);
        log(filterWorlds.size() + " " + filterWorlds);
//        filterWorlds.remove(55-17-1); //world 501? People trade
        log(filterWorlds.size() + " " + filterWorlds);
        log(filterWorlds.size());
//        finalWorlds = filterWorlds;
        finalWorlds = filterWorlds.subList(0, filterWorlds.size()/2);
//        finalWorlds = filterWorlds.subList((filterWorlds.size()/2), filterWorlds.size());
        print(filterWorlds.size() + "  " + filterWorlds);
//        if(worldToHop == "Top") {
//            log("Top Worlds Selected");
//            finalWorlds = filterWorlds.subList(0, filterWorlds.size()/2);
//        } else if (worldToHop == "Bottom") {
//            log("Bottoms Worlds Selected");
//            finalWorlds = filterWorlds.subList((filterWorlds.size()/2), filterWorlds.size());
//        }
    }
//    Banking bank = new Banking();
    @Override //Infinite loop
    public int onLoop() {
        LocalTime myObj = LocalTime.now();
        Integer timeInt = Integer.parseInt(myObj.toString().substring(0, 2));
        // 1, 2, 3,5,  5, pm,1,  3, 4, 5, 6,  am,
        //(timeInt >= 7 && timeInt < 22) || (timeInt >= 19 && timeInt < 23)
           // if(isRunning) {
                for(int i=0; i < accounts.length; i++) {
                    if (true) {
                        log("Logging into new accounts: " + accounts[i][0]);
                        LoginUtility.login(accounts[i][0], accounts[i][1]);
                        sleep(1000);
                        LoginUtility.login(accounts[i][0], accounts[i][1]);
                        Sleep.sleepUntil(()-> Client.getGameState() == GameState.LOGGED_IN, 30000); // turned off 5-22-23
                        sleep(300); // turned off  5-22-23
                        searchPlayers(Boolean.FALSE, accounts[i][0], accounts[i][1]);
                        //            bank.mainBanking();
                        //            bank.goCosmic(); //15
                        for (int j=1; j <= 15; j++) {
                            hopWorlds(accounts[i][0], accounts[i][1]);
                            loopNum += 1;
                            loopNumCycle = j;
                        }
                        if (lumbyArea.contains(Players.getLocal())) {
                            log("outer loop, in lumby staying logged out");
                            sleep(1000 * 60 * 60 * 5);
                        }
                        log("Logging out of this account");
                        Tabs.logout();
                    }
                //}
            }
        return 1000;
    }
    @Override
    public void onPaint(Graphics g) {
        try {
            String match;
            Pattern pattern = Pattern.compile("\\d{1,2}");
            Matcher matcher = pattern.matcher(Client.getUsername());
            long endTime = System.nanoTime();
            long minutesRunning = ((endTime-startTime)/1000000000)/60;
            g.setColor(Color.red);
            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.drawString("# runes on acc: " + Inventory.count(runeType), 10 ,50);
            g.drawString("$ on acc: $" + Inventory.count(runeType)*Inventory.get(runeType).getLivePrice(), 10, 35);
            g.drawString("IPM: " + totalRuneCollected/minutesRunning, 10, 65);
            g.drawString("$PM: $" + Inventory.get(runeType).getLivePrice() * totalRuneCollected/minutesRunning, 10, 80);
            g.drawString("Loop #: " + loopNum, 10, 95);
            g.drawString("Loop Cycle #: " + loopNumCycle + "/15", 10, 110);
            g.drawString("# total runes: " + totalRuneCollected, 10, 125);
            g.drawString("Hours Running: " + minutesRunning/60, 10, 140);
            g.drawString("Username: " +  ((matcher.find()) ? matcher.group() : ""), 10, 155);
            g.drawString("Forced log: " +  forcedLog, 10, 170);
        } catch (Exception e) {
        }//.substring(12, 14)
    }
    private void hopWorlds(String userAcc, String userPass) {
        for (int i = 0; i < finalWorlds.size(); i++) {
            if(Client.isLoggedIn() && !Players.getLocal().isInCombat() && !lumbyArea.contains(Players.getLocal())) {
                getRandomManager().disableSolver(RandomEvent.LOGIN);
                WorldHopper.hopWorld(finalWorlds.get(i));
//                Sleep.sleepUntil(()-> Client.getGameState() == GameState.LOGGED_IN,45000); // turned off 5-22-23
//                if(Client.getGameState() == GameState.LOGGED_IN) { // turned off 5-22-23
//                    sleep(300);
//                }
              try {
                  Sleep.sleepUntil(() -> (Tabs.isOpen(Tab.LOGOUT) && Client.getGameState() == GameState.LOGGED_IN), 30000); // 608 ms 10% chance of not logging out
                  sleep(50); // added 9-16-23 && GameState.LOGGED_IN
              } catch(Exception e) {
                  log("Tabs.isOpen Error: " + e);
                  writetoCSV(Client.getUsername()+"", new Date()+"", Worlds.getCurrentWorld() + ""); //9-16-23 added
              }
                searchPlayers(Boolean.FALSE, userAcc, userPass);
                stayLoggedIn(userAcc, userPass, 1000*60*30);
                ReadingTxt(userAcc, userPass);
                Boolean missingRune = searchRune(Boolean.TRUE, i);
                if (missingRune) {
                    sleep(200);
                    i += 1;
                }
            }
        }
    }
    private void WritingTxt(String time) {
        try {
            log(time);
            FileWriter myWriter = new FileWriter("logout.txt");
            myWriter.write(time);
            myWriter.close();
            log("Successfully wrote to the file.");
        } catch (IOException e) {
            log("An error occurred trying to write to the txt");
            e.printStackTrace();
        }
    }
    private void ReadingTxt(String userAcc, String userPass){
        try {
            File myObj = new File("logout.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(data != "") {
                    print("LOGGING OUT != ");
                    Tabs.logout();
                    stayLoggedIn(userAcc, userPass, 60000*90);
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            log("An error occurred.");
            e.printStackTrace();
        }
        try {
            FileWriter myWriter = new FileWriter("logout.txt");
            myWriter.write("");
            myWriter.close();
        } catch (IOException e) {
            log("An error occurred trying to write to the txt");
            e.printStackTrace();
        }
    }
    private boolean searchRune(Boolean afk, int loopNum) {
//        List<GroundItem> groundItems = GroundItems.all(item -> item != null && cosmicTitle.contains(item) && !item.getName().equals("Cosmic rune"));
        GroundItem runes = GroundItems.closest(item -> item.getName().equals(runeType) && item.getAmount() == runeNum);
        if (runes != null) {
            sleep(Calculations.random(550,650));
            int currentRune = Inventory.count(runeType);
            if(runes.interact("Take")) {
                Sleep.sleepUntil(() -> Inventory.count(runeType) == currentRune+runeNum, 1000);
//                Tabs.open(Tab.MAGIC);
//                Magic.castSpellOn(Normal.HIGH_LEVEL_ALCHEMY, Inventory.get(runeType));
//                Tabs.open(Tab.LOGOUT);
//                Sleep.sleepUntil(()-> !Players.getLocal().isAnimating(),3000);
//                Tabs.open(Tab.LOGOUT);
//                sleep(250);
//                Tabs.open(Tab.LOGOUT);
                if (Inventory.count(runeType) != currentRune+runeNum) {
                    sleep(Calculations.random(100,200));
                    runes.interact("Take");
                                    }
                totalRuneCollected += runeNum;
            }
            return false;
        } else {
            log("Rune is missing... " + loopNum + ", in World: " + Worlds.getCurrentWorld() );
            return true;
        }
    }
    private void stayLoggedIn(String userAcc, String userPass, int timeLen) {
        if (!Client.isLoggedIn()) {
            int milisecondsOut = (timeLen);
            forcedLog += 1;
            log("Staying logged out for... " + milisecondsOut/(60*1000));
            sleep(milisecondsOut);
            LoginUtility.login(userAcc, userPass);
            sleep(2000);
            LoginUtility.login(userAcc, userPass);
            Sleep.sleepUntil(()-> Client.getGameState() == GameState.LOGGED_IN, 30000);
            sleep(750);
        }
    }
    private void searchPlayers(Boolean hopOrNot, String userAcc, String userPass) {
        long startSearchingTime = System.nanoTime(); //Start timer for search
        List<Player> allNearbyPlayers = all(player -> player != null); // Get all nearby players, you could filter your player out;
        allNearbyPlayers.remove(Players.getLocal()); //Remove yourself fom the list
        if (allNearbyPlayers.size() > 0) { // Ensure there are moe than 1 players in the list
            for (int j = 0; j < allNearbyPlayers.size(); j++) {
                Player randomPlayer = allNearbyPlayers.get(j); //grab each individual player
                int[] equipment = randomPlayer.getComposite().getAppearance();
                List<String> armour = new ArrayList<String>();
                List<String> clanmateList = new ArrayList<String>();
                List<String> pvpItems = new ArrayList<String>();
                Collections.addAll(pvpItems,"Staff of fire","Black scimitar" ,"Mithril scimitar", "Adamant scimitar","Adamant warhammer", "Steel warhammer","Rune warhammer", "Mithril warhammer", "Black warhammer", "Staff of air", "Staff of water", "Staff of earth", "Maple shortbow", "Willow shortbow", "Oak shortbow", "Shortbow", "Training bow");
                Collections.addAll(clanmateList, "Loggggging o", "loggoff now", "T3L3 0ut n0w" , "omightyswan", "N0rth East", "N0rth E3st", "cdog swan99", "Level 80", "SJW killer", "north pile", "north pure", "off follow", "north attack", "south pile", "r i i", "i ir", "SJW die", "loggoff now", "logg off now");
                //i suk di k
                for (int i = 0; i < equipment.length; i++) {
                    if (equipment[i] - 512 > 0){
                        armour.add(new Item(equipment[i]-512, 1).getName());
                    }
                }
                boolean isaPvper = false;
                for(int i=0; i < randomPlayer.getEquipment().size(); i++) {
                    if (pvpItems.contains(armour.get(i))) {
                        log(armour.get(i));
                        isaPvper = true;
                    }
                }
                if (!clanmateList.contains(randomPlayer.getName())) { //Make sure there not a clanmate
                    int combatLevel = Combat.getCombatLevel() + Combat.getWildernessLevel(); // 3 + 45 = 48
                    if (randomPlayer.getLevel() <= combatLevel) {
                        Mouse.setAlwaysHop(true);
                        log("He can attack you: " + randomPlayer.getName() +" "+ randomPlayer.getLevel());
                        if (Client.isLoggedIn()) {
                            Tabs.logout();
                            if(Client.isLoggedIn()) {
                                sleep(500);
                                Tabs.logout();
                                log("Had to redo the logout,,");
                            }
                            if(Client.isLoggedIn()) {
                                sleep(250);
                                Tabs.logout();
                                log("Had to redo the logout,,");
                            }
                            log("In seconds how long it took to logout: " + (System.nanoTime()-startSearchingTime)/1000000 + " ms");
                            Mouse.setAlwaysHop(false);
                            getRandomManager().disableSolver(RandomEvent.LOGIN);
                            boolean skulled = apiDiscord(randomPlayer); // 1, 2, 3,5,  5,   pm,1,  3, 4, 5, 6,  am,
                            List<String> pkList = new ArrayList<String>(); //botslapperrr
                            Collections.addAll(pkList, "i dont lose","Ro Smurf","stankesai","Ram Yo Booty","Clues Easy","CosmicRuneHC","Smoke llX","L3ftHimForXp","CrHalfdemon","Rockmoore214", "Rockmoore215", "Marcos pro", "BOT PILLAGER","eL-SkuunX", "XDlolXD", "73h","Free Cosmics", "LazerHawk2", "Thoreladheat", "41snow1651", "82silent2649", "NY Blunts", "Queer Bait", "Fiend Dish", "8 Showboat 8", "TankGoodOrNo", "botslapperrr", "Assolocaust", "Clarity 1999");
                            if(pkList.contains(randomPlayer.getName())) {
                                log("Logout Level: Free Cosmicer, Iron Man killer....");
                                WritingTxt("logout");
                                log("6 hours");
                                sleep(1000*60*60*6); //60*8
                            } else if (skulled) {
                                log("Logout Level: Skulled");
                                WritingTxt("logout");
                                log("140 minutes");
                                sleep(1000*60*140); //180
                            } else if(isaPvper & Math.round(randomPlayer.distance()) <= 5) {
                                log("Logout Level: Pking items held and <= than 5 tiles");
                                WritingTxt("logout");
                                log("100 minutes");
                                sleep(1000 * 60 * 100); //120
                            } else if(isaPvper & Math.round(randomPlayer.distance()) > 5) {
                                log("Logout Level: Pking items held and > than 5 tiles");
//                                WritingTxt("logout");
                                log("45 minutes");
                                sleep(1000*60*40); //120
                            } else if(randomPlayer.getLevel() == 3 & Math.round(randomPlayer.distance()) <= 5) {
                                log("Logout Level: Level 3 and <= than 5 tiles");
                                log("30 minutes");
                                sleep(1000 * 60 * 30); //45
                            } else if(randomPlayer.getLevel() == 3 & Math.round(randomPlayer.distance()) > 5) {
                                log("Logout Level: Level 3 and > than 5 tiles");
                                log("20 minutes");
                                sleep(1000*60*20); //45
                            } else if(Math.round(randomPlayer.distance()) <= 5){
                                log("Logout Level: Not pker and <= than 5 tiles");
                                log("40 minutes");
                                sleep(1000*60*40); //45
                            } else if(Math.round(randomPlayer.distance()) > 5){
                                log("Logout Level: Not pker and > than 5 tiles");
                                log("15 minutes");
                                sleep(1000*60*15); //45
                            }
                            LoginUtility.openWorldScreen();
                            sleep(500);
//                            List<Integer> randomList = Arrays.asList(301, 469);
//                            Random randomizer = new Random();
//                            Integer randomWorld = randomList.get(randomizer.nextInt(randomList.size()));
//                            LoginUtility.setWorldListColumnSize(5);
//                            log(randomWorld);

                            LoginUtility.changeWorld(469);
                            sleep(1000);
                            LoginUtility.closeWorldScreen();
                            sleep(500);
                            LoginUtility.login(userAcc, userPass);
                            sleep(2000);
                            LoginUtility.login(userAcc, userPass);
                            Sleep.sleepUntil(()-> Client.getGameState() == GameState.LOGGED_IN, 30000);
                            sleep(500);
                        }
                    } else {
                        log("In seconds how long it took to log this message: " + (System.nanoTime()-startSearchingTime)/1000000);
                        log("He can't attack you");
                        log(randomPlayer.getLevel() + " " + randomPlayer.getName());
                        apiDiscord(randomPlayer);
                        if (hopOrNot == Boolean.TRUE){
                            World world = Worlds.getRandomWorld(w -> w.isF2P() && w.isNormal() && w.getMinimumLevel() == 0);
                            WorldHopper.hopWorld(world);
                            log("Hopping to because of spam " + world);
                        }
                    }
                } else {
                    log("Found a clanmate: " + randomPlayer.getName());
                    if(randomPlayer.getName().equals("N0rth East")) {
                        sleep(1000*15);
                    }
                }
            }
        }
    }
     public boolean apiDiscord(Player randomPlayer){
        int playerDefLevel = 0;
        try {
            playerDefLevel = new Highscores(randomPlayer.getName()).getSkillExperience(Highscores.Skills.DEFENCE);
        } catch (Exception e) {
            System.out.println(e);
        }
        int[] equipment = randomPlayer.getComposite().getAppearance();
        List<Item> equipment1 = randomPlayer.getEquipment();
        log(equipment1);
        int total = 0;
        List<String> armour = new ArrayList<String>();
        for (int i = 0; i < equipment.length; i++) {
            if (equipment[i] - 512 > 0){
                armour.add(new Item(equipment[i]-512, 1).getName());
                total += new Item(equipment[i]-512, 1).getLivePrice();
            }
        }
        DiscordWebHook webhook = new DiscordWebHook("https://discord.com/api/webhooks/1017195790961233950/TGz4ALTu8v4zHRtvRcG6KCx0A9z31W88wVO5huO6LtuFBYkO3RYiGXzVnY60jRSiLJxqz");
        webhook.setUsername(runeType);
        String skulledOrNot = "";
        boolean skulledorNotBoolean = false;
        if (randomPlayer.getSkullIcon() == 8 || randomPlayer.getSkullIcon() == 9 || randomPlayer.getSkullIcon() == 10 || randomPlayer.getSkullIcon() == 11 || randomPlayer.getSkullIcon() == 12) {
            skulledOrNot = "Key Skulled: " + (randomPlayer.getSkullIcon() - 7);
            skulledorNotBoolean = true;
        } else if(randomPlayer.isSkulled()) {
            skulledOrNot = "Normal Skull";
            skulledorNotBoolean = true;
        } else {
            skulledOrNot = "None";
            skulledorNotBoolean = false;
        }
        String titleLoc = randomPlayer.getTile() + "";
        webhook.addEmbed(new DiscordWebHook.EmbedObject()
                .setTitle(randomPlayer.getName())
                .setDescription(armour + "")
                .addField("World: " , Worlds.getCurrentWorld() + "", true)
                .addField( "Combat: ", randomPlayer.getLevel() + "", true)
                .addField("Skulled: ", skulledOrNot, true)
                .addField("Def lvl: ", "" + playerDefLevel, true)
                .addField("Distance: ", Math.round(randomPlayer.distance()) + ", Tile: " + titleLoc.substring(1, 11), true)
                .addField("Health: ", "Health: " + randomPlayer.getHealthPercent() + "%", true)
                .addField("Total $: ", "$" + total, true));
        try {
            webhook.execute();
        }
        catch (java.io.IOException e) {
            log(e);
        }
         writetoCSV(randomPlayer.getName() + "", randomPlayer.getLevel() + "", Worlds.getCurrentWorld() + "");

        return skulledorNotBoolean;
    }
    private void writetoCSV(String first, String second, String third) {
        String csvFile = "playersDatav1.csv";
        String line = String.format("%s,%s,%s", first, second, third);
        //String line = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,", "rstest", 17, 200, "faker", "ryan", 17, 200, "faker", 1);
        //armour
        log(line);
        try (FileWriter writer = new FileWriter(csvFile, true);
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {

            bufferedWriter.write(line);
            bufferedWriter.newLine();

            log("New lines added to the CSV file successfully.");
        } catch (IOException e) {
            log("Error adding lines to CSV file: " + e.getMessage());
        }
    }

//    private void createGUI() {
//        //setting up GUI
//        JFrame frame = new JFrame();
//        frame.setTitle("Cosmic Bot");
//        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        frame.setLocationRelativeTo(Client.getInstance().getCanvas());
//        frame.setPreferredSize(new Dimension( 300, 170));
//        //setting up rows
//        JPanel settingPanel = new JPanel();
//        settingPanel.setLayout(new GridLayout( 1 ,3));
//
//        JLabel label = new JLabel();
//        label.setText("Choose worlds");
//        settingPanel.add(label);
//        JComboBox<String> worldComboBox = new JComboBox<>(new String[]{
//           "Top", "Bottom"
//        });
//        settingPanel.add(worldComboBox);
//        frame.getContentPane().add(settingPanel, BorderLayout.CENTER);
//
//        JPanel buttonPanel = new JPanel();
//        buttonPanel.setLayout(new FlowLayout());
//
//        JButton button = new JButton();
//        button.setText("Start");
//        button.addActionListener(l ->{
//            worldToHop = worldComboBox.getSelectedItem().toString();
//            isRunning = true;
////            frame.setVisible(false);
//        });
//        buttonPanel.add(button);
//        frame.dispose();
//        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH); // maybe not need?
//        frame.pack();
//        frame.setVisible(true);
//
//    }
    //When script ends do this.
    public void onExit () {
        log("Bot Ended");
        try {
            FileWriter myWriter = new FileWriter("logout.txt");
            myWriter.write("");
            myWriter.close();
            log("Successfully cleared the file.");
        } catch (IOException e) {
            log("An error occurred trying to write to the txt");
            e.printStackTrace();
        }
    }
}

