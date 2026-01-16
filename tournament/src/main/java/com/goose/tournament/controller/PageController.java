package com.goose.tournament.controller;


import com.goose.tournament.model.Athlete;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PageController {
    private final List<Athlete> athletes = new ArrayList<>();

    @PostConstruct
    public void init(){
        athletes.add(new Athlete(1, "Mamadou", 10));
        athletes.add(new Athlete(2, "Abdel", 11));
        athletes.add(new Athlete(3, "Jean-Francois", 5));
        athletes.add(new Athlete(4, "Charles", 15));
        athletes.add(new Athlete(5, "Jean-Marie", 14));
        athletes.add(new Athlete(6, "Gerard", 13));
        athletes.add(new Athlete(7, "Xavier", 1));
        athletes.add(new Athlete(8, "Amine", 2));
        athletes.add(new Athlete(9, "Chaise", 20));
        athletes.add(new Athlete(10, "vuntesisse", 13));
        athletes.add(new Athlete(11, "xijingping", 17));
        athletes.add(new Athlete(12, "Squidgame", 13));
        athletes.add(new Athlete(11, "kimjungun", 11));
        athletes.add(new Athlete(12, "Dolf", 16));
        athletes.add(new Athlete(11, "JC", 15));
        athletes.add(new Athlete(12, "Mamadoubis", 12));
    }

    @GetMapping("/bracket")
    public List<Athlete> makeBracket() {
        List<Athlete> sortedAthletes = new ArrayList<>(athletes);
        sortedAthletes.sort((a, b) -> Integer.compare(b.getAthleticism(), a.getAthleticism()));

        List<Athlete> bracket = new ArrayList<>();
        int size = sortedAthletes.size();

        for (int i = 0; i < size / 2; i++) {
            bracket.add(sortedAthletes.get(i));
            bracket.add(sortedAthletes.get(size - 1 - i));
        }

        return bracket;
    }

    private List<Athlete> runRound() {
        if (athletes.size() < 2) {
            return athletes;
        }

        List<Athlete> winners = new ArrayList<>();

        for (int i = 0; i < athletes.size(); i += 2) {
            if (i + 1 >= athletes.size()) {
                winners.add(athletes.get(i));
                break;
            }

            Athlete a1 = athletes.get(i);
            Athlete a2 = athletes.get(i + 1);

            if (a1.getAthleticism() >= a2.getAthleticism()) {
                winners.add(a1);
            } else {
                winners.add(a2);
            }
        }

        this.athletes.clear();
        this.athletes.addAll(winners);

        return this.athletes;
    }

    @PostMapping("/firstRound")
    public List<Athlete> firstRound() {
        return runRound();
    }

    @PostMapping("/secondRound")
    public List<Athlete> secondRound() {
        return runRound();
    }

    @PostMapping("/thirdRound")
    public List<Athlete> thirdRound() {
        return runRound();
    }

    @PostMapping("/fourthRound")
    public List<Athlete> fourthRound() {
        List<Athlete> result = runRound();
        if (result.size() == 1) {
            System.out.println("The Champion is: " + result.get(0).getName());
        }
        return result;
    }

    @PostMapping("/reset")
    public List<Athlete> resetTournament() {
        this.athletes.clear();
        this.init(); // Refills the list with your 16 original athletes
        return this.athletes;
    }

}
