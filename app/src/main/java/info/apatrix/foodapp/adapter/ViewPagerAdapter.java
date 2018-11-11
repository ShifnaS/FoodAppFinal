package info.apatrix.foodapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import info.apatrix.foodapp.fragments.FoodFragment;
import info.apatrix.foodapp.fragments.ProductsFragment;
import info.apatrix.foodapp.model.SubCategoryList;

/**
 * Created by pc on 21-Oct-18.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<SubCategoryList> item;
    FoodFragment.UpdateBadge updateBadge;
    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    public ViewPagerAdapter(FragmentManager manager, ArrayList<SubCategoryList> item, FoodFragment.UpdateBadge updateBadge) {
        super(manager);
        this.item=item;
        this.updateBadge=updateBadge;
    }

    @Override
    public Fragment getItem(int position) {
        SubCategoryList obj=item.get(position);
        Log.e("my parent id ","////////////////////// "+obj.getSubcat_id());
        return ProductsFragment.newInstance(obj.getSubcat_id(),updateBadge);

    }
    @Override
    public CharSequence getPageTitle(int position) {
        SubCategoryList obj=item.get(position);
        return obj.getSubcatname();
    }
    @Override
    public int getCount() {
        return item.size();
    }



    public void swapArray(ArrayList arrayList) {
        item = arrayList;
        notifyDataSetChanged();
    }

    /**
     * Returns current data cursor
     * @return  Cursor with two columns {store_id, store_name}.
     */
    public ArrayList getArray() {
        return item;
    }



}

