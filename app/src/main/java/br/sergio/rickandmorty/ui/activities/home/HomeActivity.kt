package br.sergio.rickandmorty.ui.activities.home

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import br.sergio.rickandmorty.R
import br.sergio.rickandmorty.Utils
import br.sergio.rickandmorty.ui.BaseActivity
import br.sergio.rickandmorty.ui.activities.characters.ui.CharactersListActivity
import br.sergio.rickandmorty.ui.activities.characters.ui.view_model.CharactersListViewModel
import br.sergio.rickandmorty.ui.activities.home.view_model.HomeViewModel
import br.sergio.rickandmorty.ui.activities.home.view_model.HomeViewModelFactory
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

class HomeActivity : BaseActivity() {
    @Inject
    lateinit var  homeViewModelFactory: HomeViewModelFactory

    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProviders.of(this, homeViewModelFactory)
            .get(HomeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        init()
        isHomeActivity = true
    }

    override fun setActions() {
        characters_button.setOnClickListener {
            Utils.sendToActivity(this, CharactersListActivity::class.java)
        }
    }
}
