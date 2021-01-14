package com.appcent.nasashowcase.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.appcent.nasashowcase.R
import com.appcent.nasashowcase.databinding.FragmentDetailBinding
import com.appcent.nasashowcase.util.GlideApp
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.transition.MaterialContainerTransform

class DetailFragment: Fragment() {
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var viewDataBinding: FragmentDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_fragment
            duration = 500
            //setAllContainerColors(Color.TRANSPARENT)
        }

        /*val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val controller = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                controller.popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)*/
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.findViewById<ImageView>(R.id.imageViewRoverPhoto).apply {
            GlideApp.with(this)
                .load(args.nasaPhotoUrl)
                .error(R.drawable.placeholder)
                .apply(RequestOptions.centerCropTransform())
                .into(this)
        }
        view.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_detail, container, false)
        viewDataBinding = FragmentDetailBinding.bind(root).apply {
            this.nasaPhotoUrl = args.nasaPhotoUrl
            this.photoTakenDate = args.photoTakenDate
            this.roverName = args.roverName
            this.roverCamera = args.roverCamera
            this.roverStatus = args.roverStatus
            this.roverLaunchDate = args.roverLaunchDate
            this.roverLandingDate = args.roverLandingDate
        }
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        return viewDataBinding.root
    }
}