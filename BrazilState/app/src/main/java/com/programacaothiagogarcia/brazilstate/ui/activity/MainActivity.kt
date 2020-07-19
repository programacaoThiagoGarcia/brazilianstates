package com.programacaothiagogarcia.brazilstate.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.programacaothiagogarcia.brazilstate.R
import com.programacaothiagogarcia.brazilstate.ui.fragment.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }
}