package com.dev_yogesh.montra.ui.fragment.onBoardingFragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dev_yogesh.montra.R
import com.dev_yogesh.montra.databinding.FragmentOnBoardingBinding
import com.dev_yogesh.montra.model.adapter.OnBoarding
import com.dev_yogesh.montra.ui.fragment.HomeFragment.HomeFragment

class OnBoardingFragment : Fragment() {



    private var _binding: FragmentOnBoardingBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    private val listScreen = arrayListOf<OnBoarding>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnBoardingBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        initListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initListener()= with(binding) {
        tvOnBoardSkip.setOnClickListener {
            passedOnBoarding()
            //openNextPage()
        }
        btnStart.setOnClickListener {
            passedOnBoarding()
            //openNextPage()
        }

    }

    private fun passedOnBoarding() {
        findNavController().navigate(OnBoardingFragmentDirections.actionOnBoardingFragmentToHomeFragment())
    }

    private fun initList()= with(binding){
        listScreen.add(
            OnBoarding(
                R.drawable.ic_total_control_of_your_money,
                getString(R.string.onboard_1_title),
                getString(R.string.onboard_1_desc)
            ),
        )
        listScreen.add(
            OnBoarding(
                R.drawable.ic_know_where_your_money_goes,
                getString(R.string.onboard_2_title),
                getString(R.string.onboard_2_desc)
            ),
        )
        listScreen.add(
            OnBoarding(
                R.drawable.ic_planning_ahead,
                getString(R.string.onboard_3_title),
                getString(R.string.onboard_3_desc)
            ),
        )
        val introViewPagerAdapter = OnBoardingPagerAdapter(requireContext(), listScreen)
        introViewPager.adapter = introViewPagerAdapter
        tlInidcator.setupWithViewPager(introViewPager)
    }



    companion object {
        val TAG = this::class.toString()
        fun getInstance() = OnBoardingFragment()
    }
}