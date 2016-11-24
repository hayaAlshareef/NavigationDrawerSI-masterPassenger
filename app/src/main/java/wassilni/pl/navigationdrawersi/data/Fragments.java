package wassilni.pl.navigationdrawersi.data;

import android.support.v4.app.Fragment;

import wassilni.pl.navigationdrawersi.ui.FragmentAbout;
import wassilni.pl.navigationdrawersi.ui.FragmentFifth;
import wassilni.pl.navigationdrawersi.ui.FragmentFour;
import wassilni.pl.navigationdrawersi.ui.FragmentOne;
import wassilni.pl.navigationdrawersi.ui.FragmentThree;
import wassilni.pl.navigationdrawersi.ui.FragmentTwo;


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
