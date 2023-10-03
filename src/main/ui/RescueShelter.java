package ui;

import model.Cat;
import model.CatBreed;
import model.Shelter;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

// Rescue Shelter Database
public class RescueShelter {
    private static final String JSON_STORE = "./data/shelter.json";
    private Shelter shelter;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // EFFECTS: runs the RescueShelter
    public RescueShelter() throws FileNotFoundException {
        runRescueShelter();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runRescueShelter() {
        boolean keepGoing = true;
        String command;
        init();
        while (keepGoing) {
            displayMenu();
            command = input.next();
            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nBye! Stay Paw-sitive!");
    }

    //MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("r")) {
            addRescue();
        } else if (command.equals("v")) {
            viewAllCats();
        } else if (command.equals("f")) {
            viewAllCatsAvailableForAdoption();
        } else if (command.equals("s")) {
            changeStatus();
        } else if (command.equals("a")) {
            adopt();
        } else if (command.equals("1")) {
            saveShelter();
        } else if (command.equals("2")) {
            loadShelter();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes cat shelter
    private void init() {
        shelter = new Shelter();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tr -> add rescue");
        System.out.println("\tv -> view all cats");
        System.out.println("\tf -> view cats available for adoption");
        System.out.println("\ta -> adopt a cat");
        System.out.println("\ts -> change status of a cat");
        System.out.println("\t1 -> save shelter to file");
        System.out.println("\t2 -> load shelter from file");
        System.out.println("\tq -> quit");
    }

    // REQUIRES: age >= 0, cat name is not an empty string and does not already exist;
    //           breed chosen is within the four options
    // MODIFIES: this
    // EFFECTS: adds a newly rescued cat to the rescue shelter, documenting its name, age, breed, cost and status.
    private void addRescue() {
        System.out.print("\tEnter name of the newly rescued cat:\n");
        String name = input.next();
        if (shelter.isValidName(name)) {
            System.out.println("\tEnter age of the newly rescued cat: ");
            int age = input.nextInt();
            System.out.println("\nChoose breed of the newly rescued cat:");
            System.out.println("\t1 -> AmericanShortHair");
            System.out.println("\t2 -> BritishShortHair");
            System.out.println("\t3 -> Tabby");
            System.out.println("\t4 -> Others");
            int value = input.nextInt();
            CatBreed breed = CatBreed.values()[value - 1];
            Cat newCat = new Cat(name, age, breed);
            System.out.println("the breed is " + newCat.getBreed() + ";" + "\nthe cost is " + newCat.getCost() + ";"
                    + "\n" + newCat.getName() + " is unavailable for adoption for now.");
            shelter.addNewCat(newCat);
        } else {
            System.out.println("The current name is invalid or taken, please select another name...");
        }
    }

    // EFFECTS: prints out the total number of cats in the shelter, and
    //          the relevant information about each cat in the rescue shelter
    private void viewAllCats() {
        if (shelter.isEmptyShelter()) {
            System.out.println("There are no cats in the shelter!");
        } else {
            System.out.println("There are in total " + shelter.getAllCats().size() + " cats in the shelter:\n");
            for (Cat c : shelter.getAllCats()) {
                System.out.println("Name: " + c.getName() + "; Age: " + c.getAge() + "; Breed: " + c.getBreed()
                        + "; Cost of adoption: " + c.getCost() + "; Available for adoption? " + c.getStatus());
            }
        }
    }

    // EFFECTS: prints out the list of cat in the rescue shelter available for adoption
    private void viewAllCatsAvailableForAdoption() {
        if (shelter.isEmptyAvailability()) {
            System.out.println("No cats are available for adoption now! Please come back later.");
        } else {
            System.out.println("Cats Available for Adoption: \n");
            for (Cat c : shelter.getAvailableCats()) {
                System.out.println("Name: " + c.getName() + "; Age: "
                        + c.getAge() + "; Breed: " + c.getBreed() + "; Cost of adoption: " + c.getCost());
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: search through the cat shelter and
    // find whether the cat name that the user keyed in exist. If it exists, remove the cat from the shelter.
    private void adopt() {
        if (shelter.isEmptyAvailability()) {
            System.out.println("No cats are available for adoption now! Please come back later.");
        } else {
            System.out.println("Key in the name of the cat for adoption: ");
            String selection = input.next();
            if (shelter.getAvailableCatNames().contains(selection)) {
                for (int i = 0; i < shelter.getAvailableCats().size(); i++) {
                    if (shelter.getAvailableCatNames().get(i).equals(selection)) {
                        System.out.println("Congratulations, you've successfully adopted " + selection
                                + "! The cost of adoption is " + shelter.getAvailableCats().get(i).getCost() + ".");
                        shelter.removeAdoptedCat(shelter.getAvailableCats().get(i));
                    }
                }
            } else {
                System.out.println("No cat named " + selection + " found in availability list! Please view "
                        + "cats available for adoption and/or check your spelling, and try again.");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: changes the adoption status of a cat and update the availability list accordingly
    private void changeStatus() {
        if (shelter.isEmptyShelter()) {
            System.out.println("There are no cats in the shelter!");
        } else {
            System.out.println("Please key in the name of the cat that you wish to change the adoption status: ");
            String selection = input.next();
            if (shelter.getAllCatNames().contains(selection)) {
                for (int i = 0; i < shelter.getAllCats().size(); i++) {
                    Cat catFound = shelter.getAllCats().get(i);
                    if (selection.equals(catFound.getName())) {
                        catFound.setStatus();
                        if (catFound.isAvailableForAdoption()) {
                            System.out.println(catFound.getName() + " is now available for adoption!");
                        } else {
                            System.out.println(catFound.getName() + " is now unavailable for adoption!");
                        }
                    }
                }
            } else {
                System.out.println("No cat named " + selection + " found! Please check spelling and try again.");
            }
        }
    }

    // EFFECTS: saves the shelter to file
    private void saveShelter() {
        try {
            jsonWriter.open();
            jsonWriter.write(shelter);
            jsonWriter.close();
            System.out.println("Saved " + shelter.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads shelter from file
    private void loadShelter() {
        try {
            shelter = jsonReader.read();
            System.out.println("Loaded " + shelter.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}


