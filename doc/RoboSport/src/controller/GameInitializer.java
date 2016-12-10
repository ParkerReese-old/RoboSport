package controller;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import models.BoardPosition;
import models.GameBoard;
import models.Robot;
import models.Team;

import view.SetupDisplay;

public class GameInitializer {

    public GameInitializer() {

    }

    public GameBoard initializeGame() {
        SetupDisplay SD = new SetupDisplay();
        int teamnum = SD.getTeamsChosen(); // number of teams in the game
        int boardSize = teamnum == 6 ? 7 : 5; // The board size

        List<Team> listofteams = SD.getTeams();
        if (teamnum == 2 || teamnum == 3 || teamnum == 6) {

            /* START OF DYLAN'S INSERT */
            List<Team.TeamColor> cols = new LinkedList<Team.TeamColor>();
            if (teamnum == 2) {
                cols.add(Team.TeamColor.RED);
                cols.add(Team.TeamColor.GREEN);
            } else if (teamnum == 3) {
                cols.add(Team.TeamColor.RED);
                cols.add(Team.TeamColor.YELLOW);
                cols.add(Team.TeamColor.BLUE);
            } else {
                cols.add(Team.TeamColor.RED);
                cols.add(Team.TeamColor.GREEN);
                cols.add(Team.TeamColor.YELLOW);
                cols.add(Team.TeamColor.BLUE);
                cols.add(Team.TeamColor.ORANGE);
                cols.add(Team.TeamColor.INDIGO);
            }
            // Randomize "cols"
            for (int i = 0; i < teamnum; i++) {
                cols.add((int) (Math.random() * (teamnum - 1)), cols.remove(i));
            }

            // Assign all teams a random color
            for (Team t : listofteams)
                t.setColor(cols.remove(0));

            // Put all robots in their proper place
            for (Team t : listofteams) {
                BoardPosition start = new BoardPosition(boardSize - 1, boardSize - 1,
                        t.getColor().getDirection());
                for (int d = 0; d < boardSize - 1; d++)
                    start.advance();
                start.turn(3);
                for (Robot r : t.getRobots())
                    r.setLocation(start.clone());
            }

            listofteams.sort(new Comparator<Team>() {
                @Override
                public int compare(Team arg0, Team arg1) {
                    return arg0.getColor().ordinal() - arg1.getColor().ordinal();
                }
            });
            /* END OF DYLAN'S INSERT */



            GameBoard GB = new GameBoard(listofteams);
            return GB;
        }
        return null;
    }

    public static void main(String args[]) {
        GameInitializer GI = new GameInitializer();
        List<Team> teams = new LinkedList<Team>();
        GameBoard GB = new GameBoard(teams);
        BoardPosition BP = new BoardPosition(0, 0, 0);

        GB = GI.initializeGame();

        /* hardcode for a game of 2 players */
        assert (GB != null);
        if (GB.getTeams().size() == 2) {
            assert (GB.getTeams().size() == 2);
            assert (GB.getAllRobots().size() == 6);
            assert (GB.getSize() == 5);
            BP = new BoardPosition(0, 4, 0);
            assert (GB.getTeams().get(0).getRobots().get(0).getLocation().equals(BP));
            assert (GB.getTeams().get(0).getRobots().get(1).getLocation().equals(BP));
            assert (GB.getTeams().get(0).getRobots().get(2).getLocation().equals(BP));
            assert (GB.getTeams().get(0).getRobots().get(0).getLocation()
                    .directionTo(new BoardPosition(4, 4, 0)) == 0);
            assert (GB.getTeams().get(0).getRobots().get(1).getLocation()
                    .directionTo(new BoardPosition(4, 4, 0)) == 0);
            assert (GB.getTeams().get(0).getRobots().get(2).getLocation()
                    .directionTo(new BoardPosition(4, 4, 0)) == 0);

            BP = new BoardPosition(8, 4, 0);
            assert (GB.getTeams().get(1).getRobots().get(0).getLocation().equals(BP));
            assert (GB.getTeams().get(1).getRobots().get(1).getLocation().equals(BP));
            assert (GB.getTeams().get(1).getRobots().get(2).getLocation().equals(BP));
            assert (GB.getTeams().get(1).getRobots().get(0).getLocation()
                    .directionTo(new BoardPosition(4, 4, 0)) == 0);
            assert (GB.getTeams().get(1).getRobots().get(1).getLocation()
                    .directionTo(new BoardPosition(4, 4, 0)) == 0);
            assert (GB.getTeams().get(1).getRobots().get(2).getLocation()
                    .directionTo(new BoardPosition(4, 4, 0)) == 0);

        }
        if (GB.getTeams().size() == 3) {
            assert (GB.getTeams().size() == 3);
            assert (GB.getAllRobots().size() == 9);
            assert (GB.getSize() == 5);

            BP = new BoardPosition(0, 4, 0);
            assert (GB.getTeams().get(0).getRobots().get(0).getLocation().equals(BP));
            assert (GB.getTeams().get(0).getRobots().get(1).getLocation().equals(BP));
            assert (GB.getTeams().get(0).getRobots().get(2).getLocation().equals(BP));
            assert (GB.getTeams().get(0).getRobots().get(0).getLocation()
                    .directionTo(new BoardPosition(4, 4, 0)) == 0);
            assert (GB.getTeams().get(0).getRobots().get(1).getLocation()
                    .directionTo(new BoardPosition(4, 4, 0)) == 0);
            assert (GB.getTeams().get(0).getRobots().get(2).getLocation()
                    .directionTo(new BoardPosition(4, 4, 0)) == 0);

            BP = new BoardPosition(4, 0, 0);
            assert (GB.getTeams().get(1).getRobots().get(0).getLocation().equals(BP));
            assert (GB.getTeams().get(1).getRobots().get(1).getLocation().equals(BP));
            assert (GB.getTeams().get(1).getRobots().get(2).getLocation().equals(BP));
            assert (GB.getTeams().get(1).getRobots().get(0).getLocation()
                    .directionTo(new BoardPosition(4, 4, 0)) == 0);
            assert (GB.getTeams().get(1).getRobots().get(1).getLocation()
                    .directionTo(new BoardPosition(4, 4, 0)) == 0);
            assert (GB.getTeams().get(1).getRobots().get(2).getLocation()
                    .directionTo(new BoardPosition(4, 4, 0)) == 0);

            BP = new BoardPosition(8, 8, 0);
            assert (GB.getTeams().get(2).getRobots().get(0).getLocation().equals(BP));
            assert (GB.getTeams().get(2).getRobots().get(1).getLocation().equals(BP));
            assert (GB.getTeams().get(2).getRobots().get(2).getLocation().equals(BP));
            assert (GB.getTeams().get(2).getRobots().get(0).getLocation()
                    .directionTo(new BoardPosition(4, 4, 0)) == 0);
            assert (GB.getTeams().get(2).getRobots().get(1).getLocation()
                    .directionTo(new BoardPosition(4, 4, 0)) == 0);
            assert (GB.getTeams().get(2).getRobots().get(2).getLocation()
                    .directionTo(new BoardPosition(4, 4, 0)) == 0);
        }

        if (GB.getTeams().size() == 6) {
            assert (GB.getTeams().size() == 6);
            assert (GB.getAllRobots().size() == 18);
            assert (GB.getSize() == 7);

            BP = new BoardPosition(0, 6, 0);
            assert (GB.getTeams().get(0).getRobots().get(0).getLocation().equals(BP));
            assert (GB.getTeams().get(0).getRobots().get(1).getLocation().equals(BP));
            assert (GB.getTeams().get(0).getRobots().get(2).getLocation().equals(BP));
            assert (GB.getTeams().get(0).getRobots().get(0).getLocation()
                    .directionTo(new BoardPosition(6, 6, 0)) == 0);
            assert (GB.getTeams().get(0).getRobots().get(1).getLocation()
                    .directionTo(new BoardPosition(6, 6, 0)) == 0);
            assert (GB.getTeams().get(0).getRobots().get(2).getLocation()
                    .directionTo(new BoardPosition(6, 6, 0)) == 0);

            BP = new BoardPosition(0, 0, 0);
            assert (GB.getTeams().get(1).getRobots().get(0).getLocation().equals(BP));
            assert (GB.getTeams().get(1).getRobots().get(1).getLocation().equals(BP));
            assert (GB.getTeams().get(1).getRobots().get(2).getLocation().equals(BP));
            assert (GB.getTeams().get(1).getRobots().get(0).getLocation()
                    .directionTo(new BoardPosition(6, 6, 0)) == 0);
            assert (GB.getTeams().get(1).getRobots().get(1).getLocation()
                    .directionTo(new BoardPosition(6, 6, 0)) == 0);
            assert (GB.getTeams().get(1).getRobots().get(2).getLocation()
                    .directionTo(new BoardPosition(6, 6, 0)) == 0);

            BP = new BoardPosition(6, 0, 0);
            assert (GB.getTeams().get(2).getRobots().get(0).getLocation().equals(BP));
            assert (GB.getTeams().get(2).getRobots().get(1).getLocation().equals(BP));
            assert (GB.getTeams().get(2).getRobots().get(2).getLocation().equals(BP));
            assert (GB.getTeams().get(2).getRobots().get(0).getLocation()
                    .directionTo(new BoardPosition(6, 6, 0)) == 0);
            assert (GB.getTeams().get(2).getRobots().get(1).getLocation()
                    .directionTo(new BoardPosition(6, 6, 0)) == 0);
            assert (GB.getTeams().get(2).getRobots().get(2).getLocation()
                    .directionTo(new BoardPosition(6, 6, 0)) == 0);

            BP = new BoardPosition(12, 6, 0);
            assert (GB.getTeams().get(3).getRobots().get(0).getLocation().equals(BP));
            assert (GB.getTeams().get(3).getRobots().get(1).getLocation().equals(BP));
            assert (GB.getTeams().get(3).getRobots().get(2).getLocation().equals(BP));
            assert (GB.getTeams().get(3).getRobots().get(0).getLocation()
                    .directionTo(new BoardPosition(6, 6, 0)) == 0);
            assert (GB.getTeams().get(3).getRobots().get(1).getLocation()
                    .directionTo(new BoardPosition(6, 6, 0)) == 0);
            assert (GB.getTeams().get(3).getRobots().get(2).getLocation()
                    .directionTo(new BoardPosition(6, 6, 0)) == 0);

            BP = new BoardPosition(12, 12, 0);
            assert (GB.getTeams().get(4).getRobots().get(0).getLocation().equals(BP));
            assert (GB.getTeams().get(4).getRobots().get(1).getLocation().equals(BP));
            assert (GB.getTeams().get(4).getRobots().get(2).getLocation().equals(BP));
            assert (GB.getTeams().get(4).getRobots().get(0).getLocation()
                    .directionTo(new BoardPosition(6, 6, 0)) == 0);
            assert (GB.getTeams().get(4).getRobots().get(1).getLocation()
                    .directionTo(new BoardPosition(6, 6, 0)) == 0);
            assert (GB.getTeams().get(4).getRobots().get(2).getLocation()
                    .directionTo(new BoardPosition(6, 6, 0)) == 0);

            BP = new BoardPosition(6, 12, 0);
            assert (GB.getTeams().get(5).getRobots().get(0).getLocation().equals(BP));
            assert (GB.getTeams().get(5).getRobots().get(1).getLocation().equals(BP));
            assert (GB.getTeams().get(5).getRobots().get(2).getLocation().equals(BP));
            assert (GB.getTeams().get(5).getRobots().get(0).getLocation()
                    .directionTo(new BoardPosition(6, 6, 0)) == 0);
            assert (GB.getTeams().get(5).getRobots().get(1).getLocation()
                    .directionTo(new BoardPosition(6, 6, 0)) == 0);
            assert (GB.getTeams().get(5).getRobots().get(2).getLocation()
                    .directionTo(new BoardPosition(6, 6, 0)) == 0);

        }

        System.out.println("All Tests Succeeded");
    }

}
