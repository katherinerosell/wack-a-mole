package edu.quinnipiac.wack_a_mole;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

/**
 * HolderClass
 * Contains information on all the image buttons in an ImageButton array. That way the app can
 * read that constant array without mutating the button objects. The app reads the buttons in the array
 * just for their id's, getID or R.id.IMAGE_BUTTONS[index]. This array is meant to match up in terms of
 * indicies with the _gameBoard array in the CheckMole.class. The program uses 1D arrays.
 */

public class HolderClass {
    private static ImageButton[] IMAGE_BUTTONS;
    private static int[] _gameboard;

    public HolderClass(){
        IMAGE_BUTTONS = new ImageButton[9];
        _gameboard = new int[]{0,0,0,0,0,0,0,0,0};
    }


    public void setImageButtonsArray(ImageButton[] imgButtons){
        for(int i = 0; i < 9; i++){
            IMAGE_BUTTONS[i] = imgButtons[i];
        }
    }

    public ImageButton[] getImageButtons(){
        return IMAGE_BUTTONS;
    }

    public int[] getGameBoard(){
        return _gameboard;
    }

    public void setGameBoard(int[] tempGameBoard){
        for(int i = 0; i < 9; i++){
            _gameboard[i] = tempGameBoard[i];
        }
    }


}
