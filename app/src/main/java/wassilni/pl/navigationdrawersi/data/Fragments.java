package wassilni.pl.navigationdrawersi.data;

import android.support.v4.app.Fragment;

import wassilni.pl.navigationdrawersi.ui.fragments.FragmentAbout;
import wassilni.pl.navigationdrawersi.ui.fragments.FragmentFifth;
import wassilni.pl.navigationdrawersi.ui.fragments.FragmentFour;
import wassilni.pl.navigationdrawersi.ui.fragments.FragmentOne;
import wassilni.pl.navigationdrawersi.ui.fragments.FragmentThree;
import wassilni.pl.navigationdrawersi.ui.fragments.FragmentTwo;


public enum Fragments {

    ONE(FragmentOne.class), TWO(FragmentTwo.class), THREE(FragmentThree.class), FOUR(FragmentFour.class), FiFth(FragmentFifth.class), ABOUT(
            FragmentAbout.class);

    final Class<? extends Fragment> fragment;

    private Fragments(Class<? extends Fragment> fragment) {
        this.fragment = fragment;
    }

    public String getFragment() {
        return fragment.getName();
    }
}
