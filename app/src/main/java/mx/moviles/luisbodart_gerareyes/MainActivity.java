package mx.moviles.luisbodart_gerareyes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editTxt;
    private static final int DB_ACT = 0;
    public static DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTxt = findViewById(R.id.editText);

        db = new DBHelper(this);
    }

    public void toDB_Act(View v) {
        Intent i = new Intent(this, DBActivity.class);
        i.putExtra("editTxt", editTxt.getText().toString());
        startActivityForResult(i, DB_ACT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DB_ACT && resultCode == Activity.RESULT_OK){
            Toast.makeText(this, data.getStringExtra("id"), Toast.LENGTH_SHORT).show();
        }
    }
}
