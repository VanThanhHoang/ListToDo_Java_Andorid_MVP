package com.example.listtodo_ps25812.View.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listtodo_ps25812.Adapter.TaskAdapter;
import com.example.listtodo_ps25812.Listener.ChangeTopTitleListener;
import com.example.listtodo_ps25812.Listener.OpenItemsFragmentListener;
import com.example.listtodo_ps25812.Listener.SwipeItemListener;
import com.example.listtodo_ps25812.Model.Task;
import com.example.listtodo_ps25812.Presenter.Listener.TaskChangeListener;
import com.example.listtodo_ps25812.Presenter.TaskFragmentPresenter;
import com.example.listtodo_ps25812.R;
import com.example.listtodo_ps25812.Untilities.ItemTouchHelperCallback;
import com.example.listtodo_ps25812.View.Dialog.ProcessDialog;
import com.example.listtodo_ps25812.View.Dialog.TaskDialog.AddTaskDialog;
import com.example.listtodo_ps25812.View.Dialog.TaskDialog.UpdateTaskDialog;
import com.example.listtodo_ps25812.databinding.FragmentTaskBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.Collections;
import java.util.List;

public class TaskFragment extends Fragment implements TaskChangeListener, SwipeItemListener {
    FragmentTaskBinding layoutBinding;
    TaskAdapter taskAdapter;
    TaskFragmentPresenter taskFragmentPresenter;
    ProcessDialog processDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutBinding = FragmentTaskBinding.inflate(inflater, container, false);
        return layoutBinding.getRoot();
    }

    void showProcessDialog() {
        if (getContext() != null) {
            processDialog = new ProcessDialog(this.getContext());
            processDialog.show();
        }
    }

    @SuppressLint("SuspiciousIndentation")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getContext() != null)
            layoutBinding.fabAddTask.setOnClickListener(v -> showDialogAddTask());
        showProcessDialog();
        taskFragmentPresenter = new TaskFragmentPresenter( this);
    }

    void initRv(List<Task> taskList) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        taskAdapter = new TaskAdapter(taskList, (OpenItemsFragmentListener) getContext());
        layoutBinding.rvTask.setLayoutManager(layoutManager);
        layoutBinding.rvTask.setAdapter(taskAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelperCallback(this.getContext(),
                        this));
        itemTouchHelper.attachToRecyclerView(layoutBinding.rvTask);
        layoutBinding.rvTask.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    layoutBinding.fabAddTask.hide();
                } else {
                    layoutBinding.fabAddTask.show();
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    public void onStart() {
        ChangeTopTitleListener changeTopTitleListener = (ChangeTopTitleListener) getContext();
        assert changeTopTitleListener != null;
        changeTopTitleListener.changeTopTitle(ChangeTopTitleListener.TASK_TITLE, R.drawable.ic_setting);
        super.onStart();
    }

    void showSnackBar() {
        @SuppressLint("ShowToast") Snackbar snackbar = Snackbar
                .make(layoutBinding.getRoot(), " Đã xóa task ", Snackbar.LENGTH_SHORT)
                .setAction("Hoàn tác", view -> taskFragmentPresenter.undoTaskRemoved());
        snackbar.addCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                super.onDismissed(transientBottomBar, event);
                if (event != Snackbar.Callback.DISMISS_EVENT_ACTION) {
                    taskFragmentPresenter.deleteTaskOnDatabase();
                }
            }
        });
        snackbar.show();
    }

    void showDialogAddTask() {
        if (getContext() != null) {
            new AddTaskDialog(getContext(), taskFragmentPresenter).show();
        }
    }

    void showDialogUpdateTask(int position) {
        if (getContext() != null) {
            new UpdateTaskDialog(getContext(),
                    taskFragmentPresenter,
                    taskFragmentPresenter.getListTask().get(position),
                    position).show();
        }
    }

    @Override
    public void onAddTask(int position) {
        taskAdapter.notifyItemInserted(position);
    }

    @Override
    public void onRemoveTask(int position) {
        taskAdapter.notifyItemRemoved(position);
    }

    @Override
    public void onUpdateTask(int position) {
        taskAdapter.notifyItemChanged(position);
    }

    @Override
    public void onGetListTask(List<Task> taskList) {
        if (processDialog != null) {
            processDialog.dismiss();
        }
        Collections.reverse(taskList);
        initRv(taskList);
    }
    @Override
    public void onSwipeLeft(int position) {
        showDialogUpdateTask(position);
    }

    @Override
    public void onSwipeRight(int position) {
        taskFragmentPresenter.deleteTaskOnList(position);
        showSnackBar();
    }
}
