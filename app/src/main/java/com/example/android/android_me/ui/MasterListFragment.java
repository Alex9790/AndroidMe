package com.example.android.android_me.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

import androidx.fragment.app.Fragment;

// This fragment displays all of the AndroidMe images in one large list
// The list appears as a grid of images
public class MasterListFragment extends Fragment {

    //Se define una interfaz que triggers un callback en el Activity Host
    //el callback sera un metodo que contiene informacion sobre la posicion del grid de imagenes
    //en la que el usuario hizo click
    OnImageClickListener  mCallback; //el nombre de la interfaz "OnImageClickListener" se puede llamar cualquier cosa, el tutorial lo llama asi en este caso

    //esta interfaz llama a un metodo del Host Activity llamado onImageSelected
    public interface OnImageClickListener{
        void onImageSelected(int position);
    }

    //override onAttach para asegurarse que la Activity Host tiene implementado el metodo CallBack, sino tirar excepcion
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mCallback = (OnImageClickListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "Debe implementar OnImageClickListener.");
        }
    }

    // Mandatory empty constructor
    public MasterListFragment() {
    }

    // Inflates the GridView of all AndroidMe images
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);

        // Get a reference to the GridView in the fragment_master_list xml layout file
        GridView gridView = (GridView) rootView.findViewById(R.id.images_grid_view);

        // Create the adapter
        // This adapter takes in the context and an ArrayList of ALL the image resources to display
        MasterListAdapter mAdapter = new MasterListAdapter(getContext(), AndroidImageAssets.getAll());

        // Set the adapter on the GridView
        gridView.setAdapter(mAdapter);

        //se configura un ClickListener sobre el Grid, para llamar al metodo Callback onImageSelected
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //trigger el metodo Callback y pasa la posicion donde se hizo click
                mCallback.onImageSelected(position);
            }
        });


        // Return the root view
        return rootView;
    }

}
