package hockey;

import javafx.scene.Group;
import javafx.scene.GroupBuilder;
import javafx.scene.paint.Color;
import util.ArithmeticPoint;

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

    public HockeyTeam(boolean homeTeam) {
        double centerLine = 0.9 * meter;
        if (!homeTeam) {
            getChildren().add(createTeam(AWAY_TEAM_COLOR, centerLine, centerLine + 5 * meter, Rink.eastGoalLine()));
        } else {
            getChildren().add(createTeam(HOME_TEAM_COLOR, -centerLine, -centerLine - 5 * meter, Rink.westGoalLine()));
        }

    }

    private Group createTeam(Color teamColor, double centerLine, double defenceLine, double goalLine) {
        ArithmeticPoint startPoint = relativeToCenterSpot(centerLine, 0);
        HockeyPlayer center = playerFacingCenterSpot(startPoint, teamColor);
        HockeyPlayer leftForward = playerFacingCenterSpot(relativeToCenterSpot(centerLine, Rink.centerRadius()), teamColor);
        HockeyPlayer rightForward = playerFacingCenterSpot(relativeToCenterSpot(centerLine, -Rink.centerRadius()), teamColor);
        HockeyPlayer leftDefence = playerFacingCenterSpot(relativeToCenterSpot(defenceLine, Rink.centerRadius()), teamColor);
        HockeyPlayer rightDefence = playerFacingCenterSpot(relativeToCenterSpot(defenceLine, -Rink.centerRadius()), teamColor);
        HockeyPlayer goalie = playerFacingCenterSpot(new ArithmeticPoint(goalLine, Rink.rinkWidth / 2), teamColor);

        return GroupBuilder.create()
                .children(
                        center,
                        leftForward,
                        rightForward,
                        leftDefence,
                        rightDefence,
                        goalie
                )
                .build();
    }

    private HockeyPlayer playerFacingCenterSpot(ArithmeticPoint startPoint, Color teamColor) {
        HockeyPlayer center = new HockeyPlayer(startPoint, teamColor);
        center.faceTowardsPoint(Rink.centerSpot);
        return center;
    }

    private ArithmeticPoint relativeToCenterSpot(double deltaX, double deltaY) {
        return Rink.centerSpot().add(new ArithmeticPoint(deltaX, deltaY));
    }
}
