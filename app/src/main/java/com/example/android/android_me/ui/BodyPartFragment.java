package com.example.android.android_me.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BodyPartFragment extends Fragment {

    //Tag para identificar la clase en el Log
    private static final String TAG = "BodyPartFragment";

    //Constantes definidas para identificar las variables que se quieren mantener encaso de que se destruya la Activity
    //Se usaran para almacenar las variables en un Bundle en el metodo "onSaveInstanceState"
    public static final String IMAGE_ID_LIST = "image_ids";
    public static final String LIST_INDEX = "list_index";

    //Variable para guardar la lista de los IDs de todas las imagenes en los recursos
    private List<Integer> mImageIds;
    //Variable para seleccionar cual imagen mostrar en el fragmento
    private int mListIndex;

    /**
     * Constructor para instanciar el Fragment
     */
    public BodyPartFragment() {
    }

    /**
     * Inflates el Layout del Fragment y establece cualquier imagen de los recursos
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //si se encuentra un "Saved State" se cargan las variables guardadas
        if (savedInstanceState != null){
            mImageIds = savedInstanceState.getIntegerArrayList(IMAGE_ID_LIST);
            mListIndex = savedInstanceState.getInt(LIST_INDEX);
        }

        //Inflate el Fragment Layout de Android-Me
        View rootView = inflater.inflate(R.layout.fragment_body_part, container, false);

        //se obtiene la referencia del ImageView en el Fragment Layout
        final ImageView imageView = (ImageView) rootView.findViewById(R.id.body_part_image_view);

        //indica la imagen de los recursos a mostrar
        //para obtener la lista de IDs se usa la clase Utility "AndroidImageAssets"
        //imageView.setImageResource(AndroidImageAssets.getHeads().get(0));
        //Elegir dinamicamente la imagen a mostrar
        if (mImageIds != null){
            //se debe declarar final para poder usarla dentro del ClickListener
            imageView.setImageResource(mImageIds.get(mListIndex));

            //Se agrega ClickListener sobre la imagen, al hacer click se aumenta en uno el indice y se muestra la siguiente imagen
            imageView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    //incrementa indice hasta la ultima, se resetea cuando llega a la ultima
                    if(mListIndex < mImageIds.size()-1){
                        mListIndex++;
                    }else{
                        mListIndex = 0;
                    }
                    Log.i(TAG, "mListIndex= "+mListIndex+" - mImageIds.size()= "+ mImageIds.size());
                    imageView.setImageResource(mImageIds.get(mListIndex));
                }
            });
        }else{
            Log.v(TAG, "Este fragmento tiene una lista null de IDs de Imagenes.");
        }

        return rootView;
    }

    //Metodos Set para definir la lista de imagenes y cual imagen mostrar en un momento dado

    public void setmImageIds(List<Integer> mImageIds) {
        this.mImageIds = mImageIds;
    }

    public void setmListIndex(int mListIndex) {
        this.mListIndex = mListIndex;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //estructura bastante similar a un Map
        outState.putIntegerArrayList(IMAGE_ID_LIST, (ArrayList<Integer>) mImageIds);
        outState.putInt(LIST_INDEX, mListIndex);
    }
}
