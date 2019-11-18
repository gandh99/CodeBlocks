package com.gandh99.codeblocks;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TabsPagerAdapter extends FragmentPagerAdapter {
  private List<Fragment> fragmentList = new ArrayList<>();

  public TabsPagerAdapter(@NonNull FragmentManager fm) {
    super(fm);
  }

  public void addFragment(Fragment fragment) {
    fragmentList.add(fragment);
  }

  @NonNull
  @Override
  public Fragment getItem(int position) {
    return fragmentList.get(position);
  }

  @Override
  public int getCount() {
    return fragmentList.size();
  }
}
