package com.example.lexiquest;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;


public class game extends AppCompatActivity {
    TextView questionTextView, timerTextView, currentPlayerStackTextView, lavaLevelTextView;
    EditText answerEditText;
    Button submitButton;
    HashMap<Integer, String> questionsMap;
    HashMap<Integer, List<String>> answersMap;
    int currentQuestion = 0;
    int playerStack = 0;
    int lavaLevel = -1;
    Random random;
    CountDownTimer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        questionTextView = findViewById(R.id.questionTextView);
        timerTextView = findViewById(R.id.timerTextView);
        currentPlayerStackTextView = findViewById(R.id.currentPlayerStackTextView);
        lavaLevelTextView = findViewById(R.id.lavaLevelTextView);
        answerEditText = findViewById(R.id.answerEditText);
        submitButton = findViewById(R.id.submitButton);


        random = new Random();
        questionsMap = new HashMap<>();
        answersMap = new HashMap<>();


        // Add questions and answers to the maps
        questionsMap.put(0, "Name a popular gaming console");
        answersMap.put(0, new ArrayList<>(Arrays.asList("PS5", "PS", "PS2", "PS3", "PS4", "Xbox","Xbox Series X", "Nintendo Switch", "Nintendo")));


        questionsMap.put(1, "Name a popular programming language");
        answersMap.put(1, new ArrayList<>(Arrays.asList("Java", "Python", "JavaScript", "C", "C++", "C#", "HTML", "rust", "golang", "go", "ruby", "kotlin" )));


        questionsMap.put(2, "Name a popular movie genre");
        answersMap.put(2, new ArrayList<>(Arrays.asList("Action", "Comedy", "Drama", "Crime", "Thriller", "Romance", "Sci-Fi", "Biography", "Documentary", "Short film" )));


        startNewRound();


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answer = answerEditText.getText().toString().toUpperCase();
                if (Objects.requireNonNull(answersMap.get(currentQuestion)).contains(answer)) {
                    playerStack += answer.length();
                    currentPlayerStackTextView.setText("Player Stack: " + playerStack);
                    answerEditText.setText("");
                } else {
                    Toast.makeText(game.this, "Almost there, try again!!", Toast.LENGTH_SHORT).show();
                    answerEditText.setText("");
                }
            }
        });
    }


    private void startNewRound() {
        currentQuestion = random.nextInt(questionsMap.size());
        questionTextView.setText(questionsMap.get(currentQuestion));


        timer = new CountDownTimer(15000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText("Time: " + millisUntilFinished / 1000 + "s");
            }


            @Override
            public void onFinish() {
                int avgAnswerLength = 0;
                for (String answer : answersMap.get(currentQuestion)) {
                    avgAnswerLength += answer.length();
                }
                avgAnswerLength /= answersMap.get(currentQuestion).size();
                lavaLevel = avgAnswerLength;
                lavaLevelTextView.setText("Lava Level: " + lavaLevel);


                if (lavaLevel > playerStack) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(game.this);
                    builder.setMessage("Game Over! Wanna play again?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            restartGame();
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    builder.show();
                } else {
                    startNewRound();
                }
            }
        }.start();
    }


    private void restartGame() {
        currentQuestion = 0;
        playerStack = 0;
        lavaLevel = -1;
        timer.cancel();
        startNewRound();
    }
}




//end{code}
//
//        Note: If you're new to programming or Android, there might be a lot of things happening in this code that you might not be familiar with. Here's a brief explanation of what's happening:
//
//        1. The app consists of a game where the player has to guess answers to a set of questions.
//        2. The game starts by initializing a new round. In a round, the player has to guess the answers to the randomly chosen questions within the given time.
//        3. The time is displayed in the app using a TextView.
//        4. The player's score is displayed in the app using a TextView.
//        5. The player has to type their guess into an EditText.
//        6. If the player's guess is correct, their score is updated and they move on to the next round.
//        7. If the player's guess is incorrect, they can try again.
//        8. If the player runs out of time, the lava level is calculated as the average length of the answers.
//        9. If the player's score is less than the lava level, the game is over and the player is prompted to play again.
//        10. The game can be restarted by calling the `restartGame()` method.
//
//        Feel free to modify the code as per your requirements. If you need any further assistance, please let me know.
