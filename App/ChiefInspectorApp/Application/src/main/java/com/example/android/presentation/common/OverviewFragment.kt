package com.example.android.presentation.common

import android.os.Bundle
import android.os.Environment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.camera2basic.R
import com.example.android.data.viewmodel.InspectorViewModel
import com.example.android.presentation.EventAdapter
import kotlinx.android.synthetic.main.component_toolbar_standard.backBtn
import kotlinx.android.synthetic.main.fragment_overview.imageRv

open class OverviewFragment : BaseFragment() {

  lateinit var inspectorViewModel: InspectorViewModel

  var eventAdapter = EventAdapter()

  companion object {
    @JvmStatic
    fun newInstance(): OverviewFragment = OverviewFragment()

  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.fragment_overview, container, false)


  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    backBtn?.apply {
      visibility=View.VISIBLE
      setOnClickListener { onBackPressed() }
    }

    inspectorViewModel = obtainInspectorViewModel()
    val filesReponseList =
      inspectorViewModel.getFilesReponseList(Environment.getExternalStorageDirectory().toString() + "/Inspector")

    filesReponseList.forEach { Log.d("TAG", "HALLO" + it.path) }
    eventAdapter.setItems(filesReponseList)

    imageRv?.apply {
      layoutManager = LinearLayoutManager(activity)
      adapter = eventAdapter


    }

  }

}