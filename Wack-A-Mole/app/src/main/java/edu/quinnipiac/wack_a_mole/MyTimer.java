package edu.quinnipiac.wack_a_mole;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class MyTimer extends AppCompatActivity {

    private static int _seconds;
    private TextView _timeText;
    private boolean _running;
    private GameBackend _gameBackend;
    private static boolean reset;

    public MyTimer(){}

    public MyTimer(int seconds,TextView textView, boolean running, HolderClass mainHolder){
        _seconds = seconds;
        _timeText = textView;
        _running = running;
        _gameBackend = new GameBackend(mainHolder);
        _gameBackend.matchBoardButtons();
        reset = false;
    }

    public void runTimer() {
        //_gameBackend = new GameBackend();
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int min = (_seconds%3600)/60;
                int sec = _seconds%60;
                String time = String.format(Locale.getDefault(), "%02d:%02d", min, sec);
                _timeText.setText("Time Left: " + time);
                if(_running){
                    _seconds--;
                    if(_seconds%5 ==0){
                        _gameBackend.addMole();
                        Log.d("** Create New Mole ** ","--------   make new mole   -------");
                    }
                    if(_seconds<=0){
                        _running = false;
                        handler.removeCallbacks(this);
                        end_game();
                    } else{}
                    if(reset == false){
                        handler.postDelayed(this, 1000);//post code to be run again after 1000 milliseconds, or 1 second
                    } else {  _running = false;  }
                }
            }
        });

    }

    public void end_game(){
        _timeText.setText("TIME'S UP!");
        //ask user to restart?
    }



    public int getTime(){
        return _seconds;
    }

    public void setTime(int sec){ _seconds = sec; }

    public boolean getRunning(){
        return _running;
    }

    public void setRunning(boolean myRunning){
        _running = myRunning;
    }

    public void sendButtonClicked(){
        _gameBackend.imgButtonClicked();
    }

    public int getScore(){
        int score =_gameBackend.retrieveScore();
        return score;
    }

}
