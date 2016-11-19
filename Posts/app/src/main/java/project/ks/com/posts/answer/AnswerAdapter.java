package project.ks.com.posts.answer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import project.ks.com.posts.R;
import project.ks.com.posts.bean.Answer;

import java.util.List;

/**
 * Created by Gopal on 01-Sep-16.
 */
public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.MyViewHolder> {

    private List<Answer> answerList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView answerDetail, username, date;
        ImageView answerUserImage;

        public MyViewHolder(View view) {
            super(view);
            answerDetail = (TextView) view.findViewById(R.id.viewAnswerDetail);
            username = (TextView) view.findViewById(R.id.viewAnswerUsername);
            date = (TextView) view.findViewById(R.id.viewAnswerDate);
            answerUserImage = (ImageView) view.findViewById(R.id.viewAnswerUserImage);
        }
    }


    public AnswerAdapter(List<Answer> answerList) {
        this.answerList = answerList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.answer_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Answer answer = answerList.get(position);
        holder.answerDetail.setText(answer.getAnswer());
        holder.username.setText(answer.getAnswerBy().getFirstName()+" "+answer.getAnswerBy().getLastName());
        holder.date.setText(answer.getDate());
        holder.answerUserImage.setImageResource(R.mipmap.ic_account_circle_white_24dp);
    }

    @Override
    public int getItemCount() {
        return answerList.size();
    }
}
