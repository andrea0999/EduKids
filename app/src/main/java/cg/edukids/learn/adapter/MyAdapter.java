package cg.edukids.learn.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import cg.edukids.learn.fragments.AlphabetFragment;
import cg.edukids.learn.fragments.AnimalFragment;
import cg.edukids.learn.fragments.FruitFragment;

public class MyAdapter extends FragmentStateAdapter {

    public MyAdapter(FragmentManager fragmentManager, Lifecycle lifecycle){
        super(fragmentManager, lifecycle);
    }

    @Override
    public Fragment createFragment(int x){
        switch (x){
            case 0: return new AlphabetFragment();
            case 1: return new FruitFragment();
            case 2: return new AnimalFragment();
        }
        return new AnimalFragment();
    }

    @Override
    public int getItemCount(){
        return 3;
    }
}
