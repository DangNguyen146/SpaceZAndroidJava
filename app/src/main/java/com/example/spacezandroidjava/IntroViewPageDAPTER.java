package com.example.spacezandroidjava;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class IntroViewPageDAPTER extends PagerAdapter {

    Context mContext;
    List<ScreenItem> mListScreen;

    public IntroViewPageDAPTER(Context mContext, List<ScreenItem> mListScreen) {
        this.mContext = mContext;
        this.mListScreen = mListScreen;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutScreen = inflater.inflate(R.layout.layout_screen, null);

        ImageView imageSlide = layoutScreen.findViewById(R.id.intro_Img);
        TextView title = layoutScreen.findViewById(R.id.introTitle);
        TextView des = layoutScreen.findViewById(R.id.introDes);

        title.setText(mListScreen.get(position).getTitle());
        des.setText(mListScreen.get(position).getDes());
        imageSlide.setImageResource(mListScreen.get(position).getScreeenImg());

        container.addView(layoutScreen);
        return layoutScreen;
    }

    @Override
    public int getCount() {
        return mListScreen.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
