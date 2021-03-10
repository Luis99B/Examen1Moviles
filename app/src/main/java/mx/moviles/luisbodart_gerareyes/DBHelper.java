package mx.moviles.luisbodart_gerareyes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context){
        super(context, "Games.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + "tableGames" + "(" +
                "id" + " INTEGER PRIMARY KEY, " +
                "plataforma" + " TEXT, " +
                "nombre" + " TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS tableGames";
        db.execSQL(query);
        onCreate(db);
    }

    public void addGame(String plataforma, String nombre ){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("plataforma", plataforma);
        values.put("nombre", nombre);
        db.insert("tableGames", null, values);
    }

    public ArrayList<Game> getGames(String plataformaIn){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Game> games = new ArrayList<Game>();
        String clause = "plataforma" + " = ?";
        String[] args = {plataformaIn};
        Cursor cursor = db.query("tableGames", null, clause, args, null, null, null);
        if(cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String plataformaOut = cursor.getString(1);
                String nombre = cursor.getString(2);
                games.add(new Game(id, plataformaOut, nombre));
            } while (cursor.moveToNext());
        }
        return games;
    }
}
