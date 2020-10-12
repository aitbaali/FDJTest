package app.fdj.fdjtest.view.activities.base

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

abstract class BaseActivity: AppCompatActivity() {

    private fun initView(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
    }

    override fun onResume() {
        super.onResume()
        this.getToolbarInstance()?.let { initView(it) }
    }

    abstract fun getToolbarInstance(): Toolbar?
}