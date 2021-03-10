package mx.moviles.luisbodart_gerareyes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class DBActivity extends AppCompatActivity {

    private EditText saludoTxt, idTxt, plataformaTxt, nombreTxt;
    private Button ant, sig;
    private DBHelper db;
    private ArrayList<Game> gamesList;
    private boolean encontrado;
    private int indexSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dba);

        saludoTxt = findViewById(R.id.saludoEditText);
        idTxt = findViewById(R.id.idEditText);
        plataformaTxt = findViewById(R.id.plataformaEditText);
        nombreTxt = findViewById(R.id.nombreEditText);

        ant = findViewById(R.id.anteriorButton);
        sig = findViewById(R.id.siguienteButton);

        db = MainActivity.db;

        saludoTxt.setText(getIntent().getStringExtra("editTxt"));
        encontrado = false;
        indexSearch = 0;
        ant.setVisibility(View.INVISIBLE);
        sig.setVisibility(View.INVISIBLE);
    }

    public void limpiar(View v) {
        idTxt.setText("");
        plataformaTxt.setText("");
        nombreTxt.setText("");
    }

    public void guardar(View v) {
        db.addGame(plataformaTxt.getText().toString(), nombreTxt.getText().toString());
    }

    public void regresar(View v) {
        Intent i = new Intent();
        i.putExtra("id", idTxt.getText().toString());
        setResult(Activity.RESULT_OK, i);
        finish();
    }

    public void buscar(View v) {
        gamesList = db.getGames(plataformaTxt.getText().toString());
        if (!gamesList.isEmpty()){
            encontrado = true;
            indexSearch = 0;
            idTxt.setText(Integer.toString(gamesList.get(indexSearch).id));
            plataformaTxt.setText(gamesList.get(indexSearch).plataforma);
            nombreTxt.setText(gamesList.get(indexSearch).nombre);
        } else {
            encontrado = false;
        }
        if (encontrado && gamesList.size() > 1){
            sig.setVisibility(View.VISIBLE);
        } else if (encontrado && gamesList.size() == 1){
            ant.setVisibility(View.INVISIBLE);
            sig.setVisibility(View.INVISIBLE);
        } else {
            Toast.makeText(this, "NO se encontraron registros", Toast.LENGTH_SHORT).show();
            idTxt.setText("");
            plataformaTxt.setText("");
            nombreTxt.setText("");
            ant.setVisibility(View.INVISIBLE);
            sig.setVisibility(View.INVISIBLE);
        }
    }

    public void anterior(View v) {
        if (indexSearch > 0){
            indexSearch--;
            idTxt.setText(Integer.toString(gamesList.get(indexSearch).id));
            plataformaTxt.setText(gamesList.get(indexSearch).plataforma);
            nombreTxt.setText(gamesList.get(indexSearch).nombre);
            if (gamesList.size() > 1){
                sig.setVisibility(View.VISIBLE);
            }
            if (indexSearch == 0){
                ant.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void siguiente(View v) {
        if (indexSearch < gamesList.size() - 1){
            ant.setVisibility(View.VISIBLE);
            indexSearch++;
            idTxt.setText(Integer.toString(gamesList.get(indexSearch).id));
            plataformaTxt.setText(gamesList.get(indexSearch).plataforma);
            nombreTxt.setText(gamesList.get(indexSearch).nombre);
            if (indexSearch == gamesList.size() - 1){
                sig.setVisibility(View.INVISIBLE);
            }
        }
    }
}
