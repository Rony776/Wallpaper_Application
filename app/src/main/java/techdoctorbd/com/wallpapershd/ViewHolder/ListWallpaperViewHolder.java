package techdoctorbd.com.wallpapershd.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import techdoctorbd.com.wallpapershd.Interface.ItemClickListener;
import techdoctorbd.com.wallpapershd.R;

public class ListWallpaperViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ItemClickListener itemClickListener;

    public ImageView wallpaper;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public ListWallpaperViewHolder(@NonNull View itemView) {
        super(itemView);
        wallpaper = (ImageView)itemView.findViewById(R.id.image_list_wallpaper);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        itemClickListener.onClick(v,getAdapterPosition());

    }
}
