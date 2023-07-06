package kc.si6b.kampuskita.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import kc.si6b.kampuskita.Api.APIRequestData;
import kc.si6b.kampuskita.Api.RetroServer;
import kc.si6b.kampuskita.Model.ModelRespon;
import kc.si6b.kampuskita.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahActivity extends AppCompatActivity {
    private EditText etnama, etjenis, etawal, etpendapatan, etasal;
    private Button btntambah;
    private String Nama, jenis, awal, pendapatan, asal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        etnama = findViewById(R.id.et_nama);
        etjenis = findViewById(R.id.et_jenis);
        etawal = findViewById(R.id.et_awal);
        etpendapatan = findViewById(R.id.et_pendapatan);
        etasal = findViewById(R.id.et_asal);
        btntambah = findViewById(R.id.btn_tambah);

        btntambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Nama = etnama.getText().toString();
                jenis = etjenis.getText().toString();
                awal = etawal.getText().toString();
                pendapatan = etasal.getText().toString();
                asal = etasal.getText().toString();


                if(Nama.trim().isEmpty()){
                   etnama.setError("nama tidak boleh kosong");
                } else if (jenis.trim().isEmpty()) {
                    etjenis.setError("warna tidak boleh kosong");
                } else if (awal.trim().isEmpty()) {
                    etawal.setError("tentang tidak boleh kosong");
                } else if (pendapatan.trim().isEmpty()) {
                    etpendapatan.setError("asal-muasal tidak boleh kosong");
                } else if (asal.trim().isEmpty()) {
                    etasal.setError("keunikan tidak boleh kosong");
                }
                else {
                    tambahkampus();
                }
            }
        });
    }

    private void tambahkampus(){
        APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelRespon> proses = API.ardCreate(Nama, jenis, awal, pendapatan, asal);

        proses.enqueue(new Callback<ModelRespon>() {
            @Override
            public void onResponse(Call<ModelRespon> call, Response<ModelRespon> response) {
                String kode, pesan;
                kode = response.body().getKode();
                pesan = response.body().getPesan();

                Toast.makeText(TambahActivity.this, "kode :" + kode + "pesan : ", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ModelRespon> call, Throwable t) {
                Toast.makeText(TambahActivity.this, "Gagal menghubungi server!", Toast.LENGTH_SHORT).show();
                Log.d("Disini", "Error:" + t.getMessage());
            }
        });
    }
}