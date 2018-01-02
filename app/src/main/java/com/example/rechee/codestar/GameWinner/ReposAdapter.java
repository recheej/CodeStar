package com.example.rechee.codestar.GameWinner;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rechee.codestar.R;

import java.util.List;

/**
 * Created by Rechee on 1/1/2018.
 */

public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.ViewHolder> {

    private final List<Repo> repos;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView repoNameTextView;
        public TextView repoStarsTextView;
        public ViewHolder(View itemView) {
            super(itemView);

            repoNameTextView = itemView.findViewById(R.id.textView_repoName);
            repoStarsTextView = itemView.findViewById(R.id.textView_numberOfStars);
        }
    }

    public ReposAdapter(List<Repo> repos){
        this.repos = repos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.layout_repo, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Repo repo = repos.get(position);
        Context context = holder.repoNameTextView.getContext();

        holder.repoNameTextView.setText(repo.getName());
        holder.repoStarsTextView.setText(context.getString(R.string.number_of_stars, repo.getStargazersCount()));
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }
}
