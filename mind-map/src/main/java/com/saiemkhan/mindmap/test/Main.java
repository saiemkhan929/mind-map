package com.saiemkhan.mindmap.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Main {

    /*
    public static int minNumberOfAction(String password){
        int minNumberOfSteps = 0;
        String vowels = "aeiou";
        ArrayList<Character> vowelsInPassword = new ArrayList<>();
        ArrayList<Character> consonantsInPassword = new ArrayList<>();

       for (char c : password.toCharArray()) {
           if ( vowels.contains((String.valueOf(c))) ) {
               vowelsInPassword.add(c);
           }else{
               consonantsInPassword.add(c);
           }
       }


       if(vowelsInPassword.size() > consonantsInPassword.size()){
            int numberOfChangesNeeded = vowelsInPassword.size() - (password.length()/2);
            for (int i = 0; i < numberOfChangesNeeded; i++) {
                minNumberOfSteps++;
            }

       }else if(consonantsInPassword.size() > vowelsInPassword.size()){
           int numberOfChangesNeeded = consonantsInPassword.size() - (password.length()/2);

           for (int i = 0; i < numberOfChangesNeeded; i++) {
               System.out.println("In Iteration " + (i+1));
               HashMap<Character, Integer> minStepsOnEachCharacters = new HashMap<>();
               for (char c : consonantsInPassword) {
                   ArrayList<Integer> distancesOfCharacter = new ArrayList<>();
                   for (char vowel : vowels.toCharArray()) {
                       distancesOfCharacter.add(Math.abs(vowel - c));
                   }
                   // add the consonant and all the possibilities
                   minStepsOnEachCharacters.put(c, distancesOfCharacter.stream().min(Integer::compareTo).get());

               }

               System.out.println(minStepsOnEachCharacters);
               int smallestSteps = minStepsOnEachCharacters.values().stream().min(Integer::compareTo).get();

               for ( var entry :minStepsOnEachCharacters.entrySet()){
                   if(entry.getValue() == smallestSteps){
                       System.out.println("Smallest Step = " + entry.getKey() + " : " + entry.getValue());
                       minNumberOfSteps += smallestSteps;
                       consonantsInPassword.remove(entry.getKey());
                       break;
                   }

               }



           }
       }

        return minNumberOfSteps;
    }

*/

    public static int minNumberOfAction(String password) {
        int minNumberOfSteps = 0;
        String vowels = "aeiou";
        ArrayList<Character> vowelsInPassword = new ArrayList<>();
        ArrayList<Character> consonantsInPassword = new ArrayList<>();

        for (char c : password.toCharArray()) {
            if (vowels.indexOf(c) != -1) {  // Using indexOf for better efficiency
                vowelsInPassword.add(c);
            } else {
                consonantsInPassword.add(c);
            }
        }

        int halfLength = password.length() / 2;

        if (vowelsInPassword.size() > halfLength) {
            minNumberOfSteps = vowelsInPassword.size() - halfLength;
        } else if (consonantsInPassword.size() > halfLength) {
            int numberOfChangesNeeded = consonantsInPassword.size() - halfLength;

            for (int i = 0; i < numberOfChangesNeeded; i++) {
                HashMap<Character, Integer> minStepsOnEachCharacter = new HashMap<>();
                for (char c : consonantsInPassword) {
                    int minDistance = Integer.MAX_VALUE;
                    for (char vowel : vowels.toCharArray()) {
                        minDistance = Math.min(minDistance, Math.abs(vowel - c));
                    }
                    minStepsOnEachCharacter.put(c, minDistance);
                }

                int smallestSteps = minStepsOnEachCharacter.values().stream().min(Integer::compareTo).orElse(0);
                for (var entry : minStepsOnEachCharacter.entrySet()) {
                    if (entry.getValue() == smallestSteps) {
                        minNumberOfSteps += smallestSteps;
                        consonantsInPassword.remove(entry.getKey());
                        break;
                    }
                }
            }
        }

        return minNumberOfSteps;
    }


    public static void main(String[] args) {

        int getSimilarity = Main.minNumberOfAction("mockhack");
        System.out.println(getSimilarity);


    }

}
