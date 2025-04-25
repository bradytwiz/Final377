import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalxml.R

class FavoritesFragment : Fragment() {

    private lateinit var favoritesRecyclerView: RecyclerView
    private lateinit var emptyFavoritesTextView: TextView
    private lateinit var favoritesList: MutableList<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)

        favoritesRecyclerView = view.findViewById(R.id.favoritesRecyclerView)
        emptyFavoritesTextView = view.findViewById(R.id.emptyFavoritesTextView)

        favoritesRecyclerView.layoutManager = LinearLayoutManager(context)

        loadFavorites()

        val adapter = FavoritesAdapter(favoritesList)
        favoritesRecyclerView.adapter = adapter

        if (favoritesList.isEmpty()) {
            favoritesRecyclerView.visibility = View.GONE
            emptyFavoritesTextView.visibility = View.VISIBLE
        } else {
            favoritesRecyclerView.visibility = View.VISIBLE
            emptyFavoritesTextView.visibility = View.GONE
        }

        return view
    }

    private fun loadFavorites() {
        favoritesList = mutableListOf()
        favoritesList.add("The funny [adjective] story...")
        favoritesList.add("A [noun] went to the zoo...")
    }

    private class FavoritesAdapter(private val favoritesList: List<String>) :
        RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

        class FavoritesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val storyTextView: TextView = itemView.findViewById(android.R.id.text1)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
            return FavoritesViewHolder(view)
        }

        override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
            holder.storyTextView.text = favoritesList[position]
        }

        override fun getItemCount(): Int {
            return favoritesList.size
        }
    }
}