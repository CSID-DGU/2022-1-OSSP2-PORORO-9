package com.example.togaether;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.widget.AppCompatButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VisitDialog {
    private Context context;
    private Dialog dialog;
    private int pid;
    ArrayAdapter<String> firstAdapter;
    ArrayAdapter<String> midAdapter;
    ArrayAdapter<String> lastAdapter;

    public VisitDialog(Context context, int pid) {
        this.context = context;
        this.pid = pid;
    }

    public Dialog createDialog() {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_frame_visit);
        dialog.show();
        //SoundPlayer.play(sound);
        return dialog;
    }

    public void showDialog() {
        AddressConverter addressConverter = AddressConverter.getInstance();
        Dialog dialog = createDialog();
        ListView listDvisit = (ListView) dialog.findViewById(R.id.list_dvisit);
        Spinner spinFirst = (Spinner) dialog.findViewById(R.id.spin_first);
        Spinner spinMid = (Spinner) dialog.findViewById(R.id.spin_mid);
        Spinner spinLast = (Spinner) dialog.findViewById(R.id.spin_last);
        EditText edtNum = (EditText) dialog.findViewById(R.id.edt_num);
        AppCompatButton btnSearch = (AppCompatButton) dialog.findViewById(R.id.btn_search);

        PuppyListAdapter adapter = new PuppyListAdapter(2, context);
        listDvisit.setAdapter(adapter);

        ArrayList<String> arrFirst = new ArrayList<>();
        ArrayList<String> arrMid = new ArrayList<>();
        ArrayList<String> arrLast = new ArrayList<>();
        arrFirst.addAll(Arrays.asList(addressConverter.arrFirstAddr));
        arrMid.addAll(Arrays.asList(addressConverter.arrMidAddr));
        arrLast.addAll(Arrays.asList(addressConverter.arrLastAddr));
        firstAdapter = new ArrayAdapter<String>(dialog.getContext(), android.R.layout.simple_spinner_item, arrFirst);
        midAdapter = new ArrayAdapter<String>(dialog.getContext(), android.R.layout.simple_spinner_item, arrMid.subList(0,3));
        lastAdapter = new ArrayAdapter<String>(dialog.getContext(), android.R.layout.simple_spinner_item, arrLast.subList(0,3));
        firstAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        midAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lastAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinFirst.setAdapter(firstAdapter);
        spinMid.setAdapter(midAdapter);
        spinLast.setAdapter(lastAdapter);
        spinFirst.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                midAdapter = new ArrayAdapter<String>(dialog.getContext(), android.R.layout.simple_spinner_item, arrMid.subList(0+3*i,3+3*i));
                lastAdapter = new ArrayAdapter<String>(dialog.getContext(), android.R.layout.simple_spinner_item, arrLast.subList(0+9*i,3+9*i));
                midAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                lastAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinMid.setAdapter(midAdapter);
                spinLast.setAdapter(lastAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinMid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int j, long l) {
                int i = spinFirst.getSelectedItemPosition();
                lastAdapter = new ArrayAdapter<String>(dialog.getContext(), android.R.layout.simple_spinner_item, arrLast.subList(0+9*i+3*j,3+9*i+3*j));
                lastAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinLast.setAdapter(lastAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int f = spinFirst.getSelectedItemPosition();
                int m = spinMid.getSelectedItemPosition();
                int l = spinLast.getSelectedItemPosition();
                //int fSize = addressConverter.fSize;
                int mSize = addressConverter.mSize;
                int lSize = addressConverter.lSize;

                int num = -1;
                int addrToInt = f*mSize*lSize + m*lSize + l;
                try {
                    num = Integer.parseInt(edtNum.getText().toString());
                }
                catch (NumberFormatException e) {
                    num = -1;
                }
                Log.e("fml",f + " " + m + " " + l + "  " + addrToInt);

                // HTTP 요청&응답 처리하여 리스트에 추가
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://togaether.cafe24.com")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RetrofitPuppy retrofitAPI = retrofit.create(RetrofitPuppy.class);
                HashMap<String, Object> input = new HashMap<>();
                input.put("pid", addrToInt);
                input.put("num", num);
                retrofitAPI.getPuppyListByAddrPid(input).enqueue(new Callback<List<PuppyInfoItem>>() {
                    @Override
                    public void onResponse(Call<List<PuppyInfoItem>> call, Response<List<PuppyInfoItem>> response) {
                        if(response.isSuccessful()) {
                            adapter.clear();
                            List<PuppyInfoItem> data = response.body();
                            adapter.addItem(data);
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<PuppyInfoItem>> call, Throwable t) {
                        Log.e("=========OMG","here" + t);
                    }
                });
            }
        });

        //TextView tvDtitle = (TextView) dialog.findViewById(R.id.tv_dtitle);
        //tvDtitle.setText(title);
    }

    public void dissmissDailog() {
        dialog.dismiss();
    }
}
