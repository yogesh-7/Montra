package com.dev_yogesh.montra.ui.fragment.onBoardingFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.viewpager.widget.PagerAdapter
import com.dev_yogesh.montra.R
import com.dev_yogesh.montra.model.adapter.OnBoarding


class OnBoardingPagerAdapter(var context: Context, var screenList: ArrayList<OnBoarding>) :
    PagerAdapter() {
    override fun getCount(): Int = screenList.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layoutScreen = inflater.inflate(R.layout.item_onboarding, null)
        //val binding =
           // ViewDataBinding.inflate(inflater, R.layout.item_onboarding, container, false)



        layoutScreen.findViewById<ImageView>(R.id.ivOnBoardingBackground).setImageResource(screenList[position].bgImage)
        layoutScreen.findViewById<TextView>(R.id.tvItemTitle).text=screenList[position].title
        layoutScreen.findViewById<TextView>(R.id.tvItemDescription).text= screenList[position].description

        container.addView(layoutScreen)

        return layoutScreen
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }
}