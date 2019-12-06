/*
* Copyright (C) 2017 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*  	http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.android.android_me.ui;

import android.os.Bundle;
import android.util.Log;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

// This activity will display a custom Android image composed of three body parts: head, body, and legs
public class AndroidMeActivity extends AppCompatActivity {

    //Tag para identificar la clase en el Log
    private static final String TAG = "AndroidMeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_me);

        //se chequea el Saved State para evitar crear de nuevo los Fragments
        if (savedInstanceState == null){
            //Nueva Instancia de BodyPartFragment - para la cabeza del Android-Me
            BodyPartFragment headFragment = new BodyPartFragment();
            //Nueva Instancia de BodyPartFragment - para el cuerpo del Android-Me
            BodyPartFragment bodyFragment = new BodyPartFragment();
            //Nueva Instancia de BodyPartFragment - para las piernas del Android-Me
            BodyPartFragment legsFragment = new BodyPartFragment();

            //Setting la lista de recursos de imagenes y definiendo cual usar
            headFragment.setmImageIds(AndroidImageAssets.getHeads());
            bodyFragment.setmImageIds(AndroidImageAssets.getBodies());
            legsFragment.setmImageIds(AndroidImageAssets.getLegs());

            // Se recupera el id que se envio a traves del Intent y se usa para mostrar la imagen seleccionada
            //headFragment.setmListIndex(2);
            int headIndex = getIntent().getIntExtra("headIndex", 0);
            Log.i(TAG, "headIndex= "+headIndex);
            headFragment.setmListIndex(headIndex);

            //bodyFragment.setmListIndex(3);
            int bodyIndex = getIntent().getIntExtra("bodyIndex", 0);
            Log.i(TAG, "bodyIndex= "+bodyIndex);
            bodyFragment.setmListIndex(bodyIndex);

            //legsFragment.setmListIndex(4);
            int legIndex = getIntent().getIntExtra("legIndex", 0);
            Log.i(TAG, "legIndex= "+legIndex);
            legsFragment.setmListIndex(legIndex);

            //Se usa un FragmentManager y Transaction para agregar el Fragment a la pantalla
            FragmentManager fragmentManager = getSupportFragmentManager();

            //Transaction del Fragment
            //Añade nuestro fragmento al contenedor especificado en el Layout del Activity
            //Head Android-Me
            fragmentManager.beginTransaction()
                    .add(R.id.head_container, headFragment) //add, replace, remove
                    .commit();  //se llama commit cuando se este listo para completar la Transaccion

            //Body Android-Me
            fragmentManager.beginTransaction()
                    .add(R.id.body_container, bodyFragment) //add, replace, remove
                    .commit();  //se llama commit cuando se este listo para completar la Transaccion

            //Legs Android-Me
            fragmentManager.beginTransaction()
                    .add(R.id.legs_container, legsFragment) //add, replace, remove
                    .commit();  //se llama commit cuando se este listo para completar la Transaccion
        }
    }
}
