package com.classified.upuse;

import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.classified.upuse.R;
import com.classified.upuse.APIWork.ApiWork;
import com.classified.upuse.Adapters.ChatAdapter;
import com.classified.upuse.Constants.api_baseurl;
import com.classified.upuse.Models.AuthResponse;
import com.classified.upuse.Models.chatModel;
import com.classified.upuse.ViewModels.singleChatViewModel;
import com.classified.upuse.databinding.ActivityChatBinding;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class chatActivity extends AppCompatActivity implements TextWatcher, PopupMenu.OnMenuItemClickListener {
    private ActivityChatBinding binding;
    private WebSocket webSocket;
    private String user_id;
    private String product_id;
    private String person_id;
    final int IMAGE_PICK_CODE = 1000;
    final int PERMISSION_CODE = 1001;
    private ChatAdapter chatAdapter;
    private singleChatViewModel viewModel;
    private String receiver_img;
    private api_baseurl baseurl = new api_baseurl();
    private List<JSONObject> previousMessages = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        this.getSupportActionBar().hide();
        int nightModeFlags =
                getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        View decorView = getWindow().getDecorView();
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    getWindow().getDecorView().getWindowInsetsController().
                            setSystemBarsAppearance(0, APPEARANCE_LIGHT_STATUS_BARS);
                }
                break;

            case Configuration.UI_MODE_NIGHT_NO:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                }
                break;

            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    getWindow().getDecorView().getWindowInsetsController().
                            setSystemBarsAppearance(0, APPEARANCE_LIGHT_STATUS_BARS);
                }
                break;
        }

//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        ManageData();
    }

    private void playsound() {
        MediaPlayer mediaPlayer=MediaPlayer.create(this, R.raw.msgsound);
        mediaPlayer.setVolume(0.5f,0.5f);
        mediaPlayer.start();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.stop();
                //mediaPlayer.release();

            }
        });
    }


    private void recplaysound() {
        MediaPlayer mediaPlayer=MediaPlayer.create(this, R.raw.receive_msgsound);
        mediaPlayer.setVolume(0.5f,0.5f);
        mediaPlayer.start();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.stop();
                //mediaPlayer.release();

            }
        });
    }
    private void viewfuncs() {

        binding.productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                if (!user_id.equals(viewModel.getChatData().getValue().getPostedby_id())) {
                    intent = new Intent(chatActivity.this, Ad_userActivity.class);

                } else {
                    // change this to same user activity
                    intent = new Intent(chatActivity.this, Ad_posterActivity.class);
                }

                //intent.putExtra("cat_name", category_name);
                intent.putExtra("ad_id", product_id);
                intent.putExtra("product_name", viewModel.getChatData().getValue().getProduct_name());

                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

            }
        });
        binding.productTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                if (!user_id.equals(viewModel.getChatData().getValue().getPostedby_id())) {
                    intent = new Intent(chatActivity.this, Ad_userActivity.class);

                } else {
                    // change this to same user activity
                    intent = new Intent(chatActivity.this, Ad_posterActivity.class);
                }

                //intent.putExtra("cat_name", category_name);
                intent.putExtra("ad_id", product_id);
                intent.putExtra("product_name", viewModel.getChatData().getValue().getProduct_name());

                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

            }
        });
        binding.scrolldown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.chatsRec.smoothScrollToPosition(chatAdapter.getItemCount() - 1);
                binding.scrolldown.setVisibility(View.INVISIBLE);
            }
        });
        binding.sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject localMsg = new JSONObject();
                try {
                    Calendar calendar = Calendar.getInstance();
                    Date date=calendar.getTime();
                    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
                    String time = sdf.format(date);
                    localMsg.put("time", time);
                    localMsg.put("user_id", user_id);
                    localMsg.put("person_id", person_id);
                    localMsg.put("product_id",product_id);
                    localMsg.put("message", binding.msgTxt.getText().toString());
                    webSocket.send(localMsg.toString());
                    Log.d("messagehere",localMsg.toString());
                    localMsg.put("isSent", "yes");
                    chatAdapter.addItem(localMsg);
                    playsound();
                    resetmessageEdit();
                    binding.chatsRec.smoothScrollToPosition(chatAdapter.getItemCount() - 1);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        binding.pickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCropActivity();
            }
        });

        binding.menuBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(chatActivity.this, v);
                popupMenu.setOnMenuItemClickListener(chatActivity.this);
                popupMenu.inflate(R.menu.chatoptionmenu);
                MenuItem item = popupMenu.getMenu().findItem(R.id.delete);
                SpannableString s = new SpannableString("Delete");
                item.setTitle(s);
                s.setSpan(new ForegroundColorSpan(Color.parseColor("#F24747")), 0, s.length(), 0);
                popupMenu.show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void ManageData() {
        Intent intent = getIntent();
//        user_id="2";
//        person_id="3";
//        product_id="2";
        user_id = intent.getStringExtra("user_id");
        person_id = intent.getStringExtra("person_id");
        product_id = intent.getStringExtra("product_id");
        viewModel = new ViewModelProvider(chatActivity.this).get(singleChatViewModel.class);
        viewModel.initwork(user_id, product_id, person_id);
        binding.scrolldown.setVisibility(View.INVISIBLE);

        chatAdapter = new ChatAdapter(chatActivity.this, previousMessages
                , receiver_img);

        LinearLayoutManager llm = new LinearLayoutManager(chatActivity.this);
        llm.setOrientation(RecyclerView.VERTICAL);
        binding.chatsRec.setLayoutManager(llm);
        binding.chatsRec.setAdapter(chatAdapter);
        binding.chatsRec.scrollToPosition(chatAdapter.getItemCount() - 1);
       // llm.setSmoothScrollbarEnabled(true);
        binding.chatsRec.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                if (llm.findLastVisibleItemPosition() != chatAdapter.getItemCount() - 1) {

                    binding.scrolldown.setVisibility(View.VISIBLE);
                } else {
                    binding.scrolldown.setVisibility(View.INVISIBLE);
                }
            }
        });

        viewModel.getChatData().observe(chatActivity.this, new Observer<chatModel.chatResult>() {
            @Override
            public void onChanged(chatModel.chatResult chatResult) {
                if (chatResult != null) {
                    final int radius = 26;
                    final int margin = 2;
                    final Transformation transformation = new RoundedCornersTransformation(radius, margin);
                    Picasso.get().load(chatResult.getProduct_img()).resize(240, 240)
                            .transform(transformation).into(binding.productImage);

                    if(chatResult.getPerson_img()!=null) {
                        Picasso.get().load(chatResult.getPerson_img()).resize(240, 240)
                                .transform(new CropCircleTransformation())
                                .into(binding.personImage);
                    }

                    receiver_img = chatResult.getPerson_img();
                    binding.personName.setText(chatResult.getPerson_name());
                    if (chatResult.getStatus() != null) {
                        if (chatResult.getStatus().equals("online")) {
                            binding.personStatus.setVisibility(View.VISIBLE);
                            binding.personStatus.setText("Online");
                        } else {
                            binding.personStatus.setText("Offline");
                        }
                    } else {
                        binding.personStatus.setText("Offline");
                    }
                    binding.productTitle.setText(chatResult.getProduct_title());
                    binding.productPrice.setText("Rs " + chatResult.getProduct_price());

                    if(chatResult.getPostedby_number()!=null){
                        binding.phneBtn.setVisibility(View.VISIBLE);
                        binding.phneBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent callIntent = new Intent(Intent. ACTION_DIAL);
                                callIntent.setData(Uri.parse("tel:"+chatResult.getPostedby_number()));
                                startActivity(callIntent);
                            }
                        });
                    }
                    else {
                        binding.phneBtn.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
        viewModel.getPreviousChats().observe(chatActivity.this, new Observer<List<JSONObject>>() {
            @Override
            public void onChanged(List<JSONObject> jsonObjects) {
                if (jsonObjects.size() > 0) {

                    new Handler().postDelayed(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void run() {
                              previousMessages=jsonObjects;
                            chatAdapter = new ChatAdapter(chatActivity.this, previousMessages
                                    , receiver_img);
                            LinearLayoutManager llm = new LinearLayoutManager(chatActivity.this);
                            llm.setOrientation(RecyclerView.VERTICAL);
                            binding.chatsRec.setLayoutManager(llm);
                            binding.chatsRec.setAdapter(chatAdapter);
                            binding.chatsRec.scrollToPosition(chatAdapter.getItemCount() - 1);
                            llm.setSmoothScrollbarEnabled(true);
                            binding.chatsRec.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                                @Override
                                public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                                    if (llm.findLastVisibleItemPosition() != chatAdapter.getItemCount() - 1) {

                                        binding.scrolldown.setVisibility(View.VISIBLE);
                                    } else {
                                        binding.scrolldown.setVisibility(View.INVISIBLE);
                                    }
                                }
                            });

                        }
                    }, 100);
                }

            }
        });

        if(user_id!=null) {
            initiateSocketConnection();
        }
        binding.msgTxt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                // binding.chatsRec.smoothScrollToPosition(chatAdapter.getItemCount()-1);
                return false;
            }
        });
        binding.msgTxt.addTextChangedListener(this);

    }

    private void resetmessageEdit() {
        binding.msgTxt.removeTextChangedListener(this);
        binding.msgTxt.setText("");
        binding.sendMsg.setVisibility(View.INVISIBLE);
        binding.pickImage.setVisibility(View.VISIBLE);

        binding.msgTxt.addTextChangedListener(this);

    }

    private void initiateSocketConnection() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(new api_baseurl().socket_path).build();
        webSocket = client.newWebSocket(request, new socketListener());
    }


    private void startCropActivity() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                !=PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_CODE);
//            return;
        }

        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        //intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        intent.setType("image/*");
        startActivityForResult(intent,1);
    }


    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startCropActivity();
                } else {
                }
            }

        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.toString().trim().isEmpty()) {
            resetmessageEdit();
        } else {
            binding.sendMsg.setVisibility(View.VISIBLE);
            binding.pickImage.setVisibility(View.GONE);
        }

    }


    public class socketListener extends WebSocketListener {
        @Override
        public void onFailure(@NonNull WebSocket webSocket, @NonNull Throwable t, @Nullable Response response) {
            super.onFailure(webSocket, t, response);
            if(t.getMessage()!=null) {
                runOnUiThread(() -> {
                        Log.d("socketFailure", t.getMessage());
                Toast.makeText(chatActivity.this, "Failed to connect to chat socket.", Toast.LENGTH_SHORT).show();
                });
            }
        }

        @Override
        public void onClosed(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
            super.onClosed(webSocket, code, reason);
            if(reason!=null) {
                Log.d("socketClosed", reason);
            }
        }

        @Override
        public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
            super.onMessage(webSocket, text);
            runOnUiThread(() -> {
                Log.d("message01", text.toString());
                try {
                    JSONObject jsonObject = new JSONObject(text);
                    jsonObject.put("isSent", "no");
                    Log.d("message", jsonObject.toString());
                    if(jsonObject.toString().contains("message") || jsonObject.toString().contains("image")) {
                        chatAdapter.addItem(jsonObject);
                        recplaysound();
                        binding.chatsRec.smoothScrollToPosition(chatAdapter.getItemCount() - 1);
                    }
                    Calendar calendar = Calendar.getInstance();
                    Date date=calendar.getTime();
                    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
                    String time = sdf.format(date);
                    JSONObject sentcheck = new JSONObject();
                    sentcheck.put("user_id", user_id);
                    sentcheck.put("product_id", product_id);
                    sentcheck.put("person_id", person_id);
                    sentcheck.put("seen", "yesyo");

                    if (jsonObject.toString().contains("online")) {
                        binding.personStatus.setVisibility(View.VISIBLE);
                        binding.personStatus.setText("Online");
                        binding.personStatus.setTextColor(getResources()
                                .getColor(R.color.green));
                    } else {
                        binding.personStatus.setText("Offline");
                        binding.personStatus.setTextColor(getResources()
                                .getColor(R.color.yellow));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
        }

        @Override
        public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
            super.onOpen(webSocket, response);
            Log.d("connected", "yes");
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("user_id", user_id);
                jsonObject.put("person_id", person_id);
                jsonObject.put("product_id", product_id);
                webSocket.send(jsonObject.toString());
            } catch (JSONException e) {
                Log.d("errOpen", Objects.requireNonNull(e.getMessage()));
                e.printStackTrace();
            }

            runOnUiThread(chatActivity.this::viewfuncs);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
                Uri imguri = data.getData();
                try {
                    InputStream is = getContentResolver().openInputStream(imguri);
                    Bitmap image = BitmapFactory.decodeStream(is);
                    sendImage(image, String.valueOf(imguri));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

        }
    }

    private void sendImage(Bitmap image, String imageuri) {
        ByteArrayOutputStream by = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 50, by);
        String base64img = android.util.Base64.encodeToString(by.toByteArray(), Base64.DEFAULT);

        JSONObject jsonObject = new JSONObject();

        try {
            Calendar calendar = Calendar.getInstance();
            Date date=calendar.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
            String time = sdf.format(date);
            jsonObject.put("time", time);
            jsonObject.put("user_id", user_id);
            jsonObject.put("person_id", person_id);
            jsonObject.put("product_id", product_id);
            jsonObject.put("image", base64img);
            webSocket.send(jsonObject.toString());

            JSONObject localMsg = new JSONObject();
            localMsg.put("time", time);
            localMsg.put("user_id", user_id);
            localMsg.put("person_id", person_id);
            localMsg.put("image", imageuri);
            localMsg.put("isSent", "yes");
            chatAdapter.addItem(localMsg);
            playsound();
            binding.chatsRec.smoothScrollToPosition(chatAdapter.getItemCount() - 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(chatActivity.this, R.style.AlertDialogStyle).setTitle("Delete Chat")
                        .setMessage("Are you sure you want to delete the whole chat?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Retrofit retrofit = new Retrofit.Builder().baseUrl(baseurl.apibaseurl.toString())
                                        .addConverterFactory(GsonConverterFactory.create()).build();

                                ApiWork apiWork = retrofit.create(ApiWork.class);

                                Call<AuthResponse.SendOtp> call1 = apiWork.delete_singlechat(user_id, product_id, person_id);

                                call1.enqueue(new Callback<AuthResponse.SendOtp>() {
                                    @Override
                                    public void onResponse(Call<AuthResponse.SendOtp> call, retrofit2.Response<AuthResponse.SendOtp> response) {
                                        if (!response.isSuccessful()) {
                                            Log.d("error code", String.valueOf(response.code()));
                                            return;
                                        }

                                        AuthResponse.SendOtp resp = response.body();

                                        if (resp.getCode().equals("200")) {
                                            Toast.makeText(chatActivity.this, "Account Blocked", Toast.LENGTH_SHORT).show();

                                            finish();
                                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<AuthResponse.SendOtp> call, Throwable t) {
                                        Log.d("Failure", t.getMessage());
                                    }
                                });

                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder1.show();
                break;
            case R.id.block:
                AlertDialog.Builder builder = new AlertDialog.Builder(chatActivity.this, R.style.AlertDialogStyle).setTitle("Delete & Block")
                        .setMessage("You are going to block this account, the chat will also be deleted.").setPositiveButton("Block", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Retrofit retrofit = new Retrofit.Builder().baseUrl(baseurl.apibaseurl.toString())
                                        .addConverterFactory(GsonConverterFactory.create()).build();

                                ApiWork apiWork = retrofit.create(ApiWork.class);
                                Log.d("data", user_id + "," + person_id);
                                Call<AuthResponse.SendOtp> call1 = apiWork.block(user_id, product_id, person_id);

                                call1.enqueue(new Callback<AuthResponse.SendOtp>() {
                                    @Override
                                    public void onResponse(Call<AuthResponse.SendOtp> call, retrofit2.Response<AuthResponse.SendOtp> response) {
                                        if (!response.isSuccessful()) {
                                            Log.d("error code", String.valueOf(response.code()));
                                            return;
                                        }

                                        AuthResponse.SendOtp resp = response.body();

                                        if (resp.getCode().equals("200")) {
                                            Toast.makeText(chatActivity.this, "Account Blocked", Toast.LENGTH_SHORT).show();

                                            finish();
                                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<AuthResponse.SendOtp> call, Throwable t) {
                                        Log.d("Failure", t.getMessage());
                                    }
                                });

                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.show();
                break;

        }
        return false;
    }

    @Override
    public void finish() {
        super.finish();
        webSocket.close(1001,"closed");
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        getViewModelStore().clear();

    }
}