package com.testosterolapp.dogbreeds.favorites

import android.os.Bundle
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

class FavoritesActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

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

    }
}
