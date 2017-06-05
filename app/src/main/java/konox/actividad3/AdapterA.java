package konox.actividad3;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Daniel on 05/06/2017.
 */

public class AdapterA extends RecyclerView.Adapter<AdapterA.AdapterHolder> {
    public List<galeria> galerias;
    public Context context;

    public class AdapterHolder extends RecyclerView.ViewHolder {
        TextView text;
        ImageView image;

        public AdapterHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.textCard);
            image = (ImageView) itemView.findViewById(R.id.imageCard);
        }
    }

    public AdapterA(Context Context, List<galeria> albumList) {
        this.galerias=albumList;
        this.context=Context;

    }

    @Override
    public AdapterA.AdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, parent, false);
        return new AdapterA.AdapterHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterHolder holder, int position) {
        galeria galeria = galerias.get(position);
        holder.text.setText(galeria.getNombre());
        holder.image.setImageResource(galeria.getFoto());

    }

    @Override
    public int getItemCount() {
        return galerias.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
