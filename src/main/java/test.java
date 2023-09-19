import org.dreambot.api.Client;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {

    public static void main(String[] args) {
        String csvFile = "playersDatav1.csv";
//        String line = String.format("%s,%s,%s,%s,%s,%s,%s,%s", randomPlayer.getName()+"", Worlds.getCurrentWorld()+"", randomPlayer.getLevel()+"", skulledOrNot+"", playerDefLevel+"", Math.round(randomPlayer.distance())+"" , randomPlayer.getHealthPercent()+"", total+"");
        String line = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,", new Date()+"", 17, 200, "faker", "ryan", 17, 200, "faker", 1);
        //armour
        System.out.println(line);
        try (FileWriter writer = new FileWriter(csvFile, true);
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {

            bufferedWriter.write(line);
            bufferedWriter.newLine();

            System.out.println("New lines added to the CSV file successfully.");
        } catch (IOException e) {
            System.out.println("Error adding lines to CSV file: " + e.getMessage());
        }
    }

//    public static void main(String[] args) throws IOException {
//        String[][] employees = {
////                {"Man", "test", "msparkes0@springhow.com", "Engineering"},
//        };
//        String csvHeader = "Username,Items,World,Combat,Skulled,DefLvl, Distance, Health, TotalGp";
//        File csvFile = new File("playersData.csv");
//        FileWriter fileWriter = new FileWriter(csvFile);
//        fileWriter.append(csvHeader);
//        fileWriter.append("\n");
//        //write header line here if you need.
//
//        for (String[] data : employees) {
//            StringBuilder line = new StringBuilder();
//            for (int i = 0; i < data.length; i++) {
//                line.append("\"");
//                line.append(data[i].replaceAll("\"","\"\""));
//                line.append("\"");
//                if (i != data.length - 1) {
//                    line.append(',');
//                }
//            }
//            line.append("\n");
//            fileWriter.write(line.toString());
//        }
//        fileWriter.close();
//    }
}
