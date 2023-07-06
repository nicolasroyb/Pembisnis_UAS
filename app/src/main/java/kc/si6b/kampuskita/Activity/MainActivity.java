package kc.si6b.kampuskita.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import kc.si6b.kampuskita.Adapter.Adapterkampus;
import kc.si6b.kampuskita.Api.APIRequestData;
import kc.si6b.kampuskita.Api.RetroServer;
import kc.si6b.kampuskita.Model.ModelKampus;
import kc.si6b.kampuskita.Model.ModelRespon;
import kc.si6b.kampuskita.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvbisnis;
    private FloatingActionButton fabTambah;
    private ProgressBar pbbisnis;

    private RecyclerView.Adapter adKampus;
    private RecyclerView.LayoutManager lmKampus;
    private List<ModelKampus> listKampus = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvbisnis = findViewById(R.id.rv_bisnis);
        fabTambah = findViewById(R.id.fab_tambah);
        pbbisnis = findViewById(R.id.pb_bisnis);

        lmKampus = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvbisnis.setLayoutManager(lmKampus);

        fabTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TambahActivity.class));
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        retrievekampus();
    }

    public void retrievekampus(){
        pbbisnis.setVisibility(View.VISIBLE);

        APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelRespon> proses = API.ardRetrieve();

        proses.enqueue(new Callback<ModelRespon>() {
            @Override
            public void onResponse(Call<ModelRespon> call, Response<ModelRespon> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();
                listKampus = response.body().getData();

                adKampus = new Adapterkampus(MainActivity.this, listKampus);
                rvbisnis.setAdapter(adKampus);
                adKampus.notifyDataSetChanged();

                pbbisnis.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ModelRespon> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Gagal menghubungi server!", Toast.LENGTH_SHORT).show();
                pbbisnis.setVisibility(View.GONE);
            }
        });
    }
}