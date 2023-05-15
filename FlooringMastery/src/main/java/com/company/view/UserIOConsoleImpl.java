package com.company.view;

import java.util.Scanner;

// Console Implementation from Wiley Class Roster Exercise

    /*********************************
     * The Software Guild
     * Copyright (C) 2020 Wiley edu LLC - All Rights Reserved
     *********************************/


/**
 * TSG Official Implementation of the UserIO interface
 * May your view be ever in your favor!
 * @author ahill
 */

public class UserIOConsoleImpl implements UserIO {

    final private Scanner console = new Scanner(System.in);

    /**
     * A very simple method that takes in a message to display on the console
     * and then waits for a integer answer from the user to return.
     *
     * @param msg - String of information to display to the user.
     */
    @Override
    public void print(String msg) {
        System.out.println(msg);
    }

    /**
     * A simple method that takes in a message to display on the console,
     * and then waits for an answer from the user to return.
     *
     * @param msgPrompt - String explaining what information you want from the user.
     * @return the answer to the message as string
     */
    @Override
    public String readString(String msgPrompt) {
        System.out.println(msgPrompt);
        return console.nextLine();
    }


    /**
     * A simple method that takes in a message to display on the console,
     * and continually reprompts the user with that message until they enter an integer
     * to be returned as the answer to that message.
     *
     * @param msgPrompt - String explaining what information you want from the user.
     * @return the answer to the message as integer
     */
    @Override
    public int readInt(String msgPrompt) {
        boolean invalidInput = true;
        int num = 0;
        while (invalidInput) {
            try {
                // print the message msgPrompt (ex: asking for the # of cats!)
                String stringValue = this.readString(msgPrompt);
                // Get the input line, and try and parse
                num = Integer.parseInt(stringValue); // if it's 'bob' it'll break
                invalidInput = false; // or you can use 'break;'
            } catch (NumberFormatException e) {
                // If it explodes, it'll go here and do this.
                this.print("Input Error. Please Try Again.");
            }
        }
        return num;
    }

    /**
     * A slightly more complex method that takes in a message to display on the console,
     * and continually reprompts the user with that message until they enter an integer
     * within the specified min/max range to be returned as the answer to that message.
     *
     * @param msgPrompt - String explaining what information you want from the user.
     * @param min       - minimum acceptable value for return
     * @param max       - maximum acceptable value for return
     * @return an integer value as an answer to the message prompt within the min/max range
     */
    @Override
    public int readInt(String msgPrompt, int min, int max) {
        int result;
        do {
            result = readInt(msgPrompt);
        } while (result < min || result > max);

        return result;
    }

    /**
     * A simple method that takes in a message to display on the console,
     * and continually reprompts the user with that message until they enter a long
     * to be returned as the answer to that message.
     *
     * @param msgPrompt - String explaining what information you want from the user.
     * @return the answer to the message as long
     */
    @Override
    public long readLong(String msgPrompt) {
        while (true) {
            try {
                return Long.parseLong(this.readString(msgPrompt));
            } catch (NumberFormatException e) {
                this.print("Input Error. Please Try Again.");
            }
        }
    }

    /**
     * A slightly more complex method that takes in a message to display on the console,
     * and continually reprompts the user with that message until they enter a double
     * within the specified min/max range to be returned as the answer to that message.
     *
     * @param msgPrompt - String explaining what information you want from the user.
     * @param min       - minimum acceptable value for return
     * @param max       - maximum acceptable value for return
     * @return an long value as an answer to the message prompt within the min/max range
     */
    @Override
    public long readLong(String msgPrompt, long min, long max) {
        long result;
        do {
            result = readLong(msgPrompt);
        } while (result < min || result > max);

        return result;
    }

    /**
     * A simple method that takes in a message to display on the console,
     * and continually reprompts the user with that message until they enter a float
     * to be returned as the answer to that message.
     *
     * @param msgPrompt - String explaining what information you want from the user.
     * @return the answer to the message as float
     */
    @Override
    public float readFloat(String msgPrompt) {
        while (true) {
            try {
                return Float.parseFloat(this.readString(msgPrompt));
            } catch (NumberFormatException e) {
                this.print("Input Error. Please Try Again.");
            }
        }
    }

    /**
     * A slightly more complex method that takes in a message to display on the console,
     * and continually reprompts the user with that message until they enter a float
     * within the specified min/max range to be returned as the answer to that message.
     *
     * @param msgPrompt - String explaining what information you want from the user.
     * @param min       - minimum acceptable value for return
     * @param max       - maximum acceptable value for return
     * @return an float value as an answer to the message prompt within the min/max range
     */
    @Override
    public float readFloat(String msgPrompt, float min, float max) {
        float result;
        do {
            result = readFloat(msgPrompt);
        } while (result < min || result > max);

        return result;
    }

    /**
     * A simple method that takes in a message to display on the console,
     * and continually reprompts the user with that message until they enter a double
     * to be returned as the answer to that message.
     *
     * @param msgPrompt - String explaining what information you want from the user.
     * @return the answer to the message as double
     */
    @Override
    public double readDouble(String msgPrompt) {
        while (true) {
            try {
                return Double.parseDouble(this.readString(msgPrompt));
            } catch (NumberFormatException e) {
                this.print("Input Error. Please Try Again.");
            }
        }
    }

    /**
     * A slightly more complex method that takes in a message to display on the console,
     * and continually reprompts the user with that message until they enter a double
     * within the specified min/max range to be returned as the answer to that message.
     *
     * @param msgPrompt - String explaining what information you want from the user.
     * @param min       - minimum acceptable value for return
     * @param max       - maximum acceptable value for return
     * @return an double value as an answer to the message prompt within the min/max range
     */
    @Override
    public double readDouble(String msgPrompt, double min, double max) {
        double result;
        do {
            result = readDouble(msgPrompt);
        } while (result < min || result > max);
        return result;
    }

        /*
        * My methods that I added
        * */


    // Reading Date - adding a "0" if the date is lower than 10.
    // Also using "readInt" (above) to verify the input is a number between min and max
    // Returning the String of Date
    @Override
    public String readDay(String prompt, int min, int max) {
        int date;
        do {
            date = readInt(prompt);
            if (date < min || date > max) {
                this.print("Invalid Day. Please Try Again.");
            }
        } while (date < min || date > max);
        String dateString = String.valueOf(date);
        if (date < 10) {
            dateString = "0" + date;
        }
        return dateString;
    }

    // Reading Month - adding a "0" if the month is lower than 10.
    // Also using "readInt" (above) to verify the input is a number between min and max
    // Returning the String of Month
    @Override
    public String readMonth(String prompt, int min, int max) {
        int month;
        do {
            month = readInt(prompt);
            if (month < min || month > max) {
                this.print("Invalid Month. Please Try Again.");
            }
        } while (month < min || month > max);
        String monthString = String.valueOf(month);
        if (month < 10) {
            monthString = "0" + month;
        }
        return monthString;
    }

    // Reading the year - Verifying that the year is YYYY format
    // Also using (readInt) to verify the input is a number
    // Returning the String of Year
    @Override
    public String readYear(String prompt) {
        int year;
        do {
            year = readInt(prompt);
            if (String.valueOf(year).length()!=4){
                System.out.println("Invalid format.");
            }
        }
           while(String.valueOf(year).length() !=4);

        String yearString = String.valueOf(year);
        return yearString;
    }

    // Method to confirm the users choice
    @Override
    public boolean readChoice(String prompt) {
        String input;
        boolean isValidChar = false;
        do {
            input = this.readString(prompt + " (Y/N):");
            // We only want a "Y","y" or "N", "n"
            if (input.trim().equalsIgnoreCase("Y") || input.trim().equalsIgnoreCase("N")) {
                isValidChar = true;
            } else {
                System.out.println("Invalid Choice. Please Input 'Y' Or 'N'.");
            }

        } while (!isValidChar);
        // Checking and return true or false
        // At this point it can only be y or n
        return input.trim().equalsIgnoreCase("Y");
    }

    // Verifying the customer name follows the rules
    // Returns the name

    @Override
    public String readName(String prompt) {
        boolean isValid = true;
        String name;
        do {
            name = readString(prompt);
            isValid = true;
            // Checking to see if the name validates the requirements
            for (Character x : name.toCharArray()) {
                if (!Character.isLetterOrDigit(x) && !(Character.isWhitespace(x)) && x != '.') {
                    System.out.println("Invalid Character: " + x);
                    isValid = false;
                }
            }
        }while (!isValid);
        return name;
    }

    // Method to read the Edited Order name
    @Override
    public String readName(String prompt, String oldName) {
        boolean isValid = true;
        String name = "";
        do {
            name = readString(prompt + "\nOr Enter 1.");
            isValid = true;
            // Checking if the new name follows the rules
            for (Character x : name.toCharArray()) { // STILL DOES NOT ALLOW "."'S
                if (!Character.isLetterOrDigit(x) && !(Character.isWhitespace(x)) && x != '.')  {
                    System.out.println("Invalid Character: " + x);
                    isValid = false;
                }
                // If the user enters 1, return the original name
                else if(name.trim().equals("1")){
                    name = oldName;
                }
            }
        }while (!isValid);
        return name;
    }

    // Method for checking when the user edits an order, to check if the edited parameter
    // is valid in terms of no numbers, OR if the only input is a "1" then the original
    // parameter is returned
    @Override
    public String readEditParameter(String prompt, String oldParameter) {
        boolean isValid = true;
        String input = "";
        do {
            input = readString(prompt + "\nOr Enter 1.");
            isValid = true;
            for (Character x : input.toCharArray()) {
                if (!Character.isLetter(x)) {
                    isValid = false;
                    // If the user inputted "1", return the old parameter
                    if (input.trim().equalsIgnoreCase("1")) {
                        input = oldParameter;
                        isValid = true;
                    }
                }
            }
        }while (!isValid);
        return input;
    }

    // Method to verify if the user inputs a valid number that is above the min
    // For the Area the min is required to be 100 - (Could be HardCoded)
    public double readArea(String prompt, double min){
        double result;
        do {
            result = readDouble(prompt);
            if (result < min){
                System.out.println("Please Input A Number Larger Than 100.");
            }
        } while (result < min);
        return result;
    }

}

