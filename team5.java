//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package oog.melee;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D.Double;
import robocode.AdvancedRobot;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;

public class team5 extends AdvancedRobot {
    static Double fieldRect;
    static double enemyDist;
    static String enemyName;
    static double absBearing;
    static double eHeading;
    static double eEnergy;
    static double targetValue;
    static double eVel;

    public team5() {
    }

    public void run() {
        this.setColors(Color.black, Color.red, Color.black);
        fieldRect = new Double(18.0D, 18.0D, this.getBattleFieldWidth() - 36.0D, this.getBattleFieldHeight() - 36.0D);
        this.setAdjustGunForRobotTurn(true);
        this.setAdjustRadarForGunTurn(true);
        targetValue = 1.0D / 0.0;

        while(true) {
            if (this.getGunHeat() >= 1.0D || bulletPower() <= 1.0D) {
                this.setTurnRadarRightRadians(1.0D / 0.0);
            }

            this.setFire(bulletPower());
            this.setMaxVelocity(1.0D / this.getTurnRemaining());
            if (this.getDistanceRemaining() == 0.0D) {
                double dist = Math.min(250.0D, Math.max(100.0D, enemyDist * 0.5D * Math.random()));
                double maxRating = -1.0D / 0.0;

                for(double pointAngle = 0.0D; pointAngle < 6.283185307179586D; pointAngle += 0.19634954084936207D) {
                    java.awt.geom.Point2D.Double movePoint = new java.awt.geom.Point2D.Double(this.getX() + dist * Math.sin(pointAngle), this.getY() + dist * Math.cos(pointAngle));
                    double rating = Math.abs(movePoint.x - this.getBattleFieldWidth() / 2.0D) + Math.abs(movePoint.y - this.getBattleFieldHeight() / 2.0D) - 200.0D * Math.min(Math.abs(Utils.normalRelativeAngle(pointAngle - absBearing + 1.5707963267948966D)), Math.abs(Utils.normalRelativeAngle(pointAngle - absBearing - 1.5707963267948966D)));
                    if (rating > maxRating && fieldRect.contains(movePoint)) {
                        maxRating = rating;
                        int dir = Math.abs(pointAngle - this.getHeadingRadians()) < 1.5707963267948966D ? 1 : -1;
                        this.setAhead(dist * (double)dir);
                        this.setTurnRightRadians(Utils.normalRelativeAngle(pointAngle + (dir == 1 ? 0.0D : 3.141592653589793D) - this.getHeadingRadians()));
                    }
                }
            }

            this.execute();
        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        if (e.getName() == enemyName || e.getDistance() + e.getEnergy() < targetValue) {
            java.awt.geom.Point2D.Double ePos = project(new java.awt.geom.Point2D.Double(this.getX(), this.getY()), e.getDistance(), absBearing = e.getBearingRadians() + this.getHeadingRadians());
            int count = 0;
            double enemyHeading;
            double headingChange = (enemyHeading = e.getHeadingRadians()) - eHeading;
            double eDir = Math.signum(eVel - (eVel = e.getVelocity()));
            double speed = eVel;

            do {
                ++count;
                if (!((double)count * (20.0D - 3.0D * bulletPower()) < Point2D.distance(this.getX(), this.getY(), ePos.x, ePos.y))) {
                    break;
                }

                speed = Math.max(-8.0D, Math.min(8.0D, speed - (speed / eDir > 0.0D ? eDir : eDir * 2.0D)));
                ePos = project(ePos, speed, enemyHeading);
                enemyHeading += headingChange;
            } while(fieldRect.contains(ePos));

            targetValue = (enemyDist = e.getDistance()) + (eEnergy = e.getEnergy());
            eHeading = e.getHeadingRadians();
            enemyName = e.getName();
            if (this.getGunHeat() < 1.0D) {
                this.setTurnRadarRightRadians(Utils.normalRelativeAngle(absBearing - this.getRadarHeadingRadians()) * 2.0D);
            }

            this.setTurnGunRightRadians(Utils.normalRelativeAngle(Utils.normalAbsoluteAngle(Math.atan2(ePos.x - this.getX(), ePos.y - this.getY())) - this.getGunHeadingRadians()));
        }

    }

    public static double bulletPower() {
        return Math.min(3.0D, Math.max(0.1D, Math.min(eEnergy / 3.0D, 500.0D / enemyDist)));
    }

    public static java.awt.geom.Point2D.Double project(java.awt.geom.Point2D.Double origin, double dist, double angle) {
        return new java.awt.geom.Point2D.Double(origin.x + dist * Math.sin(angle), origin.y + dist * Math.cos(angle));
    }

    public void onRobotDeath(RobotDeathEvent e) {
        targetValue = 1.0D / 0.0;
    }
}
