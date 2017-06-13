package konox.actividad3;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by Daniel on 05/06/2017.
 */

public class AdapterA extends RecyclerView.Adapter<AdapterA.AdapterHolder> {
    public List<galeria> galerias;
    public Context context;

    public class AdapterHolder extends RecyclerView.ViewHolder {
        TextView txtNombre;
        TextView txtDificultad;
        public ImageView image;

        public AdapterHolder(View itemView) {
            super(itemView);
            txtNombre = (TextView) itemView.findViewById(R.id.txtNombreSpot);
            txtDificultad  = (TextView) itemView.findViewById(R.id.txtDificultad);
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
        holder.txtNombre.setText(galeria.getNombre());
        holder.txtDificultad.setText(galeria.getDificultad());
        //holder.image.setImageResource(galeria.getFoto());
        Picasso.with(context).load(galeria.getAndroid_image_url()).transform(new RoundedCornersTransformation(10, 10)).rotate(90).into(holder.image);

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
