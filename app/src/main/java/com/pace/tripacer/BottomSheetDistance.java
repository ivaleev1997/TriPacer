package com.pace.tripacer;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BottomSheetDistance extends BottomSheetDialogFragment {

    private ContentAdapter mContentAdapter;

    public static BottomSheetDistance getInstance() {
        return new BottomSheetDistance();
    }

    public BottomSheetDistance() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.distance_recycler, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mContentAdapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(mContentAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private TextView mTextView;

        private ViewHolder(final LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_distance, parent, false));
            mImageView = itemView.findViewById(R.id.imageDistance);
            mTextView = itemView.findViewById(R.id.nameDistance);
        }
    }

    public class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {

        ArrayList<String> namesDistanceArrayList;
        private final Drawable[] avatarsDrawable;

        public ContentAdapter(Context context) {
            Resources resources = context.getResources();
            TypedArray distanceAvatarArray = resources.obtainTypedArray(R.array.distance_avatars);
            TypedArray namesArray = resources.obtainTypedArray(R.array.distance_names);

            avatarsDrawable = new Drawable[distanceAvatarArray.length()];
            namesDistanceArrayList = new ArrayList<>();
            for (int i = 0; i < distanceAvatarArray.length(); i ++) {
                avatarsDrawable[i] = distanceAvatarArray.getDrawable(i);
                namesDistanceArrayList.add(namesArray.getString(i));
            }
            distanceAvatarArray.recycle();
            namesArray.recycle();
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.mTextView.setText(namesDistanceArrayList.get(position));
            holder.mImageView.setImageDrawable(avatarsDrawable[position]);
        }

        @Override
        public int getItemCount() {
            return namesDistanceArrayList.size();
        }
    }


}
