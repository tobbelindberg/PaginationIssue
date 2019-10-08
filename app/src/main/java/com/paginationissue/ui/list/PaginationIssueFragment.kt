package com.paginationissue.ui.list

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.paginationissue.R
import com.paginationissue.databinding.FragmentPaginationIssueBinding
import com.paginationissue.paging.PaginationScrollListener
import kotlinx.android.synthetic.main.fragment_pagination_issue.*

class PaginationIssueFragment : Fragment() {

    companion object {

        private const val VISIBLE_THRESHOLD = 3

        fun newInstance() = PaginationIssueFragment()
    }

    private lateinit var viewModel: PaginationIssueViewModel

    lateinit var paginationScrollListener: PaginationScrollListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PaginationIssueViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = FragmentPaginationIssueBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.fragment = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        paginationScrollListener = PaginationScrollListener(recycler_view.layoutManager,
                VISIBLE_THRESHOLD) {
            viewModel.onLoadMore()
        }
        paginationScrollListener.setEnabled(true)

        viewModel.initStateObservable()
    }

}
