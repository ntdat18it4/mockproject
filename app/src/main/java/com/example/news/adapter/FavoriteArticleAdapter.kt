package com.example.news.adapter

import android.view.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.data.database.entities.FavoritesArticlesEntity
import com.example.news.databinding.ItemFavoriteBinding
import com.example.news.models.Articles
import com.example.news.ui.fragment.favorites.FavoriteFragmentDirections
import com.example.news.utils.NewsDiffUtil
import com.example.news.viewmodel.MainViewModel
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar

class FavoriteArticleAdapter(
    private val requireActivity: FragmentActivity,
    private var mainViewModel: MainViewModel
) :
    RecyclerView.Adapter<FavoriteArticleAdapter.MyViewHolder>(), ActionMode.Callback {

    private var multiSelection = false
    private lateinit var rootView: View

    private var selectedArticle = arrayListOf<FavoritesArticlesEntity>()
    private var myViewHolders = arrayListOf<MyViewHolder>()
    private var favoriteRecipes = emptyList<FavoritesArticlesEntity>()

    class MyViewHolder(private val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favoritesEntity: FavoritesArticlesEntity) {
            binding.articleFavorite = favoritesEntity
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemFavoriteBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        myViewHolders.add(holder)
        rootView = holder.itemView.rootView

        val currentArticle = favoriteRecipes[position]
        holder.bind(currentArticle)

        /**
         * Single Click Listener
         * */

        holder.itemView.findViewById<ConstraintLayout>(R.id.layoutNews).setOnClickListener {
            if (multiSelection) {
                applySelection(holder, currentArticle)
            } else {
                val articles = Articles(
                    source = null,
                    title = currentArticle.title,
                    description = null,
                    urlToImage = currentArticle.urlToImage,
                    publishedAt = currentArticle.publishedAt,
                    url = currentArticle.url
                )
                val action =
                    FavoriteFragmentDirections.actionFavoriteFragmentToDetailsActivity(
                        articles, "Detail"
                    )
                holder.itemView.findNavController().navigate(action)
            }

        }

        /**
         * Long Click Listener
         * */
        holder.itemView.findViewById<ConstraintLayout>(R.id.layoutNews).setOnLongClickListener {
            if (!multiSelection) {
                multiSelection = true
                applySelection(holder, currentArticle)
                requireActivity.startActionMode(this)
                true
            } else {
                multiSelection = false
                false
            }

        }
    }

    private fun applySelection(holder: MyViewHolder, currentAritcle: FavoritesArticlesEntity) {
        if (selectedArticle.contains(currentAritcle)) {
            selectedArticle.remove(currentAritcle)
            changArticleStyle(holder, R.color.white, R.color.strokeColor)
        } else {
            selectedArticle.add(currentAritcle)
            changArticleStyle(holder, R.color.cardBackgroundColor, R.color.colorPrimary)
        }
    }

    private fun changArticleStyle(holder: MyViewHolder, backgroundColor: Int, strokeColor: Int) {
        holder.itemView.findViewById<ConstraintLayout>(R.id.layoutNews).setBackgroundColor(
            ContextCompat.getColor(requireActivity, backgroundColor)
        )
        holder.itemView.findViewById<MaterialCardView>(R.id.favorite_row_card_view).strokeColor =
            ContextCompat.getColor(requireActivity, strokeColor)
    }


    override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        actionMode?.menuInflater?.inflate(R.menu.favorites_contextual_menu, menu)

        applyStatusBarColor(R.color.contextualStatusBarColor)
        return true
    }

    override fun onPrepareActionMode(p0: ActionMode?, p1: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(actionMode: ActionMode?, menu: MenuItem?): Boolean {
        if (menu?.itemId == R.id.delete_favorite_recipe_menu) {
            selectedArticle.forEach {
                mainViewModel.deleteFavoriteArticle(it)
            }
            showSnackBar("${selectedArticle.size} Article/s removed.")

            multiSelection = false
            selectedArticle.clear()
            actionMode?.finish()
        }
        return true
    }

    override fun onDestroyActionMode(p0: ActionMode?) {
        myViewHolders.forEach { viewHolder ->
            changArticleStyle(viewHolder, R.color.white, R.color.strokeColor)
        }

        multiSelection = false
        selectedArticle.clear()
        applyStatusBarColor(R.color.statusBarColor)
    }

    private fun applyStatusBarColor(color: Int) {
        requireActivity.window.statusBarColor = ContextCompat.getColor(requireActivity, color)
    }

    override fun getItemCount(): Int {
        return favoriteRecipes.size
    }

    fun setData(newFavoriteArticles: List<FavoritesArticlesEntity>) {
        val favoriteRecipesDiffUtil =
            NewsDiffUtil(favoriteRecipes, newFavoriteArticles)
        val diffUtilResult = DiffUtil.calculateDiff(favoriteRecipesDiffUtil)
        favoriteRecipes = newFavoriteArticles
        diffUtilResult.dispatchUpdatesTo(this)
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            rootView,
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("Okay") {}
            .show()
    }
}