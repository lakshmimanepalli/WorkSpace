package ${packageName}

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.${tabFragmentLayout}.*

class ${tabFragmentClass}: Fragment(){

    private lateinit var mAdapter: ${itemAdapterClass}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.${tabFragmentLayout}, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        swipe.setOnRefreshListener { createDummyData() }
    }

    private fun setupAdapter() {
        mAdapter = ${itemAdapterClass}()
        recycler.apply {
            adapter = mAdapter
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onStart() {
        super.onStart()
        createDummyData()
    }
    
    fun createDummyData() {
        val data: ArrayList<MediumAdapter.DummyData> = ArrayList()
        for (i in 0 until 10) {
            data.add(MediumAdapter.DummyData("Lorem ipsum dolor sit amet."))
        }
        mAdapter.setRecyclerData(data)
        swipe.isRefreshing = false
    }
}
