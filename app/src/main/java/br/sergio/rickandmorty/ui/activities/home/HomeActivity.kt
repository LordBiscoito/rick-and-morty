package br.sergio.rickandmorty.ui.activities.home

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.sergio.rickandmorty.R
import br.sergio.rickandmorty.Utils
import br.sergio.rickandmorty.api.models.CharacterModel
import br.sergio.rickandmorty.ui.BaseActivity
import br.sergio.rickandmorty.ui.activities.characters.ui.CharactersListActivity
import br.sergio.rickandmorty.ui.activities.home.view_model.HomeViewModel
import br.sergio.rickandmorty.ui.activities.home.view_model.HomeViewModelFactory
import br.sergio.rickandmorty.ui.activities.samples.SimpleEspressoIdlingResourceSampleActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

class HomeActivity : BaseActivity() {
    @Inject
    lateinit var homeViewModelFactory: HomeViewModelFactory

    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProviders.of(this, homeViewModelFactory)
            .get(HomeViewModel::class.java)
    }

    private lateinit var featuredCharacter: CharacterModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        init()
        setFeaturedCharacter()
        isHomeActivity = true
    }

    override fun setActions() {
        characters_button.setOnClickListener {
            Utils.sendToActivity(this, CharactersListActivity::class.java)
        }

        espresso_idling_resource_Button.setOnClickListener {
            Utils.sendToActivity(
                this,
                SimpleEspressoIdlingResourceSampleActivity::class.java
            )
        }
    }

    private fun setFeaturedCharacter() {
        homeViewModel.getFeaturedCharacter()

        homeViewModel.getFeaturedCharacterMutable().observe(this, Observer {
            featuredCharacter = it

            Glide.with(this).load(featuredCharacter.image)
                .apply(RequestOptions().placeholder(R.drawable.rick_placeholder))
                .into(character_avatar)

            featured_text.text = "Featured Character: ${featuredCharacter.name}"
        })
    }
}
