package com.example.listtodo_ps25812.Untilities;

import android.content.Context;
import android.graphics.Canvas;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listtodo_ps25812.Listener.SwipeItemListener;
import com.example.listtodo_ps25812.R;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class ItemTouchHelperCallback extends ItemTouchHelper.SimpleCallback {
    Context _context;
    SwipeItemListener _swipeItemListener ;
    public ItemTouchHelperCallback(Context context,SwipeItemListener swipeItemListener) {
        super(0,ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT);
        _context = context;
        _swipeItemListener = swipeItemListener;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlags = 0;
        int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        switch (direction) {
            case ItemTouchHelper.RIGHT:
                    _swipeItemListener.onSwipeRight(viewHolder.getLayoutPosition() );
                break;
            case ItemTouchHelper.LEFT:
                _swipeItemListener.onSwipeLeft(viewHolder.getLayoutPosition());
                break;
        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                .addSwipeLeftActionIcon(R.drawable.ic_edit)
                .addSwipeRightActionIcon(R.drawable.ic_delete)
                .setActionIconTint(ContextCompat.getColor(_context, R.color.white))
                .addSwipeLeftBackgroundColor(ContextCompat.getColor(_context, R.color.green))
                .addSwipeRightBackgroundColor(ContextCompat.getColor(_context, R.color.red))
                .create()
                .decorate();
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}
