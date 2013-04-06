package hockey;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import util.ArithmeticPoint;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: perty
 * Date: 2013-04-06
 * Time: 19:13
 */
public class HockeyTeam extends Group {
    static final double meter = Hockey.meter;
    private static final Color HOME_TEAM_COLOR = Color.GREEN;
    private static final Color AWAY_TEAM_COLOR = Color.BLUE;

    enum Role {
        CENTER, LEFT_FORWARD, RIGHT_FORWARD, LEFT_DEFENCE, RIGHT_DEFENCE, GOALIE
    }

    private Map<Role, HockeyPlayer> players = new HashMap<Role, HockeyPlayer>();

    public HockeyTeam(boolean homeTeam) {
        double centerLine = 0.9 * meter;
        if (!homeTeam) {
            createTeam(AWAY_TEAM_COLOR, centerLine, centerLine + 5 * meter, Rink.eastGoalLine());
        } else {
            createTeam(HOME_TEAM_COLOR, -centerLine, -centerLine - 5 * meter, Rink.westGoalLine());
        }

    }

    private void createTeam(Color teamColor, double centerLine, double defenceLine, double goalLine) {
        ArithmeticPoint startPoint = relativeToCenterSpot(centerLine, 0);
        players.put(Role.CENTER, playerFacingCenterSpot(startPoint, teamColor));
        players.put(Role.LEFT_FORWARD, playerFacingCenterSpot(relativeToCenterSpot(centerLine, Rink.centerRadius()), teamColor));
        players.put(Role.RIGHT_FORWARD, playerFacingCenterSpot(relativeToCenterSpot(centerLine, -Rink.centerRadius()), teamColor));
        players.put(Role.LEFT_DEFENCE, playerFacingCenterSpot(relativeToCenterSpot(defenceLine, Rink.centerRadius()), teamColor));
        players.put(Role.RIGHT_DEFENCE, playerFacingCenterSpot(relativeToCenterSpot(defenceLine, -Rink.centerRadius()), teamColor));
        players.put(Role.GOALIE, playerFacingCenterSpot(new ArithmeticPoint(goalLine, Rink.rinkWidth / 2), teamColor));

        for (Role role : Role.values()) {
            getChildren().add(players.get(role));
        }
    }

    private HockeyPlayer playerFacingCenterSpot(ArithmeticPoint startPoint, Color teamColor) {
        HockeyPlayer center = new HockeyPlayer(startPoint, teamColor);
        center.faceTowardsPoint(Rink.centerSpot);
        return center;
    }

    private ArithmeticPoint relativeToCenterSpot(double deltaX, double deltaY) {
        return Rink.centerSpot().add(new ArithmeticPoint(deltaX, deltaY));
    }

    public void act() {
        for (Role role : Role.values()) {
            players.get(role).act();
        }
    }
}
