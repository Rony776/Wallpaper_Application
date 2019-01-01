package techdoctorbd.com.wallpapershd;

import android.content.Intent;
import android.graphics.ColorSpace;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import techdoctorbd.com.wallpapershd.Common.Common;
import techdoctorbd.com.wallpapershd.Interface.ItemClickListener;
import techdoctorbd.com.wallpapershd.Model.WallpaperItem;
import techdoctorbd.com.wallpapershd.ViewHolder.ListWallpaperViewHolder;

public class ListWallpaperActivity extends AppCompatActivity {

    Query query;
    FirebaseRecyclerOptions<WallpaperItem> options;
    FirebaseRecyclerAdapter<WallpaperItem,ListWallpaperViewHolder> adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_wallpaper);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_wallpaper_list);
        toolbar.setTitle(Common.SELECTED_CATEGORY);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_wallpaper_list);
        recyclerView.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);

        LoadBackGroundList();
    }

    private void LoadBackGroundList() {
        query = FirebaseDatabase.getInstance().getReference(Common.STR_WALLPAPER)
        .orderByChild("CategoryID").equalTo(Common.SELECTED_CATEGORY_ID);

        options = new FirebaseRecyclerOptions.Builder<WallpaperItem>().setQuery(query,WallpaperItem.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<WallpaperItem, ListWallpaperViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ListWallpaperViewHolder holder, int position, @NonNull final WallpaperItem model) {

                Picasso.with(getBaseContext())
                        .load(model.getImageLink())
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .into(holder.wallpaper, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {

                                Picasso.with(getBaseContext())
                                        .load(model.getImageLink())
                                        .error(R.drawable.ic_rss_feed_black_24dp)
                                        .into(holder.wallpaper, new Callback() {
                                            @Override
                                            public void onSuccess() {

                                            }

                                            @Override
                                            public void onError() {
                                                Log.e("Connection Error","Couldn't fetch images");
                                            }
                                        });

                            }
                        });
                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {

                        Intent intent = new Intent(ListWallpaperActivity.this,ViewWallpaperActivity.class);
                        Common.Selected_Wallpaper = model;
                        startActivity(intent);

                    }
                });
            }

            @NonNull
            @Override
            public ListWallpaperViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View item_view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_wallpaper_item,viewGroup,false);

                int height = viewGroup.getMeasuredHeight()/2;
                item_view.setMinimumHeight(height);


                return new ListWallpaperViewHolder(item_view);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (adapter != null)
            adapter.startListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null)
            adapter.startListening();
    }

    @Override
    protected void onStop() {
        if (adapter != null)
            adapter.stopListening();
        super.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home);
        finish();
        return super.onOptionsItemSelected(item);
    }
}
