package com.example.news.ui.fragment.setting

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.news.base.BaseFragment
import com.example.news.databinding.FragmentSettingsBinding
import com.example.news.viewmodel.MainViewModel

class SettingFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun initView() {
        composeEmail()
        callPhone()

        binding.tvBM.setOnClickListener {
            val action = SettingFragmentDirections.actionSettingFragmentToFavoriteFragment()
            findNavController().navigate(action)
        }

        binding.tvLocation.setOnClickListener {
            val action = SettingFragmentDirections.actionSettingFragmentToProvinceBottomSheet()
            this@SettingFragment.findNavController().navigate(action)
        }

        binding.tvLocation.text = mainViewModel.getProvince()
    }

    override fun obServerLivedata() {
//        mainViewModel.province.observe(viewLifecycleOwner) {
//            if (it == "Thành Phố Hồ Chí Minh") {
//                binding.tvLocation.text = "Hồ Chí Minh"
//            } else {
//                binding.tvLocation.text = it
//            }
//        }
        mainViewModel.isChangeProvince.observe(viewLifecycleOwner){
            if(it == true){
                binding.tvLocation.text = mainViewModel.getProvince()
            }
        }
    }

        private fun composeEmail() {
            binding.icRate.setOnClickListener {
                val email = "dat29022000@gmail.com"
                val subject = "Testing app"
                val message = "Testing"
                val address = email.split(",".toRegex()).toTypedArray()

                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:")
                    putExtra(Intent.EXTRA_EMAIL, address)
                    putExtra(Intent.EXTRA_SUBJECT, subject)
                    putExtra(Intent.EXTRA_TEXT, message)

                }
                startActivity(intent)

            }
        }

        private fun callPhone() {
            binding.icContact.setOnClickListener {
                val phoneNumber = "0348887357"
                val callIntent = Intent(Intent.ACTION_DIAL)
                callIntent.data = Uri.parse("tel:$phoneNumber")
                startActivity(callIntent)
            }
        }

    }