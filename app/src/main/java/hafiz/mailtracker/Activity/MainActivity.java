package hafiz.mailtracker.Activity;

import android.app.ActionBar;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import hafiz.mailtracker.Fragments.Admin.AdminMain;
import hafiz.mailtracker.Fragments.MailReceiver.MailReceiver_main;
import hafiz.mailtracker.Fragments.User.UserMain;
import hafiz.mailtracker.Model.User_data;
import hafiz.mailtracker.R;
import hafiz.mailtracker.services.MailChecker;
import hafiz.mailtracker.services.UserMailChecker;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        final NavigationView nv = findViewById(R.id.nav_view);
        String email = "";
        if (user != null) {
            email = user.getEmail();
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open_Drawer, R.string.close_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        if (user == null) {
            Intent intent = new Intent(this, Authorization.class);
            startActivity(intent);
        } else if(email.equals("admin@admin.com")){
            MenuItem menu = nv.getMenu().findItem(R.id.nav_item_two);
            menu.setTitle("Logout");
            LinearLayout iv = findViewById(R.id.history_icon);
            iv.setVisibility(View.INVISIBLE);
            nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    int id = menuItem.getItemId();
                    switch (id) {
                        case R.id.nav_item_one:
                            nextFragment(new AdminMain(),0);
                            drawer.closeDrawers();
                            break;
                        case R.id.nav_item_two:
                            MenuItem menu = nv.getMenu().findItem(R.id.nav_item_two);
                            String text = menu.getTitle().toString();
                            if (text.equals("Login")) {
                                Intent intent = new Intent(MainActivity.this, Authorization.class);
                                startActivity(intent);
                                drawer.closeDrawers();
                            } else {
                                TextView tv = nv.getHeaderView(0).findViewById(R.id.nav_header_textView);
                                tv.setText("");
                                mAuth.signOut();
                                StopAllServices();
                                Intent intent = new Intent(MainActivity.this, Authorization.class);
                                startActivity(intent);
                            }
                            break;
                        default:
                            return true;
                    }
                    return true;
                }
            });
            nextFragment(new AdminMain(),0);
        } else if(email.equals("security@admin.com")){
            StopAllServices();
            MenuItem menu = nv.getMenu().findItem(R.id.nav_item_two);
            menu.setTitle("Logout");
            LinearLayout iv = findViewById(R.id.history_icon);
            iv.setVisibility(View.INVISIBLE);
            nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    int id = menuItem.getItemId();
                    switch (id) {
                        case R.id.nav_item_one:
                            nextFragment(new MailReceiver_main(),0);
                            drawer.closeDrawers();
                            break;
                        case R.id.nav_item_two:
                            MenuItem menu = nv.getMenu().findItem(R.id.nav_item_two);
                            String text = menu.getTitle().toString();
                            if (text.equals("Login")) {
                                Intent intent = new Intent(MainActivity.this, Authorization.class);
                                startActivity(intent);
                                drawer.closeDrawers();
                            } else {
                                TextView tv = nv.getHeaderView(0).findViewById(R.id.nav_header_textView);
                                tv.setText("");
                                mAuth.signOut();
                                
                                Intent intent = new Intent(MainActivity.this, Authorization.class);
                                startActivity(intent);
                            }
                            break;
                        default:
                            return true;
                    }
                    return true;
                }
            });
            nextFragment(new MailReceiver_main(),0);
        }
        else {
            FirebaseDatabase db;
            db = FirebaseDatabase.getInstance();
            DatabaseReference myref = db.getReference("user_data");
            String UID = user.getUid();
            myref.child(UID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User_data data = dataSnapshot.getValue(User_data.class);
                    View header = nv.getHeaderView(0);
                    TextView uname = header.findViewById(R.id.nav_header_textView);
                    assert data != null;
                    uname.setText(data.getUsername());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    int id = menuItem.getItemId();
                    switch (id) {
                        case R.id.nav_item_one:
                            nextFragment(new UserMain(),0);
                            drawer.closeDrawers();
                            break;
                        case R.id.nav_item_two:
                            MenuItem menu = nv.getMenu().findItem(R.id.nav_item_two);
                            String text = menu.getTitle().toString();
                            if (text.equals("Login")) {
                                Intent intent = new Intent(MainActivity.this, Authorization.class);
                                startActivity(intent);
                                drawer.closeDrawers();
                            } else {
                                TextView tv = nv.getHeaderView(0).findViewById(R.id.nav_header_textView);
                                tv.setText("");
                                mAuth.signOut();
                                StopAllServices();
                                Intent intent = new Intent(MainActivity.this, Authorization.class);
                                startActivity(intent);
                            }
                            break;
                        default:
                            return true;
                    }
                    return true;
                }
            });
            MenuItem menu = nv.getMenu().findItem(R.id.nav_item_two);
            menu.setTitle("Logout");
            nextFragment(new UserMain(),0);
        }


    }
    public void StopAllServices(){
        Intent services = new Intent(MainActivity.this, MailChecker.class);
        stopService(services);
        Intent services2 = new Intent(MainActivity.this, UserMailChecker.class);
        stopService(services2);

    }
    public void nextFragment(Fragment fragment, int marker){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.do_nothing,R.anim.fade_in);
        if(marker >0){ ft.addToBackStack("back pressed"); }
        ft.replace(R.id.fragment_container,fragment);
        ft.commit();
    }
}
