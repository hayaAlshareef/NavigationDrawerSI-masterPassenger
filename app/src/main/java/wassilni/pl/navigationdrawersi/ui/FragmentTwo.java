package wassilni.pl.navigationdrawersi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import wassilni.pl.navigationdrawersi.R;

public class FragmentTwo extends Fragment {

   // @InjectView(R.id.circleLayout)
    //LinearLayout circleLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup containter,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, containter, false);
          Button search=(Button) view.findViewById(R.id.searchbutton);
        ///to open search page --- before user click this button , user must be enter all requierd fields then we will send it to next page to get result
        // user can select one or two from result then click requiest : 1-the requ display it in fragment one page(with wait status) , 2-the req send it to rhe driver
        //in the search page the user can view any driver page
        search.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent i=new Intent(getActivity(),Search.class);
                 startActivity(i);
             }
         });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
      //  ButterKnife.reset(this);
    }



}
