package br.sergio.rickandmorty.ui.activities.samples

import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import br.sergio.rickandmorty.R
import br.sergio.rickandmorty.utils.EspressoIdlingResource
import kotlinx.android.synthetic.main.activity_simple_espresso_idling_resource_sample.*

class SimpleEspressoIdlingResourceSampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_espresso_idling_resource_sample)

        EspressoIdlingResource.increment()

        //fake delay
        Handler().postDelayed(Runnable {
            idleText.text = getString(R.string.text_was_changed)
            EspressoIdlingResource.decrement()
        }, 1000)
    }
}