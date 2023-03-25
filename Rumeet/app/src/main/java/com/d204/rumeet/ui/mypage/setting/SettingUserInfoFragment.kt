package com.d204.rumeet.ui.mypage.setting

import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.d204.rumeet.R
import com.d204.rumeet.databinding.FragmentSettingUserInfoBinding
import com.d204.rumeet.ui.base.BaseFragment
import com.d204.rumeet.ui.base.BaseViewModel
import com.d204.rumeet.ui.mypage.MypageViewModel
import com.d204.rumeet.ui.mypage.adapter.SettingItemListAdapter
import com.d204.rumeet.ui.mypage.model.SettingOptionUiMdel

class SettingUserInfoFragment : BaseFragment<FragmentSettingUserInfoBinding, BaseViewModel>() {
    private val mypageViewModel by navGraphViewModels<MypageViewModel>(R.id.navigation_mypage)

    override val layoutResourceId: Int
        get() = R.layout.fragment_setting_user_info
    override val viewModel: BaseViewModel
        get() = mypageViewModel

    override fun initStartView() {
        initView()
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }

    private fun initView() {
        //TODO(임시 데이터)
        val userInfo = listOf<String>(
            "ssafy@naver.com",
            "김싸피피피피피피",
            "23",
            "남",
            "183cm/15kg"
        )

        val settingOptionList = resources.getStringArray(R.array.title_user_info_content).toList()
            .mapIndexed { index, title ->
                SettingOptionUiMdel(title, userInfo[index])
            }

        val userInfoAdapter = SettingItemListAdapter().apply {
            submitList(settingOptionList)
        }

        with(binding.rvSettingUserInfo){
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = userInfoAdapter
        }
    }

}