package com.testosterolapp.dogbreeds.main

import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.testosterolapp.dogbreeds.R
import com.testosterolapp.dogbreeds.breeds.BreedPicturesActivity
import com.testosterolapp.dogbreeds.data.Breed
import com.testosterolapp.dogbreeds.database.DaoRepository
import com.testosterolapp.dogbreeds.favorites.FavoritesActivity
import com.testosterolapp.dogbreeds.util.LinearLayoutManagerWrapper


class MainActivity : AppCompatActivity(), MainActivityAdapter.MainActivityAdapterOnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar_main))

        val viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManagerWrapper(this)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.setHasFixedSize(true)

        val adapter = MainActivityAdapter(this)
        recyclerView.adapter = adapter

        val repositoryDao = DaoRepository(this)
        viewModel.init(repositoryDao)

        viewModel.allBreeds!!.observe(this) { breeds ->
            if (breeds.isNotEmpty()) {
                adapter.submitList(breeds)
            }
        }
        viewModel.filterBreedAll.value = ""
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_favorite_pics -> {
            val intent = Intent(this, FavoritesActivity::class.java)
            startActivity(intent)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onBreedClickListener(breed: Breed?, v: View?, position: Int) {
        val intent = Intent(this, BreedPicturesActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, breed!!.id)
        }
        startActivity(intent)
    }
}