package project.ks.com.posts.question;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import project.ks.com.posts.R;
import project.ks.com.posts.bean.Question;

import java.util.List;

/**
 * Created by Gopal on 31-Aug-16.
 */
public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.MyViewHolder> {

    private List<Question> questionList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView questionTitle, questionDetail, username, userdetail;

        public MyViewHolder(View view) {
            super(view);
            questionTitle = (TextView) view.findViewById(R.id.questionTitle);
            questionDetail = (TextView) view.findViewById(R.id.questionDetail);

        }
    }


    public QuestionAdapter(List<Question> questionList) {
        this.questionList = questionList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Question question = questionList.get(position);
        holder.questionTitle.setText(question.getTitle());
        holder.questionDetail.setText(question.getDetail());

    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }
}
