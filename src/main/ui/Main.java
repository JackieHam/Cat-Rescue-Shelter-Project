package ui;
// create a new shelter

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            new RescueShelterGUI();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }

    }
//        try {
//            new RescueShelter();
//        } catch (FileNotFoundException e) {
//            System.out.println("Unable to run application: file not found");
//        }
//    }
}
