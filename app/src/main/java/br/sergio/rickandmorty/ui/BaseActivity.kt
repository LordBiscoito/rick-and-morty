package br.sergio.rickandmorty.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import br.sergio.rickandmorty.APIUtils
import br.sergio.rickandmorty.R
import br.sergio.rickandmorty.view_models.BaseViewModel
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog

abstract class BaseActivity : AppCompatActivity() {
    private lateinit var dialogProgress: SweetAlertDialog

    var isHomeActivity: Boolean = false

    open fun populateData() {}
    open fun setActions() {}

    protected fun init() {
        populateData()
        setActions()
    }

    protected fun addAPIObservables(viewModel: BaseViewModel) {
        viewModel.onError.observe(this, Observer {
            APIUtils.errorResponse(this, it!!)
        })

        viewModel.isLoading.observe(this, Observer {
            if (it!!) {
                showProgress()
            } else {
                hideProgress()
            }
        })
    }

    //region progress
    private fun initDialogProgress() {
        dialogProgress = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)

        dialogProgress.getProgressHelper().setBarColor(ContextCompat.getColor(this, R.color.progress_color))
        dialogProgress.setTitleText(getString(R.string.loading_text))
        dialogProgress.setCancelable(false)
    }

    protected fun showProgress() {
        if (!::dialogProgress.isInitialized) initDialogProgress()

        if (!dialogProgress.isShowing()) dialogProgress.show()
    }

    protected fun hideProgress() {
        if (!::dialogProgress.isInitialized) initDialogProgress()
        if (dialogProgress.isShowing()) dialogProgress.dismiss()
    }

    override fun onBackPressed() {
        if (!isHomeActivity) {
            super.onBackPressed()
            overridePendingTransition(R.anim.left_in, R.anim.right_out)
        }
    }
    //endregion
}