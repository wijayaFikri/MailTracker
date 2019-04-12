package hafiz.mailtracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            Intent intent = new Intent(this, Authorization.class);
            startActivity(intent);
        } else {
            NavigationView nv = findViewById(R.id.nav_view);
            MenuItem menu = nv.getMenu().findItem(R.id.nav_item_seven);
            menu.setTitle("Logout");
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open_Drawer, R.string.close_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        final NavigationView nv = findViewById(R.id.nav_view);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.nav_item_one:
                        drawer.closeDrawers();
                        break;
                    case R.id.nav_item_two:
                        Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                        drawer.closeDrawers();
                        break;
                    case R.id.nav_item_seven:
                        MenuItem menu = nv.getMenu().findItem(R.id.nav_item_seven);
                        String text = menu.getTitle().toString();
                        if (text.equals("Login")) {
                            Intent intent = new Intent(MainActivity.this, Authorization.class);
                            startActivity(intent);
                            drawer.closeDrawers();
                        } else {
                            TextView tv = nv.getHeaderView(0).findViewById(R.id.nav_header_textView);
                            tv.setText("");
                            mAuth.signOut();
                            menu.setTitle("Login");
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
    }
}
