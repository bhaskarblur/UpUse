package com.classified.justsell.Fragments;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.classified.justsell.Adapters.adsAdapter;
import com.classified.justsell.Adapters.bannerAdapter;
import com.classified.justsell.Adapters.categoryAdapter;
import com.classified.justsell.Models.homeResponse;
import com.classified.justsell.R;
import com.classified.justsell.ViewModels.homefragViewModel;
import com.classified.justsell.databinding.FragmentHomeBinding;
import com.google.android.gms.location.FusedLocationProviderClient;

import java.util.ArrayList;
import java.util.List;


public class homeFragment extends Fragment implements LocationListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentHomeBinding hmbinding;
    private bannerAdapter bannerAdapter;
    private com.classified.justsell.Adapters.categoryAdapter categoryAdapter;
    private com.classified.justsell.Adapters.adsAdapter adsAdapter;
    private List<homeResponse.bannerResult> bannerlist = new ArrayList<>();
    private homefragViewModel hmViewModel;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Integer pos = 0;
    private SharedPreferences sharedPreferences;
    private String lat;
    private String longit;
    private String userid;
    private LocationManager locationManager;
    private FusedLocationProviderClient fusedLocationProviderClient;
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        hmbinding=FragmentHomeBinding.inflate(inflater,container,false);

        ManageData();
        viewfuncs();
        return hmbinding.getRoot();
    }

    private void viewfuncs() {

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
                    hmbinding.onbprog.getBackground().setTint(Color.parseColor("#0881E3"));
                    hmbinding.onbprog2.getBackground().setTint(Color.parseColor("#C6C6C6"));
                    hmbinding.onbprog3.getBackground().setTint(Color.parseColor("#C6C6C6"));
                    hmbinding.onbprog4.getBackground().setTint(Color.parseColor("#C6C6C6"));
                } else if (position == 1) {
                    hmbinding.onbprog2.getBackground().setTint(Color.parseColor("#0881E3"));
                    hmbinding.onbprog.getBackground().setTint(Color.parseColor("#C6C6C6"));
                    hmbinding.onbprog3.getBackground().setTint(Color.parseColor("#C6C6C6"));
                    hmbinding.onbprog4.getBackground().setTint(Color.parseColor("#C6C6C6"));
                } else if (position == 2) {
                    hmbinding.onbprog3.getBackground().setTint(Color.parseColor("#0881E3"));
                    hmbinding.onbprog2.getBackground().setTint(Color.parseColor("#C6C6C6"));
                    hmbinding.onbprog.getBackground().setTint(Color.parseColor("#C6C6C6"));
                    hmbinding.onbprog4.getBackground().setTint(Color.parseColor("#C6C6C6"));
                } else if (position == 3) {
                    hmbinding.onbprog4.getBackground().setTint(Color.parseColor("#0881E3"));
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
    }

    private void ManageData() {
        pos = 0;
        sharedPreferences = getActivity().getSharedPreferences("userlogged", 0);
        userid = sharedPreferences.getString("userid", "");
        hmViewModel=new ViewModelProvider(getActivity()).get(homefragViewModel.class);
        hmViewModel.initwork(userid,"0","0","location");

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
                        if(categoryResults.size()>0) {
                            categoryAdapter.notifyDataSetChanged();
                        }
                    }
                },100);

            }
        });
        hmViewModel.getAdsdata().observe(getActivity(), new Observer<List<homeResponse.adsResult>>() {
            @Override
            public void onChanged(List<homeResponse.adsResult> adsResults) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(adsResults.size()>0) {
                            adsAdapter.notifyDataSetChanged();
                        }
                    }
                },100);
            }
        });
        categoryAdapter =new categoryAdapter(getActivity(),hmViewModel.getCategorydata().getValue());
        LinearLayoutManager llm=new LinearLayoutManager(getActivity());
        llm.setOrientation(RecyclerView.HORIZONTAL);
        hmbinding.categoryrec.setLayoutManager(llm);
        hmbinding.categoryrec.setAdapter(categoryAdapter);
        categoryAdapter.setoncardclicklistener(new categoryAdapter.oncardclicklistener() {
            @Override
            public void oncardclick(String catname) {

            }
        });

        adsAdapter=new adsAdapter(getActivity(),hmViewModel.getAdsdata().getValue());
        LinearLayoutManager llm1=new LinearLayoutManager(getActivity());
        hmbinding.adsrec.setLayoutManager(llm1);
        hmbinding.adsrec.setAdapter(adsAdapter);
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

}