package com.app.movies.ui.movies

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.transition.Fade
import android.util.Log
import android.util.TimeUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.movies.R
import com.app.movies.base.BaseActivity
import com.app.movies.dpToPx
import com.app.movies.networking.Resource
import com.app.movies.utils.SimpleItemDecoration
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.reactivestreams.Publisher
import java.util.concurrent.TimeUnit


class MainActivity : BaseActivity() {

    private val viewModel: MoviesViewModel by viewModel()
    private val adapter = MoviesAdapter(R.layout.movies_list_item)
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var publisherSubject = PublishSubject.create<CharSequence>()
    private var pastVisiblesItems: Int = 0
    private var visibleItemCount: Int = 0
    private var totalItemCount: Int = 0
    private var loading = false
    private var currentPage = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        iniUI();
        setListeners()
        setObservables();

        viewModel.fetchPopularMovies(currentPage)

    }

    private fun setObservables() {
        viewModel.moviesLiveData.observe(this, Observer {
            loading = false
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    adapter.addMovies(it.data!!.results!!)
                }
            }
        })
    }

    private fun iniUI() {
        linearLayoutManager = movies_recyclerview.layoutManager as LinearLayoutManager
        movies_recyclerview.layoutManager = linearLayoutManager
        movies_recyclerview.addItemDecoration(SimpleItemDecoration())
        movies_recyclerview.adapter = adapter
    }

    private fun setListeners() {

        input.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                publisherSubject.onNext(p0 ?:"")
            }
        })


        movies_recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    visibleItemCount = linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();

                    if (!loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = true;
                            currentPage++
                            viewModel.fetchPopularMovies(currentPage)
                        }
                    }
                }
            }
        })

        search_icon.setOnClickListener {
            showSearchWithAnimation()
            openKeyboard()
        }

        publisherSubject
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .debounce(1,TimeUnit.SECONDS)
            .map {
                return@map it.toString()
            }.subscribe {

            }

    }

    fun showSearchWithAnimation(){
        val moveSearchIconX = ObjectAnimator.ofFloat(search_icon,View.X,input.x + 10.dpToPx)
        val scaleInputX = ObjectAnimator.ofFloat(input,View.SCALE_X,0f,1f)
        val scaleInputY = ObjectAnimator.ofFloat(input,View.SCALE_Y,0f,1f)
        val inputAlpha = ObjectAnimator.ofFloat(input,View.ALPHA,1f)

        val animatorSet = AnimatorSet().apply {
            duration = 500
            addListener(object : Animator.AnimatorListener{
                override fun onAnimationRepeat(p0: Animator?) {

                }

                override fun onAnimationEnd(p0: Animator?) {

                }

                override fun onAnimationCancel(p0: Animator?) {

                }

                override fun onAnimationStart(p0: Animator?) {
                   input.visibility = View.VISIBLE
                }
            })
            playTogether(moveSearchIconX,scaleInputX,scaleInputY,inputAlpha)
        }
        animatorSet.start()


    }

    fun openKeyboard(){
        input.requestFocus()
        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT);
    }

}
