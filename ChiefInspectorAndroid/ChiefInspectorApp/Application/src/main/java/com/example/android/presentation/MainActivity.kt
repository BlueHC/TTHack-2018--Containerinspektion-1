/*
 * Copyright 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.presentation

import android.os.Bundle
import android.os.Environment
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.android.camera2basic.R
import com.example.android.camera2basic.R.id
import com.example.android.enum.SideMenuItem
import com.example.android.presentation.home.StartFragment
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import java.io.File

class MainActivity : AppCompatActivity() {
  lateinit var result: Drawer


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)


    val directory = File(Environment.getExternalStorageDirectory().toString() + "/Inspector")

    directory.listFiles().forEach { Log.d("TAG", "HALLO" + it.path) }


    changeFragment(StartFragment.newInstance())
    initDrawer()
    initToolbar()

  }

  fun showMenu() {
    //drawer_layout?.openDrawer(left_drawer)
    result.openDrawer()
  }

  private fun initToolbar() {


  }

  private fun initDrawer() {

    val headerResult = AccountHeaderBuilder()
      .withActivity(this)
      .withHeaderBackground(R.drawable.blue_button)
      //.withHeaderBackground(R.drawable.header)
      .addProfiles(
        ProfileDrawerItem().withName(
          "Max Mustermann").withEmail(
          "MaNr. 003048171761819").withIcon(
          resources.getDrawable(R.drawable.profile))
      )
      .withSelectionListEnabledForSingleProfile(false)
      .withOnAccountHeaderListener(
        AccountHeader.OnAccountHeaderListener { _, _, _ -> false })
      .build()





    result = DrawerBuilder()
      .withAccountHeader(headerResult)
      .withActivity(this)
      //.withOnDrawerItemClickListener(this)
      .build()

    result.addItem(SecondaryDrawerItem().withIdentifier(1).withName(
      SideMenuItem.LOGIN.title))

    result.addItem(SecondaryDrawerItem().withIdentifier(2).withName(
      SideMenuItem.EINSTELLUNGEN.title))
    result.addItem(SecondaryDrawerItem().withIdentifier(3).withName(
      SideMenuItem.CEDEX.title))

  }

  fun changeFragment(newInstance: Fragment) {
    supportFragmentManager.beginTransaction()
      .replace(id.container, newInstance)
      .addToBackStack(null)
      .commit()
  }

  override fun onBackPressed() {
    val manager = supportFragmentManager
    manager.popBackStackImmediate()

  }


}
