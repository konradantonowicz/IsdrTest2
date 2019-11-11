package com.example.isdrtest2;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity implements Klik_na_Numer_Pytania
    {
        RecyclerView recyclerView;
        AdapterRecycler adapterRecycler;
        private ViewModel model;
        private List<DaoPytZazPytPraw> mFav;
        Obsluga_Bazy_Danych  mObslugaBazyDanych;
        @Override protected void onCreate(Bundle savedInstanceState)
            {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

                mObslugaBazyDanych = new Obsluga_Bazy_Danych(this);
                mObslugaBazyDanych.sprawdz();
                model = ViewModelProviders.of(this).get(ViewModel.class);
                List<DaoPytZazPytPraw>lista = new ArrayList<>( mObslugaBazyDanych.Lista_wyswietl_odpPraw_odpZaz());
                for (DaoPytZazPytPraw a : lista) {
                    System.out.println(a.getOdpowiedzPrawidlowa());
                }

                recyclerView = findViewById(R.id.id_listaRecyclerview);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new GridLayoutManager(this,7));
                adapterRecycler = new AdapterRecycler(this, mObslugaBazyDanych.Lista_wyswietl_odpPraw_odpZaz());
                recyclerView.setAdapter(adapterRecycler);
                adapterRecycler.ustawSluchaczaKlikniecia(this);
                Observer<List<DaoPytZazPytPraw>> favsObserver = new Observer<List<DaoPytZazPytPraw>>() {
                    @Override public void onChanged(List<DaoPytZazPytPraw> updatedList)
                        {
                            mFav = updatedList;
                            assert mFav != null;
                            adapterRecycler.addItems(mFav);
                        }
                };
                model.getCurrentName().observe(this,favsObserver);
            }
        @Override public void klik_na_numerze_pytania(String NumerPytania)
            {
                Intent intent = new Intent(getApplicationContext(),Aktywnosc_ViewPager.class);
                intent.putExtra("numer_pytania",NumerPytania);
                startActivity(intent);
            }
        public void onResume()
            {
                super.onResume();
                model.getCurrentName().setValue(mObslugaBazyDanych.Lista_wyswietl_odpPraw_odpZaz());
            }
    }


