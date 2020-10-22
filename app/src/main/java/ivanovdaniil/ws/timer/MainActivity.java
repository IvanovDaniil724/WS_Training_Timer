package ivanovdaniil.ws.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
{
    public Timer timer; public TimerTask timerTask;
    public int minutes = 0, seconds = 0, milliseconds = 0;
    public boolean isTimerStart = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView visualTimer = findViewById(R.id.Timer_TextView);
        new FullscreenOptimization(getSupportActionBar(), getWindow().getDecorView()).Optimization();

        findViewById(R.id.StartButton).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (isTimerStart) { return; }
                else { isTimerStart = true; }

                findViewById(R.id.InputTimer_ConstraintLayout).setVisibility(View.INVISIBLE);
                findViewById(R.id.StartButton).setVisibility(View.INVISIBLE);

                new Timer().schedule(new TimerTask()
                {
                    @Override
                    public void run()
                    {
                        if (milliseconds == 0 && seconds == 0 && minutes == 0)
                        {
                            startActivity(new Intent(MainActivity.this, SecondActivity.class)); finish();
                        }

                        if (milliseconds == 0)
                        {
                            milliseconds = 400;
                            if (seconds == 0)
                            {
                                seconds = 59;
                                if (minutes != 0) { minutes -= 1; }
                            } else { seconds -= 1; }
                        } else { milliseconds -= 1; }

                        runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                visualTimer.setText(validateTimerTextFormat(minutes, seconds, milliseconds));
                            }
                        });
                    }
                }, 0, 1);
            }
        });

        changeInputTimer();
    }

    public String validateTimerTextFormat(int _minutes, int _seconds, int _milliseconds)
    {
        String resultTimer = "";

        if (minutes < 10) { resultTimer = "0" + minutes + ":"; }
        else { resultTimer = minutes + ":"; }
        if (seconds < 10) { resultTimer += "0" + seconds + ":"; }
        else { resultTimer += seconds + ":"; }

        if (milliseconds < 100 && milliseconds >= 10) { resultTimer += "00" + milliseconds; }
        else
        {
            if (milliseconds < 10) { resultTimer += "000" + milliseconds; }
            else { resultTimer += "0" + milliseconds; }
        }

        return resultTimer;
    }

    public void changeInputTimer()
    {
        final TextView inputTimerText = findViewById(R.id.InputTimer_TextView);

        ImageView[] arrows =
                {
                        findViewById(R.id.MinutesUpArrow_ImageView),
                        findViewById(R.id.SecondsUpArrow_ImageView),
                        findViewById(R.id.MillisecondsUpArrow_ImageView),
                        findViewById(R.id.MinutesDownArrow_ImageView),
                        findViewById(R.id.SecondsDownArrow_ImageView),
                        findViewById(R.id.MillisecondsDownArrow_ImageView)
                };

        for (ImageView arrow : arrows) { arrow.setImageResource(R.drawable.input_arrow); }

        arrows[0].setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                minutes++; inputTimerText.setText(validateTimerTextFormat(minutes, seconds, milliseconds));
            }
        });

        arrows[1].setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                seconds++; inputTimerText.setText(validateTimerTextFormat(minutes, seconds, milliseconds));
            }
        });

        arrows[2].setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                milliseconds++; inputTimerText.setText(validateTimerTextFormat(minutes, seconds, milliseconds));
            }
        });

        arrows[3].setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (!(minutes - 1 < 0))
                {
                    minutes--;
                    inputTimerText.setText(validateTimerTextFormat(minutes, seconds, milliseconds));
                }
            }
        });

        arrows[4].setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (!(seconds - 1 < 0))
                {
                    seconds--;
                    inputTimerText.setText(validateTimerTextFormat(minutes, seconds, milliseconds));
                }
            }
        });

        arrows[5].setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (!(milliseconds - 1 < 0))
                {
                    milliseconds--;
                    inputTimerText.setText(validateTimerTextFormat(minutes, seconds, milliseconds));
                }
            }
        });
    }
}