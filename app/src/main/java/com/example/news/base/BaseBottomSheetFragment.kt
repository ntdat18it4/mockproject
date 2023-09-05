package com.example.news.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetFragment<VB : ViewBinding>(
    private val bindingInflater: (inflater: LayoutInflater) -> VB,
) : BottomSheetDialogFragment() {

    private var _binding: VB? = null
    val binding
        get() = _binding as VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = bindingInflater.invoke(inflater)
        if (_binding == null)
            throw IllegalArgumentException("Binding can not be null")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            obServerLivedata()
            initData()
            initView()
    }

    open fun obServerLivedata() {}

    open fun initData() {}

    open fun initView() {}

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}