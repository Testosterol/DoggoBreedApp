package com.testosterolapp.dogbreeds.breeds

import android.content.res.Configuration
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.DisplayMetrics
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
        initUi(resources.configuration.orientation)
    }

    private fun initUi(orientation: Int){

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar1)
        val progressBarText = findViewById<TextView>(R.id.textView_progress_bar)

        val breedFk = intent.getIntExtra(EXTRA_MESSAGE, 0)

        val viewModel = ViewModelProvider(this).get(BreedPicturesViewModel::class.java)

        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManagerWrapper(this)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.setHasFixedSize(false)

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

        // Last minute fix for screen orientation cache memory limitation from 3rd party library
        if(orientation == 1){
            //portrait
            recyclerView.layoutParams.height = RecyclerView.LayoutParams.WRAP_CONTENT
            recyclerView.layoutParams.width = RecyclerView.LayoutParams.MATCH_PARENT
        }else{
            //landscape
            val outMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(outMetrics)
            recyclerView.layoutParams.height = outMetrics.heightPixels
            recyclerView.layoutParams.width = outMetrics.heightPixels
        }
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
