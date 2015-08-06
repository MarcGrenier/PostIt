package com.marcgrenier.postit;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.app.Activity;
import android.graphics.Color;

public class MainActivity extends Activity {

	private  PostView m;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        m = new PostView(this);
        m.setBackgroundColor(Color.BLACK);
        
        for (int i = 0; i < 100; i++) {
			m.addPost("Salut le monde");
		}
        
        setContentView(m);
        
    }
    
}
