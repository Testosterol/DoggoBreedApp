package com.testosterolapp.dogbreeds.breeds

import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.testosterolapp.dogbreeds.R
import com.testosterolapp.dogbreeds.database.DaoRepository
import com.testosterolapp.dogbreeds.util.LinearLayoutManagerWrapper

class BreedPicturesActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_breed_pictures)

        val progressBar = findViewById<ProgressBar>(R.id.progressBar1)
        val progressBarText = findViewById<TextView>(R.id.textView_progress_bar)

        val breedFk = intent.getIntExtra(EXTRA_MESSAGE, 0)

        val viewModel = ViewModelProvider(this).get(BreedPicturesViewModel::class.java)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManagerWrapper(this)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.setHasFixedSize(true)
        val adapter = BreedPicturesAdapter(this)
        recyclerView.adapter = adapter

        val repositoryDao = DaoRepository(this)
        viewModel.init(repositoryDao, breedFk)

        viewModel.allBreedPictures!!.observe(this) { breedPictures ->
            if (breedPictures.isNotEmpty()) {
                progressBar.visibility = View.GONE
                progressBarText.visibility = View.GONE
                adapter.submitList(breedPictures)
            }else{
                progressBar.visibility = View.VISIBLE
                progressBarText.visibility = View.VISIBLE
            }
        }
    }
}
