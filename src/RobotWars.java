import java.io.*;
import java.util.*;

public class RobotWars {
    static int xMax;
    static int yMax;

    private List<String> calculateEndPosition(String startPosition, String path){


        // To calculate first I create an array list out of the robots input
        List<String> position = new ArrayList<>(Arrays.asList(startPosition.split(" ")));

        // Then I loop through the path, updating the robot each time
        for (int i = 0; i < path.length(); i++) {

            // Then I parse the information into useful data types
            int xPos = Integer.parseInt(position.get(0));
            int yPos = Integer.parseInt(position.get(1));
            char orientation = position.get(2).charAt(0);

            char instruction = path.charAt(i);

            // Depending on the instruction we change the robots position or orientation
            if (instruction == 'M') {
                position = moveRobot(xPos, yPos, orientation);
            } else if (instruction == 'R') {
                position = turnRight(xPos, yPos, orientation);
            } else {
                position = turnLeft(xPos, yPos, orientation);
            }
        }

        // I check if the robot has gone out of bounds otherwise I return the final position
        if(!validatePosition(position)){  System.out.println("Robot out of bounds!!"); return new ArrayList<>(); }
        else{
           return position;
        }
    }

    private List<String> moveRobot(int xPos, int yPos, char orientation) {
        switch (orientation) {
            case 'N':
                yPos += 1;
                break;
            case 'E':
                xPos += 1;
                break;
            case 'W':
                xPos -= 1;
                break;
            case 'S':
                yPos -= 1;
        }

        List<String> position;
        position=arrayListBuilder(xPos, yPos, orientation);

        return position;
    }

    private List<String> turnLeft(int xPos, int yPos, char orientation) {
        switch (orientation) {
            case 'N':
                orientation = 'W';
                break;
            case 'E':
                orientation = 'N';
                break;
            case 'W':
                orientation = 'S';
                break;
            case 'S':
                orientation = 'E';
                break;
        }

        List<String> position;
        position=arrayListBuilder(xPos, yPos, orientation);

        return position;
    }

    private List<String> turnRight(int xPos, int yPos, char orientation) {
        switch (orientation) {
            case 'N':
                orientation = 'E';
                break;
            case 'E':
                orientation = 'S';
                break;
            case 'W':
                orientation = 'N';
                break;
            case 'S':
                orientation = 'W';
                break;
        }

        List<String> position;
        position=arrayListBuilder(xPos, yPos, orientation);

        return position;
    }

    private void execute(ArrayList<String> entries, int lineCount) {
        // Every second line is a new robot. We pass through two lines at a time to the calculation method.

        for (int i = 0; i < lineCount; i += 2) {
            formatAndPrintResponse(calculateEndPosition(entries.get(i), entries.get(i + 1)));
        }
    }

    private void formatAndPrintResponse(List<String> response) {
        // Finally we get the String back into the accepted format and print

        StringBuffer sb = new StringBuffer();
        for (String s : response){
            sb.append(s).append(" ");
        }
        System.out.println(sb);
    }

    // This method checks the robot hasn't gone out of the grid
    private boolean validatePosition(List<String> position) {
        int xPos = Integer.parseInt(position.get(0));
        int yPos = Integer.parseInt(position.get(1));

        return xPos <= xMax && xPos >= 0 && yPos <= yMax && yPos >= 0;
    }

    // This method is just to eliminate code duplication
    private List<String> arrayListBuilder(int xPos, int yPos, int orientation){
        List<String> position = new ArrayList<>();
        position.add(String.valueOf(xPos));
        position.add(String.valueOf(yPos));
        position.add(Character.toString(orientation));
        return position;
    }



    public static void main(String[] args) throws IOException {

        // I used a text file compromising of Test Input
        BufferedReader in = new BufferedReader(
                new FileReader("src/input.txt"));

        ArrayList<String> entries = new ArrayList<>();
        String input;
        boolean gridSized = false;
        int lineCount = 0;

        String[] gridValues;

        // First I size the grid using the first line of input and added the actual robot input to an array list
        while ((input = in.readLine()) != null) {
            if (!gridSized) {
                gridValues = input.split(" ");
                xMax=Integer.parseInt(gridValues[0]);
                yMax=Integer.parseInt(gridValues[1]);
                gridSized = true;
            } else {
                entries.add(input);
                lineCount++;
            }

        }
        // I create our object and pass through relevant information
        RobotWars robotWars = new RobotWars();
        robotWars.execute(entries, lineCount);

    }
}
