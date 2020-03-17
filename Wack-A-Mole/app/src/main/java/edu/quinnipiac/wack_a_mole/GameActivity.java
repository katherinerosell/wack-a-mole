package edu.quinnipiac.wack_a_mole;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.core.view.MenuItemCompat;
import java.util.Locale;


/**
 * GameActivity
 * In this activity, the game runs! Every 5 seconds a mole appears in one of the 9 spaces. During (maybe)
 * the 1.5 seconds the mole is on screen the program will check which button the user clicks.
 * If that button is NOT the mole, the user MISSED. If that button IS the mole, they get a point!
 */

public class GameActivity extends AppCompatActivity{

    private HolderClass _mainHolderClass;
    private GameBackend _mainGameBackend;

    private ImageButton[] _mainImageButtons = new ImageButton[9];
    private int[] _mainGameboard = new int[]{0,0,0,0,0,0,0,0,0};

    private int _score;
    private String playerName;

    //for my timer
    private static int _seconds;
    private boolean _running;
    private TextView _scoreText;
    private TextView _timeText;

    private ShareActionProvider shareActionProvider;

    /**
     * onCreate
     * Load in all the objects needed to continue where player was after application is destroyed
     * then recreated.
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);

        if(savedInstanceState != null){
            _score = savedInstanceState.getInt("Score");
            //_seconds = savedInstanceState.getInt("Seconds");
            _running = savedInstanceState.getBoolean("Running");
            //_myTimer.setTime(_seconds);
            //_mainGameboard = savedInstanceState.getIntArray("GameBoard");
            Log.d(" -- Game Board -- ", "" + _mainGameboard);
            //_mainHolderClass.setGameBoard(_mainGameboard);
            playerName = savedInstanceState.getString("PlayerName");
            _scoreText = (TextView) findViewById(R.id.score_textview);
            _scoreText.setText("Score: " + _score);
            _timeText = (TextView) findViewById(R.id.countdown_timer);

        } else{
            _seconds=60;
            _score = 0;
            _running = true;

        }

        _scoreText = (TextView) findViewById(R.id.score_textview);
        _scoreText.setText("Score: " + _score);
        _timeText = (TextView) findViewById(R.id.countdown_timer);
        //Display welcome text with player name
        TextView welcomeText = (TextView) findViewById(R.id.welcome_text);
        playerName = (String) getIntent().getExtras().get("PlayerName");
        welcomeText.setText("Welcome, "+ playerName + "!");
        //Set and display timer
        //_myTimer.setTime(_seconds);

        //Begin the game code. Set up the HolderClass, ImageButton[], int[] gameboard, MyTimer classes.
        _mainHolderClass = new HolderClass();
        //Add all ImageButtons in game_layout to array.
        for (int i = 0; i < 9; i++){
            String imgButtonID = "button_" + i;
            //resource ID to pass to find new ID
            int btnID = getResources().getIdentifier(imgButtonID, "id", getPackageName());
            _mainImageButtons[i] = findViewById(btnID);
            //Log.d("Image Button  ", "" + _mainImageButtons[i]);
        }

        _mainHolderClass.setImageButtonsArray(_mainImageButtons);
        _mainHolderClass.setGameBoard(_mainGameboard);
        _mainGameBackend = new GameBackend(_mainHolderClass);
        //final TextView _timeText = (TextView) findViewById(R.id.countdown_timer);
        //_myTimer = new MyTimer(60, timeText,true, _mainHolderClass);
        //_myTimer.runTimer();
        runTimer();
    }

    /**
     * runTimer
     * Timer that counts down from a 60 seconds (initially)
     */
    public void runTimer() {
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
                        _mainGameBackend.addMole();
                        Log.d("** Create New Mole ** ","--------   make new mole   -------");
                    }
                    if(_seconds<=0){
                        _running = false;
                        handler.removeCallbacks(this);
                        //end_game();
                    }
                    handler.postDelayed(this, 1000);//post code to be run again after 1000 milliseconds, or 1 second
                }
            }
        });
    }


    /**
     * imgButtonClick
     * When an ImageButton is clicked, check if a MOLE or NOTHING was clicked.
     * If MOLE clicked : add 1 to score!
     * If NONE clicked : subtract 1 from score!
     * @param view
     */

    public void imgButtonClick(View view) {
        if(_mainHolderClass.getGameBoard()[calcID(view.getId())] == 1){
            //Log.d("IMG BUTN CLICKed --   ", calcID(view.getId()) + "");
            //destroy mole, add a point to score
            _score++;
        }
        if(_mainHolderClass.getGameBoard()[calcID(view.getId())] == 0){
            _score--;
        }
        _scoreText.setText("Score: " + _score);
        //_myTimer.sendButtonClicked();//button has been clicked, clear the board
        _mainGameBackend.clearBoard();
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

    /**
     * Action Bar!
     * Basically just allow the user to send their score through an intent
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //inflate the menu, this adds items to the app bar!
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        setShareActionIntent("Check out my score!! Can you beat me? I got: " + _score);
        return super.onCreateOptionsMenu(menu);
    }

    private void setShareActionIntent(String s) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, s);
        shareActionProvider.setShareIntent(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_share:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    /**
     * Save Instance State onDestroy
     * Save the following:
     * - score
     * - seconds
     * - running
     * - ImageButtons[]
     * - gameboard int[]
     * - playerName
     */

    public void onSavedInstanceState(Bundle savedInstanceState){
        savedInstanceState.putInt("Score", _score);
        savedInstanceState.putString("PlayerName", playerName);
        //_seconds = _myTimer.getTime();
        savedInstanceState.putInt("Seconds", _seconds);
        //_running = _myTimer.getRunning();
        savedInstanceState.putBoolean("Running", _running);
        //_mainGameboard = _mainHolderClass.getGameBoard();
        //savedInstanceState.putIntArray("GameBoard", _mainGameboard);
    }

}
