/**
 * Author: Madhukar Chavali (mchavali)
 * Email: mchavali@andrew.cmu.edu

 * This class represents a simple Joke structure in Java.
 * It encapsulates the properties of a joke, including its type, setup, punchline, and an identifier.
 * The class provides a constructor for initializing these properties,
 * getters and setters for each field, a method to display the joke, and an override of the toString method.
 * The main method is included for basic testing of the class functionality.
 *
 * Example usage:
 * Joke lifesaverJoke = new Joke("general", "Did you hear about the guy who invented Lifesavers?",
 *                                "They say he made a mint.", 89);
 * lifesaverJoke.tellJoke();
 */
package com.example.demo3;

public class Joke {
    private String type;
    private String setup;
    private String punchline;
    private int id;

    // Constructor
    public Joke(String type, String setup, String punchline, int id) {
        this.type = type;
        this.setup = setup;
        this.punchline = punchline;
        this.id = id;
    }

    // Getters and setters for each field
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSetup() {
        return setup;
    }

    public void setSetup(String setup) {
        this.setup = setup;
    }

    public String getPunchline() {
        return punchline;
    }

    public void setPunchline(String punchline) {
        this.punchline = punchline;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Method to display the joke
    public void tellJoke() {
        System.out.println(setup);
        System.out.println(punchline);
    }

    // Main method to test the Joke class
    public static void main(String[] args) {
        Joke lifesaverJoke = new Joke(
                "general",
                "Did you hear about the guy who invented Lifesavers?",
                "They say he made a mint.",
                89
        );

        lifesaverJoke.tellJoke();
    }

    @Override
    public String toString() {
        return "Joke{" +
                "type='" + type + '\'' +
                ", setup='" + setup + '\'' +
                ", punchline='" + punchline + '\'' +
                ", id=" + id +
                '}';
    }
}