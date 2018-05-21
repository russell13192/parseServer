/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {
    TextView changeSignupModeTxtVw;
    Boolean signUpModeActive = true;
    EditText passwordExtTxt;

    public void showUserList() {
        Intent intent = new Intent(getApplicationContext(), UserListActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
            signUp(view);
        }

        return false;
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.changeSignUpMModeTxtVw) {
            Button signLoginBtn = (Button) findViewById(R.id.signUpBtn);
            if (signUpModeActive) {

                signUpModeActive = false;

                signLoginBtn.setText("Login");
                changeSignupModeTxtVw.setText("Sign Up");
            } else {
                signUpModeActive = true;
                signLoginBtn.setText("Sign Up");
                changeSignupModeTxtVw.setText("Login");
            }
        } else if (view.getId() == R.id.backgroudRelLayOut || view.getId() == R.id.logoImageVw) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void signUp(View view) {
        EditText usernameEdtTxt = (EditText) findViewById(R.id.usernameEdTxt);


        if (usernameEdtTxt.getText().toString().matches( "") || passwordExtTxt.getText().toString().matches("")) {
            Toast.makeText(this, "A username and password are required.", Toast.LENGTH_SHORT).show();
        } else {
            if (signUpModeActive) {

                ParseUser user = new ParseUser();
                user.setUsername(usernameEdtTxt.getText().toString());
                user.setPassword(passwordExtTxt.getText().toString());

                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Log.i("Sign Up", "Successful");

                            showUserList();
                        } else {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                ParseUser.logInInBackground(usernameEdtTxt.getText().toString(), passwordExtTxt.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user != null) {
                            Log.i("Sign Up", "Login Successful");
                            showUserList();
                        } else {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

 

      changeSignupModeTxtVw = (TextView) findViewById(R.id.changeSignUpMModeTxtVw);

      changeSignupModeTxtVw.setOnClickListener(this);

      RelativeLayout backgroundRelativeLayout = (RelativeLayout) findViewById(R.id.backgroudRelLayOut);

      ImageView logoImageView = (ImageView) findViewById(R.id.logoImageVw);

      backgroundRelativeLayout.setOnClickListener(this);

      logoImageView.setOnClickListener(this);

      passwordExtTxt = (EditText) findViewById(R.id.passwordEdTxt);

      passwordExtTxt.setOnKeyListener(this);

      if (ParseUser.getCurrentUser() != null) {
          showUserList();
      }

//    ParseUser.logOut();
//
//    if (ParseUser.getCurrentUser() != null) {
//        Log.i("currentUser", "User logged in " + ParseUser.getCurrentUser().getUsername());
//    } else {
//        Log.i("currentUser", "User not logged in");
//    }


//
//    ParseUser.logInInBackground("russell13192", "sanyu13192", new LogInCallback() {
//        @Override
//        public void done(ParseUser user, ParseException e) {
//            if (user != null) {
//                Log.i("Login", "Successful");
//            } else {
//                Log.i("Login", "Failed: " + e.toString());
//            }
//        }
//    });

//
//    ParseUser user = new ParseUser();
//
//    user.setUsername("russell13192");
//    user.setPassword("sanyu13192");
//
//    user.signUpInBackground(new SignUpCallback() {
//        @Override
//        public void done(ParseException e) {
//            if (e == null) {
//                Log.i("Sign Up", "Successful");
//            } else {
//                Log.i("Sign Up", "Failed");
//            }
//        }
//    });

//    ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("Score");
//
//    parseQuery.whereGreaterThan("score", 80);
//
//    parseQuery.findInBackground(new FindCallback<ParseObject>() {
//        @Override
//        public void done(List<ParseObject> objects, ParseException e) {
//            if (e == null && objects != null) {
//                for (ParseObject parseObject : objects) {
//                    parseObject.put("score", parseObject.getInt("score") + 50);
//                    parseObject.saveInBackground();
//                }
//            }
//        }
//    });

//    parseQuery.whereEqualTo("username", "george");
//    parseQuery.setLimit(1);
//
//    parseQuery.findInBackground(new FindCallback<ParseObject>() {
//      @Override
//      public void done(List<ParseObject> objects, ParseException e) {
//        if (e == null) {
//          Log.i("findInBackground", "Retrieved" + objects.size() + "objects");
//
//          if (objects.size() > 0) {
//            for (ParseObject parseObject : objects) {
//              Log.i("findInBackgroundResult", parseObject.getString("username"));
//            }
//          }
//        }
//      }
//    });

//
//    ParseObject score = new ParseObject("Score");
//    score.put("username", "george");
//    score.put("score", 95);
//    score.saveInBackground(new SaveCallback() {
//      @Override
//      public void done(ParseException e) {
//        if (e == null) {
//          Log.i("SaveInBackground", "Successful");
//        } else {
//          Log.i("SaveInBackground", "Failed. Error: " + e.toString());
//        }
//      }
//    });

//    ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");
//
//    query.getInBackground("ul60eizavk", new GetCallback<ParseObject>() {
//      @Override
//      public void done(ParseObject object, ParseException e) {
//        if (e == null && object != null) {
//
//          object.put("score", 200);
//          object.saveInBackground();
//          Log.i("ObjectValue", object.getString("username"));
//          Log.i("ObjectValue", Integer.toString(object.getInt("score")));
//        }
//      }
//    });


    
    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }



}