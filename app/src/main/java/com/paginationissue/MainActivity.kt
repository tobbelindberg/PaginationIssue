package com.paginationissue

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.paginationissue.ui.list.PaginationIssueFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, PaginationIssueFragment.newInstance())
                    .commitNow()
        }
    }

}
