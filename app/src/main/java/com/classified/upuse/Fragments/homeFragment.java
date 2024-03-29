package com.classified.upuse.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.classified.upuse.R;
import com.classified.upuse.databinding.FragmentHomeBinding;
import com.classified.upuse.APIWork.ApiWork;
import com.classified.upuse.Ad_posterActivity;
import com.classified.upuse.Ad_userActivity;
import com.classified.upuse.Adapters.adsAdapter;
import com.classified.upuse.Adapters.bannerAdapter;
import com.classified.upuse.Adapters.categoryAdapter;
import com.classified.upuse.AuthActivity;
import com.classified.upuse.Constants.api_baseurl;
import com.classified.upuse.Models.AuthResponse;
import com.classified.upuse.Models.homeResponse;

import com.classified.upuse.ViewModels.homefragViewModel;
import com.classified.upuse.helpingCode.progressDialog;
import com.google.android.gms.location.FusedLocationProviderClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class homeFragment extends Fragment implements LocationListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentHomeBinding hmbinding;
    private bannerAdapter bannerAdapter;
    private com.classified.upuse.Adapters.categoryAdapter categoryAdapter;
    private com.classified.upuse.Adapters.adsAdapter adsAdapter;
    private List<homeResponse.bannerResult> bannerlist = new ArrayList<>();
    private homefragViewModel hmViewModel;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Integer pos = 0;
    private SharedPreferences sharedPreferences;
    private String lat;
    private String longit;
    private String user_id;
    private String city;
    private LocationManager locationManager;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private api_baseurl baseurl=new api_baseurl();
    public homeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static homeFragment newInstance(String param1, String param2) {
        homeFragment fragment = new homeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        sharedPreferences = getActivity().getSharedPreferences("userlogged", 0);
        Bundle bundle = getArguments();
        if (bundle != null) {
            city = bundle.getString("city");
        } else {
            city = sharedPreferences.getString("usercity", "");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        hmbinding = FragmentHomeBinding.inflate(inflater, container, false);
        View bottombar = getActivity().findViewById(R.id.bottomnav);
        bottombar.setVisibility(View.VISIBLE);
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() != NetworkInfo.State.CONNECTED &&
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() != NetworkInfo.State.CONNECTED) {
            noInternetFragment nocon = new noInternetFragment();
            FragmentTransaction transaction1 = getActivity().getSupportFragmentManager().beginTransaction();
            transaction1.setCustomAnimations(R.anim.fade_2, R.anim.fade);
            transaction1.replace(R.id.mainFragment, nocon);
            transaction1.addToBackStack("A");
            transaction1.commit();
        } else {
            ManageData();
            viewfuncs();
        }


        return hmbinding.getRoot();
    }

    private void viewfuncs() {

        hmbinding.swipelayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               getActivity().getViewModelStore().clear();
                hmbinding.swipelayout.setRefreshing(false);
                homeFragment homeFragment=new homeFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.mainFragment, homeFragment);
                transaction.commit();

            }
        });

        hmbinding.citytext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationFragment homeFragment = new locationFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
                transaction.replace(R.id.mainFragment, homeFragment);
                transaction.addToBackStack("A");
                transaction.commit();
            }
        });

        hmbinding.locaticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationFragment homeFragment = new locationFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
                transaction.replace(R.id.mainFragment, homeFragment);
                transaction.addToBackStack("A");
                transaction.commit();
            }
        });
        hmbinding.onbprog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hmbinding.bannerrv.setCurrentItem(0, true);
                hmbinding.onbprog.getBackground().setTint(Color.parseColor("#FFD057"));
                hmbinding.onbprog2.getBackground().setTint(Color.parseColor("#C6C6C6"));
                hmbinding.onbprog3.getBackground().setTint(Color.parseColor("#C6C6C6"));
                hmbinding.onbprog4.getBackground().setTint(Color.parseColor("#C6C6C6"));
            }
        });

        hmbinding.onbprog2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hmbinding.bannerrv.setCurrentItem(1, true);
                hmbinding.onbprog2.getBackground().setTint(Color.parseColor("#FFD057"));
                hmbinding.onbprog.getBackground().setTint(Color.parseColor("#C6C6C6"));
                hmbinding.onbprog3.getBackground().setTint(Color.parseColor("#C6C6C6"));
                hmbinding.onbprog4.getBackground().setTint(Color.parseColor("#C6C6C6"));
            }
        });

        hmbinding.onbprog3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hmbinding.bannerrv.setCurrentItem(2, true);
                hmbinding.onbprog3.getBackground().setTint(Color.parseColor("#FFD057"));
                hmbinding.onbprog.getBackground().setTint(Color.parseColor("#C6C6C6"));
                hmbinding.onbprog2.getBackground().setTint(Color.parseColor("#C6C6C6"));
                hmbinding.onbprog4.getBackground().setTint(Color.parseColor("#C6C6C6"));
            }
        });

        hmbinding.onbprog4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hmbinding.bannerrv.setCurrentItem(3, true);
                hmbinding.onbprog4.getBackground().setTint(Color.parseColor("#FFD057"));
                hmbinding.onbprog2.getBackground().setTint(Color.parseColor("#C6C6C6"));
                hmbinding.onbprog3.getBackground().setTint(Color.parseColor("#C6C6C6"));
                hmbinding.onbprog.getBackground().setTint(Color.parseColor("#C6C6C6"));
            }
        });

        hmbinding.bannerrv.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == 0) {
                    hmbinding.onbprog.getBackground().setTint(Color.parseColor("#FFD057"));
                    hmbinding.onbprog2.getBackground().setTint(Color.parseColor("#C6C6C6"));
                    hmbinding.onbprog3.getBackground().setTint(Color.parseColor("#C6C6C6"));
                    hmbinding.onbprog4.getBackground().setTint(Color.parseColor("#C6C6C6"));
                } else if (position == 1) {
                    hmbinding.onbprog2.getBackground().setTint(Color.parseColor("#FFD057"));
                    hmbinding.onbprog.getBackground().setTint(Color.parseColor("#C6C6C6"));
                    hmbinding.onbprog3.getBackground().setTint(Color.parseColor("#C6C6C6"));
                    hmbinding.onbprog4.getBackground().setTint(Color.parseColor("#C6C6C6"));
                } else if (position == 2) {
                    hmbinding.onbprog3.getBackground().setTint(Color.parseColor("#FFD057"));
                    hmbinding.onbprog2.getBackground().setTint(Color.parseColor("#C6C6C6"));
                    hmbinding.onbprog.getBackground().setTint(Color.parseColor("#C6C6C6"));
                    hmbinding.onbprog4.getBackground().setTint(Color.parseColor("#C6C6C6"));
                } else if (position == 3) {
                    hmbinding.onbprog4.getBackground().setTint(Color.parseColor("#FFD057"));
                    hmbinding.onbprog.getBackground().setTint(Color.parseColor("#C6C6C6"));
                    hmbinding.onbprog2.getBackground().setTint(Color.parseColor("#C6C6C6"));
                    hmbinding.onbprog3.getBackground().setTint(Color.parseColor("#C6C6C6"));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        hmbinding.searchicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchFragment homeFragment = new searchFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
                transaction.replace(R.id.mainFragment, homeFragment);
                transaction.addToBackStack("A");
                transaction.commit();
            }
        });

        hmbinding.notiicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notiFragment homeFragment = new notiFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
                transaction.replace(R.id.mainFragment, homeFragment);
                transaction.addToBackStack("A");
                transaction.commit();
            }
        });
    }

    private void searchfun(String query) {
        List<homeResponse.adsResult> searchedList = new ArrayList<>();
        for (homeResponse.adsResult model : hmViewModel.getAdsdata().getValue()) {

            if (model.getAd_category().toLowerCase().contains(query.toLowerCase()) ||
                    model.getAd_category().toLowerCase().contains(query.toLowerCase())) {

                if (model.getFeatured_status().equals("1")) {
                    searchedList.add(model);
                } else {
                    searchedList.add(model);
                }
            }

        }
        adsAdapter.searchList(searchedList);
    }

    private void ManageData() {
        progressDialog progressdialog = new progressDialog();
        progressdialog.showLoadingDialog(getContext(), "Loading",
                "Loading Feed. Please wait");
        pos = 0;
        user_id = sharedPreferences.getString("userid", "");
        Log.d("userId", user_id);
        String state = sharedPreferences.getString("userstate", "");
        String citystate;
        Bundle bundle = getArguments();
        if (bundle != null) {
            citystate = bundle.getString("city");
            hmbinding.citytext.setText(citystate);
        } else {
            citystate = sharedPreferences.getString("usercity", "");
            hmbinding.citytext.setText(citystate + ", " + state);
        }

        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseurl.apibaseurl.toString())
                .addConverterFactory(GsonConverterFactory.create()).build();

        ApiWork apiWork = retrofit.create(ApiWork.class);

        Call<AuthResponse.VerifyOtp> call = apiWork.getprofile(user_id);

        call.enqueue(new Callback<AuthResponse.VerifyOtp>() {
            @Override
            public void onResponse(Call<AuthResponse.VerifyOtp> call, Response<AuthResponse.VerifyOtp> response) {
                if (!response.isSuccessful()) {
                    Log.d("error code", String.valueOf(response.code()));
                    return;
                }

                AuthResponse.VerifyOtp resp = response.body();

                if (resp.getResult() != null) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("userlogged", "yes");
                    editor.putString("userimage", resp.getResult().getImage());
                    editor.putString("userid", resp.getResult().getId());
                    editor.putString("usermobile", resp.getResult().getMobile());
                    editor.putString("username", resp.getResult().getName());
                    editor.commit();
                }
                else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();
                    startActivity(new Intent(getActivity(), AuthActivity.class));
                    getActivity().finish();
                    getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                }
            }

            @Override
            public void onFailure(Call<AuthResponse.VerifyOtp> call, Throwable t) {
                Log.d("Failure", t.getMessage());
            }
        });
        hmViewModel = new ViewModelProvider(getActivity()).get(homefragViewModel.class);
        hmViewModel.initwork(user_id, "0", "0", citystate);

        hmViewModel.getBannerdata().observe(getActivity(), new Observer<List<homeResponse.bannerResult>>() {
            @Override
            public void onChanged(List<homeResponse.bannerResult> bannermodels) {
                if (bannermodels.size() > 0) {
                    bannerlist.clear();
                    bannerlist = bannermodels;
                    bannerAdapter = new bannerAdapter(getActivity(), bannerlist);
                    hmbinding.bannerrv.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
                    hmbinding.bannerrv.setAdapter(bannerAdapter);
                    hmbinding.bannerrv.setCurrentItem(0);
                    rotatebanner();
                }
            }
        });
        hmViewModel.getCategorydata().observe(getActivity(), new Observer<List<homeResponse.categoryResult>>() {
            @Override
            public void onChanged(List<homeResponse.categoryResult> categoryResults) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (categoryResults.size() > 0) {
                            categoryAdapter.notifyDataSetChanged();
                        }
                    }
                }, 100);

            }
        });
        hmViewModel.getAdsdata().observe(getActivity(), new Observer<List<homeResponse.adsResult>>() {
            @Override
            public void onChanged(List<homeResponse.adsResult> adsResults) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (adsResults.size() > 0) {
                            adsAdapter.notifyDataSetChanged();
                        }
                        progressdialog.hideLoadingDialog();
                    }
                }, 500);
            }
        });
        categoryAdapter = new categoryAdapter(getActivity(), hmViewModel.getCategorydata().getValue());
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(RecyclerView.HORIZONTAL);
        hmbinding.categoryrec.setLayoutManager(llm);
        hmbinding.categoryrec.setAdapter(categoryAdapter);
        categoryAdapter.setoncardclicklistener(new categoryAdapter.oncardclicklistener() {
            @Override
            public void oncardclick(String catname) {
                if (!catname.equals("All")) {
                    searchfun(catname);
                } else {
                    searchfun("");
                }
            }
        });

        adsAdapter = new adsAdapter(getActivity(), hmViewModel.getAdsdata().getValue());
        LinearLayoutManager llm1 = new LinearLayoutManager(getActivity());
        hmbinding.adsrec.setLayoutManager(llm1);
        hmbinding.adsrec.setAdapter(adsAdapter);

        adsAdapter.setonItemClick(new adsAdapter.onItemClick() {
            @Override
            public void onAdClick(String category_name, String ad_id, String prod_name, String userid) {
                Intent intent = null;
                if (!userid.equals(user_id)) {
                    intent = new Intent(getActivity(), Ad_userActivity.class);

                } else {
                    // change this to same user activity
                    intent = new Intent(getActivity(), Ad_posterActivity.class);
                }

                intent.putExtra("cat_name", category_name);
                intent.putExtra("ad_id", ad_id);
                intent.putExtra("product_name", prod_name);

                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

            }
        });
    }

    private void rotatebanner() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (bannerAdapter.getItemCount() > 0) {
                    if (bannerAdapter.getItemCount() > hmbinding.bannerrv.getCurrentItem() && hmbinding.bannerrv.getCurrentItem() == 0) {
                        hmbinding.bannerrv.setCurrentItem(1, true);
                        hmbinding.onbprog2.getBackground().setTint(Color.parseColor("#FFD057"));
                        hmbinding.onbprog.getBackground().setTint(Color.parseColor("#C6C6C6"));
                        hmbinding.onbprog3.getBackground().setTint(Color.parseColor("#C6C6C6"));
                        hmbinding.onbprog4.getBackground().setTint(Color.parseColor("#C6C6C6"));
                        rotatebanner();
                        return;
                    }
                    if (bannerAdapter.getItemCount() > hmbinding.bannerrv.getCurrentItem() && hmbinding.bannerrv.getCurrentItem() == 1) {
                        hmbinding.bannerrv.setCurrentItem(2, true);
                        hmbinding.onbprog3.getBackground().setTint(Color.parseColor("#FFD057"));
                        hmbinding.onbprog.getBackground().setTint(Color.parseColor("#C6C6C6"));
                        hmbinding.onbprog2.getBackground().setTint(Color.parseColor("#C6C6C6"));
                        hmbinding.onbprog4.getBackground().setTint(Color.parseColor("#C6C6C6"));
                        rotatebanner();
                        return;
                    }
                    if (bannerAdapter.getItemCount() > hmbinding.bannerrv.getCurrentItem() && hmbinding.bannerrv.getCurrentItem() == 2) {
                        hmbinding.bannerrv.setCurrentItem(3, true);
                        hmbinding.onbprog4.getBackground().setTint(Color.parseColor("#FFD057"));
                        hmbinding.onbprog.getBackground().setTint(Color.parseColor("#C6C6C6"));
                        hmbinding.onbprog2.getBackground().setTint(Color.parseColor("#C6C6C6"));
                        hmbinding.onbprog3.getBackground().setTint(Color.parseColor("#C6C6C6"));
                        rotatebanner();
                        return;
                    }

                    if (bannerAdapter.getItemCount() > hmbinding.bannerrv.getCurrentItem() && hmbinding.bannerrv.getCurrentItem() == 2) {
                        hmbinding.bannerrv.setCurrentItem(0, true);
                        hmbinding.onbprog.getBackground().setTint(Color.parseColor("#FFD057"));
                        hmbinding.onbprog2.getBackground().setTint(Color.parseColor("#C6C6C6"));
                        hmbinding.onbprog3.getBackground().setTint(Color.parseColor("#C6C6C6"));
                        hmbinding.onbprog4.getBackground().setTint(Color.parseColor("#C6C6C6"));
                        rotatebanner();
                        return;
                    }
                    if (hmbinding.bannerrv.getCurrentItem() == bannerAdapter.getItemCount() - 1) {
                        hmbinding.bannerrv.setCurrentItem(0);
                        hmbinding.onbprog.getBackground().setTint(Color.parseColor("#FFD057"));
                        hmbinding.onbprog2.getBackground().setTint(Color.parseColor("#C6C6C6"));
                        hmbinding.onbprog3.getBackground().setTint(Color.parseColor("#C6C6C6"));
                        hmbinding.onbprog4.getBackground().setTint(Color.parseColor("#C6C6C6"));
                        rotatebanner();
                        return;

                    } else {
                        hmbinding.bannerrv.setCurrentItem(0);
                        hmbinding.onbprog.getBackground().setTint(Color.parseColor("#FFD057"));
                        hmbinding.onbprog2.getBackground().setTint(Color.parseColor("#C6C6C6"));
                        hmbinding.onbprog3.getBackground().setTint(Color.parseColor("#C6C6C6"));
                        hmbinding.onbprog4.getBackground().setTint(Color.parseColor("#C6C6C6"));
                        rotatebanner();
                    }
                }


            }
        }, 5000);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().getViewModelStore().clear();
    }
}