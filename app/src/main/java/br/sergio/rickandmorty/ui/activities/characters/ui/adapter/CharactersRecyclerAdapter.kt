package br.sergio.rickandmorty.ui.activities.characters.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import br.sergio.rickandmorty.R
import br.sergio.rickandmorty.api.models.CharacterModel
import br.sergio.rickandmorty.ui.activities.characters.ui.view_model.CharactersListViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_characters_list.view.*

class CharactersRecyclerAdapter(
    val items: ArrayList<CharacterModel>,
    val context: Context,
    val charactersListViewModel: CharactersListViewModel
) :
    RecyclerView.Adapter<CharactersRecyclerAdapter.ViewHolder>() {

    var hasStoppedPaging: Boolean = false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_characters_list,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setup(items.get(position), context)

        holder.mainLayout.setOnClickListener(View.OnClickListener {
            //todo click
        })

        if (position == items.size - 1 && !hasStoppedPaging) {
            charactersListViewModel.fetchCharactersByPage()
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mainLayout: ConstraintLayout = itemView.main_layout

        var characterAvatar: ImageView = itemView.character_avatar

        var characterNameText: TextView = itemView.character_name_text
        var characterGenderText: TextView = itemView.character_gender_text
        var characterStatusText: TextView = itemView.character_status_text
        var characterSpecieText: TextView = itemView.character_specie_text

        @SuppressLint("SetTextI18n")
        fun setup(item: CharacterModel, context: Context) {
            Glide.with(context).load(item.image)
                .apply(RequestOptions().placeholder(R.drawable.rick_placeholder))
                .into(characterAvatar)

            characterNameText.text = "${context.getString(R.string.name_text)} ${item.name}"
            characterGenderText.text = "${context.getString(R.string.gender_text)} ${item.gender}"
            characterStatusText.text = "${context.getString(R.string.status_text)} ${item.status}"
            characterSpecieText.text = "${context.getString(R.string.specie_text)} ${item.species}"
        }
    }
}