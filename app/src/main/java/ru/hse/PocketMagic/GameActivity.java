package ru.hse.PocketMagic;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {

    private Controller controller;

    private int maxHP;
    private int maxMP;

    private ProgressBar playerHP;
    private TextView valueHP;
    private ProgressBar playerMP;
    private TextView valueMP;
    private ProgressBar opponentHP;
    private TextView valueOHP;

    private TextView opponentName;
    private ImageView opponentSpell;

    private GestureLibrary gestureLibrary;
    private GestureOverlayView gestureOverlayView;
    private GestureListener gestureListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        gestureLibrary = GestureLibraries.fromRawResource(getApplicationContext(), R.raw.gestures);
        gestureOverlayView = findViewById(R.id.gestureListener);
        if (!gestureLibrary.load()) {
            finish();
        }

        playerHP = findViewById(R.id.playerHP);
        valueHP = findViewById(R.id.valueHP);
        playerMP = findViewById(R.id.playerMP);
        valueMP = findViewById(R.id.valueMP);
        opponentHP = findViewById(R.id.opponentHP);
        valueOHP = findViewById(R.id.valueOHP);

        opponentName = findViewById(R.id.opponentName);
        opponentSpell = findViewById(R.id.opponentSpell);

        controller = new Controller(this);

        gestureListener = new GestureListener(gestureLibrary, this, controller);
        gestureOverlayView.addOnGesturePerformedListener(gestureListener);
    }

    @Override
    public void onBackPressed() { /* Do nothing. */}

    public void setOpponentName(String name) {
        opponentName.setText(name);
    }

    public void setMaxHP(int value) {
        maxHP = value;
    }

    public void setMaxMP(int value) {
        maxMP = value;
    }

    public void setPlayerHP(int value) {
        playerHP.setProgress(value);
        String text = value + "/" + maxHP;
        valueHP.setText(text);
    }

    public void setPlayerMP(int value) {
        playerMP.setProgress(value);
        String text = value + "/" + maxMP;
        valueMP.setText(text);
    }

    public void setOpponentHP(int value) {
        opponentHP.setProgress(value);
        String text = value + "/" + maxHP;
        valueOHP.setText(text);
    }

    public void endGame(GameResult result) {
        Intent intent = new Intent(GameActivity.this, GameResultsActivity.class);

        switch (result) {
            case WIN:
                intent.putExtra("RESULT", 1);
                break;
            case LOSE:
                intent.putExtra("RESULT", -1);
                break;
            case DRAW:
                intent.putExtra("RESULT", 0);
                break;
        }

        startActivity(intent);
        finish();
    }

    public void showOpponentSpell(String spell) {

    }

    public void showPlayerSpell(String spell) {
        Toast.makeText(this.getApplicationContext(), spell, Toast.LENGTH_LONG).show();
    }
}
