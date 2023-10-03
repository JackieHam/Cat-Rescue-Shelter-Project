package ui;

import model.Cat;
import model.CatBreed;
import model.Event;
import model.EventLog;
import model.Shelter;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;


// Rescue Shelter Graphical Database
public class RescueShelterGUI extends JFrame implements WindowListener {

    private JFrame rescueWindow;
    private JList<String> listOfAllCatsInfo;
    DefaultListModel<String> allCatsInfo;
    DefaultListModel<String> availableCatsInfo;

    private JLabel infoLabel;
    private JLabel rescueInfoLabel;
    private JTextField adoptionOption;
    private JTextField nameField;
    private JComboBox<Integer> ageOption;
    private JComboBox<CatBreed> catBreedOption;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final int LEFTBOUND = 100;
    public static final int TOPBOUND = 30;
    public static final int LISTWIDTH = 600;
    public static final int LISTHEIGHT = 200;

    private Shelter shelter;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/shelter.json";
    private static final String space = "           ";


    // EFFECTS: runs the RescueShelterGUI
    public RescueShelterGUI() throws FileNotFoundException {
        super("Meowie Rescue Shelter");
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.addWindowListener(this);

//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        shelter = new Shelter();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        initializeAllCatsList();
        initializeCatsAvailableForAdoption();
        createButtons();
        createTextFields();
        createLabels();
        this.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: initializes listOfAllCatsInfo JList component and place it within bounds specified in JFrame
    private void initializeAllCatsList() {
        allCatsInfo = new DefaultListModel<>();
        listOfAllCatsInfo = new JList<>(allCatsInfo);
        listOfAllCatsInfo.setBounds(LEFTBOUND, TOPBOUND, LISTWIDTH, LISTHEIGHT);
        List<Cat> allCats = shelter.getAllCats();
        for (Cat c : allCats) {
            allCatsInfo.addElement(getCatInfo(c));
        }
        this.add(listOfAllCatsInfo);
    }

    // MODIFIES: this
    // EFFECTS: initializes Available Cats List JList component and place it within bounds specified in JFrame
    private void initializeCatsAvailableForAdoption() {
        availableCatsInfo = new DefaultListModel<>();

        JList<String> listOfAvailableCatsInfo = new JList<>(availableCatsInfo);
        listOfAvailableCatsInfo.setBounds(LEFTBOUND, TOPBOUND + LISTHEIGHT + 70, LISTWIDTH, LISTHEIGHT);
        this.add(listOfAvailableCatsInfo);
    }

    // MODIFIES: this
    // EFFECTS: add and render buttons in JFrame
    private void createButtons() {
        createRescueButton();
        createChangeStatusButton();
        createAdoptButton();
        createLoadButton();
        createSaveButton();
    }

    // MODIFIES:this
    // EFFECTS: initialize Rescue button and add it to JFrame, adding an Action Listener to the button so that
    // the program will respond to user input and the rescueWindow JFrame will pop out
    private void createRescueButton() {
        JButton rescueButton = new JButton("Add Rescue");
        rescueButton.setBounds(LEFTBOUND, TOPBOUND + LISTHEIGHT, 150, 30);
        rescueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createRescueAction();
            }
        });

        this.add(rescueButton);
    }

    // MODIFIES:this
    // EFFECTS: initialize Change Status button and add it to JFrame, adding an Action Listener to the button so that
    // the program will respond to user input (the cat selected)
    private void createChangeStatusButton() {
        JButton changeStatusButton = new JButton("Change Status");
        changeStatusButton.setBounds(LEFTBOUND + 180, TOPBOUND + LISTHEIGHT, 150, 30);
        changeStatusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectionIndex = listOfAllCatsInfo.getSelectedIndex();
                if (selectionIndex != -1) {
                    changeStatusAction(selectionIndex);
                }
                if (selectionIndex == -1) {
                    infoLabel.setText("No cat selected! Please check you've selected a cat in the relevant list.");
                }
            }
        });

        this.add(changeStatusButton);
    }

    // MODIFIES: this
    // EFFECTS: initialize Load button and add it to JFrame, adding an Action Listener to the button so that
    // the state of the program will be restored once the button is pressed
    private void createLoadButton() {
        JButton loadButton = new JButton("Load");
        loadButton.setBounds(LEFTBOUND + 360, TOPBOUND + LISTHEIGHT, 100, 30);
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == loadButton) {
                    loadShelter();
                }
            }
        });

        this.add(loadButton);
    }

    // MODIFIES: this
    // EFFECTS: initialize Save button and add it to JFrame, adding an Action Listener to the button so that
    // the state of the program will be saved once the button is pressed
    private void createSaveButton() {
        JButton saveButton = new JButton("Save");
        saveButton.setBounds(LEFTBOUND + 490, TOPBOUND + LISTHEIGHT, 100, 30);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == saveButton) {
                    saveShelter();
                }
            }
        });
        this.add(saveButton);
    }

    // MODIFIES: this
    // EFFECTS: initialize Adoption button and add it to JFrame, adding an Action Listener to the button so that
    // the program will respond and update the infoLabel accordingly to the string keyed in by the user in the text
    // box next to it
    private void createAdoptButton() {
        JButton adoptButton = new JButton("Adopt a Cat (enter name in the text box)");
        adoptButton.setBounds(LEFTBOUND + 200, TOPBOUND + 2 * LISTHEIGHT + 70, 320, 30);
        adoptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nameEntered = adoptionOption.getText();
                if (shelter.getAvailableCatNames().contains(nameEntered)) {
                    for (int i = 0; i < shelter.getAvailableCats().size(); i++) {
                        if (shelter.getAvailableCatNames().get(i).equals(nameEntered)) {
                            Cat catSelected = shelter.getAvailableCats().get(i);
                            infoLabel.setText("Congratulations, you've successfully adopted " + nameEntered
                                    + "! The cost of adoption is " + catSelected.getCost() + ".");
                            shelter.removeAdoptedCat(catSelected);
                            allCatsInfo.removeElement(getCatInfo(catSelected));
                            availableCatsInfo.removeElement(getAdoptionInfo(catSelected));
                        }
                    }
                } else {
                    infoLabel.setText("No cat named \"" + nameEntered + "\" found! Please check spelling.");
                }
            }
        });
        this.add(adoptButton);
    }



    // MODIFIES: this
    // EFFECTS: initialize a text box for users to key in name of the cat they wish to adopt
    private void createTextFields() {
        this.adoptionOption = new JTextField();
        adoptionOption.setBounds(LEFTBOUND, TOPBOUND + 2 * LISTHEIGHT + 70, 180, 30);
        this.add(adoptionOption);
    }

    // MODIFIES: this
    // EFFECTS: initialize, customize and place labels within the specified bounds on JFrame
    private void createLabels() {
        JLabel allCatsListLabel = new JLabel("All Cats List");
        allCatsListLabel.setForeground(Color.blue);
        allCatsListLabel.setFont(new Font("Serif", Font.PLAIN, 15));
        allCatsListLabel.setBounds(LEFTBOUND, 10, 100, 20);
        this.add(allCatsListLabel);

        JLabel availableCatsLabel = new JLabel("Available Cats List");
        availableCatsLabel.setForeground(Color.blue);
        availableCatsLabel.setFont(new Font("Serif", Font.PLAIN, 15));
        availableCatsLabel.setBounds(LEFTBOUND, TOPBOUND + LISTHEIGHT + 50, 300, 20);
        this.add(availableCatsLabel);

        infoLabel = new JLabel("Select an option");
        infoLabel.setForeground(Color.red);
        infoLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        infoLabel.setBounds(LEFTBOUND - 20, 540, 1000, 20);
        this.add(infoLabel);
    }


    // MODIFIES: this
    // EFFECTS: initialize the rescueWindow field by instantiating a new JFrame, customize the rescueWindow and place
    // relevant components on the rescueWindow
    private void createRescueAction() {
        rescueWindow = new JFrame("Add New Rescue");
        rescueWindow.setSize(WIDTH / 2, HEIGHT / 2);
        rescueWindow.setResizable(false);
        rescueWindow.setLocationRelativeTo(null);
        rescueWindow.setDefaultCloseOperation(HIDE_ON_CLOSE);
        rescueWindow.setLayout(null);
        rescueWindow.setVisible(true);
        addRescueInfoLabel();
        addNameField();
        addAgeCombo();
        addBreedCombo();
        createSubmitButton();
        loadImage();
    }


    // MODIFIES: this
    // EFFECTS: load image, parse it as a JLabel and place it on the rescueWindow within the bounds specified
    private void loadImage() {
        ImageIcon americanShorthair = new ImageIcon("./images/American Shorthair.png");
        JLabel imageAsLabel = new JLabel(americanShorthair);
        imageAsLabel.setBounds(100, 110, 200, 135);
        rescueWindow.add(imageAsLabel);
    }

    // MODIFIES: this
    // EFFECTS: add an information label to the rescueWindow that will display information in response to user input
    private void addRescueInfoLabel() {
        rescueInfoLabel = new JLabel("Please enter information of the cat");
        rescueInfoLabel.setFont(new Font("Serif", Font.PLAIN, 14));
        rescueInfoLabel.setForeground(Color.red);
        rescueInfoLabel.setBounds(10, 235, 400, 50);
        rescueWindow.add(rescueInfoLabel);
    }

    // MODIFIES: this
    // EFFECTS: initialize a new JTextField and place it next to the name JLabel, adding both JTextField and JLabel to
    // rescueWindow
    private void addNameField() {
        JLabel name = new JLabel("Enter Name: ");
        nameField = new JTextField();
        name.setBounds(10, 20, 100, 30);
        nameField.setBounds(110, 20, 200, 30);
        rescueWindow.add(name);
        rescueWindow.add(nameField);
    }

    // MODIFIES: this
    // EFFECTS: initialize a new JComboBox of age for user to choose from, and place it next to the age JLabel,
    // adding both JComboBox and JLabel to rescueWindow
    private void addAgeCombo() {
        JLabel age = new JLabel("Enter Age: ");
        this.ageOption = new JComboBox<>();
        age.setBounds(10, 50, 100, 30);
        ageOption.setBounds(110, 50, 200, 30);
        for (int i = 1; i <= 20; i++) {
            ageOption.addItem(i);
        }
        rescueWindow.add(age);
        rescueWindow.add(ageOption);
    }

    // MODIFIES: this
    // EFFECTS: initialize a new JComboBox of breed for user to choose from, and place it next to the breed JLabel,
    // adding both JComboBox and JLabel to rescueWindow
    private void addBreedCombo() {
        JLabel breedLabel = new JLabel("Choose Breed: ");
        breedLabel.setBounds(10, 80, 100, 30);
        this.catBreedOption = new JComboBox<>(CatBreed.values());
        catBreedOption.setEditable(false);
        catBreedOption.setBounds(110, 80, 200, 30);
        rescueWindow.add(breedLabel);
        rescueWindow.add(catBreedOption);
    }

    // MODIFIES: this
    // EFFECTS: initialize a new Submit JButton and place it within the bounds specified on rescueWindow JFrame. Add an
    // Action Listener to respond to user input in all three fields (name, age and breed) in the rescueWindow
    private void createSubmitButton() {
        JButton submit = new JButton("Submit");
        submit.setBounds(300, 80,100, 30);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == submit) {
                    rescueAction();
                }
            }
        });
        rescueWindow.add(submit);
    }

    // MODIFIES: this
    // EFFECTS: add a new cat to a shelter according to name entered as well as age and breed chosen. Add the
    // information of the newly rescued cat to listOfAllCatsInfo, and update message displayed in the rescueInfoLabel
    // accordingly
    private void rescueAction() {
        if (nameField.getText().isEmpty()) {
            rescueInfoLabel.setText("Please fill in all the relevant information before submitting!");
        } else {
            String name = nameField.getText();
            int age = (ageOption.getSelectedIndex() + 1);
            CatBreed breed = CatBreed.values()[catBreedOption.getSelectedIndex()];
            if (shelter.isValidName(name)) {
                Cat c = new Cat(name, age, breed);
                shelter.addNewCat(c);
                allCatsInfo.addElement(getCatInfo(c));
                rescueInfoLabel.setText("Successfully added " + name + " to the shelter!");
            } else {
                rescueInfoLabel.setText("Duplication of name! Please choose another name.");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: change the status of the cat selected in the listOfAllCatsInfo from true to false and vise versa, update
    // the listOfAllCatsInfo and listOfAvailableCats, as well as the message displayed in the infoLabel
    private void changeStatusAction(int selectionIndex) {
        String msg;
        Cat c = shelter.getAllCats().get(selectionIndex);
        c.setStatus();
        msg = "You've changed the adoption status of " + c.getName() + " to " + c.getStatus() + ".";
        infoLabel.setText(msg);
        String newValue = getCatInfo(c);
        allCatsInfo.setElementAt(newValue, selectionIndex);
        availableCatsInfoRefreshAction();
    }

    // MODIFIES: this
    // EFFECTS: if a cat's adoption status is set to true, add the cat to the listOfAvailableCatsInfo and
    // listOfAvailableCats; if a cat's adoption status is set to false, remove the cat from the listOfAvailableCatsInfo
    // and listOfAvailableCats
    private void availableCatsInfoRefreshAction() {
        List<Cat> allCats = shelter.getAllCats();

        for (Cat c : allCats) {
            String catInfo = getAdoptionInfo(c);
            if (c.getStatus()) {
                if (!availableCatsInfo.contains(catInfo)) {
                    availableCatsInfo.addElement(catInfo);
                }
            } else {
                if (availableCatsInfo.contains(catInfo)) {
                    availableCatsInfo.removeElement(catInfo);
                }
            }
        }
    }

    // EFFECTS: returns the Info of a cat to be printed in listOfAllCatsInfo
    private String getCatInfo(Cat c) {
        return c.getName() + ", " + c.getAge() + " year(s) old, " + c.getBreed()
                + space + "Availability: " + c.getStatus();
    }

    // EFFECTS: returns the Adoption Info of a cat to be printed in listOfAvailableCats
    private String getAdoptionInfo(Cat c) {
        return c.getName() + ", " + c.getAge() + " year(s) old, " + c.getBreed()
                + "; Cost of Adoption: $" + c.getCost();
    }

    // MODIFIES: this
    // EFFECTS: saves shelter to file
    private void saveShelter() {
        try {
            jsonWriter.open();
            jsonWriter.write(shelter);
            jsonWriter.close();
            infoLabel.setText("Saved " + shelter.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            infoLabel.setText("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads shelter from file
    private void loadShelter() {
        try {
            shelter = jsonReader.read();
            infoLabel.setText("Loaded " + shelter.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            infoLabel.setText("Unable to read from file: " + JSON_STORE);
        }
        List<Cat> allCats = shelter.getAllCats();
        for (Cat c : allCats) {
            if (!allCatsInfo.contains(getCatInfo(c))) {
                allCatsInfo.addElement(getCatInfo(c));
            }
            if (c.getStatus()) {
                if (!availableCatsInfo.contains(getAdoptionInfo(c))) {
                    availableCatsInfo.addElement(getAdoptionInfo(c));
                }
            }
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        for (Event next : EventLog.getInstance()) {
            System.out.println(next.toString() + "\n");
        }
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

//    @Override
//    public void windowOpened(WindowEvent e) {
//
//    }
//
//    @Override
//    public void windowClosing(WindowEvent e) {
//
//    }
//
//    @Override
//    public void windowClosed(WindowEvent e) {
//        for (Event next : EventLog.getInstance()) {
//            System.out.println(next.toString() + "\n");
//        }
//    }
//
//    @Override
//    public void windowIconified(WindowEvent e) {
//
//    }
//
//    @Override
//    public void windowDeiconified(WindowEvent e) {
//
//    }
//
//    @Override
//    public void windowActivated(WindowEvent e) {
//
//    }
//
//    @Override
//    public void windowDeactivated(WindowEvent e) {
//
//    }
}
