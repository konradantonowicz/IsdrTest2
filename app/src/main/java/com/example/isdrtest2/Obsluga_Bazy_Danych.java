package com.example.isdrtest2;


        import android.annotation.SuppressLint;
        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.util.Log;
        import java.io.File;
        import java.io.FileOutputStream;
        import java.io.InputStream;
        import java.io.OutputStream;
        import java.util.ArrayList;
        import java.util.List;

public class Obsluga_Bazy_Danych extends SQLiteOpenHelper
    {
        private static final String DBNAME = "BazaDanychPytan.db";
        @SuppressLint("SdCardPath") private static final String DBLOCATION = "/data/data/" + BuildConfig.APPLICATION_ID + "/databases/";
        private Context mContext;
        private SQLiteDatabase mDatabase;
        Obsluga_Bazy_Danych(Context context)
            {
                super(context, DBNAME, null, 1);
                this.mContext = context;
            }
        @Override public void onCreate(SQLiteDatabase db)
            {
            }
        @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
            {
            }
        void openDatabase()
            {
                String dbPath = mContext.getDatabasePath(DBNAME).getPath();
                if (mDatabase != null && mDatabase.isOpen()) {
                    return;
                }
                mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
            }
        void closeDatabase()
            {
                if (mDatabase != null) {
                    mDatabase.close();
                }
            }
        boolean sprawdz()
            {
                boolean czyjest = true;
                File database = mContext.getDatabasePath(Obsluga_Bazy_Danych.DBNAME);
                if (!database.exists()) {
                    getReadableDatabase();
                    close();
                    copyDatabase(mContext);
                    czyjest = false;
                }
                return czyjest;
            }
        private void copyDatabase(Context context)
            {
                try {
                    InputStream inputStream = context.getAssets().open(Obsluga_Bazy_Danych.DBNAME);
                    String outFileName = Obsluga_Bazy_Danych.DBLOCATION + Obsluga_Bazy_Danych.DBNAME;
                    OutputStream outputStream = new FileOutputStream(outFileName);
                    byte[] buff = new byte[1024];
                    int length;
                    while ((length = inputStream.read(buff)) > 0) {
                        outputStream.write(buff, 0, length);
                    }
                    outputStream.flush();
                    outputStream.close();
                    Log.w("AktywnoscGlowna", "DB copied");
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        List<DaoPytZazPytPraw> Lista_wyswietl_odpPraw_odpZaz()
            {
                openDatabase();


                Cursor k = mDatabase.rawQuery("select OdpowiedzPrawidlowa,Odpowiedz_zaznaczona from Pytania",null);



                List<DaoPytZazPytPraw> ListaOdpPrawZaz = new ArrayList<>();

                DaoPytZazPytPraw pytaniaDao;
                k.moveToFirst();
                while (!k.isAfterLast()) {
                    String Tresc_odpPrawidlowa = k.getString(0);
                    String Tresc_odpZaznaczona = k.getString(1);

                    pytaniaDao = new DaoPytZazPytPraw(Tresc_odpPrawidlowa,Tresc_odpZaznaczona);
                    ListaOdpPrawZaz.add(pytaniaDao);
                    k.moveToNext();
                }
                k.close();
                closeDatabase();
                return ListaOdpPrawZaz;
            }

        Cursor Cursor_wyswietl_pytaniaiodp(String[] nrpytania, String mySQL) {
            openDatabase();
            return mDatabase.rawQuery(mySQL,nrpytania);
        }

        long updateOdpowiedz(String numer_pytania, String kliknieta_odp) {
            int number = Integer.parseInt(numer_pytania);

            openDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put("Odpowiedz_zaznaczona",kliknieta_odp);
            String whereClause = "Nr_pytania" + " = ?";
            String[] w_ktorym_wierszu_usunac ={Integer.toString(number)};
            long returnValue =  mDatabase.update("Pytania",contentValues,whereClause,w_ktorym_wierszu_usunac);

            closeDatabase();
            return returnValue;
        }



    }
