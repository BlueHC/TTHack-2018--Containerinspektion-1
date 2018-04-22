package com.example.android.presentation.home

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.camera2basic.R
import com.example.android.data.viewmodel.InspectorViewModel
import com.example.android.presentation.camera.Camera2BasicFragment
import com.example.android.presentation.common.BaseFragment
import com.example.android.presentation.common.OverviewFragment
import com.example.android.presentation.home.LastSessionAdapter.OnInspectionItemListener
import kotlinx.android.synthetic.main.component_toolbar_standard.backBtn
import kotlinx.android.synthetic.main.component_toolbar_standard.pageTitleTv
import kotlinx.android.synthetic.main.component_toolbar_standard.settingsIv
import kotlinx.android.synthetic.main.fragment_start.lastSessionRv
import kotlinx.android.synthetic.main.fragment_start.prevBtn

class StartFragment : BaseFragment(), OnInspectionItemListener {
  override fun onInspectionClicked(string: String) {

  }

  lateinit var inspectorViewModel: InspectorViewModel

  val lastSeAdapter = LastSessionAdapter()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.fragment_start, container, false)


  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    inspectorViewModel = obtainInspectorViewModel()
    inspectorViewModel.setSessionList()



    initToolbar()

    lastSessionRv?.apply{
adapter= lastSeAdapter
      layoutManager = LinearLayoutManager(context)

    }


    inspectorViewModel?.sessionList.observe(this, Observer {
      it?.let {
        lastSeAdapter.setItems(it)
        lastSeAdapter.onInspectionItemListener = this

      }
    })

    //inspectorViewModel.uploadFile(Environment.getExternalStorageDirectory().toString() + "/Inspector/2pic.jpg")

  }

  private fun initToolbar() {
    backBtn?.apply {
      visibility = View.VISIBLE

      setOnClickListener { showSideBar() }
      setImageDrawable(activity?.getDrawable(R.drawable.ic_menu_white_24dp))
    }

    settingsIv?.apply {
      visibility = View.VISIBLE

      setOnClickListener { changeFragment(Camera2BasicFragment.newInstance()) }
      setImageDrawable(activity?.getDrawable(R.drawable.ic_photo_camera_white_24dp))
    }

    pageTitleTv?.apply {
      visibility = View.VISIBLE
      text = "Chief Inspector"
    }


    prevBtn?.apply {
      setOnClickListener { changeFragment(OverviewFragment.newInstance()) }

    }
  }


  companion object {
    fun newInstance() = StartFragment()
  }
}