package br.sergio.rickandmorty.ui.activities.home

import android.os.Bundle
import br.sergio.rickandmorty.R
import br.sergio.rickandmorty.Utils
import br.sergio.rickandmorty.ui.BaseActivity
import br.sergio.rickandmorty.ui.activities.characters.ui.CharactersListActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        init()
    }

    override fun setActions() {
        characters_button.setOnClickListener {
            Utils.sendToActivity(this, CharactersListActivity::class.java)
        }
    }
}
