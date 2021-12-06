package com.testosterolapp.dogbreeds.favorites

import android.content.res.Configuration
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.testosterolapp.dogbreeds.R
import com.testosterolapp.dogbreeds.database.DaoRepository
import com.testosterolapp.dogbreeds.util.LinearLayoutManagerWrapper
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.ConstraintLayout


class FavoritesActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        initUi(resources.configuration.orientation)
    }

    private fun initUi(orientation: Int) {
        val viewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManagerWrapper(this)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.setHasFixedSize(true)
        val adapter = FavoritesAdapter(this)
        recyclerView.adapter = adapter

        val repositoryDao = DaoRepository(this)
        viewModel.init(repositoryDao)

        viewModel.allBreedPictures!!.observe(this) { breedPictures ->
            adapter.submitList(breedPictures)
        }
        viewModel.filterFksAll.value = 0

        val breedsFk = repositoryDao.getListOfFavoriteBreeds() as MutableList
        val breedNames = repositoryDao.getListOfFavoriteBreedNames(breedsFk) as MutableList
        breedNames.add(breedNames.size, "All")
        breedsFk.add(breedsFk.size, 0)

        val map: Map<String, Int> = breedNames.sortedBy { it }.zip(breedsFk.sortedBy { it }).toMap()

        val spinner = findViewById<Spinner>(R.id.spinner_breed_selection)

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, breedNames)
        spinner!!.adapter = arrayAdapter
        spinner.setSelection(breedNames.size-1)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                viewModel.filterFksAll.value = map[spinner.selectedItem]
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                // not needed in our scenario
            }
        }

        // Last minute fix for screen orientation cache memory limitation from 3rd party library
        if(orientation == 1){
            //portrait
            setPortraitConstraints(recyclerView)
        }else{
            //landscape
            setLandscapeConstraints(recyclerView)

        }
    }

    // Last minute fix for screen orientation cache memory limitation from 3rd party library
    private fun setPortraitConstraints(recyclerView: RecyclerView){
        val outMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(outMetrics)
        recyclerView.layoutParams.height = outMetrics.heightPixels - 150
        recyclerView.layoutParams.width = outMetrics.widthPixels
        val constraintLayout = findViewById<ConstraintLayout>(R.id.parent_layout)
        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)
        constraintSet.connect(
            R.id.recycler_view,
            ConstraintSet.TOP,
            R.id.child_layout,
            ConstraintSet.BOTTOM,
            8
        )
        constraintSet.connect(
            R.id.recycler_view,
            ConstraintSet.BOTTOM,
            R.id.parent_layout,
            ConstraintSet.BOTTOM,
            0
        )
        constraintSet.connect(
            R.id.child_layout,
            ConstraintSet.BOTTOM,
            R.id.recycler_view,
            ConstraintSet.TOP,
            0
        )

        constraintSet.connect(
            R.id.child_layout,
            ConstraintSet.TOP,
            R.id.parent_layout,
            ConstraintSet.TOP,
            0
        )
        constraintSet.applyTo(constraintLayout)

    }

    // Last minute fix for screen orientation cache memory limitation from 3rd party library
    private fun setLandscapeConstraints(recyclerView: RecyclerView) {
        val outMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(outMetrics)
        recyclerView.layoutParams.height = outMetrics.heightPixels - 100
        recyclerView.layoutParams.width = outMetrics.heightPixels
        val constraintLayout = findViewById<ConstraintLayout>(R.id.parent_layout)
        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)
        constraintSet.connect(
            R.id.filter_text,
            ConstraintSet.RIGHT,
            R.id.spinner_breed_selection,
            ConstraintSet.RIGHT,
            0
        )
        constraintSet.connect(
            R.id.filter_text,
            ConstraintSet.BOTTOM,
            R.id.recycler_view,
            ConstraintSet.TOP,
            0
        )
        constraintSet.connect(
            R.id.spinner_breed_selection,
            ConstraintSet.BOTTOM,
            R.id.recycler_view,
            ConstraintSet.TOP,
            0
        )
        constraintSet.connect(
            R.id.recycler_view,
            ConstraintSet.TOP,
            R.id.filter_text,
            ConstraintSet.BOTTOM,
            0
        )
        constraintSet.connect(
            R.id.recycler_view,
            ConstraintSet.TOP,
            R.id.spinner_breed_selection,
            ConstraintSet.BOTTOM,
            0
        )

        constraintSet.connect(
            R.id.recycler_view,
            ConstraintSet.BOTTOM,
            R.id.parent_layout,
            ConstraintSet.BOTTOM,
            0
        )
        constraintSet.centerVertically( R.id.recycler_view,  R.id.spinner_breed_selection, )
        constraintSet.centerVertically( R.id.recycler_view,  R.id.filter_text, )
        constraintSet.applyTo(constraintLayout)
    }

    // Last minute fix for screen orientation cache memory limitation from 3rd party library
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val orientation = newConfig.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT){
            initUi(1)
        }else if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            initUi(2)
        }
    }
}
