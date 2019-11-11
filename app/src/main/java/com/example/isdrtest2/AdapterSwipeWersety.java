package com.example.isdrtest2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
public class AdapterSwipeWersety extends FragmentStatePagerAdapter
{

    AdapterSwipeWersety(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);

    }


    @Override
    public int getCount() {
        return 520;
    }

    @NonNull @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment_Pokaz_pytania();
        Bundle bundle   =new Bundle();
        bundle.putString("autalna_strona_swipe", String.valueOf(position+1));
        fragment.setArguments(bundle);

        return fragment;
    }



}