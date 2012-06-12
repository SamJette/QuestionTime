package com.ehb.questiontime;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class QuestionAdapter extends ArrayAdapter<Question> {

	// final LayoutInflater layoutInflater = (LayoutInflater)
	// this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	private final Activity activity;
	private final int resource;
	private LayoutInflater inflater = null;

	public QuestionAdapter(Activity _activity, int _resource,
			ArrayList<Question> _items) {
		super(_activity, _resource, _items);
		resource = _resource;
		activity = _activity;
		inflater = LayoutInflater.from(_activity.getBaseContext());
		// TODO Auto-generated constructor stub
	}

	public static class ViewHolder {
		TextView questionTextView;
		Button pushButton;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		ViewHolder holder;

		if (convertView == null) {
			v = inflater.inflate(resource, null);
			holder = new ViewHolder();
			holder.questionTextView = (TextView) v
					.findViewById(R.id.vragenTextTextView);
			holder.pushButton = (Button) v.findViewById(R.id.buttonPush);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}

		Question tempQuestion = getItem(position);

		holder.questionTextView.setText(tempQuestion.questionText);
		holder.questionTextView.setTag(position);
		holder.pushButton.setTag(position);

		final OnClickListener makeListener = new OnClickListener() {

			public void onClick(View v) {
				LinearLayout ll = (LinearLayout) v.getParent();
				TextView tv = (TextView) ll.getChildAt(0);
				Integer pos = (Integer) tv.getTag();
				// ((QuestionTab) activity).makeInfo(pos);

			}
		};
		holder.questionTextView.setOnClickListener(makeListener);

		final OnClickListener pushListener = new OnClickListener() {

			public void onClick(View v) {

				LinearLayout ll = (LinearLayout) v.getParent();
				Button tv = (Button) ll.getChildAt(0);
				Integer pos = (Integer) tv.getTag();
				// ((QuestionTab) activity).modelInfo(pos);

			}
		};
		holder.pushButton.setOnClickListener(pushListener);
		return v;
	}
}
