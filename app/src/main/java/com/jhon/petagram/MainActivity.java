package com.jhon.petagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.jhon.petagram.adaptadores.PageAdapter;
import com.jhon.petagram.fragment.FragmentReciclerview;
import com.jhon.petagram.fragment.InstagramFragment;
import com.jhon.petagram.fragment.MascotaFavoritaFragment;
import com.jhon.petagram.menus.ActivityAbout;
import com.jhon.petagram.menus.ActivityContacto;
import com.jhon.petagram.menus.ActivityNotificaciones;
import com.jhon.petagram.resApi.ConstantesApi;
import com.jhon.petagram.resApi.JsonKeys;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String msg;
    String token;
    private static final String TAG = "MainActivity";
    //  private ImageButton favoritoimagen;
 //   private TextView like1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        tootb.setLogo(R.drawable.pataperro);
        toolbar     = findViewById(R.id.toolbarfr);
        tabLayout   = findViewById(R.id.tablayout);
        viewPager   =findViewById(R.id.viewpager);
        Toolbar tootb = findViewById(R.id.actionbar);
        setSupportActionBar(tootb);
        setupviewpager();

        if (toolbar != null){
            setSupportActionBar(toolbar);
        }
    }
    private ArrayList<Fragment> agregarfragments (){
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new FragmentReciclerview());
        fragments.add(new InstagramFragment());
        return fragments;
    }
   public void setupviewpager(){
        viewPager.setAdapter(new PageAdapter(getSupportFragmentManager(),agregarfragments()));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_baseline_house_24);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_baseline_pets_24);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_opciones,menu);
        return (true);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.Favoritos:
                Intent intent1 = new Intent(MainActivity.this, MisContactos.class);
                startActivity(intent1);
                break;
           case R.id.About:
                Intent intent = new Intent(this, ActivityAbout.class);
                startActivity(intent);
                break;
            case R.id.Contacto:

                Intent intent2 = new Intent(this, ActivityContacto.class);
                startActivity(intent2);
                break;
case R.id.Notificaciones:

                notificacion();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void notificacion(){
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }
                        // Get new Instance ID token
                        token = task.getResult().getToken();
                        // Log and toast
                        msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, msg);
                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), ActivityNotificaciones.class);
                        i.putExtra ("token",msg );
                        i.putExtra("idinstagram", ConstantesApi.USER_ID);
                        startActivity(i);
                    }
                });

    }

}