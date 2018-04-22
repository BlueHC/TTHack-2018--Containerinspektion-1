package com.example.android.presentation.common

import android.support.v4.app.Fragment
import com.example.android.data.viewmodel.InspectorViewModel
import com.example.android.data.viewmodel.ViewModelFactory
import com.example.android.presentation.MainActivity

open class BaseFragment : Fragment() {

  fun changeFragment(newInstance: Fragment) {
    when (activity) {
      is MainActivity -> (activity as MainActivity).changeFragment(newInstance)
    }
  }

  fun onBackPressed() {
    when (activity) {
      is MainActivity -> (activity as MainActivity).onBackPressed()
    }
  }

  fun showSideBar() {
    when (activity) {
      is MainActivity -> (activity as MainActivity).showMenu()
    }
  }

  fun obtainInspectorViewModel(): InspectorViewModel {
    return ViewModelFactory.obtainInspectorViewModel(activity!!)
  }



}