package practicaltest01var03.eim.systems.cs.pub.ro.practicaltest01var03;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest01var03MainActivity extends AppCompatActivity {

    private Button display, navig;
    private CheckBox c1, c2;
    private EditText name, grupa;
    private TextView out;
    private int REQUEST_CODE = 1;
    private boolean isServiceStarted = false;
    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private IntentFilter intentFilter = null;

    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("msg");
            Toast.makeText(getApplicationContext(), "[MessageBroadcastReceiver] -> Text is: "
                            + " " + message,
                    Toast.LENGTH_SHORT).show();
            Log.d("[BroadcastReceiver]", "Text is: " + message);
        }
    }

    private ButtonClickListener buttonClickListener = new ButtonClickListener();

    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.check1:
                    if(c1.isChecked()) {
                        out.setText(" " + name.getText().toString() + " " + out.getText().toString());
                    }
                    break;
                case R.id.check2:
                    if(c2.isChecked()) {
                        out.setText(" " + out.getText().toString() + " " + grupa.getText().toString());
                    }
                    break;
                case R.id.display_button:
                    out.setVisibility(View.VISIBLE);

                    if(!isServiceStarted && name.getText().length() > 0) {
                        Intent intent = new Intent(getApplicationContext(), MyService.class);
                        intent.putExtra("text", out.getText().toString());
                        startService(intent);
                        isServiceStarted = true;
                    }
                    break;
                case R.id.navigate_button:
                    Intent intent = new Intent(getApplicationContext(), SecondaryActivity.class);
                    intent.putExtra("msg", out.getText().toString());
                    startActivityForResult(intent, REQUEST_CODE);
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var03_main);

        name = findViewById(R.id.nume_button);
        grupa = findViewById(R.id.grupa_button);
        c1 = findViewById(R.id.check1);
        c2 = findViewById(R.id.check2);
        out = findViewById(R.id.info_text);
        navig = findViewById(R.id.navigate_button);
        display = findViewById(R.id.display_button);

        name.setOnClickListener(buttonClickListener);
        grupa.setOnClickListener(buttonClickListener);
        c1.setOnClickListener(buttonClickListener);
        c2.setOnClickListener(buttonClickListener);
        navig.setOnClickListener(buttonClickListener);
        display.setOnClickListener(buttonClickListener);

        intentFilter = new IntentFilter();

        for(int i = 0; i < Constants.action_types.length; i++) {
            intentFilter.addAction(Constants.action_types[i]);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(messageBroadcastReceiver);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE) {
            Toast.makeText(getApplicationContext(), "Activity returned with result: " + resultCode,
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("name", name.getText().toString());
        outState.putString("grupa", grupa.getText().toString());
        outState.putString("out", out.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if(savedInstanceState != null) {
            if(savedInstanceState.containsKey("name")) {
                name.setText(savedInstanceState.getString("name"));
            }
            if(savedInstanceState.containsKey("out")) {
                out.setText(savedInstanceState.getString("out"));
            }
            if(savedInstanceState.containsKey("grupa")) {
                grupa.setText(savedInstanceState.getString("grupa"));
            }
        }
    }
}
