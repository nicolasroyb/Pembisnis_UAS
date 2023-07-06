package kc.si6b.kampuskita.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kc.si6b.kampuskita.Activity.MainActivity;
import kc.si6b.kampuskita.Activity.UbahActivity;
import kc.si6b.kampuskita.Api.APIRequestData;
import kc.si6b.kampuskita.Api.RetroServer;
import kc.si6b.kampuskita.Model.ModelKampus;
import kc.si6b.kampuskita.Model.ModelRespon;
import kc.si6b.kampuskita.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Adapterkampus extends RecyclerView.Adapter<Adapterkampus.VHkampus>{
    private Context ctx;
    private List<ModelKampus> listkampus;

    public Adapterkampus(Context ctx, List<ModelKampus> listkampus) {
        this.ctx = ctx;
        this.listkampus = listkampus;
    }

    @NonNull
    @Override
    public VHkampus onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View varView = LayoutInflater.from(ctx).inflate(R.layout.list_item_kampus, parent, false);
        return new VHkampus(varView);
    }

    @Override
    public void onBindViewHolder(@NonNull VHkampus holder, int position) {
        ModelKampus MK = listkampus.get(position);
        holder.tvid.setText(MK.getId());
        holder.tvnama.setText(MK.getNama());
        holder.tvjenis.setText(MK.getJenis());
        holder.tvawal.setText(MK.getAwal());
        holder.tvpendapatan.setText(MK.getPendapatan());
        holder.tvasal.setText(MK.getAsal());


    }

    @Override
    public int getItemCount() {
        return listkampus.size();
    }

    public class VHkampus extends RecyclerView.ViewHolder{
        TextView tvid, tvnama, tvjenis, tvawal, tvpendapatan, tvasal;
        Button btnhapus, btnubah;

        public VHkampus(@NonNull View itemView) {
            super(itemView);

            tvid = itemView.findViewById(R.id.tv_id);
            tvnama = itemView.findViewById(R.id.tv_nama);
            tvjenis = itemView.findViewById(R.id.tv_jenis);
            tvawal = itemView.findViewById(R.id.tv_awal);
            tvpendapatan = itemView.findViewById(R.id.tv_pendapatan);
            tvasal = itemView.findViewById(R.id.tv_asal);
            btnhapus = itemView.findViewById(R.id.btn_hapus);
            btnubah = itemView.findViewById(R.id.btn_Ubah);

            btnhapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deletekampus(tvid.getText().toString());
                }
            });

            btnubah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent pindah = new Intent(ctx, UbahActivity.class);
                    pindah.putExtra("xId", tvid.getText().toString());
                    pindah.putExtra("xNama", tvnama.getText().toString());
                    pindah.putExtra("xjenis", tvjenis.getText().toString());
                    pindah.putExtra("xawal", tvawal.getText().toString());
                    pindah.putExtra("xpendapatan", tvpendapatan.getText().toString());
                    pindah.putExtra("xasal", tvasal.getText().toString());
                    ctx.startActivity(pindah);
                }
            });
        }

        void deletekampus(String id){
            APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ModelRespon> proses = API.ardDelete(id);

            proses.enqueue(new Callback<ModelRespon>() {
                @Override
                public void onResponse(Call<ModelRespon> call, Response<ModelRespon> response) {
                    String kode = response.body().getKode();
                    String pesan = response.body().getPesan();

                    Toast.makeText(ctx, "kode" + kode + "pesan : " + pesan, Toast.LENGTH_SHORT).show();
                    ((MainActivity)ctx).retrievekampus();
                }

                @Override
                public void onFailure(Call<ModelRespon> call, Throwable t) {
                    Toast.makeText(ctx, "error gagal menghubungi server", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
