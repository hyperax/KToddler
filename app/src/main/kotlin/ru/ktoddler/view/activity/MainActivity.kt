package ru.ktoddler.view.activity

import android.os.Bundle
import ru.ktoddler.R
import ru.ktoddler.databinding.ActMainBinding
import ru.ktoddler.presenter.KToddlerPresenter
import ru.ktoddler.view.KToddlerView
import ru.ktoddler.view.model.KToddlerVM
import java.util.*
import javax.inject.Inject

open class MainActivity : PresenterActivity<KToddlerPresenter, KToddlerView>(), KToddlerView {
    @Inject
    lateinit var pres: KToddlerPresenter

    lateinit var binding: ActMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        provideAppComponent().inject(this)
        binding = bindView(R.layout.act_main)
        binding.loadDistance.setOnClickListener({ view -> getPresenter().loadDistance() })
        val dP = binding.datePicker
        binding.getTemperature.setOnClickListener({ view ->
            getPresenter().loadWeatherHistory(dP.year, dP.month, dP.dayOfMonth) })
        dP.maxDate = Date().time
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setTitle(R.string.app_name)
    }

    override fun getPresenter(): KToddlerPresenter {
        return pres
    }

    override fun showToddler(toddlerVM: KToddlerVM) {
        binding.toddler = toddlerVM
    }

    override fun fetchView(): KToddlerView {
        return this
    }
}
