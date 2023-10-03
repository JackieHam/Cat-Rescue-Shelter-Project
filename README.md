# My Personal Project: Cat Adoption Shelter

## Phase 0: A Brief Overview

### Background story of the project

A new cat rescue shelter has opened its business! It rescues cats, registers their information and helps match them 
to their new homes.
People who adopt cats here will be charged a reasonable price to add to the fund of the shelter, so that more cats can
be rescued and sent to new homes in the future.

### What will the application do and who will use it?

The application will simulate a database system for the cat adoption shelter, including which cats are newly rescued,
name, age, breed and (automatically generated) cost of the cats, and the list of cats available for adoption.

### Why am I interested?

I love cats, and I am planning to adopt at least two cats once I graduate and have my own apartment. 

I sometimes even hope to open my own rescue shelter, so this project will become handy for tracking cats
rescued if I did so.



### User Stories
#### For P1:
- As a user, I want to be able to add newly rescued cats to the shelter, and document relevant information.
- As a user, I want to be able to view details of all the cats in my shelter, as well as total number of cats.
- As a user, I want to be able to view details of cats available for adoption in my shelter.
- As a user, I want to change the status of adoption for the cats in my shelter.
- As a user, I want to be able to remove cats from the shelter when they are successfully adopted.
#### For P2:
- As a user, I want to be able to save the information of all cats in my shelter to file (if I so choose).
- As a user, I want to be able to reload the information of all cats in my shelter from file (if I so choose).



#### Instructions for Grader
- You can generate the first required action related to adding Xs to a Y by clicking the "Add Rescue" button and keying
in the required information. You will be able to add newly rescued Cats to the Rescue Shelter.
- You can generate the second required action related to adding Xs to a Y by selecting a cat in the first list and 
clicking the "Change Status" button, to change the availability for adoption status of the selected cat from "true" to 
"false" or vice versa. The changes would also be reflected in the Available Cats List (below All Cats List).
- You can also generate a third action related to removing Cats from the Shelter by keying in the name of the cat you'd
like to adopt in the text field and clicking the "Adopt a Cat" button.
- You can locate my visual component by clicking the "Add Rescue" button
- You can save the state of my application by clicking "Save" button
- You can reload the state of my application by clicking "Load" button

#### Phase 4: Task 2
Wed Apr 12 16:46:31 PDT 2023
New cat named Jerry added to the shelter.

Wed Apr 12 16:46:36 PDT 2023
New cat named Jackie added to the shelter.

Wed Apr 12 16:46:40 PDT 2023
Adoption status of Jerry changed to true.

Wed Apr 12 16:46:41 PDT 2023
Adoption status of Jackie changed to true.

Wed Apr 12 16:46:49 PDT 2023
Adoption status of Jackie changed to false.

Wed Apr 12 16:46:54 PDT 2023
Adopted cat Jerry removed from the shelter.

Wed Apr 12 16:46:55 PDT 2023
Adoption status of Jackie changed to true.

Wed Apr 12 16:47:01 PDT 2023
Adopted cat Jackie removed from the shelter.


Process finished with exit code 0


#### Phase 4: Task 3
I would have broken down the RescueShelterGUI into smaller classes, by creating new classes for
the fields involved in RescueShelterGUI class. This is because currently, the RescueShelterGUI class
is performing a lot of different tasks, and it could be hard to debug and make changes due to the complexity
of the class.

For example, I could have created separate classes for
the front page JFrame (the page with two lists) and the rescue window JFrame (the page where you need
to key in information for newly rescued cats). This would make it easier to identify bugs, as well as
making changes to a certain JFrame. Moreover, I could have put all the JButtons in a list (for example, 
a list of Tools that allows you to perform different functionalities, just like in Simple Drawing Editor), 
so that the code could be simplified.
