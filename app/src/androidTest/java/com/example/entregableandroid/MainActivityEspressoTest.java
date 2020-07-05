package com.example.entregableandroid;


import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.entregableandroid.Vista.MostrarResultadoBusqueda.ProductoAdapter;
import com.example.entregableandroid.utils.EspressoIdlingTask;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

/***************************************************************************************************
 *
 *
 *
 *   Para que esto funcione es necesario dehabilitar todas las animaciones en el modo desarrollador
 *
 *
 *
 *
 *************************************************************************************************/
@RunWith(AndroidJUnit4.class)
public class MainActivityEspressoTest  {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);


    @Before
    public void registerIdlingResource(){
        IdlingRegistry.getInstance().register(EspressoIdlingTask.countingIdlingResource);
    }

    @After
    public void unregisterIdlingResource(){
        IdlingRegistry.getInstance().unregister(EspressoIdlingTask.countingIdlingResource);
    }

    @Test
    public void test2(){
        onView(withId(R.id.RecyclerViewListaProductos)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.MainFragment)).perform(swipeLeft());
        onView(withId(R.id.MainFragment)).perform(swipeLeft());
        onView(withId(R.id.MainFragment)).perform(swipeLeft());
    }


    @Test
    public void test3(){
        onView(withId(R.id.MainFragment))
                .perform(swipeLeft());
    }

    @Test
    public void test4(){
        onView(withId(R.id.MainFragment))
                .perform(swipeRight());
    }


    @Test
    public void test5(){
        onView(withId(R.id.MainFragment))
                .perform(swipeUp());
    }


}
