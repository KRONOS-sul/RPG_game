package com.company;

import java.util.Random;

public class Main {


    public static int bossHealth = 1000;
    public static int bossDamage = 70;
    public static String bossDefence = "";
    public static int[] heroesHealth = {270/*Warrior*/, 260/*Wizard*/, 250/*Kinetic*/, 221/*Medic*/, 400/*Golem*/, 250/*Lucky*/, 240/*Berserk*/, 190/*Thor*/};
    public static int[] heroesDamage = {20, 15, 25, 0, 8, 20, 27, 15};
    public static String[] heroesAttackType = {"Warrior", "Wizard", "Kinetic", "Medic", "Golem", "Lucky", "Berserk", "Thor"};
    public static int round_number = 0;
    public static Random random = new Random();
    public static String[] heroesNames = {"Physical", "Magic", "Kinetic", "Nothing", "Hard", "Destiny", "Rage", "Thunder"};

    public static void main(String[] args) {
        printStatistics();
        while (!isGameFinished()) {
            round();
        }
    }

    public static void bossWeakness() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesNames.length);
        bossDefence = heroesNames[randomIndex];
        System.out.println("Boss is vulnurable to " + bossDefence);
    }

    public static void round() {
        round_number++;
        bossWeakness();
        if (bossHealth > 0) {
            bossHits();
        }
        Lucky();
        Golem();
        Berserk();
        heroesHit();
        Thor();
        printStatistics();
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Victory");
            return true;
        }

        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println(" GAME OVER ");
        }
        return allHeroesDead;
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (heroesAttackType[i] == bossDefence) {
                    int coeff = random.nextInt(11); //0,1,2,3,4,5,6,7,8,9,10
                    if (bossHealth - heroesDamage[i] * coeff < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i] * coeff;
                    }
                    System.out.println("Critical damage: " + heroesDamage[i] * coeff);
                } else {
                    if (bossHealth - heroesDamage[i] < 0) {
                        bossHealth = 0;

                    } else {
                        bossHealth = bossHealth - heroesDamage[i];
                    }
                    for (int j = 0; j <= heroesDamage[3]; j++) {
                        if (heroesHealth[3] < 100) {
                            continue;
                        }
                        if (heroesHealth[i] < 100) {
                            heroesHealth[i] += 35;
                            break;
                        }
                    }
                }
            }
        }

    }

    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static void printStatistics() {
        System.out.println(round_number + " ROUND ______________");
        System.out.println("Boss hp: " + bossHealth + " (" + bossDamage + ")");
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i]
                    + " health: " + heroesHealth[i]
                    + " (" + heroesDamage[i] + ")");
        }
        System.out.println("____________________");
    }

    public static void Golem() {

        int absorbDamage = bossDamage / 5;
        int heroesAlive = 0;

        if (heroesHealth[4] > 0) {
            for (int i = 0; i < heroesHealth.length; i++) {
                if (i == 4) {
                    continue;
                } else if (heroesHealth[i] > 0) {
                    heroesAlive++;

                }
            }
            heroesHealth[4] -= absorbDamage * heroesAlive;

        }

    }

    public static void Lucky() {
        boolean luckysEvasion = random.nextBoolean();
        if (heroesHealth[5] > 0) {
            if (luckysEvasion) {
                heroesHealth[5] += bossDamage - 10;

                System.out.println("Lucky has evaded");
            }
        }
    }

    public static void Berserk() {
        int blocked = random.nextInt(80 - 10) + 10;
        if (heroesHealth[6] > 0) {
            heroesHealth[6] += blocked;
            bossHealth -= blocked;
        }
    }

    public static void Thor () {
        int stun = random.nextInt(2);
        if (heroesHealth[7] > 0) {
            for (int i = 0; i < bossDamage; i++) {
                if (stun == 1) {
                    System.out.println("Thor stuns the boss");
                    bossDamage = 0;
                    break;
                }
            }
        }



    }

}