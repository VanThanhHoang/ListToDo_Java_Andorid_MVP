package com.example.listtodo_ps25812.View.Activities;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.listtodo_ps25812.Listener.ChangeTopTitleListener;
import com.example.listtodo_ps25812.Listener.NetWorkStateChangeListener;
import com.example.listtodo_ps25812.Listener.OpenItemsFragmentListener;
import com.example.listtodo_ps25812.Model.Task;
import com.example.listtodo_ps25812.R;
import com.example.listtodo_ps25812.Untilities.NetWorkChangeReceive;
import com.example.listtodo_ps25812.View.Dialog.OtherDialog.BottomSheetDialogMenu;
import com.example.listtodo_ps25812.View.Fragment.TaskFragment;
import com.example.listtodo_ps25812.View.Fragment.TaskItemFragment;
import com.example.listtodo_ps25812.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity implements NetWorkStateChangeListener, OpenItemsFragmentListener, ChangeTopTitleListener {
    ActivityHomeBinding layoutBinding;
    NetWorkChangeReceive netWorkChangeReceive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = ActivityHomeBinding.inflate(getLayoutInflater(), null, false);
        setContentView(layoutBinding.getRoot());
        netWorkChangeReceive = new NetWorkChangeReceive();
        replaceFragment(new TaskFragment());
    }

    void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.layoutChangeFragment, fragment);
        if (fragment instanceof TaskFragment) {
            fragmentTransaction.commit();
            return;
        }
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    protected void onStart() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netWorkChangeReceive, intentFilter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(netWorkChangeReceive);
        super.onStop();
    }

    @Override
    public void handleNetWorkConnected() {
        if (layoutBinding.tvNetWorkState.isShown()) {
            layoutBinding.tvNetWorkState.setText("Đã quay lại trực tuyến");
            layoutBinding.tvNetWorkState.setBackgroundColor(getResources().getColor(R.color.green,null));
            new Handler().postDelayed(() -> layoutBinding.tvNetWorkState.setVisibility(View.GONE), 2000);
        }
    }

    @Override
    public void handleNetWorkDisconnected() {
        layoutBinding.tvNetWorkState.setVisibility(View.VISIBLE);
    }

    @Override
    public void openItemsFragment(Task task) {
        TaskItemFragment taskItemFragment = new TaskItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString("taskKey",task.getKey());
        Log.d("123", "onBindViewHolder: "+task.getKey());
        taskItemFragment.setArguments(bundle);
        replaceFragment(taskItemFragment);
    }

    @Override
    public void changeTopTitle(String title, int icon) {
        layoutBinding.tvTitle.setText(title);
        if (title.equals(ChangeTopTitleListener.TASK_ITEM_TITLE)) {
            layoutBinding.btnHelper.setOnClickListener(v -> onBackPressed());
        } else {
            layoutBinding.btnHelper.setOnClickListener(v -> showBottomSheetDialog());
        }
        layoutBinding.btnHelper.setImageResource(icon);
    }

    void showBottomSheetDialog() {
        layoutBinding.btnHelper.startAnimation(AnimationUtils.loadAnimation(this, R.anim.animation_rotato));
        BottomSheetDialogMenu bottomSheetDialog = new BottomSheetDialogMenu(this, () -> {
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
        });
        bottomSheetDialog.setOnCancelListener(dialog ->
                layoutBinding.btnHelper.
                        startAnimation(AnimationUtils.loadAnimation(
                                HomeActivity.this, R.anim.animation_rotato_reverse)));
        bottomSheetDialog.show();
    }
}