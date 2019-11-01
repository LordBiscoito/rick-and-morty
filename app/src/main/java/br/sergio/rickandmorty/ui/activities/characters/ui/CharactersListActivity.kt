package br.sergio.rickandmorty.ui.activities.characters.ui

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import br.sergio.rickandmorty.R
import br.sergio.rickandmorty.Utils
import br.sergio.rickandmorty.api.models.CharacterModel
import br.sergio.rickandmorty.app.MyApplication
import br.sergio.rickandmorty.ui.BaseActivity
import br.sergio.rickandmorty.ui.activities.characters.ui.adapter.CharactersRecyclerAdapter
import br.sergio.rickandmorty.ui.activities.characters.ui.view_model.CharactersListViewModel
import kotlinx.android.synthetic.main.activity_character_list.*

class CharactersListActivity : BaseActivity() {
    private var characterList: ArrayList<CharacterModel> = ArrayList()
    private lateinit var adapter: CharactersRecyclerAdapter

    //view model
    var charactersListViewModel: CharactersListViewModel =
        MyApplication.fakeDaggerInjectionForCharactersListViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_list)
        init()
    }

    override fun populateData() {
        setViewModel()
    }

    private fun setViewModel() {
        charactersListViewModel.getCharactersMutable().observe(this, Observer {
            if (it == null) {
                charactersListViewModel.hasStoppedPaging = true

                //prevent crashes
                if (::adapter.isInitialized) {
                    adapter.hasStoppedPaging = true
                }

                Utils.showSimpleMessage(
                    this,
                    getString(R.string.api_error_text),
                    getString(R.string.unexpected_api_error_text)
                )
                return@Observer
            }

            if (!::adapter.isInitialized) {
                characterList = it
                setRecyclerView()
            } else if (it.size == 0) {
                charactersListViewModel.hasStoppedPaging = true
                adapter.hasStoppedPaging = true
            } else {
                characterList.addAll(it)
                adapter.notifyDataSetChanged()
            }
        })

        addAPIObservables(charactersListViewModel)
        charactersListViewModel.fetchCharactersByPage()
    }

    private fun setRecyclerView() {
        adapter = CharactersRecyclerAdapter(characterList, this, charactersListViewModel)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapter
    }
}