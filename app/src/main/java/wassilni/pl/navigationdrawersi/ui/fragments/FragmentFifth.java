package wassilni.pl.navigationdrawersi.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import wassilni.pl.navigationdrawersi.R;

/**
 * Created by haya on 10/19/2016.
 */

public class FragmentFifth extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fifth, container, false);
      //  Button edit=(Button)view.findViewById(R.id.editinfo);
          /*  edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent e=new Intent(getActivity(),editpage.class);
                    startActivity(e);
                }
            });*/
       return view;

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
