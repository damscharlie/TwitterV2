package nl.saxion.ap.twitterv2.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import nl.saxion.ap.twitterv2.R;

/**
 * Created by MindR on 25-Jun-16.
 */
public class LoginActivity extends Activity {

   /**
    * 1. make auth class
    * 2.
    * 3.
    **/

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_login);

      Button loginButton = (Button) findViewById(R.id.activity_login_loginBT);
      loginButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            startActivity(new Intent(LoginActivity.this, AuthActivity.class));
            Toast.makeText(getApplicationContext(), "Loading", Toast.LENGTH_LONG).show();
            finish();
         }
      });
   }
}
