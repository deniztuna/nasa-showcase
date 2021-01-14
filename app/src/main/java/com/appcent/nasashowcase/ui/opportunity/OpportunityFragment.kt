package com.appcent.nasashowcase.ui.opportunity

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.appcent.nasashowcase.R
import com.appcent.nasashowcase.databinding.FragmentOpportunityBinding
import com.appcent.nasashowcase.manager.NasaFirebaseEventManager
import com.appcent.nasashowcase.ui.adapter.RoverPhotoAdapter
import com.appcent.nasashowcase.util.Util
import com.google.android.material.transition.MaterialElevationScale
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class OpportunityFragment : Fragment() {

    private lateinit var opportunityViewModel: OpportunityViewModel
    private lateinit var viewDataBinding: FragmentOpportunityBinding

    private lateinit var roverPhotoAdapter: RoverPhotoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        opportunityViewModel =
            ViewModelProvider(this).get(OpportunityViewModel::class.java)
        viewDataBinding = FragmentOpportunityBinding.inflate(inflater, container, false).apply {
            viewModel = opportunityViewModel
        }
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner

        opportunityViewModel.loadingState.observe(viewLifecycleOwner, Observer {
            if (it) roverPhotoAdapter.dataStartedLoading()
            else roverPhotoAdapter.dataFinishedLoading()
        })
        setupAdapter()
        setupRecyclerView()
        setHasOptionsMenu(true)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.menu_filter -> {
                showFilteringPopUpMenu()
                true
            }
            else -> false
        }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_curiosity_filter, menu)
    }

    private fun setupRecyclerView() {
        viewDataBinding.recyclerViewOpportunityPhotos.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE && Util.isOnline(requireActivity())) {
                    opportunityViewModel.loadRoverPhotos()
                }
            }
        })
    }

    private fun showFilteringPopUpMenu() {
        val view = activity?.findViewById<View>(R.id.menu_filter) ?: return
        PopupMenu(requireContext(), view).run {
            menuInflater.inflate(R.menu.filter_opportunity, menu)

            setOnMenuItemClickListener {
                Timber.e("DT:: filter with camera to list")
                opportunityViewModel.resetPagination()
                it.title?.let { title ->
                    opportunityViewModel.cameraName = if (title == "ALL") null else title.toString()
                    opportunityViewModel.loadRoverPhotos()
                }
                true
            }
            show()
        }
    }

    private fun setupAdapter() {
        val viewModel = viewDataBinding.viewModel
        if (viewModel != null) {
            roverPhotoAdapter = RoverPhotoAdapter()
            viewDataBinding.recyclerViewOpportunityPhotos.adapter = roverPhotoAdapter
            roverPhotoAdapter.onRoverPhotoSelected = { photoModel, root ->

                NasaFirebaseEventManager(requireActivity()).logExampleEvent(
                    photoModel.rover.roverName,
                    photoModel.roverCamera.fullCameraName,
                    photoModel.photoId
                )

                exitTransition = MaterialElevationScale(false).apply {
                    duration = 500
                }
                reenterTransition = MaterialElevationScale(true).apply {
                    duration = 500
                }
                val extras = FragmentNavigatorExtras(
                    root to getString(R.string.action_card_transition)
                )
                val action = OpportunityFragmentDirections
                    .actionOpportunityFragmentToDetailFragment(
                        photoModel.imgSource,
                        photoModel.solToEarthDate,
                        photoModel.rover.roverName,
                        photoModel.roverCamera.fullCameraName,
                        photoModel.rover.status,
                        photoModel.rover.launchDate,
                        photoModel.rover.landingDate
                    )
                findNavController().navigate(action, extras)
            }
        }
    }
}