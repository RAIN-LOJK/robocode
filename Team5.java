//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package radnor;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

public class Team5 extends AdvancedRobot {
    static final double STOPDECOMPILING = 0.002755783029464731D;
    static double a;
    static double b;

    public void run() {
        this.setAdjustGunForRobotTurn(true);
        this.setAdjustRadarForGunTurn(true);
        b = 190.0D;

        while(true) {
            this.turnRadarRightRadians(1.0D / 0.0);
        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        double c;
        double d;
        this.setTurnRightRadians(Math.cos((c = e.getBearingRadians()) - 0.002755783029464731D * b * (double)((d = e.getDistance()) < 200.0D ? -1 : 1)));
        if (this.getDistanceRemaining() == 0.0D) {
            this.setAhead((b = -b) * (0.4D + Math.random()));
        }

        if (d < 1000.0D / Math.sqrt((double)this.getOthers())) {
            this.setTurnRadarRightRadians((double)5 * Math.sin((c += this.getHeadingRadians()) - this.getRadarHeadingRadians()));
            this.setTurnGunRightRadians(Math.sin(c - this.getGunHeadingRadians() + Math.asin(Math.sin(e.getHeadingRadians() - c) * (c = e.getVelocity()) / (20.0D - (double)3 * (d = Math.min((double)3, Math.max(1.0D, 400.0D / d)))))));
            if ((a = 0.9D * a + 0.1D * c) * c >= 0.0D) {
                this.setFire(d);
            }
        }

        this.clearAllEvents();
    }

    public DoctorBob() {
    }
}
