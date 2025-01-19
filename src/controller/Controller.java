package controller;

import java.io.IOException;

import models.SoccerTournament;

public class Controller {

    private SoccerTournament soccerTournament;

    public Controller() {
        try {
            soccerTournament = new SoccerTournament();
        } catch (IOException e) {
            e.printStackTrace();
        }
        run();
    }

    public void run() {
        soccerTournament.sortList().forEach((emp) -> System.out.println(emp));
    }

    public static void main(String[] args) {
        new Controller();
    }
}
