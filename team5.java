//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package team5;

import robocode.AdvancedRobot;
import robocode.BulletHitEvent;
import robocode.HitByBulletEvent;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;
import robocode.*;
import robocode.util.*;
import java.awt.*;
public class team5 extends AdvancedRobot {
    static double avgMoveLong = 2.0D;
    private static final double BULLET_POWER = 3.0D;
    private static final double BULLET_SPEED = 11.0D;
    static double MOVE_DIST = 219.0D;
    private static double move;
    private static int mode;
    private static double BULLET_DODGE;
    private static double enemyEnergy;

    static {
        move = MOVE_DIST;
        mode = -1;
        BULLET_DODGE = 0.17D;
    }

    public team5() {
    }

    public void run() {
        this.setTurnRadarRightRadians(1.0D / 0.0);
        this.setAdjustGunForRobotTurn(true);
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        this.setTurnRadarLeftRadians(this.getRadarTurnRemaining());
        double rd = e.getBearingRadians();
        this.setTurnRightRadians(Math.cos(rd));
        rd += this.getHeadingRadians();
        avgMoveLong = 0.048780488D * avgMoveLong + 0.951219512D * e.getVelocity() * Math.sin(e.getHeadingRadians() - rd);
		double power = Math.min(e.getEnergy() / 4,
						Math.min(e.getDistance() < 250 ? 3 + .4 : 3,
						Math.min(Math.abs(e.getVelocity()) < 1 || e.getDistance() < 250 ? 3 : 2,
						getEnergy()/2)));
        this.setFireBullet(power);
        this.setTurnRightRadians(Math.cos(e.getBearingRadians()));
        if (enemyEnergy > (enemyEnergy = e.getEnergy())) {
            this.setAhead((move *= (double)mode) * Math.random() + BULLET_DODGE);
        }

        rd -= this.getGunHeadingRadians();
        this.setTurnGunRightRadians(Utils.normalRelativeAngle(rd + avgMoveLong / 11.0D * Math.sin(rd)));
    }

    public void onHitByBullet(HitByBulletEvent event) {
        mode = -mode;
    }

    public void onBulletHit(BulletHitEvent event) {
        mode = -mode;
    }

    public void onHitWall(HitWallEvent e) {
        this.setAhead(move = -move);
    }
}
