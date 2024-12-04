# CSCIO-651-Group-Project

# Starting Program:
  * Execute Code from App.java 
  * Will prompt User to select file to load as initial data, you have to pick one or the app will fail

# User Interface:

## Interface Functions

### Search Function:
* Insert desired Part ID into provided TextField
* Click Search Button and results will appear in TableView below

### Insert Function:
* Insert Information about New Part into Textfields
* Click Insert Button and the New Part will be added

### Remove Function:
* Insert Part ID into the provided TextField
* Click the Remove Button to remove part from data
* Adding commas between multiple Part IDs will allow people to delete multiple parts at once

### Update Function:
* Insert Part ID of part you wish to update the Description of
* Insert new Description into provided textfield at the same time as Part ID
* Click Update Button

## Menu Options:

### Open File:
* Click Open File to open file explorer
* Browser files till you find desired **Text File** and then select
* Program will replace previous data with new data, **DELETING PREVIOUS UNSAVED CHANGES**

### Save File:
* Click Save File to open file explorer
* Browser files till you find desired location to save or file you wish to replace
* Program will save file there or replace previous file, **REPLACING FILE WILL DELETE PREVIOUS SAVE MAKING CHANGES PERMAMENT**

### Close:
* Click Close to close program safetly
* Will prompt user to save any changes to database before shutting down the program

## UI Information:
* UI is independant of data structure
* data structure can be replace with minimal changes to UI
* Will clean Textfields when switch betweens Tabs

### Table View:
* Used to display data from datastructure
* Will display the 10 most related items to inquiry

### Text:
* Shows information about present data structure
