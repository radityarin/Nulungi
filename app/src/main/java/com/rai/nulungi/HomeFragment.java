package com.rai.nulungi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeFragment extends Fragment {

    FirebaseRecyclerAdapter<Berita,HomeFragment.BeritaViewHolder> beritaadapter;
    DatabaseReference produkRef;


    ViewFlipper viewFlipper;

    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        ImageButton btn_furnitur, btn_pakaian, btn_elektronik, btn_buku;
        btn_furnitur = view.findViewById(R.id.btnfurnitur);
        btn_pakaian = view.findViewById(R.id.btnpakaian);
        btn_elektronik = view.findViewById(R.id.btnelektronik);
        btn_buku = view.findViewById(R.id.btnbuku);
        btn_furnitur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),ListPerKebutuhan.class);
                intent.putExtra("barang","furnitur");
                startActivity(intent);
            }
        });
        btn_pakaian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),ListPerKebutuhan.class);
                intent.putExtra("barang","pakaian");
                startActivity(intent);
            }
        });

        btn_elektronik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),ListPerKebutuhan.class);
                intent.putExtra("barang","elektronik");
                startActivity(intent);
            }
        });
        btn_buku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),ListPerKebutuhan.class);
                intent.putExtra("barang","buku");
                startActivity(intent);
            }
        });

        ImageButton btn_pantiasuhan = view.findViewById(R.id.pantiasuhanbutton);
        btn_pantiasuhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ListPerKategoriPage.class);
                intent.putExtra("kategori","Panti Asuhan");
                startActivity(intent);
            }
        });
        ImageButton btn_pantijompo = view.findViewById(R.id.pantijompobutton);
        btn_pantijompo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ListPerKategoriPage.class);
                intent.putExtra("kategori","Panti Jompo");
                startActivity(intent);
            }
        });ImageButton btn_komunitas = view.findViewById(R.id.komunitasbutton);
        btn_komunitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ListPerKategoriPage.class);
                intent.putExtra("kategori","Komunitas");
                startActivity(intent);
            }
        });ImageButton btn_masjid = view.findViewById(R.id.masjidbutton);
        btn_masjid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ListPerKategoriPage.class);
                intent.putExtra("kategori","Masjid");
                startActivity(intent);
            }
        });

        //======================

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        produkRef = FirebaseDatabase.getInstance().getReference().child("berita");

        Query query = produkRef.orderByChild("berita");
        FirebaseRecyclerOptions<Berita> options =
                new FirebaseRecyclerOptions.Builder<Berita>()
                        .setQuery(query, Berita.class)
                        .build();

        beritaadapter = new FirebaseRecyclerAdapter<Berita, HomeFragment.BeritaViewHolder>(options) {
            @Override
            public HomeFragment.BeritaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.card_news, parent, false);
                return new HomeFragment.BeritaViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(HomeFragment.BeritaViewHolder holder, int position, final Berita model) {
                holder.display(model.getJudul(), model.getUrlfoto(), model.getIsiberita());
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(),BeritaPage.class);
                        intent.putExtra("judul",model.getJudul());
                        intent.putExtra("url",model.getUrlfoto());
                        intent.putExtra("isi",model.getIsiberita());
                        startActivity(intent);
                    }
                });
            }
        };
        recyclerView.setAdapter(beritaadapter);

        //=========================





        //=========================
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        beritaadapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        beritaadapter.stopListening();
    }

    public class BeritaViewHolder extends RecyclerView.ViewHolder {

        View view;

        public BeritaViewHolder(View itemView) {
            super(itemView);

            view = itemView;

        }

        public void display(String judulberita ,String urlPhoto, String isiberita){
            TextView judulberitatv = (TextView) view.findViewById(R.id.judulberita);
            judulberitatv.setText(judulberita);
            ImageView fotoberita = (ImageView) view.findViewById(R.id.urlphoto);
            Picasso.get().load(urlPhoto).into(fotoberita);
            TextView isiberitatv = (TextView) view.findViewById(R.id.isiberita);
            isiberitatv.setText(isiberita);
        }

    }

}
