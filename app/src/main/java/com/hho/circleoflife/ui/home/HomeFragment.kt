package com.hho.circleoflife.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.charts.RadarChart
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.hho.circleoflife.databinding.FragmentHomeBinding
import com.hho.circleoflife.models.request.CircleRequestModel
import com.hho.circleoflife.repository.CircleRepository
import java.util.logging.Logger


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var viewModel: HomeViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var radarChart:RadarChart;

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val log = Logger.getLogger(HomeFragment::class.java.name)

        val factory = HomeViewModel.Factory(CircleRepository())
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

        viewModel.newData.observe(viewLifecycleOwner) {
            it?.let { res ->
                if (res.isLoading) {
                    binding.textMessage.text = "Loading data from server"
                } else {
                    binding.textMessage.text = "Successfully loaded data"

                    if (!res.message.isNullOrEmpty()){
                        log.warning(res.message)
                    }

                    radarChart = binding.radarChart
                    val sectors = res.sectors
                    if (sectors != null) {

                        val labels: MutableList<String> = mutableListOf()
                        val list: ArrayList<RadarEntry> = ArrayList()

                        for (sector in sectors.iterator()) {
                            labels.add(sector.name)
                            list.add(RadarEntry(sector.value))
                        }

                        val radarDataSet = RadarDataSet(list, "List")
                        val xAxis = radarChart.xAxis
                        xAxis.valueFormatter = IndexAxisValueFormatter(labels);

                        val radarData = RadarData()
                        radarData.addDataSet(radarDataSet)

                        radarChart.data = radarData
                    }
                }
            }
        }

        binding.buttonUpdate.setOnClickListener{
            val username = binding.editTextUsername.text
            if (username.isNullOrEmpty()) {
                binding.textMessage.text = "Username field must be filled!"
            } else {
                val circleRequestData = CircleRequestModel(username = username.toString())
                viewModel.getCircle(circleRequestData)
            }

        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}