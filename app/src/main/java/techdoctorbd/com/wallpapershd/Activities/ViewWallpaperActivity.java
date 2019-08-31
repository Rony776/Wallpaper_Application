package techdoctorbd.com.wallpapershd.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.util.UUID;

import dmax.dialog.SpotsDialog;
import techdoctorbd.com.wallpapershd.Common.Common;
import techdoctorbd.com.wallpapershd.Helper.DownloadHelper;
import techdoctorbd.com.wallpapershd.R;


public class ViewWallpaperActivity extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton floatingActionButton,downloadButton;
    ImageView imageView;
    CoordinatorLayout rootlayout;
    TextView description;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case Common.PERMISSION_REQUEST_CODE:
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    AlertDialog dialog = new SpotsDialog(ViewWallpaperActivity.this);
                    dialog.setMessage("Please wait");
                    dialog.show();
                    String fileName = UUID.randomUUID().toString()+".png";
                    Picasso.with(getBaseContext()).load(Common.Selected_Wallpaper.getImageLink())
                            .into(new DownloadHelper(getBaseContext(),dialog,getApplicationContext().getContentResolver(),fileName,"This Image from wallpapers HD"));
                }
                else
                {
                    Toast.makeText(this, "You need accept this permission to download wallpaper", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    private Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
            try
            {
                wallpaperManager.setBitmap(bitmap);
                Snackbar.make(rootlayout,"Wallpaper Set Successfully",Snackbar.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_wallpaper);

        Toolbar toolbar = findViewById(R.id.toolbar_view_wallpaper);
        setSupportActionBar(toolbar);
        rootlayout = findViewById(R.id.rootlayout);
        description = findViewById(R.id.wallpaper_description);
        collapsingToolbarLayout = findViewById(R.id.collapsing_layout_WP);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);

        collapsingToolbarLayout.setTitle(Common.SELECTED_CATEGORY);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageView =findViewById(R.id.image_view_full);
        Picasso.with(this)
                .load(Common.Selected_Wallpaper.getImageLink())
                .into(imageView);
        description.setText(Common.Selected_Wallpaper.getDescription());

        floatingActionButton = findViewById(R.id.floating_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.with(getApplicationContext())
                        .load(Common.Selected_Wallpaper.getImageLink())
                        .into(target);
            }
        });

        downloadButton = findViewById(R.id.floating_button_download);
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                if (ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission
                        .WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},Common.PERMISSION_REQUEST_CODE);
                }
                else
                {
                    AlertDialog dialog = new SpotsDialog(ViewWallpaperActivity.this);
                    dialog.show();
                    dialog.setMessage("Please wait");

                    String fileName = UUID.randomUUID().toString()+".png";
                    Picasso.with(getBaseContext()).load(Common.Selected_Wallpaper.getImageLink())
                            .into(new DownloadHelper(getBaseContext(),dialog,getApplicationContext().getContentResolver(),fileName,"This Image from wallpapers HD"));
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        Picasso.with(getApplicationContext()).cancelRequest(target);
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        finish();
        return super.onOptionsItemSelected(item);
    }
}
