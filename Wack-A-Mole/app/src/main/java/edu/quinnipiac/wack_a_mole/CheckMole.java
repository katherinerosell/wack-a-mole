package edu.quinnipiac.wack_a_mole;

import android.view.View;

public interface CheckMole {
    public final int MOLE = 1, NONE = 0;
    //add a mole into a random slot
    public void addMole();
    //remove mole after a certain amount of time if they're not clicked
    public void removeMole();
    //reset board to all 0, NONE
    public void clearBoard();
}
