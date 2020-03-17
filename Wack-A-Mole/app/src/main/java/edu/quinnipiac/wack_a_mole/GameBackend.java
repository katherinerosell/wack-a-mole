package edu.quinnipiac.wack_a_mole;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import java.util.Random;

public class GameBackend implements CheckMole {

    private static HolderClass _holderClass;
    private static ImageButton[] _imageButtons;
    private static int[] _gameboard;
    private static int _score;

    public GameBackend(HolderClass holderClass){
        _holderClass = holderClass;
        _imageButtons = holderClass.getImageButtons();
        _gameboard = holderClass.getGameBoard();
        _score = 0;
    }

    @Override
    public void addMole() {
        clearBoard();
        Random random = new Random();
        int n = random.nextInt(9);
        _gameboard[n] = MOLE;
        //_imageButtons[n].setBackgroundResource(R.drawable.mole);
        matchBoardButtons();
    }

    @Override
    public void removeMole() {
        //clearBoard();
    }

    /**
     * clearBoard
     * For every item in the board defined in CheckMole interface, make it "blank" by making each
     * item 0, or NONE. Meaning no mole is in any of the spots
     */
    @Override
    public void clearBoard() {
        for(int i = 0; i < _gameboard.length-1; i++){
            _gameboard[i] = NONE;//for each item in the gameboard array, make it mole-less
        }
        matchBoardButtons();
    }

    /**
     * matchBoardButtons
     * This method goes through the array in CheckMole interface and matches its values to images to
     * give the ImageButtons. 0 = no mole, so green square image is matched with that button. If the value
     * is 1 = that button displays a mole!
     */
    public void matchBoardButtons(){
        //Log.d("** GameBackend ** ","--------   match board buttons   -------");
        for(int i = 0; i < _gameboard.length-1; i++){
            if(_gameboard[i]==NONE)   _imageButtons[i].setBackgroundResource(R.drawable.green_square);
            if(_gameboard[i] == MOLE) _imageButtons[i].setBackgroundResource(R.drawable.mole);
        }
        _holderClass.setGameBoard(_gameboard);
        _holderClass.setImageButtonsArray(_imageButtons);
    }

    public void imgButtonClicked() {
            clearBoard();
    }

    /**
     * calcID
     * Calculate the "index" according to the ID from the view object clicked.
     * @param id
     * @return
     */
    private int calcID(int id){
        int resID =0;
        switch(id){
            case R.id.button_0:
                resID=0;
                break;
            case R.id.button_1:
                resID=1;
                break;
            case R.id.button_2:
                resID=2;
                break;
            case R.id.button_3:
                resID=3;
                break;
            case R.id.button_4:
                resID=4;
                break;
            case R.id.button_5:
                resID=5;
                break;
            case R.id.button_6:
                resID=6;
                break;
            case R.id.button_7:
                resID=7;
                break;
            case R.id.button_8:
                resID=8;
                break;
            default:
                break;
        }
        return resID;
    }

    public int retrieveScore() {
        //Log.d("Score:" + "  ", " " + _score);
        return _score;
    }
}
