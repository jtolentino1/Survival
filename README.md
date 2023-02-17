# Survival
#### A dodging-practice game for League of Legends players.
##### By Joshua Tolentino and James Khalil

## Table of Contents
**[Getting Started](#Getting-Started)**<br>
**[Controls](#Controls)**<br>
**[Gallery](#Gallery)**<br>

## Getting Started
Here are the steps to running and compiling our project.
All the steps assume that you are working on the Eclipse IDE
and you are using Java 9.0.4 Development Kit.

Additionally, this game assumes that you are running it within a device
running at 60hz. There is currently a bug with JavaFX AnimationTimer where
if the user is running at above 60hz, the game is seen to be sped up.

### Step 1: Installing Dependencies
##### Install e(fx)clipse (version 3.7.0) on the Eclipse Marketplace.
  1. Help > Eclipse Marketplace > search e(fx)clipse and install.
##### Install JavaFX from [here](https://gluonhq.com/products/javafx/).
  1. Extract all the `.jar` files within the download and put it in a folder, remember this directory.
  2. On Eclipse, Window > Preferences > Java > Build Path > User Libraries
  3. Hit the new button, user library name should be JavaFX then click "Ok"
  4. Now, in User Libraries click on JavaFX and click on "Add External Jars"
  5. From step 1, go to the directory with all `.jar` files and select all of them.
  6. Hit "Apply and Close"

### Step 2: Pulling and Initializing the Project
##### Pull the project from github and on to Eclipse.
  1. Copy the github repository link. (https://github.com/jtolentino1/S21CPSC233Project.git)
  2. On Eclipse, click File > Import > Git > Projects from Git (with smart import) > Clone URL
  3. Fill in URL and Authentication then keep clicking next then finish.
##### Add JavaFX SDK Library and the JavaFX User Library into the build path (module path).
  1. Right click on the project, then hit properties > Java Build Path then click on Modulepath
  2. Click "Add Library" and add JavaFX SDK. Then go to User Libraries and select JavaFX.
  3. Hit "Apply and Close"
###### If the code is not compiling, please make sure that the required dependencies (JavaFX SDK and user library JavaFX) are in module path.
###### Additionally, check module-info.java to double check that you are not missing any dependencies.
### Step 3: Running the Game
##### To run the game, run `GameApp.java` within the `application` directory.

## Controls
* Click: Shoot in the direction of the mouse location.
* F: Blink a certain distance to the location of mouse.
* W: Move forwards.
* S: Move backwards.
* A: Move left.
* D: Move right.
* ESC: Pause / Unpause

## Gallery

<p align="center"> 
<img src="https://github.com/jtolentino1/Survival/blob/main/READMEImages/Gameplay1.png">
</p>

<p align="center"> 
<img src="https://github.com/jtolentino1/Survival/blob/main/READMEImages/Gameplay2.png">
</p>

