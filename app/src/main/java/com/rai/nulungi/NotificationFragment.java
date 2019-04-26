package com.rai.nulungi;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class NotificationFragment extends Fragment {

    FirebaseRecyclerAdapter<Donasi, NotificationFragment.DonasiViewHolder> donasiadapter;
    DatabaseReference donasiRef;
    FirebaseAuth auth;


    public NotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_notification, container, false);auth = FirebaseAuth.getInstance();

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        donasiRef = FirebaseDatabase.getInstance().getReference().child("List Barang Donasi");

        Query query = donasiRef.orderByChild("iddonatur").equalTo(auth.getUid());
        FirebaseRecyclerOptions<Donasi> options =
                new FirebaseRecyclerOptions.Builder<Donasi>()
                        .setQuery(query, Donasi.class)
                        .build();

        donasiadapter = new FirebaseRecyclerAdapter<Donasi, NotificationFragment.DonasiViewHolder>(options) {
            @Override
            public NotificationFragment.DonasiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.card_history, parent, false);

                return new NotificationFragment.DonasiViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(NotificationFragment.DonasiViewHolder holder, int position, final Donasi model) {
                holder.display(model.getKategori(), model.getNama(), model.getTgldonasi(),model.getStatusdonasi());
//                holder.view.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent = new Intent(getContext(),produkPage.class);
//                        intent.putExtra("judulproduk",model.getNamaProduk());
//                        intent.putExtra("urlproduk",model.getUrlProduk());
//                        intent.putExtra("deskripsiproduk",model.getDeskripsiProduk());
//                        intent.putExtra("hargaproduk",model.getHargaProduk());
//                        intent.putExtra("kategoriproduk",model.getKategoriProduk());
//                        startActivity(intent);
//                    }
//                });
            }
        };
        recyclerView.setAdapter(donasiadapter);


        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        donasiadapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        donasiadapter.stopListening();
    }

    public class DonasiViewHolder extends RecyclerView.ViewHolder {

        View view;

        public DonasiViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }

        public void display(String kategoribarang,String namatempat, String tanggaldonasi, String statusdonasi){
            ImageView ivlogokategori = view.findViewById(R.id.logokategori);
            if(kategoribarang.equals("pakaian")){
                ivlogokategori.setImageResource(R.drawable.baju);
            } else if(kategoribarang.equals("elektronik")){
                ivlogokategori.setImageResource(R.drawable.elektronik);
            } else if(kategoribarang.equals("furnitur")){
                ivlogokategori.setImageResource(R.drawable.furniture);
            } else if(kategoribarang.equals("buku")){
                ivlogokategori.setImageResource(R.drawable.book);
            }
            TextView tvnamatempat = view.findViewById(R.id.namatempat);
            tvnamatempat.setText(namatempat);
            TextView tvtanggaldonasi = view.findViewById(R.id.tanggaldonasi);
            tvtanggaldonasi.setText(tanggaldonasi);
            TextView tvstatusdonasi = view.findViewById(R.id.statusdonasi);
            tvstatusdonasi.setText(statusdonasi);
        }

    }
}
