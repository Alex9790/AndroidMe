package com.example.android.android_me.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

//esta Activity es responsalbe de mostrar la Master List de todas las imagenes
public class MainActivity extends AppCompatActivity implements MasterListFragment.OnImageClickListener{

    //Tag para identificar la clase en el Log
    private static final String TAG = "MainActivity";

    //ariables para guardar el Id de las imagenes seleccionadas
    private int headIndex;
    private int bodyIndex;
    private int legIndex;

    // Track whether to display a two-pane or single-pane UI
    // A single-pane display refers to phone screens, and two-pane to larger tablet screens
    private boolean mTwoPane;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Determine if you're creating a two-pane or single-pane display
        if(findViewById(R.id.android_me_linear_layout) != null) {
            // This LinearLayout will only initially exist in the two-pane tablet case
            mTwoPane = true;

            // Change the GridView to space out the images more on tablet
            GridView gridView = (GridView) findViewById(R.id.images_grid_view);
            gridView.setNumColumns(2);

            // Getting rid of the "Next" button that appears on phones for launching a separate activity
            Button nextButton = (Button) findViewById(R.id.next_button);
            nextButton.setVisibility(View.GONE);

            if(savedInstanceState == null) {
                // In two-pane mode, add initial BodyPartFragments to the screen
                FragmentManager fragmentManager = getSupportFragmentManager();

                // Creating a new head fragment
                BodyPartFragment headFragment = new BodyPartFragment();
                headFragment.setmImageIds(AndroidImageAssets.getHeads());
                // Add the fragment to its container using a transaction
                fragmentManager.beginTransaction()
                        .add(R.id.head_container, headFragment)
                        .commit();

                // New body fragment
                BodyPartFragment bodyFragment = new BodyPartFragment();
                bodyFragment.setmImageIds(AndroidImageAssets.getBodies());
                fragmentManager.beginTransaction()
                        .add(R.id.body_container, bodyFragment)
                        .commit();

                // New leg fragment
                BodyPartFragment legFragment = new BodyPartFragment();
                legFragment.setmImageIds(AndroidImageAssets.getLegs());
                fragmentManager.beginTransaction()
                        .add(R.id.leg_container, legFragment)
                        .commit();
            }
        } else {
            // We're in single-pane mode and displaying fragments on a phone in separate activities
            mTwoPane = false;
        }
    }

    //Para permitir la comunicacion entre los fragmentos contenidos en un Activity
    //se deben implementar metodos Callbacks
    //Para este caso, la clase MasterListFragment realiza un Callback a traves de la interfaz OnImageClickListener
    //utilizando el metodo onImageSelected
    @Override
    public void onImageSelected(int position) {
        //se crea un Toast que muestra la posicion seleccionada
        Toast.makeText(this, "Se hizo click en la posicion = " + position, Toast.LENGTH_LONG).show();

        //esta variable contendra el valor 0 para idenatificar head, 1 para body y 2 para legs
        // 0 => Head --- 1 => Body --- 2 => Leg
        //dividir entre 12 nos da estos 3 numeros enteros, porque hay 12 imagenes para cada parte del cuerpo
        //0-11 = Head --- 12-23 = Body --- 24-35 = Leg
        int bodyPartNumber = position/12;

        //se traduce el valor obtenido a una escala del 0 al 11, sn importar donde se hizo click
        int listIndex = position - 12*bodyPartNumber;

        // Handle the two-pane case and replace existing fragments right when a new image is selected from the master list
        if (mTwoPane) {
            // Create two=pane interaction
            BodyPartFragment newFragment = new BodyPartFragment();

            // Set the currently displayed item for the correct body part fragment
            switch (bodyPartNumber) {
                case 0:
                    // A head image has been clicked
                    // Give the correct image resources to the new fragment
                    newFragment.setmImageIds(AndroidImageAssets.getHeads());
                    newFragment.setmListIndex(listIndex);
                    // Replace the old head fragment with a new one
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.head_container, newFragment)
                            .commit();
                    break;
                case 1:
                    newFragment.setmImageIds(AndroidImageAssets.getBodies());
                    newFragment.setmListIndex(listIndex);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.body_container, newFragment)
                            .commit();
                    break;
                case 2:
                    newFragment.setmImageIds(AndroidImageAssets.getLegs());
                    newFragment.setmListIndex(listIndex);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.leg_container, newFragment)
                            .commit();
                    break;
                default:
                    break;
            }
        } else {
            //se almacena el id para la parte correcta del cuerpo
            switch (bodyPartNumber){
                case 0: headIndex = listIndex; break;
                case 1: bodyIndex = listIndex; break;
                case 2: legIndex = listIndex; break;
                default: break;
            }
            /*Log.i(TAG, "listIndex= "+listIndex);
            Log.i(TAG, "headIndex= "+headIndex);
            Log.i(TAG, "bodyIndex= "+bodyIndex);
            Log.i(TAG, "legIndex= "+legIndex);
            */
            //se almacenan los index en un bundle
            Bundle b = new Bundle();
            b.putInt("headIndex", headIndex);
            b.putInt("bodyIndex", bodyIndex);
            b.putInt("legIndex", legIndex);

            Log.i(TAG, "b.headIndex= "+b.getInt("headIndex"));
            Log.i(TAG, "b.bodyIndex= "+b.getInt("bodyIndex"));
            Log.i(TAG, "b.legIndex= "+b.getInt("legIndex"));

            //se adjunta el Bundle a un Intent y se inicia AndroidMeActivity
            final Intent intent = new Intent(this, AndroidMeActivity.class);
            intent.putExtras(b);

            //se obtiene referencia del boton definido en fragment_master_list layout, para ejecutar el Intent cuando sea presionado
            Button nextButton = (Button)  findViewById(R.id.next_button);
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(intent);
                }
            });
        }

    }

}
