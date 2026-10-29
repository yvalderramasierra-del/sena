package com.example.facturabanck;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String NOMBRE_BD = "factura_banck.db";
    private static final int VERSION_BD = 1;

    public static final String TABLA_USUARIOS = "usuarios";
    public static final String COL_ID = "id";
    public static final String COL_USUARIO = "usuario";
    public static final String COL_CONTRASENA = "contrasena";

    public DBHelper(Context context) {
        super(context, NOMBRE_BD, null, VERSION_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String crearTabla = "CREATE TABLE " + TABLA_USUARIOS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USUARIO + " TEXT NOT NULL UNIQUE, " +
                COL_CONTRASENA + " TEXT NOT NULL)";

        db.execSQL(crearTabla);

        insertarUsuarioInicial(db, "admin", "12345");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_USUARIOS);
        onCreate(db);
    }

    private void insertarUsuarioInicial(SQLiteDatabase db, String usuario, String contrasena) {
        ContentValues valores = new ContentValues();
        valores.put(COL_USUARIO, usuario);
        valores.put(COL_CONTRASENA, contrasena);
        db.insert(TABLA_USUARIOS, null, valores);
    }

    public boolean validarUsuario(String usuario, String contrasena) {
        SQLiteDatabase db = this.getReadableDatabase();

        String seleccion = COL_USUARIO + " = ? AND " + COL_CONTRASENA + " = ?";
        String[] argumentos = {usuario, contrasena};

        Cursor cursor = db.query(
                TABLA_USUARIOS,
                new String[]{COL_ID},
                seleccion,
                argumentos,
                null,
                null,
                null
        );

        boolean existe = cursor.getCount() > 0;
        cursor.close();
        db.close();

        return existe;
    }
}

