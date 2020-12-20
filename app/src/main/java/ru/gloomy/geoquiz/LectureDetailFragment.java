package ru.gloomy.geoquiz;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class LectureDetailFragment extends Fragment {

    private long lectureId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lecture_detail, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null) {
           // TextView title =  view.findViewById(R.id.textTitle);
            Lecture lecture = Lecture.lectures[(int) lectureId];
          //  title.setText(lecture.getTitle());
            TextView description = view.findViewById(R.id.textDescription);
            description.setText(lecture.getLecture());
        }
    }

    public void setLecture(long id) {
        this.lectureId = id;
    }
}