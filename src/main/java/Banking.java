import org.apache.tools.ant.types.resources.selectors.None;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.methods.walking.path.PathDirection;
import org.dreambot.api.methods.walking.path.impl.LocalPath;
import org.dreambot.api.methods.walking.web.node.CustomWebPath;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.GameObject;

import java.util.Arrays;
import java.util.List;

import static org.dreambot.api.utilities.Logger.log;
import static org.dreambot.api.utilities.Sleep.sleep;

public class Banking {


    Area firstTile = new Area(2950, 3796, 2956, 3790);
    Area secondTile = new Area(2950, 3629, 2962, 3619);
    Area thirdTile = new Area(3107, 3798, 3115, 3790);
    Area fourthTile = new Area(3133, 3746, 3138, 3741);
    Area tile3 = new Area(3135, 3722, 3140, 3717);

    Area fifthTile = new Area(3133, 3641, 3136, 3640);

    Area bankTile = new Area(3131, 3629, 3132, 3629);
    Area cosmicTile = new Area(2979, 3896, 2945, 3903);
    Area safeTile = new Area(3132, 3641, 3136, 3640);
    Area bankLeaveTitle = new Area(3134, 3638, 3134, 3638);
    LocalPath<Tile> localPath = new LocalPath<>();
    LocalPath<Tile> goPath = new LocalPath<>();


    public void mainBanking() {
        localPath.add(new Tile(2945, 3903, 0));
        //2945, 3903
        localPath.add(new Tile(2954, 3902, 0));
        localPath.add(new Tile(2966, 3899, 0));
        localPath.add(new Tile(2974, 3887, 0));
        localPath.add(new Tile(2983, 3874, 0));
        localPath.add(new Tile(2992, 3860, 0));
        localPath.add(new Tile(3000, 3844, 0));
        localPath.add(new Tile(3006, 3828, 0));
        localPath.add(new Tile(3016, 3819, 0));
        localPath.add(new Tile(3032, 3816, 0));
        localPath.add(new Tile(3045, 3809, 0)); //
        localPath.add(new Tile(3054, 3806, 0)); //
        localPath.add(new Tile(3064, 3804, 0));
        localPath.add(new Tile(3071, 3801, 0));
        localPath.add(new Tile(3079, 3799, 0));
        localPath.add(new Tile(3083, 3797, 0));
        localPath.add(new Tile(3087, 3799, 0));
        localPath.add(new Tile(3095, 3795, 0));
        localPath.add(new Tile(3111, 3793, 0));
        localPath.add(new Tile(3122, 3786, 0));
        localPath.add(new Tile(3122, 3770, 0));
        localPath.add(new Tile(3126, 3758, 0));

        localPath.add(new Tile(3129, 3755, 0));
        localPath.add(new Tile(3135, 3749, 0));


        localPath.add(new Tile(3136, 3740, 0));
        localPath.add(new Tile(3135, 3724, 0));
        localPath.add(new Tile(3135, 3709, 0));
        localPath.add(new Tile(3143, 3698, 0));
        localPath.add(new Tile(3141, 3684, 0));
        localPath.add(new Tile(3139, 3670, 0));


        localPath.add(new Tile(3139, 3665, 0));
        localPath.add(new Tile(3138, 3656, 0));
        localPath.add(new Tile(3133, 3643, 0));


//        localPath.reverse();
        log(localPath.size());
        Walking.setRunThreshold(101);
        if(Walking.isRunEnabled() == true) {
            Walking.toggleRun();
        }
        int i = 1;
        while (i <= localPath.size()) {
            if (i == 1) {
                Walking.toggleRun();
            } else if (i == 3) {
                Walking.toggleRun();
            }
            log(i);
            localPath.walk();
            sleep(1000);
            Sleep.sleepUntil(() -> !Players.getLocal().isMoving(), 15000);
            i++;
        }

//        while (!firstTile.contains(Players.getLocal())) {
//            Walking.walk(firstTile.getCenter());
//            sleep(1000);
//            Sleep.sleepUntil(() -> !Players.getLocal().isMoving(), 5000);
//        }
//        Walking.toggleRun();

        sleep(2000);
        GameObject b = GameObjects.closest(object -> object.getName().equals("Barrier"));
        if (b.interact("Pass-Through")) {
            sleep(8000);
        }

        Walking.walk(bankTile);
        sleep(8000);
        Bank.open();
        Sleep.sleepUntil(() -> Bank.isOpen() == true, 10000);
        Bank.depositAll("Cosmic rune");
        sleep(1000);
        Bank.close();
        sleep(500);
    }

    public void goCosmic() {
        goPath.add(new Tile(2945, 3903, 0));
        //2945, 3903
        goPath.add(new Tile(2954, 3902, 0));
        goPath.add(new Tile(2966, 3899, 0));
        goPath.add(new Tile(2974, 3887, 0));
        goPath.add(new Tile(2983, 3874, 0));
        goPath.add(new Tile(2992, 3860, 0));
        goPath.add(new Tile(3000, 3844, 0));
        goPath.add(new Tile(3006, 3828, 0));
        goPath.add(new Tile(3016, 3819, 0));
        goPath.add(new Tile(3032, 3816, 0));
        goPath.add(new Tile(3045, 3809, 0)); //
        goPath.add(new Tile(3054, 3806, 0)); //
        goPath.add(new Tile(3064, 3804, 0));
        goPath.add(new Tile(3071, 3801, 0));
        goPath.add(new Tile(3079, 3799, 0));
        goPath.add(new Tile(3083, 3797, 0));
        goPath.add(new Tile(3087, 3799, 0));
        goPath.add(new Tile(3095, 3795, 0));
        goPath.add(new Tile(3111, 3793, 0));
        goPath.add(new Tile(3122, 3786, 0));
        goPath.add(new Tile(3122, 3770, 0));
        goPath.add(new Tile(3126, 3758, 0));
        goPath.add(new Tile(3129, 3755, 0));
        goPath.add(new Tile(3135, 3749, 0));
        goPath.add(new Tile(3136, 3740, 0));
        goPath.add(new Tile(3135, 3724, 0));
        goPath.add(new Tile(3135, 3709, 0));
        goPath.add(new Tile(3143, 3698, 0));
        goPath.add(new Tile(3141, 3684, 0));
        goPath.add(new Tile(3141, 3679, 0));
        goPath.add(new Tile(3139, 3670, 0));
        goPath.add(new Tile(3140, 3677, 0));

        goPath.add(new Tile(3139, 3665, 0));
        goPath.add(new Tile(3138, 3656, 0));
//        goPath.add(new Tile(3133, 3643, 0));
        goPath.reverse();
        Walking.walk(bankLeaveTitle);
        sleep(10000);
        GameObject b = GameObjects.closest(object -> object.getName().equals("Barrier"));
        if (b.interact("Pass-Through")) {
            sleep(3000);
        }
        sleep(2000);

        Walking.walk(new Tile(3133, 3643, 0));
        sleep(2000);



        int i = 1;
        while (i <= goPath.size()) {
            log(i);
            goPath.walk();
            sleep(1000);
            Sleep.sleepUntil(() -> !Players.getLocal().isMoving(), 15000);
            i++;
        }

        //        log("rebuild");
//        for(int i=0; i <= pathToCosmic.length; i++) {
//            log("loop" + pathToBank[i]);
//            Walking.walk(pathToCosmic[i]);
//            sleep(500);
//            Sleep.sleepUntil(() -> !Players.getLocal().isMoving(), 8000);
//        }
        sleep(1000);
        Walking.toggleRun();
        sleep(1000);
        Walking.walk(new Tile(2962, 3903, 0));
        Sleep.sleepUntil(() -> !Players.getLocal().isMoving(), 8000);
        Walking.walk(new Tile(2945, 3903, 0));
        Sleep.sleepUntil(() -> !Players.getLocal().isMoving(), 8000);
        sleep(50000);
    }
}
