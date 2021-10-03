package com.example.carcinofit.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    private val vm: InfoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.apply {
            viewModel = vm
            lifecycleOwner = this@InfoFragment
            heightSeekBar.setOnSeekBarChangeListener(this@InfoFragment)
            heightSeekBar.progress = vm.getProgressBarProgress()
            saveBtn.setOnClickListener { vm.saveData() }
            maleCard.setOnClickListener { toggleGender(0) }
            femaleCard.setOnClickListener { toggleGender(1) }
            setGender()
        }
        return binding.root
    }

    private fun toggleGender(i: Int) {
        val card = binding.genderRoot.getChildAt(vm.gender) as CardView
        card.setCardBackgroundColor(Color.WHITE)
        val card1 = binding.genderRoot.getChildAt(i) as CardView
        card1.setCardBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.secondaryLightColor
            )
        )
        vm.gender = i
    }

    private fun setGender() {
        val card = binding.genderRoot.getChildAt(vm.gender) as CardView
        card.setCardBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.secondaryLightColor
            )
        )
    }

    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
        vm.progressHeight(p1)
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {}
    override fun onStopTrackingTouch(p0: SeekBar?) {}
}