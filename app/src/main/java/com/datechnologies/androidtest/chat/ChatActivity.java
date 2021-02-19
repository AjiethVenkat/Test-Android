package com.datechnologies.androidtest.chat;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;


import com.android.volley.toolbox.Volley;
import com.datechnologies.androidtest.MainActivity;
import com.datechnologies.androidtest.R;
import com.datechnologies.androidtest.api.ChatLogMessageModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Screen that displays a list of chats from a chat log.
 */
public class ChatActivity extends AppCompatActivity {

    //==============================================================================================
    // Class Properties
    //==============================================================================================

    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;

    private final String JSON_URL = "http://dev.rapptrlabs.com/Tests/scripts/chat_log.php";
    private RequestQueue requestQueue;


    //==============================================================================================
    // Static Class Methods
    //==============================================================================================

    public static void start(Context context) {
        Intent starter = new Intent(context, ChatActivity.class);
        context.startActivity(starter);
    }

    //==============================================================================================
    // Lifecycle Methods
    //==============================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        setTitle("Chat");
        requestQueue = Volley.newRequestQueue(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        chatAdapter = new ChatAdapter();

        recyclerView.setAdapter(chatAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL,
                false));


        //==============================================================================================
        // JSON get method to retrieve chat data using volley
        //==============================================================================================

        StringRequest request = new StringRequest(Request.Method.GET, JSON_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                List<ChatLogMessageModel> tempList = new ArrayList<>();

                try {
                    /* The JSON response is treated as object */
                    JSONObject json_Object = new JSONObject(response);
                    JSONArray user_ArrayObject = json_Object.getJSONArray("data");

                    for (int i = 0; i < user_ArrayObject.length(); i++) {
                        JSONObject single_User_Object = user_ArrayObject.getJSONObject(i);

                        ChatLogMessageModel chatLogMessageModel = new ChatLogMessageModel();

                        /* Did not retrieve id from JSON since it was not required */
                        chatLogMessageModel.message = single_User_Object.getString("message");
                        chatLogMessageModel.username = single_User_Object.getString("name");
                        chatLogMessageModel.avatarUrl = single_User_Object.getString("avatar_url");

                        tempList.add(chatLogMessageModel);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                /* Sending the data to adapter to populate each cell */
                chatAdapter.setChatLogMessageModelList(tempList);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue = Volley.newRequestQueue(ChatActivity.this);
        requestQueue.add(request);

//        chatLogMessageModel.message = "This is test data. Please retrieve real data.";

//        tempList.add(chatLogMessageModel);
//        tempList.add(chatLogMessageModel);
//        tempList.add(chatLogMessageModel);
//        tempList.add(chatLogMessageModel);
//        tempList.add(chatLogMessageModel);
//        tempList.add(chatLogMessageModel);
//        tempList.add(chatLogMessageModel);


        // TODO: Make the UI look like it does in the mock-up. Allow for horizontal screen rotation.

        // TODO: Retrieve the chat data from http://dev.rapptrlabs.com/Tests/scripts/chat_log.php
        // TODO: Parse this chat data from JSON into ChatLogMessageModel and display it.
    }

    /* Call back the stack on AppBar back button */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
