/*
 * Copyright 2018 ACINQ SAS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package info.apatrix.foodapp.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;



import info.apatrix.foodapp.fragments.HistoryFragment;
import info.apatrix.foodapp.fragments.PendingOrPastOrderFragment;

public class SimplePagerAdapter extends FragmentStatePagerAdapter {
  int tabCount;



  //Constructor to the class
  public SimplePagerAdapter(FragmentManager fm, int tabCount) {
    super(fm);
    //Initializing tab count
    this.tabCount= tabCount;
  }

  //Overriding method getItem
  @Override
  public Fragment getItem(int position) {
    //Returning the current tabs
    Bundle bundle = new Bundle();
    switch (position) {
      case 0:
        PendingOrPastOrderFragment tab1 = new PendingOrPastOrderFragment();
        bundle.putInt("status", 2);
        tab1.setArguments(bundle);
        return tab1;
      case 1:
        PendingOrPastOrderFragment tab2 = new PendingOrPastOrderFragment();
        bundle.putInt("status", 1);
        tab2.setArguments(bundle);
        return tab2;

      default:
        return null;
    }
  }

  //Overriden method getCount to get the number of tabs
  @Override
  public int getCount() {
    return tabCount;
  }
}

