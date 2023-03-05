package com.example.listtodo_ps25812.View.Fragment;

import android.content.Context;
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

import com.example.listtodo_ps25812.Adapter.TaskItemAdapter;
import com.example.listtodo_ps25812.Listener.ChangeTopTitleListener;
import com.example.listtodo_ps25812.Listener.OpenItemsFragmentListener;
import com.example.listtodo_ps25812.Listener.SwipeItemListener;
import com.example.listtodo_ps25812.Model.TaskItem;
import com.example.listtodo_ps25812.Presenter.Listener.TaskItemChangeListener;
import com.example.listtodo_ps25812.Presenter.TaskItemFragmentPresenter;
import com.example.listtodo_ps25812.R;
import com.example.listtodo_ps25812.Untilities.ItemTouchHelperCallback;
import com.example.listtodo_ps25812.Untilities.NetworkChecker;
import com.example.listtodo_ps25812.View.Dialog.TaskItemDialog.AddTaskItemDialog;
import com.example.listtodo_ps25812.View.Dialog.TaskItemDialog.UpdateTaskItemDialog;
import com.example.listtodo_ps25812.databinding.FragmentTaskBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class TaskItemFragment extends Fragment implements TaskItemChangeListener, SwipeItemListener {
    private FragmentTaskBinding layoutBinding;
    private AddTaskItemDialog addItemTaskDialog;
    private TaskItemFragmentPresenter taskItemFragmentPresenter;
    private TaskItemAdapter taskItemAdapter;
    private long taskId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutBinding = FragmentTaskBinding.inflate(inflater, container, false);
        if (this.getContext() != null) {

            taskItemFragmentPresenter = new TaskItemFragmentPresenter(this,
                    new NetworkChecker(this.getContext()),
                    taskId);
            addItemTaskDialog = new AddTaskItemDialog(this.getContext(), taskItemFragmentPresenter, taskId);
        }
        return layoutBinding.getRoot();
    }

    void initRv(List<TaskItem> taskItemList) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        taskItemAdapter = new TaskItemAdapter(taskItemList, (OpenItemsFragmentListener) getContext(),
                (taskItem) -> taskItemFragmentPresenter.changeStatusTaskItem(taskItem));
        layoutBinding.rvTask.setLayoutManager(layoutManager);
        layoutBinding.rvTask.setAdapter(taskItemAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback(this.getContext(), this));
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

    void showSnackBar() {
        Snackbar snackbar = Snackbar.make(
                layoutBinding.getRoot(),
                "Đã xóa",
                Snackbar.LENGTH_SHORT).setAction("Hoàn tác",
                v -> taskItemFragmentPresenter.undo()
        );
        snackbar.addCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                if (event != Snackbar.Callback.DISMISS_EVENT_ACTION) {
                    taskItemFragmentPresenter.deleteOnDatabase();
                }
                super.onDismissed(transientBottomBar, event);
            }
        });
        snackbar.show();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        if (getArguments() != null) {
            taskId = getArguments().getLong("taskId", 0);
        }
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        layoutBinding.fabAddTask.setOnClickListener(v -> addItemTaskDialog.show());
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        ChangeTopTitleListener changeTopTitleListener = (ChangeTopTitleListener) getContext();
        assert changeTopTitleListener != null;
        changeTopTitleListener.changeTopTitle(ChangeTopTitleListener.TASK_ITEM_TITLE, R.drawable.ic_back);
        super.onStart();
    }

    @Override
    public void onAddTask(int position) {
        taskItemAdapter.notifyItemInserted(position);
    }

    @Override
    public void onRemoveTask(int position) {
        taskItemAdapter.notifyItemRemoved(position);
    }

    @Override
    public void onUpdateTask(int position) {
        taskItemAdapter.notifyItemChanged(position);
    }

    @Override
    public void onGetListTask(List<TaskItem> taskItemList) {
        initRv(taskItemList);
    }

    @Override
    public void onSwipeLeft(int position) {
        if (getContext() != null) {
            new UpdateTaskItemDialog(this.getContext(),
                    taskItemFragmentPresenter,
                    position,
                    taskItemFragmentPresenter.getListTaskItem().get(position)).show();
        }
    }

    @Override
    public void onSwipeRight(int position) {
        showSnackBar();
        taskItemFragmentPresenter.deleteOnList(position);
    }
}
