package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

class ArrayHoldingCharacter{
    char value;
    Integer[] coordinates;

    public ArrayHoldingCharacter(char value,Integer[] coordinates){
        this.value=value;
        this.coordinates=coordinates;
    }
}


public class MyPanel extends JPanel {
    JFrame frame;
    String input;
    char keyPressed;
    int length;//45
    int fWidth;//600+10
    int fHeight;//400+10
    int availableSpace;
    int xOffset=10;
    int yOffset=10;
    int xPos=xOffset;
    int yPos=yOffset;
    int spaceWidth=50;
    int spaceHeight=30;
    int fullRows;
    int rowsLeftOut;
    static int guessesWrong=0;
    int possibleMistakes=7;
    static String putTogether="";

    ArrayList<Integer[]> storeCoordinates;
    ArrayList<Character> charList;
    static ArrayList<Character> alreadyPressedList=new ArrayList<>();
    static ArrayList<Character> rightlyGuessedWord=new ArrayList<>();
    ArrayList<ArrayHoldingCharacter> cooStoreCharacter;


    public MyPanel(String input, JFrame frame) {
        this.input=input;
        this.frame=frame;
        calculate();
    }

    private void calculate() {
        this.fWidth=frame.getWidth()-15;
        this.fHeight=frame.getHeight()-40;
        this.availableSpace=fHeight/2;

        this.length=input.length();
        this.fullRows=length/10;
        this.rowsLeftOut=length%10;

        this.charList=new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            charList.add(input.charAt(i));
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.RED);
        g.drawRect(0,0,fWidth,fHeight/2);
        g.setColor(Color.BLUE);
        g.fillRect(1,1,fWidth-1,fHeight/2-1);
        g.setColor(Color.RED);
        g.drawRect(0,fHeight/2,fWidth/2,fHeight/2);
        g.setColor(Color.GREEN);
        g.fillRect(1,fHeight/2+1,fWidth/2-1,fHeight/2-1);
        g.setColor(Color.RED);
        g.drawRect(fWidth/2,fHeight/2,fWidth/2,fHeight/2);
        g.setColor(Color.CYAN);
        g.fillRect(fWidth/2+1,fHeight/2+1,fWidth/2-1,fHeight/2-1);

        storeCoordinates= new ArrayList<>();
        for (int i = 0; i < fullRows; i++) {
            for (int j = 0; j < 10; j++) {
                if (input.charAt(i*10+j)!=' ') {
                    g.setColor(Color.BLACK);
                    g.drawRect(xPos, yPos, spaceWidth, spaceHeight);
                    g.setColor(Color.DARK_GRAY);
                    g.fillRect(xPos + 1, yPos + 1, spaceWidth - 1, spaceHeight - 1);
                }else {
                    g.setColor(Color.WHITE);
                    g.fillRect(xPos + 1, yPos + 1, spaceWidth - 1, spaceHeight - 1);
                }
                Integer[] temp = {xPos, yPos, spaceWidth, spaceHeight};
                storeCoordinates.add(temp);

                xPos+=spaceWidth+xOffset;
            }
            xPos=xOffset;
            yPos+=spaceHeight+yOffset;
        }

        if (rowsLeftOut%2==0){
            xPos=(5-rowsLeftOut/2)*(spaceWidth+xOffset)+xOffset;
        }else{
            xPos=(5-(rowsLeftOut-1)/2)*(spaceWidth+xOffset)+xOffset-(spaceWidth+xOffset)/2;
        }
        for (int i = 0; i < rowsLeftOut; i++) {
            if (input.charAt(fullRows*10+i)!=' ') {
                g.setColor(Color.BLACK);
                g.drawRect(xPos, yPos, spaceWidth, spaceHeight);
                g.setColor(Color.DARK_GRAY);
                g.fillRect(xPos + 1, yPos + 1, spaceWidth - 1, spaceHeight - 1);

            }else {
                g.setColor(Color.WHITE);
                g.fillRect(xPos + 1, yPos + 1, spaceWidth - 1, spaceHeight - 1);
            }
            Integer[] temp = {xPos, yPos, spaceWidth, spaceHeight};
            storeCoordinates.add(temp);
            xPos+=spaceWidth+xOffset;
        }
        yPos=yOffset;
        xPos=xOffset;

        calculateArrayHoldingCharacters();



        g.setColor(Color.RED);
        g.drawLine(480,370,520,370);
        g.drawLine(500,370,500,260);
        g.drawLine(500,260,440,260);
        g.drawLine(440,280,440,260);

        for (int i = 0; i < guessesWrong&& i<=possibleMistakes; i++) {
            if (i == 0) {
                g.drawOval(430, 280, 20, 20);
            } else if (i == 1) {
                g.drawLine(440, 320, 440, 300);
            } else if (i == 2) {
                g.drawLine(440, 320, 440, 340);
            } else if (i == 3) {
                g.drawLine(440, 310, 420, 300);
            } else if (i == 4) {
                g.drawLine(440, 310, 460, 300);
            } else if (i == 5) {
                g.drawLine(420, 350, 440, 340);
            } else if (i == 6) {
                g.drawLine(460, 350, 440, 340);
            }
        }
        g.setFont(new Font("Sans-serif",Font.PLAIN,20));
        g.drawString(putTogether,10,280);

        paintLettersInRightlyGuessedWordList(g);

        frame.repaint();
    }

    private void calculateArrayHoldingCharacters() {
        cooStoreCharacter=new ArrayList<>();
        for (int i = 0; i < storeCoordinates.size(); i++) {
            //System.out.println(input.charAt(i)+"_"+ Arrays.toString(storeCoordinates.get(i))+":"+storeCoordinates.size()+"_"+input.charAt(i));
            cooStoreCharacter.add(new ArrayHoldingCharacter(input.charAt(i),storeCoordinates.get(i)));
        }
    }

    public void setCharTyped(char keyPressed) {
        this.keyPressed=keyPressed;

        boolean checkHasBeenPressed=false;

        for (char element : alreadyPressedList) {
            if (element == keyPressed) {
                checkHasBeenPressed = true;
                break;
            }
        }
        if (!checkHasBeenPressed) {
            checkGuess();
            alreadyPressedList.add(keyPressed);
        }
        putTogether="";
        for (char element : alreadyPressedList) {
            putTogether+=element+" ";
        }
    }

    private void checkGuess() {
        boolean check=false;

        for (int i = 0; i < charList.size(); i++) {
            char element = charList.get(i);
            if (keyPressed == element) {
                check = true;
                charList.remove(i);
            }
        }
        showList();
        if (!check) {
            guessesWrong++;
        }else {
            rightlyGuessedWord.add(keyPressed);
            paintLettersInRightlyGuessedWordList(getGraphics());
        }
    }

    private void paintLettersInRightlyGuessedWordList(Graphics g) {

        System.out.println("Drawing"+Collections.unmodifiableList(rightlyGuessedWord)+"..");
        for (ArrayHoldingCharacter e :
                cooStoreCharacter) {
            System.out.print("{"+e.value+"}");
        }
        System.out.println();
        for (int i = 0; i < rightlyGuessedWord.size(); i++) {
            for (int j = 0; j < cooStoreCharacter.size(); j++) {

                if (rightlyGuessedWord.get(i)==cooStoreCharacter.get(j).value){
                    g.setColor(Color.WHITE);
                    g.drawString(cooStoreCharacter.get(j).value+"",cooStoreCharacter.get(j).coordinates[0]+spaceWidth/2-5,cooStoreCharacter.get(j).coordinates[1]+spaceHeight-5);
                }
            }
        }

        frame.repaint();

    }

    @Override
    public void update(Graphics g) {
        super.update(g);

        this.paintComponent(g);
    }

    @Override
    public Graphics getGraphics() {
        return super.getGraphics();
    }

    public void showList(){
        for (char element : charList) {
            //System.out.println(element);
        }
    }

}
