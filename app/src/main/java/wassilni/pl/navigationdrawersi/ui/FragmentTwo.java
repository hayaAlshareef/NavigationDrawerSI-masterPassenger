package wassilni.pl.navigationdrawersi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import wassilni.pl.navigationdrawersi.R;

public class FragmentTwo extends Fragment {

   // @InjectView(R.id.circleLayout)
    //LinearLayout circleLayout;
   Button pickupB,dropoffB,searchB;
    public static EditText pickupET,dropoffET;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup containter,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, containter, false);
          searchB=(Button) view.findViewById(R.id.searchb);
          pickupB=(Button) view.findViewById(R.id.pickupB);
          dropoffB=(Button) view.findViewById(R.id.dropoffB);
        pickupET=(EditText) view.findViewById(R.id.pickupET);
        dropoffET=(EditText) view.findViewById(R.id.dropOffLET);
        pickupET.setEnabled(false);
        dropoffET.setEnabled(false);
        ///to open search page --- before user click this button , user must be enter all requierd fields then we will send it to next page to get result
        // user can select one or two from result then click requiest : 1-the requ display it in fragment one page(with wait status) , 2-the req send it to rhe driver
        //in the search page the user can view any driver page
        searchB.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent i=new Intent(getActivity(),Search.class);
                 startActivity(i);
             }
         });
        pickupB.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent i=new Intent(getActivity(),passengerPickupLocation.class);
                 i.putExtra("Type","pickup");
                 startActivity(i);
             }
         });

        dropoffB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),passengerPickupLocation.class);
                i.putExtra("Type","dropoff");
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
