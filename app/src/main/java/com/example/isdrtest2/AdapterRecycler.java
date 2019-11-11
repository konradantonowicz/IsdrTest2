package com.example.isdrtest2;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.ViewHolder>{

    private List<DaoPytZazPytPraw> listItems;
    private MainActivity mmainActivity;


    AdapterRecycler(MainActivity mainActivity, List<DaoPytZazPytPraw> titles)

    {
        this.mmainActivity = mainActivity;
        this.listItems = titles;
    }
    @NonNull @Override
    public AdapterRecycler.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    { View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.widok_wiersza, viewGroup, false);
        return new AdapterRecycler.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(AdapterRecycler.ViewHolder holder, int position)
    {

        holder.itemTv.setText(String.valueOf(position+1));
        holder.itemTv.setTextColor(0xffffffff);

        if (listItems.get(position).getOdpowiedzZaznaczona() == null)
        {
            holder.zaz.setVisibility(View.GONE);
        }
        else if (listItems.get(position).getOdpowiedzZaznaczona().equals(listItems.get(position).getOdpowiedzPrawidlowa()))
        {
            holder.zaz.setVisibility(View.VISIBLE);
            holder.zaz.setBackgroundColor(0xff00ff00);
            holder.itemTv.setTextColor(0xff000000);
        }
        else if (!listItems.get(position).getOdpowiedzZaznaczona().equals(listItems.get(position).getOdpowiedzPrawidlowa()))
        {
            holder.zaz.setVisibility(View.VISIBLE);
            holder.zaz.setBackgroundColor(0xffff0000);
            holder.itemTv.setTextColor(0xff000000);
        }
        else
        {
            holder.zaz.setVisibility(View.VISIBLE);
            holder.zaz.setText(listItems.get(position).getOdpowiedzZaznaczona());
            holder.itemTv.setTextColor(0xff000000);
        }

    }
    @Override
    public int getItemCount()
    {
        return listItems.size();
    }

    void ustawSluchaczaKlikniecia(MainActivity mainActivity) {
        this.mmainActivity=mainActivity;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    { TextView itemTv,zaz;
        ViewHolder(View itemView) {
            super(itemView);
            itemTv = itemView.findViewById(R.id.id_wiersza);
            zaz = itemView.findViewById(R.id.id_wiersza_zaz);
            itemTv.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
        mmainActivity.klik_na_numerze_pytania(itemTv.getText().toString());
        }
    }
    void addItems(List<DaoPytZazPytPraw> borrowModelList) {
        this.listItems = borrowModelList;
        notifyDataSetChanged();
    }


}


