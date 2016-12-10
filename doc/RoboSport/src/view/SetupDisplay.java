package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;

import models.Robot;
import models.Team;
import models.Robot.Type;

public class SetupDisplay{
    private int teamsChosen;
    private int humansChosen;
    private int AIsChosen;

    protected boolean[] humanTeams;
    private ArrayList<Team> teams;

    protected boolean numTeamsCompleted = false;

    protected boolean completed = false;

    // Windows
    private SetupGameTeamNumbers tn;


    public SetupDisplay() {
        teams = new ArrayList<Team>(6);
        tn = new SetupGameTeamNumbers();

        JFrame teamnumWind = SwingOps.newFrame(tn);
        teamnumWind.setTitle("Choose Teams");
        tn.onComplete(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                teamnumWind.dispose();
                humanTeams = tn.isHumanTeams();
                teamsChosen = humanTeams.length;
                humansChosen = 0;
                for (boolean b : humanTeams)
                    if (b)
                        humansChosen++;
                AIsChosen = teamsChosen - humansChosen;
                numTeamsCompleted = true;
            }
        });

        while (!numTeamsCompleted) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e1) {
                break;
            }
        }

        for (int i = 0; i < teamsChosen; i++) {
            if (humanTeams[i]) {
                // Add human window?

                ArrayList<Robot> rbts = new ArrayList<Robot>(3);
                for (int r = 0; r < 3; r++)
                    rbts.add(new Robot(new Type[] {Type.SCOUT, Type.SNIPER, Type.TANK}[r],
                            "human " + i + " robot " + r));
                teams.add(new Team(rbts, null, i));
            } else {
                SelectNPCTeam snt = new SelectNPCTeam(i + 1);
                JFrame snpctWind = SwingOps.newFrame(snt);
                snt.onComplete(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        snpctWind.dispose();
                    }
                });
                teams.add(new Team(snt.getRobots(), null, 0));
            }
        }
        completed = true;
    }

    public int getTeamsChosen() {
        waitForCompletion();
        return this.teamsChosen;
    }

    public int getHumansChosen() {
        waitForCompletion();
        return this.humansChosen;
    }

    public int getAIChosen() {
        waitForCompletion();
        return this.AIsChosen;
    }

    public ArrayList<Team> getTeams() {
        waitForCompletion();
        return this.teams;
    }

    public void waitForCompletion() {
        while (!completed) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                return;
            }
        }
    }


    protected class KillEvent implements ActionListener {
        private JFrame killer;

        public KillEvent(JFrame tk) {
            killer = tk;
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            killer.dispose();
        }

    }

    public static void main(String[] args) {}


}
