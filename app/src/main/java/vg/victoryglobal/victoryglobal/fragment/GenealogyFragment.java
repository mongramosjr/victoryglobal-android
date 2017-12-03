/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/18/17 7:59 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 11/18/17 7:53 PM
 */

package vg.victoryglobal.victoryglobal.fragment;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.zip.Inflater;

import vg.victoryglobal.victoryglobal.R;
import vg.victoryglobal.victoryglobal.listener.LogoutListener;
import vg.victoryglobal.victoryglobal.model.AuthLoginRequest;
import vg.victoryglobal.victoryglobal.model.GenealogyAccount;
import vg.victoryglobal.victoryglobal.model.GenealogyRequest;
import vg.victoryglobal.victoryglobal.model.GenealogyResponse;

public class GenealogyFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public View currentView;
    private LayoutInflater currentInflater;

    private SwipeRefreshLayout swipeRefreshLayout;

    AuthLoginRequest authLoginRequest;

    GenealogyRequest genealogyRequest;



    ImageView genealogyMeAvatar;
    TextView genealogyMeId;
    TextView genealogyMeFullname;
    TextView genealogyMeMlmRank;

    ImageView genealogyLevel_1_0;
    ImageView genealogyLevel_1_1;
    LinearLayout genealogyLevel_1_0_container_more;
    LinearLayout genealogyLevel_1_1_container_more;
    TextView genealogyLevel_1_0_id;
    TextView genealogyLevel_1_1_id;

    ImageView genealogyLevel_2_0;
    ImageView genealogyLevel_2_1;
    ImageView genealogyLevel_2_2;
    ImageView genealogyLevel_2_3;
    LinearLayout genealogyLevel_2_0_container_more;
    LinearLayout genealogyLevel_2_1_container_more;
    LinearLayout genealogyLevel_2_2_container_more;
    LinearLayout genealogyLevel_2_3_container_more;
    TextView genealogyLevel_2_0_id;
    TextView genealogyLevel_2_1_id;
    TextView genealogyLevel_2_2_id;
    TextView genealogyLevel_2_3_id;

    ImageView genealogyLevel_3_0;
    ImageView genealogyLevel_3_1;
    ImageView genealogyLevel_3_2;
    ImageView genealogyLevel_3_3;
    ImageView genealogyLevel_3_4;
    ImageView genealogyLevel_3_5;
    ImageView genealogyLevel_3_6;
    ImageView genealogyLevel_3_7;
    LinearLayout genealogyLevel_3_0_container_more;
    LinearLayout genealogyLevel_3_1_container_more;
    LinearLayout genealogyLevel_3_2_container_more;
    LinearLayout genealogyLevel_3_3_container_more;
    LinearLayout genealogyLevel_3_4_container_more;
    LinearLayout genealogyLevel_3_5_container_more;
    LinearLayout genealogyLevel_3_6_container_more;
    LinearLayout genealogyLevel_3_7_container_more;
    TextView genealogyLevel_3_0_id;
    TextView genealogyLevel_3_1_id;
    TextView genealogyLevel_3_2_id;
    TextView genealogyLevel_3_3_id;
    TextView genealogyLevel_3_4_id;
    TextView genealogyLevel_3_5_id;
    TextView genealogyLevel_3_6_id;
    TextView genealogyLevel_3_7_id;

    TextView genealogyLeft;
    TextView genealogyRight;

    LogoutListener logoutListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authLoginRequest = AuthLoginRequest.getAuthLoginRequest("main");
        genealogyRequest = GenealogyRequest.getInstance();
        setRetainInstance(true);

        if(getActivity() instanceof LogoutListener){
            logoutListener = (LogoutListener) getActivity();
        }
    }

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        currentInflater = inflater;
        return inflater.inflate(R.layout.genealogy_fragment, container, false);

    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        currentView = view;

        genealogyMeAvatar = view.findViewById(R.id.genealogy_me);
        genealogyMeFullname = view.findViewById(R.id.genealogy_me_fullname);
        genealogyMeId= view.findViewById(R.id.genealogy_me_id);

        genealogyMeMlmRank = view.findViewById(R.id.genealogy_me_mlm_rank);

        genealogyLevel_1_0 = view.findViewById(R.id.genealogy_level_1_0);
        genealogyLevel_1_1 = view.findViewById(R.id.genealogy_level_1_1);
        genealogyLevel_1_0_container_more = view.findViewById(R.id.genealogy_level_1_0_container_more);
        genealogyLevel_1_1_container_more = view.findViewById(R.id.genealogy_level_1_1_container_more);
        genealogyLevel_1_0_id = view.findViewById(R.id.genealogy_level_1_0_id);
        genealogyLevel_1_1_id = view.findViewById(R.id.genealogy_level_1_1_id);

        genealogyLevel_2_0 = view.findViewById(R.id.genealogy_level_2_0);
        genealogyLevel_2_1 = view.findViewById(R.id.genealogy_level_2_1);
        genealogyLevel_2_2 = view.findViewById(R.id.genealogy_level_2_2);
        genealogyLevel_2_3 = view.findViewById(R.id.genealogy_level_2_3);
        genealogyLevel_2_0_container_more = view.findViewById(R.id.genealogy_level_2_0_container_more);
        genealogyLevel_2_1_container_more = view.findViewById(R.id.genealogy_level_2_1_container_more);
        genealogyLevel_2_2_container_more = view.findViewById(R.id.genealogy_level_2_2_container_more);
        genealogyLevel_2_3_container_more = view.findViewById(R.id.genealogy_level_2_3_container_more);
        genealogyLevel_2_0_id = view.findViewById(R.id.genealogy_level_2_0_id);
        genealogyLevel_2_1_id = view.findViewById(R.id.genealogy_level_2_1_id);
        genealogyLevel_2_2_id = view.findViewById(R.id.genealogy_level_2_2_id);
        genealogyLevel_2_3_id = view.findViewById(R.id.genealogy_level_2_3_id);

        genealogyLevel_3_0 = view.findViewById(R.id.genealogy_level_3_0);
        genealogyLevel_3_1 = view.findViewById(R.id.genealogy_level_3_1);
        genealogyLevel_3_2 = view.findViewById(R.id.genealogy_level_3_2);
        genealogyLevel_3_3 = view.findViewById(R.id.genealogy_level_3_3);
        genealogyLevel_3_4 = view.findViewById(R.id.genealogy_level_3_4);
        genealogyLevel_3_5 = view.findViewById(R.id.genealogy_level_3_5);
        genealogyLevel_3_6 = view.findViewById(R.id.genealogy_level_3_6);
        genealogyLevel_3_7 = view.findViewById(R.id.genealogy_level_3_7);
        genealogyLevel_3_0_container_more = view.findViewById(R.id.genealogy_level_3_0_container_more);
        genealogyLevel_3_1_container_more = view.findViewById(R.id.genealogy_level_3_1_container_more);
        genealogyLevel_3_2_container_more = view.findViewById(R.id.genealogy_level_3_2_container_more);
        genealogyLevel_3_3_container_more = view.findViewById(R.id.genealogy_level_3_3_container_more);
        genealogyLevel_3_4_container_more = view.findViewById(R.id.genealogy_level_3_4_container_more);
        genealogyLevel_3_5_container_more = view.findViewById(R.id.genealogy_level_3_5_container_more);
        genealogyLevel_3_6_container_more = view.findViewById(R.id.genealogy_level_3_6_container_more);
        genealogyLevel_3_7_container_more = view.findViewById(R.id.genealogy_level_3_7_container_more);
        genealogyLevel_3_0_id = view.findViewById(R.id.genealogy_level_3_0_id);
        genealogyLevel_3_1_id = view.findViewById(R.id.genealogy_level_3_1_id);
        genealogyLevel_3_2_id = view.findViewById(R.id.genealogy_level_3_2_id);
        genealogyLevel_3_3_id = view.findViewById(R.id.genealogy_level_3_3_id);
        genealogyLevel_3_4_id = view.findViewById(R.id.genealogy_level_3_4_id);
        genealogyLevel_3_5_id = view.findViewById(R.id.genealogy_level_3_5_id);
        genealogyLevel_3_6_id = view.findViewById(R.id.genealogy_level_3_6_id);
        genealogyLevel_3_7_id = view.findViewById(R.id.genealogy_level_3_7_id);

        genealogyLeft = view.findViewById(R.id.genealogy_left);
        genealogyRight = view.findViewById(R.id.genealogy_right);

        swipeRefreshLayout = view.findViewById(R.id.genealogy_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        if(genealogyRequest.isSuccess() && genealogyRequest.getGenealogyResponse() != null) {
            GenealogyResponse genealogy_structure = genealogyRequest.getGenealogyResponse();
            prepareGenealogy(genealogy_structure);
        }else{
            genealogyStructure(currentView, null);
        }
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);

        if(genealogyRequest.isSuccess() && genealogyRequest.getGenealogyResponse() != null) {
            GenealogyResponse genealogy_structure = genealogyRequest.getGenealogyResponse();
            prepareGenealogy(genealogy_structure);

            swipeRefreshLayout.setRefreshing(false);

        }else {
            genealogyStructure(currentView, null);
        }
    }

    private void prepareGenealogy(GenealogyResponse genealogy_structure){

        //remove views that were added programatically
        removeAddedGenealogyItem();

        String full_name = genealogy_structure.profile.frontend_label;

        String distributor_id_label =
                genealogy_structure.account.country_code
                + "-"
                + String.format("%09d", genealogy_structure.account.id);

        String mlm_rank = genealogy_structure.mlm_ranks
                .get(String.valueOf(genealogy_structure.account.mlm_rank));
        genealogyMeFullname.setText(full_name);
        genealogyMeId.setText(distributor_id_label);
        genealogyMeMlmRank.setText(mlm_rank);

        NumberFormat nf = NumberFormat.getIntegerInstance(Locale.US);
        nf.setParseIntegerOnly(true);
        if(genealogy_structure.total_downlines !=null ) {
            if(genealogy_structure.total_downlines.mlm_location.size()>0) {
                if (genealogy_structure.total_downlines.mlm_location.get(0) != null) {
                    genealogyLeft.setText(
                            nf.format(genealogy_structure.total_downlines.mlm_location.get(0).count)
                            );
                }
                if (genealogy_structure.total_downlines.mlm_location.get(1) != null) {
                    genealogyRight.setText(
                            nf.format(genealogy_structure.total_downlines.mlm_location.get(1).count)
                            );
                }
            }
        }

        if(genealogy_structure.profile.gender == 0 ) {
            genealogyMeAvatar.setImageResource(R.mipmap.genealogy_businessman_round);
        }else if(genealogy_structure.profile.gender == 1 ){
            genealogyMeAvatar.setImageResource(R.mipmap.genealogy_businesswoman_round);
        }

        ArrayList<DistributorIds> downline_ids = new ArrayList<>();
        ArrayList<DistributorIds> upline_ids = new ArrayList<>();
        for(int i = 0; i < genealogy_structure.genealogy.size(); i++){
            HashMap<String, GenealogyAccount> genealogy_list = genealogy_structure.genealogy.get(i).getMlm_members();

            if(i>=4) break;

            downline_ids.clear();

            if(i==1){
                for(int j = 0; j < Math.pow(2, i); j++){
                    DistributorIds distributor_ids = new DistributorIds();
                    downline_ids.add(j, distributor_ids);
                }
                for (GenealogyAccount genealogy : genealogy_list.values()) {
                    if(genealogy.mlm_location == 0){
                        if(downline_ids.get(0).ids.size()==0) {
                            genealogyLevel_1_0_id.setText(String.format("%09d", genealogy.id));
                            genealogyLevel_1_0.setImageResource(R.mipmap.genealogy_businessman_round);
                        }else{
                            //add image and text here
                            addGenealogyItem(currentView.getContext(), genealogyLevel_1_0_container_more, genealogy, false);

                        }
                        downline_ids.get(0).ids.add(Integer.valueOf(genealogy.id));
                    }
                    if(genealogy.mlm_location == 1){
                        if(downline_ids.get(1).ids.size()==0) {
                            genealogyLevel_1_1_id.setText(String.format("%09d", genealogy.id));
                            genealogyLevel_1_1.setImageResource(R.mipmap.genealogy_businessman_round);
                        }else{
                            //add image and text here
                            addGenealogyItem(currentView.getContext(), genealogyLevel_1_1_container_more, genealogy, false);
                        }
                        downline_ids.get(1).ids.add(Integer.valueOf(genealogy.id));

                    }
                }
            }

            if(i>1) {
                for (int j = 0; j < Math.pow(2, i); j++) {
                    DistributorIds distributor_ids = new DistributorIds();
                    downline_ids.add(j, distributor_ids);
                }

                int num_cell = upline_ids.size();

                //rearrange
                for (GenealogyAccount genealogy : genealogy_list.values()) {
                    int mlm_upline = genealogy.mlm_upline;
                    for(int j = 0; j < num_cell; j++){ //iterate throuh the cell
                        boolean got_match = false;
                        //j=0 (0,1) => 2 * 0 (0,1) => 0,1
                        //j=1 (0,1) => 2 * 1 (0,1) => 2,3
                        //j=2 (0,1) => 2 * 2 (0,1) => 4,5
                        //j=3 (0,1) => 2 * 3 (0,1) => 6,7
                        Double k = (2 * j) + genealogy.mlm_location.doubleValue();
                        for(Integer upline_id:upline_ids.get(j).ids)
                        { //iterate thru the upline
                            if(upline_id == mlm_upline){
                                got_match = true;
                                if(genealogy.mlm_location == 0)
                                {
                                    if(downline_ids.get(k.intValue()).ids.size()==0)
                                    {
                                        if(i==2 && k==0) {
                                            genealogyLevel_2_0_id.setText(String.format("%09d", genealogy.id));
                                            genealogyLevel_2_0.setImageResource(R.mipmap.genealogy_businessman_round);
                                        }else if(i==2 && k==2) {
                                            genealogyLevel_2_2_id.setText(String.format("%09d", genealogy.id));
                                            genealogyLevel_2_2.setImageResource(R.mipmap.genealogy_businessman_round);
                                        }
                                        else if(i==3 && k==0) {
                                            genealogyLevel_3_0_id.setText(String.format("%09d", genealogy.id));
                                            genealogyLevel_3_0.setImageResource(R.mipmap.genealogy_businessman_round);
                                        }
                                        else if(i==3 && k==2) {
                                            genealogyLevel_3_2_id.setText(String.format("%09d", genealogy.id));
                                            genealogyLevel_3_2.setImageResource(R.mipmap.genealogy_businessman_round);
                                        }
                                        else if(i==3 && k==4) {
                                            genealogyLevel_3_4_id.setText(String.format("%09d", genealogy.id));
                                            genealogyLevel_3_4.setImageResource(R.mipmap.genealogy_businessman_round);
                                        }
                                        else if(i==3 && k==6) {
                                            genealogyLevel_3_6_id.setText(String.format("%09d", genealogy.id));
                                            genealogyLevel_3_6.setImageResource(R.mipmap.genealogy_businessman_round);
                                        }

                                    }else{
                                        //add image and text here
                                        if(i==2 && k==0) {
                                            addGenealogyItem(currentView.getContext(), genealogyLevel_2_2_container_more, genealogy, false);
                                        }else if(i==2 && k==2) {
                                            addGenealogyItem(currentView.getContext(), genealogyLevel_2_2_container_more, genealogy, false);
                                        }
                                        else if(i==3 && k==0) {
                                            addGenealogyItem(currentView.getContext(), genealogyLevel_3_0_container_more, genealogy, true);
                                        }
                                        else if(i==3 && k==2) {
                                            addGenealogyItem(currentView.getContext(), genealogyLevel_3_2_container_more, genealogy, true);
                                        }
                                        else if(i==3 && k==4) {
                                            addGenealogyItem(currentView.getContext(), genealogyLevel_3_4_container_more, genealogy, true);
                                        }
                                        else if(i==3 && k==6) {
                                            addGenealogyItem(currentView.getContext(), genealogyLevel_3_6_container_more, genealogy, true);
                                        }
                                    }
                                    downline_ids.get(k.intValue()).ids.add(Integer.valueOf(genealogy.id));
                                }
                                if(genealogy.mlm_location == 1)
                                {
                                    if(downline_ids.get(k.intValue()).ids.size()==0) {

                                        if(i==2 && k==1) {
                                            genealogyLevel_2_1_id.setText(String.format("%09d", genealogy.id));
                                            genealogyLevel_2_1.setImageResource(R.mipmap.genealogy_businessman_round);
                                        }
                                        else if(i==2 && k==3) {
                                            genealogyLevel_2_3_id.setText(String.format("%09d", genealogy.id));
                                            genealogyLevel_2_3.setImageResource(R.mipmap.genealogy_businessman_round);
                                        }
                                        else if(i==3 && k==1) {
                                            genealogyLevel_3_1_id.setText(String.format("%09d", genealogy.id));
                                            genealogyLevel_3_1.setImageResource(R.mipmap.genealogy_businessman_round);
                                        }
                                        else if(i==3 && k==3) {
                                            genealogyLevel_3_3_id.setText(String.format("%09d", genealogy.id));
                                            genealogyLevel_3_3.setImageResource(R.mipmap.genealogy_businessman_round);
                                        }
                                        else if(i==3 && k==5) {
                                            genealogyLevel_3_5_id.setText(String.format("%09d", genealogy.id));
                                            genealogyLevel_3_5.setImageResource(R.mipmap.genealogy_businessman_round);
                                        }
                                        else if(i==3 && k==7) {
                                            genealogyLevel_3_7_id.setText(String.format("%09d", genealogy.id));
                                            genealogyLevel_3_7.setImageResource(R.mipmap.genealogy_businessman_round);
                                        }


                                    }else{
                                        //add image and text here
                                        if(i==2 && k==1) {
                                            addGenealogyItem(currentView.getContext(), genealogyLevel_2_1_container_more, genealogy, false);
                                        }
                                        else if(i==2 && k==3) {
                                            addGenealogyItem(currentView.getContext(), genealogyLevel_2_3_container_more, genealogy, false);
                                        }
                                        else if(i==3 && k==1) {
                                            addGenealogyItem(currentView.getContext(), genealogyLevel_3_1_container_more, genealogy, true);
                                        }
                                        else if(i==3 && k==3) {
                                            addGenealogyItem(currentView.getContext(), genealogyLevel_3_3_container_more, genealogy, true);
                                        }
                                        else if(i==3 && k==5) {
                                            addGenealogyItem(currentView.getContext(), genealogyLevel_3_5_container_more, genealogy, true);
                                        }
                                        else if(i==3 && k==7) {
                                            addGenealogyItem(currentView.getContext(), genealogyLevel_3_7_container_more, genealogy, true);
                                        }
                                    }
                                    downline_ids.get(k.intValue()).ids.add(Integer.valueOf(genealogy.id));

                                }
                                break;
                            }
                        }
                        if(got_match) break;
                    }

                }

                //
            }



            upline_ids.clear();
            upline_ids = new ArrayList<>(downline_ids);

        }

    }

    public class DistributorIds{
        public ArrayList<Integer> ids;
        public DistributorIds(){
            this.ids = new ArrayList<>();
        }
    }

    public void addGenealogyItem(Context context, LinearLayout item_container, GenealogyAccount genealogy_account, boolean caption)
    {



        int padding = getResources().getDimensionPixelOffset(R.dimen.icon_spacing);

        ImageView imageView = new ImageView(context);
        imageView.setImageResource(R.mipmap.genealogy_businessman_round);
        imageView.setPadding(padding,
                padding*2,
                padding,
                padding);

        TextView textView;
        //textView = new TextView(context);
        //textView.setText(String.format("%09d", genealogy_account.id));
        //textView.setGravity(Gravity.CENTER_HORIZONTAL);

        //textView = (TextView) LayoutInflater.from(context).inflate(resource, null, false);

        if(caption) {
            textView = (TextView) View.inflate(context, R.layout.genealogy_structure_item_caption, null);
        }else{
            textView = (TextView) View.inflate(context, R.layout.genealogy_structure_item, null);
        }
        textView.setText(String.format("%09d", genealogy_account.id));

        item_container.addView(imageView);
        item_container.addView(textView);
    }


    private void removeAddedGenealogyItem()
    {
        int count = 0;
        count = genealogyLevel_1_0_container_more.getChildCount();
        if(count>0) genealogyLevel_1_0_container_more.removeViews(0, count);

        count = genealogyLevel_1_1_container_more.getChildCount();
        if(count>0) genealogyLevel_1_1_container_more.removeViews(0, count);


        count = genealogyLevel_2_0_container_more.getChildCount();
        if(count>0) genealogyLevel_2_0_container_more.removeViews(0, count);

        count = genealogyLevel_2_1_container_more.getChildCount();
        if(count>0) genealogyLevel_2_1_container_more.removeViews(0, count);

        count = genealogyLevel_2_2_container_more.getChildCount();
        if(count>0) genealogyLevel_2_2_container_more.removeViews(0, count);

        count = genealogyLevel_2_3_container_more.getChildCount();
        if(count>0) genealogyLevel_2_3_container_more.removeViews(0, count);


        count = genealogyLevel_3_0_container_more.getChildCount();
        if(count>0) genealogyLevel_3_0_container_more.removeViews(0, count);

        count = genealogyLevel_3_1_container_more.getChildCount();
        if(count>0) genealogyLevel_3_1_container_more.removeViews(0, count);

        count = genealogyLevel_3_2_container_more.getChildCount();
        if(count>0) genealogyLevel_3_2_container_more.removeViews(0, count);

        count = genealogyLevel_3_3_container_more.getChildCount();
        if(count>0) genealogyLevel_3_3_container_more.removeViews(0, count);

        count = genealogyLevel_3_4_container_more.getChildCount();
        if(count>0) genealogyLevel_3_4_container_more.removeViews(0, count);

        count = genealogyLevel_3_5_container_more.getChildCount();
        if(count>0) genealogyLevel_3_5_container_more.removeViews(0, count);

        count = genealogyLevel_3_6_container_more.getChildCount();
        if(count>0) genealogyLevel_3_7_container_more.removeViews(0, count);

        count = genealogyLevel_3_7_container_more.getChildCount();
        if(count>0) genealogyLevel_3_7_container_more.removeViews(0, count);
    }

    private void fetchGenealogyStructure(Integer distributor_id)
    {
        if(genealogyRequest.getGenealogyList().containsKey(distributor_id)){
            GenealogyResponse genealogy_structure = genealogyRequest.getGenealogyList().get(distributor_id);
            prepareGenealogy(genealogy_structure);
        }else{
            genealogyStructure(currentView, distributor_id);
        }
    }


    private void genealogyCallback(View view, String response_data) {
        try {
            JSONObject object = (JSONObject) new JSONTokener(response_data).nextValue();
            int status = object.getInt("status");

            //Log.e("accountCallback ", "Status: " + String.valueOf(status));

            if(status == 200 ){

                if(genealogyRequest.saveGenealogy(response_data)){
                    prepareGenealogy(genealogyRequest.getGenealogyResponse());
                }
            }else if(status == 402 ) {
                //TODO: redirect/show to error page
                String message = object.getString("message");
                Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }else if(status == 401 ){
                String message = object.getString("message");
                //force logout
                Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
                logoutListener.prepareLogout(authLoginRequest.getAccountLogin());

            }else{
                Toast.makeText(getActivity().getApplicationContext(), R.string.ui_exception, Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            //do nothing
            Log.e("ProfileFragment", "AccountCallback: (4) " + e.getMessage());
            Toast.makeText(getActivity().getApplicationContext(), R.string.ui_exception, Toast.LENGTH_LONG).show();
        }

        swipeRefreshLayout.setRefreshing(false);
    }


    private void genealogyStructure(final View view, @Nullable Integer distributor_id) {

        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        String mlm_member_id = String.valueOf(authLoginRequest.getAccountLogin().getMlmMemberId());
        String session = authLoginRequest.getAccountLogin().getSession();
        String auth_token = authLoginRequest.getAccountLogin().getAuthToken();

        String url = getString(R.string.api_url) + getString(R.string.api_genealogy);

        if(distributor_id != null){
            mlm_member_id = String.valueOf(distributor_id);
        }

        JSONObject post_data = new JSONObject();
        try {
            post_data.put("session", session);
            post_data.put("auth_token", auth_token);
            post_data.put("mlm_member_id", mlm_member_id);
        }catch(JSONException ex) {

            //callback_code.getStepperLayout().hideProgress();
            Toast.makeText(getActivity().getApplicationContext(), R.string.ui_exception, Toast.LENGTH_LONG).show();
            Log.e("Genealogy", ex.getMessage());
            return;
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST,
                        url,
                        post_data,
                        new com.android.volley.Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                //Log.e("Genealogy", "Response: " + response.toString());
                                genealogyCallback(view, response.toString());
                            }
                        },
                        new com.android.volley.Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Do nothing
                                Log.e("Genealogy", "onErrorResponse: " + error.toString());
                                Toast.makeText(getActivity().getApplicationContext(), R.string.ui_unexpected_response, Toast.LENGTH_LONG).show();
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        }
                );

        // 6 minutes
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 144,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        queue.add(jsObjRequest);
    }


}
