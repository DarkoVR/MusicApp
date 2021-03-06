/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.marco.musicapp.activity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marco.musicapp.R;
import com.example.marco.musicapp.api.model.ShoppingCart;
import com.example.marco.musicapp.fragment.AccountContentFragment;
import com.example.marco.musicapp.fragment.AlbumContentFragment;
import com.example.marco.musicapp.fragment.CartContentFragment;
import com.example.marco.musicapp.fragment.DiscountContentFragment;
import com.example.marco.musicapp.fragment.EmptyContentFragment;
import com.example.marco.musicapp.fragment.LoginContentFragment;
import com.example.marco.musicapp.fragment.MainContentFragment;
import com.example.marco.musicapp.fragment.OrderContentFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Provides UI for the main screen.
 */
public class MainActivity extends AppCompatActivity {
    private int count = 0;
    private DrawerLayout mDrawerLayout;
    NavigationView navigationView;
    String token,user;
    int idDetail,idPayment,idUser,idDiscount,percentage_value;
    ShoppingCart shoppingCart;
    List<ShoppingCart> shoppingCartList = new ArrayList<ShoppingCart>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Adding Toolbar to Main screen
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final TextView txtAccount = findViewById(R.id.txtAccount);
        setSupportActionBar(toolbar);
        // Setting ViewPager for each Tabs
        if(savedInstanceState == null)
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new MainContentFragment())
                    .commit();
        // Create Navigation drawer and inlfate layout
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        // Adding menu icon to Toolbar
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            VectorDrawableCompat indicator
                    = VectorDrawableCompat.create(getResources(), R.drawable.ic_menu, getTheme());
            indicator.setTint(ResourcesCompat.getColor(getResources(),R.color.white,getTheme()));
            supportActionBar.setHomeAsUpIndicator(indicator);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        // Set behavior of Navigation drawer
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    // This method will trigger on item Click of navigation menu
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // Set item in checked state
                        menuItem.setChecked(true);
                        if (menuItem.getTitle().equals("Inicio")){
                            Toast.makeText(getApplicationContext(), menuItem.getTitle(), Toast.LENGTH_SHORT).show();

                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_container, new MainContentFragment())
                                    .commit();

                        }else if (menuItem.getTitle().equals("Albums")){
                            Toast.makeText(getApplicationContext(), menuItem.getTitle(), Toast.LENGTH_SHORT).show();

                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_container, new AlbumContentFragment())
                                    .commit();

                        }else if (menuItem.getTitle().equals("Para ti")){
                            Toast.makeText(getApplicationContext(), menuItem.getTitle(), Toast.LENGTH_SHORT).show();

                        }else if (menuItem.getTitle().equals("Perfil")){
                            Toast.makeText(getApplicationContext(), menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                            if (getToken()!=null){
                                Toast.makeText(getApplicationContext(), menuItem.getTitle(), Toast.LENGTH_SHORT).show();

                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.fragment_container, new AccountContentFragment())
                                        .commit();
                            }else{
                                Snackbar.make(getCurrentFocus(), "No has iniciado sesión chavo", Snackbar.LENGTH_LONG).show();
                            }

                        }else if (menuItem.getTitle().equals("Inicia sesión")){
                            Toast.makeText(getApplicationContext(), menuItem.getTitle(), Toast.LENGTH_SHORT).show();

                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_container, new LoginContentFragment())
                                    .commit();

                        }if (menuItem.getTitle().equals("Cupones")){
                            Toast.makeText(getApplicationContext(), menuItem.getTitle(), Toast.LENGTH_SHORT).show();

                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_container, new DiscountContentFragment())
                                    .commit();

                        }if (menuItem.getTitle().equals("Ordenes")){
                            Toast.makeText(getApplicationContext(), menuItem.getTitle(), Toast.LENGTH_SHORT).show();

                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_container, new OrderContentFragment())
                                    .commit();

                        }if (menuItem.getTitle().equals("Cerrar sesión")){
                            Toast.makeText(getApplicationContext(), menuItem.getTitle(), Toast.LENGTH_SHORT).show();

                            setToken("");
                            setUser("");
                            ChangeUserAccount("Invitado");
                            removeMenuItems();

                            Snackbar.make(getCurrentFocus(),"¡Sesión Cerrada!",Snackbar.LENGTH_LONG).show();

                        }else{
                            Toast.makeText(getApplicationContext(), menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        }

                        // TODO: handle navigation

                        // Closing drawer on item click
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
        // Adding Floating Action Button to bottom right of main view
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Información sin sentido!", Snackbar.LENGTH_LONG).show();
                //doIncrease();
                //addMenuItems();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // Action to put the cart vector asset and send the params to the metod buildCounterDrawable
        MenuItem menuItem = menu.findItem(R.id.testAction);
        menuItem.setIcon(buildCounterDrawable(count, R.drawable.ic_action_cart_white));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        } else if (id == R.id.testAction) {
            Toast.makeText(getApplicationContext(), "Carrito", Toast.LENGTH_SHORT).show();

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new CartContentFragment())
                    .commit();

        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Void to put the number in the cart vector asset
     * @param count
     * @param backgroundImageId
     * @return
     */
    public Drawable buildCounterDrawable(int count, int backgroundImageId) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.counter_menuitem_layout, null);
        view.setBackgroundResource(backgroundImageId);

        if (count == 0) {
            View counterTextPanel = view.findViewById(R.id.counterValuePanel);
            counterTextPanel.setVisibility(View.GONE);
        } else {
            TextView textView = (TextView) view.findViewById(R.id.count);
            textView.setText("" + count);
        }

        view.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        return new BitmapDrawable(getResources(), bitmap);
    }

    /**
     * Void to increase the number int the cart vector asset
     */
    @SuppressLint("RestrictedApi")
    public void doIncrease() {
        count++;
        invalidateOptionsMenu();
    }

    /**
     * Void to decrease the number in the cart vector asset
     */
    @SuppressLint("RestrictedApi")
    public void doDecrease() {
        if (count>0){
            count--;
            invalidateOptionsMenu();
        }else{
            Toast toast = Toast.makeText(getApplicationContext(), "No tienes elementos en tu carrito", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /**
     * Void to decrease the number in the cart vector asset
     */
    @SuppressLint("RestrictedApi")
    public void doClear() {
        count=0;
        invalidateOptionsMenu();
    }

    @SuppressLint("ResourceAsColor")
    public void ChangeUserAccount(String name){
        View hView =  navigationView.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.txtAccount);
        nav_user.setText(name);

    }

    @Override
    public void onBackPressed() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new MainContentFragment())
                .commit();
    }

    public void addMenuItems(){
        final Menu menu = navigationView.getMenu();
        final SubMenu subMenu = menu.addSubMenu("Cuenta");
        subMenu.add("Perfil");
        subMenu.add("Cupones");
        subMenu.add("Ordenes");

        navigationView.getMenu().getItem(3).setTitle("Cerrar sesión");
    }

    public void removeMenuItems(){
        navigationView.getMenu().removeGroup(0);
        navigationView.getMenu().add("Inicia sesión");
        navigationView.getMenu().getItem(3).setIcon(R.drawable.ic_account);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getIdDetail() {
        return idDetail;
    }

    public void setIdDetail(int idDetail) {
        this.idDetail = idDetail;
    }

    public List<ShoppingCart> getShoppingCartList() {
        return shoppingCartList;
    }

    public void setShoppingCartList(List<ShoppingCart> shoppingCartListFK) {
        this.shoppingCartList = shoppingCartList;
        //shoppingCartList.add(shoppingCartListFK.get(0));
    }

    public int getIdPayment() {
        return idPayment;
    }

    public void setIdPayment(int idPayment) {
        this.idPayment = idPayment;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdDiscount() {
        return idDiscount;
    }

    public void setIdDiscount(int idDiscount) {
        this.idDiscount = idDiscount;
    }

    public int getPercentage_value() {
        return percentage_value;
    }

    public void setPercentage_value(int percentage_value) {
        this.percentage_value = percentage_value;
    }
}
