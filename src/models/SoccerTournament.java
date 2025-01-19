package models;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class SoccerTournament {

    public static final String SEPARATOR = ";";
    public ArrayList<String> arrayMatches;
    private ArrayList<String> uniqueTeam;

    public SoccerTournament() throws FileNotFoundException, IOException {
        arrayMatches = FileReading.readFile("datas/matches.txt");
        uniqueTeam = new ArrayList<String>();
        getTeams();
    }

    private ArrayList<String> getTeams() {
        HashSet<String> seen = new HashSet<>();

        for (int i = 0; i < arrayMatches.size(); i++) {
            String[] partsLine = arrayMatches.get(i).split(SEPARATOR);
            if (seen.add(partsLine[0])) {
                uniqueTeam.add(partsLine[0]);
            }

            if (seen.add(partsLine[1])) {
                uniqueTeam.add(partsLine[1]);
            }
        }

        return uniqueTeam;
    }

    private List<String> createDatas() {
        ArrayList<String> datasTeam = new ArrayList<>();
        datasTeam.add(String.format("%-35s | %2s | %2s | %2s | %2s | %3s",
                "TEAM", "MP", "W", "D", "L", "P"));

        datasTeam.add(String.format("%-35s | %2s | %2s | %2s | %2s | %3s",
                "-----------------------------------", "--", "--", "--", "--", "---"));
        for (String team : uniqueTeam) {
            byte matchesPlayed = 0, matchesWon = 0, matchesDrawn = 0, matchesLost = 0;
            int points = 0;
            for (String match : arrayMatches) {
                String[] matchData = match.split(SEPARATOR);
                String team1 = matchData[0];
                String team2 = matchData[1];
                String result = matchData[2];

                if (team.equals(team1)) {
                    matchesPlayed++;
                    if (result.equals("win")) {
                        matchesWon++;
                        points += 3;
                    } else if (result.equals("loss")) {
                        matchesLost++;
                    } else if (result.equals("draw")) {
                        matchesDrawn++;
                        points += 1;
                    }
                }

                if (team.equals(team2)) {
                    matchesPlayed++;
                    if (result.equals("win")) {
                        matchesLost++;
                    } else if (result.equals("loss")) {
                        matchesWon++;
                        points += 3;
                    } else if (result.equals("draw")) {
                        matchesDrawn++;
                        points += 1;
                    }
                }

            }

            datasTeam.add(String.format("%-35s | %2d | %2d | %2d | %2d | %3d",
                    team, matchesPlayed, matchesWon, matchesDrawn, matchesLost, points));
        }

        return datasTeam;
    }

    public List<String> sortList() {
        List<String> datasTeam = createDatas();

        List<String> beforeRow3 = datasTeam.subList(0, 2); // Las primeras dos filas no se tocan
        List<String> fromRow3Onward = datasTeam.subList(2, datasTeam.size());

        Collections.sort(fromRow3Onward, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                // Extraer los puntos (último valor de la cadena, después del último "|")
                int points1 = Integer.parseInt(o1.split("\\|")[5].trim());
                int points2 = Integer.parseInt(o2.split("\\|")[5].trim());

                // Orden descendente de los puntos
                return Integer.compare(points2, points1);
            }
        });

        beforeRow3.addAll(fromRow3Onward);

        return beforeRow3;
    }
}
