package wassilni.pl.navigationdrawersi.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import wassilni.pl.navigationdrawersi.R;


/**
 * Created by haya on 10/12/2016.
 */

public class FragmentFour extends Fragment {


   // @InjectView(R.id.circleLayout)
   // LinearLayout circleLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_four, container, false);
       // ButterKnife.inject(this, view);
        //((GradientDrawable) circleLayout.getBackground())
              //  .setColor(getResources().getColor(R.color.material_blue));
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
       // ButterKnife.reset(this);
    }
}
