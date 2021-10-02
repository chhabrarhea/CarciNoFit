package com.example.carcinofit.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.carcinofit.R
import com.example.carcinofit.databinding.FragmentInfoBinding
import com.example.carcinofit.ui.viewmodels.InfoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoFragment : Fragment(), SeekBar.OnSeekBarChangeListener {

    private val binding: FragmentInfoBinding by lazy {
        FragmentInfoBinding.inflate(layoutInflater, null, false)
    }
    private val viewModel: InfoViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewModel = this@InfoFragment.viewModel
            lifecycleOwner = this@InfoFragment
            heightSeekBar.setOnSeekBarChangeListener(this@InfoFragment)
            heightSeekBar.progress = viewModel.getProgressBarProgress()
            saveBtn.setOnClickListener { viewModel.saveData() }
            maleCard.setOnClickListener { toggleGender(0) }
            femaleCard.setOnClickListener { toggleGender(1) }
            setGender()
        }
    }

    private fun toggleGender(i: Int) {
        val card = binding.genderRoot.getChildAt(viewModel.gender) as CardView
        card.setCardBackgroundColor(Color.WHITE)
        val card1 = binding.genderRoot.getChildAt(i) as CardView
        card1.setCardBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.secondaryLightColor
            )
        )
        viewModel.gender = i
    }

    private fun setGender() {
        val card = binding.genderRoot.getChildAt(viewModel.gender) as CardView
        card.setCardBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.secondaryLightColor
            )
        )
    }

    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
        viewModel.progressHeight(p1)
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {}
    override fun onStopTrackingTouch(p0: SeekBar?) {}
}