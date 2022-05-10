package com.example.movieapplication.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.movieapplication.R
import com.example.movieapplication.model.APIResponse
import com.example.movieapplication.model.Movie
import com.example.movieapplication.model.Status
import com.example.movieapplication.utils.MovieListUtils
import com.example.movieapplication.viewmodel.MoviesViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {
    private val moviesViewModel: MoviesViewModel by activityViewModels()
    private lateinit var adapter: MovieAdapter
    private val disposable = CompositeDisposable()
    private lateinit var movieLists: List<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        movieLists = MovieListUtils.getMovieList(requireContext())

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MovieAdapter(requireContext(), listOf())
        disposable.add(
            adapter.clickEvent.subscribe { movie ->
                moviesViewModel.setCurrentMovie(movie)
            }
        )
        moviesRecycleView.adapter = adapter


        swipeRefreshLayout.setOnRefreshListener {
            getMoviesBySpinnerPosition(movieListSpinner.selectedItemPosition, true)
        }

        val adapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_item, movieLists)
        movieListSpinner.adapter = adapter

        movieListSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                getMoviesBySpinnerPosition(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        moviesViewModel.movies.observe(viewLifecycleOwner, Observer { response ->
            swipeRefreshLayout.isRefreshing = false
            if (response.status == Status.SUCCESS) {
                response.data?.let {
                    updateMovieList(it)
                }
            }
        })
    }

    private fun getMoviesBySpinnerPosition(movieListPosition: Int, isRefresh: Boolean = false) {
        var list = MovieListUtils.MovieList.topRated
        if (movieLists[movieListPosition] == getString(R.string.Most_Popular)) {
            list = MovieListUtils.MovieList.mostPopular
        }

        if (isRefresh) {
            moviesViewModel.refresh(list)
        }
        else {
            moviesViewModel.getMovies(list)
        }

    }

    private fun updateMovieList(movies: List<Movie>) {
        adapter.update(movies)

        adapter.notifyDataSetChanged()
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }

    override fun onDestroy() {
        super.onDestroy()

        disposable.clear()
    }
}